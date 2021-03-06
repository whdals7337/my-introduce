package introduce.service;

import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import introduce.domain.network.Header;
import introduce.domain.network.Pagination;
import introduce.domain.project.Project;
import introduce.domain.skill.Skill;
import introduce.error.exception.file.FileNotTransferException;
import introduce.error.exception.member.MemberNotFoundException;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService extends BaseService<MemberRequestDto, MemberResponseDto, MemberRepository> {

    @Value("${file.upload-dir}")
    private String fileUploadPath;
    @Value("${server-domain}")
    private String domain;
    @Value("${file.images-dir}")
    private String dirType;
    @Value("${file.member-dir}")
    private String subFileUploadPath;

    private final SkillService skillService;

    private final ProjectService projectService;

    @Override
    @Transactional
    public Header<MemberResponseDto> save(MemberRequestDto requestDto, MultipartFile file) throws FileNotTransferException {
        log.info("member save start");

        // [1] file parameter setting
        String originalName = FileUtil.cutFileName(Objects.requireNonNull(file.getOriginalFilename()), 100);
        String saveName = FileUtil.getRandomFileName(originalName);
        String fileUrl = domain + "/" + dirType + "/" + subFileUploadPath + "/" + saveName;
        String saveDir = fileUploadPath + subFileUploadPath;
        String savePath =  saveDir +"/"+ saveName;
        log.info("[1] file parameter setting");

        // [2] file 디렉토리 생성
        FileUtil.createDir(saveDir);
        log.info("[2] file 디렉토리 생성");

        // [3] member info DB 등록
        Member member = baseRepository.save(requestDto.toEntity(savePath, originalName, fileUrl, "N"));
        log.info("[3] member info DB 등록");

        // [4] file transfer
        try {
            file.transferTo(new File(savePath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileNotTransferException();
        }
        log.info("[4] file transfer");


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

                // [1] member info DB update
                member.update(requestDto.toEntity(member.getFileInfo().getFilePath(), member.getFileInfo().getFileOriginName(),
                        member.getFileInfo().getFileUrl(), member.getSelectYN()));
                log.info("[1] member info DB update");
            }
            // 첨부된 파일이 있는 경우
            else {
                log.info("첨부된 파일 있음");

                // [1] file parameter setting
                String originalName = FileUtil.cutFileName(Objects.requireNonNull(file.getOriginalFilename()), 100);
                String saveName = FileUtil.getRandomFileName(originalName);
                String fileUrl = domain + "/" + dirType + "/" + subFileUploadPath + "/" + saveName;
                String saveDir = fileUploadPath + subFileUploadPath;
                String savePath = saveDir + "/" + saveName;
                String preExistingFilePath = member.getFileInfo().getFilePath();
                log.info("[1] file parameter setting");

                // [2] file 디렉토리 생성
                FileUtil.createDir(saveDir);
                log.info("[2] file 디렉토리 생성");

                // [3] member info DB update
                member.update(requestDto.toEntity(savePath, originalName, fileUrl, member.getSelectYN()));
                log.info("[3] member info DB update");

                // [4] file transfer
                try {
                    file.transferTo(new File(savePath));
                } catch (IOException e) {
                    log.info("[4] file transfer fail");
                    e.printStackTrace();
                }
                log.info("[4] file transfer");


                // [5] pre-existing file delete
                FileUtil.deleteFile(preExistingFilePath);
                log.info("[5] pre-existing file delete");
            }

            log.info("member update end");
            return Header.OK(response(member));
        }).orElseThrow(MemberNotFoundException::new);
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
            FileUtil.deleteFile(member.getFileInfo().getFilePath());
            log.info("[2] pre-existing file delete");

            log.info("member delete end");
            return Header.OK();

        }).orElseThrow(MemberNotFoundException::new);
    }

    @Override
    @Transactional
    public Header<MemberResponseDto> findById(Long id) {
        log.info("member findById start");
        log.info("member findById end");
        return baseRepository.findById(id)
                .map((this::response))
                .map(Header::OK)
                .orElseThrow(MemberNotFoundException::new);
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
    public Header<MemberResponseDto> findBySelectYN() {
        log.info("member findBySelectYN start");
        log.info("member findBySelectYN end");
        return baseRepository.findBySelectYN("Y")
                .map(this::response)
                .map(Header::OK)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    public Header<MemberTotalInfoResponseDto> totalInfo(Long id) {
        log.info("member totalInfo start");

        // [1] MemberResponseDto 조회
        Member member = baseRepository.findTotalInfo(id).orElseThrow(MemberNotFoundException::new);
        MemberResponseDto memberResponseDto = response(member);
        log.info("[1] MemberResponseDto 조회");

        // [2] skillResponseDtoList 조회
        List<Skill> skillList = member.getSkillList();
        List<SkillResponseDto> skillResponseDtoList = null;
        if(skillList != null) {
            skillResponseDtoList = skillList.stream()
                    .map(skillService::response)
                    .map(response -> Header.OK(response).getData())
                    .collect(Collectors.toList());
            log.info("[2] skillResponseDtoList 조회");
        }

        // [3] projectResponseDtoList 조회
        List<Project> projectList = member.getProjectList();
        List<ProjectResponseDto> projectResponseDtoList = null;
        if(projectList != null) {
            projectResponseDtoList = projectList.stream()
                    .map(projectService::response)
                    .collect(Collectors.toList());
            log.info("[3] projectResponseDtoList 조회");
        }

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
    public Header<MemberResponseDto> updateSelect(Long id){
        List<Member> memberList = baseRepository.findAll();
        for(Member member : memberList){
            member.unSelect();
        }
        Member member = baseRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        member.select();

        return Header.OK(response(member));
    }

    public Member getMember(Long id) {
        return baseRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    private MemberResponseDto response(Member member) {
        return MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .comment(member.getComment())
                .fileUrl(member.getFileInfo().getFileUrl())
                .fileOriginName(member.getFileInfo().getFileOriginName())
                .subIntroduction(member.getSubIntroduction())
                .introduction(member.getIntroduction())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .selectYN(member.getSelectYN())
                .build();
    }
}
