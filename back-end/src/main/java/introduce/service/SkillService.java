package introduce.service;

import introduce.domain.skill.Skill;
import introduce.domain.skill.SkillRepository;
import introduce.utill.FileUtil;
import introduce.web.dto.skill.SkillResponseDto;
import introduce.web.dto.skill.SkillSaveRequestDto;
import introduce.web.dto.skill.SkillUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SkillService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${file.upload-dir}")
    private String fileUploadPath;

    @Value("${file.skill-dir}")
    private String subFileUploadPath;

    private final SkillRepository skillRepository;

    @Transactional
    public SkillResponseDto getSkill(Long id) {
        Skill skill = skillRepository.findById(id).get();
        SkillResponseDto dto = new SkillResponseDto(skill);
        return dto;
    }

    @Transactional
    public Long save(SkillSaveRequestDto requestDto, MultipartFile file) throws Exception {
        logger.info("skill save start");

        // [1] file parameter setting
        String originalName = FileUtil.cutFileName(file.getOriginalFilename(), 100);
        String saveName = FileUtil.getRandomFileName(originalName);
        String saveDir = fileUploadPath + "/" + subFileUploadPath;
        String savePath =  saveDir +"/"+ saveName;
        logger.info("[1] file parameter setting");

        // [2] file 디렉토리 생성
        FileUtil.createDir(saveDir);
        logger.info("[2] file 디렉토리 생성");

        // [3] project info DB 등록
        requestDto.saveFileInfoSetting(savePath, originalName);
        Long skillId = skillRepository.save(requestDto.toEntity()).getSkillId();
        logger.info("[3] skill info DB 등록");

        // [4] file transfer
        file.transferTo(new File(savePath));
        logger.info("[4] file transfer");

        logger.info("project save end");
        return skillId;
    }

    @Transactional
    public Long update(Long id, SkillUpdateRequestDto requestDto, MultipartFile file) throws Exception {
        logger.info("skill update start");
        Skill skill = skillRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 스킬의 정보가 없습니다."));

        // 순서값이 변경 된 경우
        if(skill.getLevel() != requestDto.getLevel()){
            int originLevel = skill.getLevel();
            int changedLevel = requestDto.getLevel();

            // 원래 순서 값이 변경할 순서 값보다 큰 경우
            if (originLevel > changedLevel) {
                // 원래 값부터 변경할 순서 값보다 작은 순서의 칼럼의 순서값을 1 증가
                List<Skill> rangeRows = skillRepository.findByLevelBetween(changedLevel, originLevel-1);
                for(Skill row : rangeRows){
                    row.levelUp();
                }
            }
            // 원래 순서 값이 변경할 순서 값보다 작은 경우
            else {
                // 원래 값보다 크고 변경할 순서 값보다 작은 순서의 칼럼의 순서값을 1 감소
                List<Skill> rangeRows = skillRepository.findByLevelBetween(originLevel+1, changedLevel);
                for(Skill row : rangeRows){
                    row.levelDown();
                }
            }
        }
        logger.info("[1] 순서 변경");

        // 첨부된 파일이 없는 경우
        if(file == null || file.isEmpty()) {
            logger.info("첨부된 파일 없음");

            // [2] 기존 정보 셋팅
            requestDto.updateFileInfoSetting(skill.getFilePath(), skill.getFileOriginName());
            logger.info("[2] 기존 정보 셋팅");

            // [3] skill info DB update
            skill.update(requestDto.getSkillName(), requestDto.getFilePath(), requestDto.getFileOriginName(),
                    requestDto.getSkillLevel(), requestDto.getLevel(), requestDto.getMemberId());
            logger.info("[3] skill info DB update");

            logger.info("skill update end");
            return id;
        }

        // 첨부된 파일이 있는 경우
        logger.info("첨부된 파일 있음");

        // [2] file parameter setting
        String originalName = FileUtil.cutFileName(file.getOriginalFilename(), 100);
        String saveName = FileUtil.getRandomFileName(originalName);
        String saveDir = fileUploadPath + "/" + subFileUploadPath;
        String savePath =  saveDir +"/"+ saveName;
        String preExistingFilePath = skill.getFilePath();
        requestDto.updateFileInfoSetting(savePath, originalName);
        logger.info("[2] file parameter setting");

        // [3] file 디렉토리 생성
        FileUtil.createDir(saveDir);
        logger.info("[3] file 디렉토리 생성");

        // [4] skill info DB update
        skill.update(requestDto.getSkillName(), requestDto.getFilePath(), requestDto.getFileOriginName(),
                requestDto.getSkillLevel(), requestDto.getLevel(), requestDto.getMemberId());
        logger.info("[4] skill info DB update");

        // [5] file transfer
        file.transferTo(new File(savePath));
        logger.info("[5] file transfer");

        // [6] pre-existing file delete
        FileUtil.deleteFile(preExistingFilePath);
        logger.info("[6] pre-existing file delete");

        logger.info("skill update end");
        return id;
    }


    @Transactional
    public void delete(Long id) {
        logger.info("skill delete start");
        Skill skill = skillRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 스킬의 정보가 없습니다."));
        String preExistingFilePath = skill.getFilePath();

        // [1] skill info DB delete
        skillRepository.delete(skill);
        logger.info("[1] skill info DB delete");

        // [2] pre-existing file delete
        FileUtil.deleteFile(preExistingFilePath);
        logger.info("[2] pre-existing file delete");

        logger.info("member delete end");
    }

    @Transactional
    public List<SkillResponseDto> findAll() {
        return skillRepository.findAll().stream().map(SkillResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public SkillResponseDto findById(Long id) {
        Skill entity = skillRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 스킬의 정보가 없습니다."));
        return new SkillResponseDto(entity);
    }
}
