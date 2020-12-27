package introduce.service;

import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import introduce.domain.network.Header;
import introduce.domain.network.Pagination;
import introduce.domain.project.Project;
import introduce.domain.skill.Skill;
import introduce.utill.FileUtil;
import introduce.web.dto.member.MemberRequestDto;
import introduce.web.dto.member.MemberResponseDto;
import introduce.web.dto.membertotalinfo.MemberTotalInfoResponseDto;
import introduce.web.dto.project.ProjectResponseDto;
import introduce.web.dto.skill.SkillResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService extends BaseService<MemberRequestDto, MemberResponseDto, MemberRepository> {

    @Value("${file.upload-dir}")
    private String fileUploadPath;

    @Value("${file.member-dir}")
    private String subFileUploadPath;

    private final SkillService skillService;

    private final ProjectService projectService;

    @Override
    @Transactional
    public Header<MemberResponseDto> save(MemberRequestDto requestDto, MultipartFile file) {
        log.info("member save start");

        // [1] file parameter setting
        String originalName = FileUtil.cutFileName(file.getOriginalFilename(), 100);
        String saveName = FileUtil.getRandomFileName(originalName);
        String saveDir = fileUploadPath + "/" + subFileUploadPath;
        String savePath =  saveDir +"/"+ saveName;
        log.info("[1] file parameter setting");

        // [2] file 디렉토리 생성
        FileUtil.createDir(saveDir);
        log.info("[2] file 디렉토리 생성");

        // [3] member info DB 등록
        requestDto.settingFileInfo(savePath, originalName);
        Member member = baseRepository.save(requestDto.toEntity());
        log.info("[3] member info DB 등록");

        // [4] file transfer
        try {
            file.transferTo(new File(savePath));
            log.info("[4] file transfer");

        } catch (IOException e) {
            log.info("[4] file transfer fail");
            e.printStackTrace();
            return Header.ERROR("파일 변환 실패");
        }

        log.info("member save end");
        return Header.OK(response(member));
    }
    @Override
    @Transactional
    public Header update(MemberRequestDto requestDto, Long id, MultipartFile file) {
        log.info("member update start");
        Optional<Member> optional = baseRepository.findById(id);

        return optional.map(member -> {
            // 첨부된 파일이 없는 경우
            if(file == null || file.isEmpty()) {
                log.info("첨부된 파일 없음");

                // [1] 기존 정보 셋팅
                requestDto.settingFileInfo(member.getFilePath(), member.getFileOriginName());
                log.info("[1] 기존 정보 셋팅");

                // [2] member info DB update
                member.update(requestDto.toEntity());
                log.info("[2] member info DB update");
            }
            // 첨부된 파일이 있는 경우
            else {
                log.info("첨부된 파일 있음");

                // [1] file parameter setting
                String originalName = FileUtil.cutFileName(file.getOriginalFilename(), 100);
                String saveName = FileUtil.getRandomFileName(originalName);
                String saveDir = fileUploadPath + "/" + subFileUploadPath;
                String savePath = saveDir + "/" + saveName;
                String preExistingFilePath = member.getFilePath();
                requestDto.settingFileInfo(savePath, originalName);
                log.info("[1] file parameter setting");

                // [2] file 디렉토리 생성
                FileUtil.createDir(saveDir);
                log.info("[2] file 디렉토리 생성");

                // [3] member info DB update
                member.update(requestDto.toEntity());
                log.info("[3] member info DB update");

                // [4] file transfer
                try {
                    file.transferTo(new File(savePath));
                    log.info("[4] file transfer");

                } catch (IOException e) {
                    log.info("[4] file transfer fail");
                    e.printStackTrace();
                    return Header.ERROR("파일 변환 실패");
                }

                // [5] pre-existing file delete
                FileUtil.deleteFile(preExistingFilePath);
                log.info("[5] pre-existing file delete");

            }

            log.info("member update end");
            return Header.OK(response(member));
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    @Transactional
    public Header delete(Long id) {
        log.info("member delete start");
        Optional<Member> optional = baseRepository.findById(id);

        return optional.map(member -> {
            // [1] member info DB delete
            baseRepository.delete(member);
            log.info("[1] member info DB delete");

            // [2] pre-existing file delete
            FileUtil.deleteFile(member.getFilePath());
            log.info("[2] pre-existing file delete");

            log.info("member delete end");
            return Header.OK();

        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    @Transactional
    public Header<MemberResponseDto> findById(Long id) {
        log.info("member findById start");
        log.info("member findById end");
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Transactional
    public Header<List<MemberResponseDto>> findAll(MemberRequestDto requestDto, Pageable pageable) {
        log.info("member findAll start");
        Page<Member> members = baseRepository.findAll(pageable);

        List<MemberResponseDto> memberResponseDtoList = members.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(members.getTotalPages())
                .totalElements(members.getTotalElements())
                .currentPage(members.getNumber())
                .currentElements(members.getNumberOfElements())
                .build();

        log.info("member findAll end");
        return Header.OK(memberResponseDtoList, pagination);
    }

    @Transactional
    public Header<MemberTotalInfoResponseDto> totalInfo(Long id) {
        log.info("member totalInfo start");

        // [1] MemberResponseDto 조회
        Member member = baseRepository.getOne(id);
        MemberResponseDto memberResponseDto = response(member);
        log.info("[1] MemberResponseDto 조회");

        // [2] skillResponseDtoList 조회
        List<Skill> skillList = member.getSkillList();
        List<SkillResponseDto> skillResponseDtoList = skillList.stream()
                .map(skill -> {
                    return skillService.response(skill);
                })
                .map(response -> Header.OK(response).getData())
                .collect(Collectors.toList());
        log.info("[2] skillResponseDtoList 조회");

        // [3] skillResponseDtoList 조회
        List<Project> projectList = member.getProjectList();
        List<ProjectResponseDto> projectResponseDtoList = projectList.stream()
                .map(project -> {
                    return projectService.response(project);
                })
                .map(response -> Header.OK(response).getData())
                .collect(Collectors.toList());
        log.info("[3] skillResponseDtoList 조회");

        // [4] MemberTotalInfoResponseDto SET
        MemberTotalInfoResponseDto memberTotalInfoResponseDto = MemberTotalInfoResponseDto.builder()
                .memberResponseDto(memberResponseDto)
                .skillResponseDtoList(skillResponseDtoList)
                .projectResponseDtoList(projectResponseDtoList)
                .build();
        log.info("[4] MemberTotalInfoResponseDto SET");

        log.info("member totalInfo end");
        return Header.OK(memberTotalInfoResponseDto);
    }

    @Transactional
    public MemberResponseDto getMember(Long id) {
        Member member = baseRepository.findById(id).get();
        MemberResponseDto dto = new MemberResponseDto(member);
        return dto;
    }

    private MemberResponseDto response(Member member) {
        MemberResponseDto responseDto = MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .comment(member.getComment())
                .filePath(member.getFilePath())
                .fileOriginName(member.getFileOriginName())
                .subIntroduction(member.getSubIntroduction())
                .introduction(member.getIntroduction())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .build();

        return responseDto;
    }
}
