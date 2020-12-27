package introduce.service;

import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import introduce.domain.network.Header;
import introduce.domain.network.Pagination;
import introduce.domain.skill.Skill;
import introduce.domain.skill.SkillRepository;
import introduce.utill.FileUtil;
import introduce.web.dto.skill.SkillRequestDto;
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
public class SkillService extends BaseService<SkillRequestDto, SkillResponseDto, SkillRepository> {

    @Value("${file.upload-dir}")
    private String fileUploadPath;

    @Value("${file.skill-dir}")
    private String subFileUploadPath;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Header<SkillResponseDto> save(SkillRequestDto requestDto, MultipartFile file) {
        log.info("skill save start");

        // [1] file parameter setting
        String originalName = FileUtil.cutFileName(file.getOriginalFilename(), 100);
        String saveName = FileUtil.getRandomFileName(originalName);
        String saveDir = fileUploadPath + "/" + subFileUploadPath;
        String savePath =  saveDir +"/"+ saveName;
        log.info("[1] file parameter setting");

        // [2] file 디렉토리 생성
        FileUtil.createDir(saveDir);
        log.info("[2] file 디렉토리 생성");

        // [3] project info DB 등록
        requestDto.settingFileInfo(savePath, originalName);
        Skill skill = baseRepository.save(requestDto.toEntity(memberRepository.getOne(requestDto.getMemberId())));
        log.info("[3] skill info DB 등록");

        // [4] file transfer
        try {
            file.transferTo(new File(savePath));
            log.info("[4] file transfer");

        } catch (IOException e) {
            log.info("[4] file transfer fail");
            e.printStackTrace();
            return Header.ERROR("파일 변환 실패");
        }

        log.info("project save end");
        return Header.OK(response(skill));
    }

    @Override
    @Transactional
    public Header update(SkillRequestDto requestDto, Long id, MultipartFile file) {
        log.info("skill update start");
        Optional<Skill> optional = baseRepository.findById(id);

        return optional.map(skill -> {
            // 순서값이 변경 된 경우
            if(skill.getLevel() != requestDto.getLevel()){
                int originLevel = skill.getLevel();
                int changedLevel = requestDto.getLevel();

                // 원래 순서 값이 변경할 순서 값보다 큰 경우
                if (originLevel > changedLevel) {
                    // 원래 값부터 변경할 순서 값보다 작은 순서의 칼럼의 순서값을 1 증가
                    List<Skill> rangeRows = baseRepository.findByLevelBetween(changedLevel, originLevel-1);
                    for(Skill row : rangeRows){
                        row.levelUp();
                    }
                }
                // 원래 순서 값이 변경할 순서 값보다 작은 경우
                else {
                    // 원래 값보다 크고 변경할 순서 값보다 작은 순서의 칼럼의 순서값을 1 감소
                    List<Skill> rangeRows = baseRepository.findByLevelBetween(originLevel+1, changedLevel);
                    for(Skill row : rangeRows){
                        row.levelDown();
                    }
                }
            }
            log.info("[1] 순서 변경");

            // 첨부된 파일이 없는 경우
            if(file == null || file.isEmpty()) {
                log.info("첨부된 파일 없음");

                // [2] 기존 정보 셋팅
                requestDto.settingFileInfo(skill.getFilePath(), skill.getFileOriginName());
                log.info("[2] 기존 정보 셋팅");

                // [3] skill info DB update
                skill.update(requestDto.toEntity(memberRepository.getOne(requestDto.getMemberId())));
                log.info("[3] skill info DB update");
            }
            // 첨부된 파일이 있는 경우
            else {
                log.info("첨부된 파일 있음");

                // [2] file parameter setting
                String originalName = FileUtil.cutFileName(file.getOriginalFilename(), 100);
                String saveName = FileUtil.getRandomFileName(originalName);
                String saveDir = fileUploadPath + "/" + subFileUploadPath;
                String savePath =  saveDir +"/"+ saveName;
                String preExistingFilePath = skill.getFilePath();
                requestDto.settingFileInfo(savePath, originalName);
                log.info("[2] file parameter setting");

                // [3] file 디렉토리 생성
                FileUtil.createDir(saveDir);
                log.info("[3] file 디렉토리 생성");

                // [4] skill info DB update
                skill.update(requestDto.toEntity(memberRepository.getOne(requestDto.getMemberId())));
                log.info("[4] skill info DB update");

                // [5] file transfer
                try {
                    file.transferTo(new File(savePath));
                    log.info("[5] file transfer");

                } catch (IOException e) {
                    log.info("[5] file transfer fail");
                    e.printStackTrace();
                    return Header.ERROR("파일 변환 실패");
                }

                // [6] pre-existing file delete
                FileUtil.deleteFile(preExistingFilePath);
                log.info("[6] pre-existing file delete");
            }

            log.info("skill update end");
            return Header.OK(response(skill));
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    @Transactional
    public Header delete(Long id) {
        log.info("skill delete start");
        Optional<Skill> optional = baseRepository.findById(id);

        return optional.map(skill -> {
            String preExistingFilePath = skill.getFilePath();

            // [1] skill info DB delete
            baseRepository.delete(skill);
            log.info("[1] skill info DB delete");

            // [2] pre-existing file delete
            FileUtil.deleteFile(preExistingFilePath);
            log.info("[2] pre-existing file delete");

            log.info("member delete end");
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    @Transactional
    public Header<SkillResponseDto> findById(Long id) {
        log.info("skill findById start");
        log.info("member findById end");
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Transactional
    public Header<List<SkillResponseDto>> findAll(SkillRequestDto requestDto, Pageable pageable) {
        log.info("skill findAll start");
        Page<Skill> skills;
        // 특정 멤버 id 값이 들어온 경우
        if(requestDto.getMemberId() != null && requestDto.getMemberId() > 0) {
            log.info("exist memberId");
            Member member = memberRepository.findById(requestDto.getMemberId()).get();
            skills = baseRepository.findAllByMember(member, pageable);
        }
        else {
            log.info("no memberId");
            skills = baseRepository.findAll(pageable);
        }

        List<SkillResponseDto> skillResponseDtoList = skills.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(skills.getTotalPages())
                .totalElements(skills.getTotalElements())
                .currentPage(skills.getNumber())
                .currentElements(skills.getNumberOfElements())
                .build();

        log.info("member findAll end");
        return Header.OK(skillResponseDtoList, pagination);
    }

    @Transactional
    public SkillResponseDto getSkill(Long id) {
        Skill skill = baseRepository.findById(id).get();
        SkillResponseDto dto = new SkillResponseDto(skill);
        return dto;
    }

    public SkillResponseDto response(Skill skill) {
        SkillResponseDto responseDto = SkillResponseDto.builder()
                .skillId(skill.getSkillId())
                .skillName(skill.getSkillName())
                .filePath(skill.getFilePath())
                .fileOriginName(skill.getFileOriginName())
                .skillLevel(skill.getSkillLevel())
                .level(skill.getLevel())
                .memberId(skill.getMember().getMemberId())
                .build();

        return responseDto;
    }
}
