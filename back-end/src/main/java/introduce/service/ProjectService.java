package introduce.service;

import introduce.domain.project.Project;
import introduce.domain.project.ProjectRepository;
import introduce.utill.FileUtil;
import introduce.web.dto.project.ProjectResponseDto;
import introduce.web.dto.project.ProjectSaveRequestDto;
import introduce.web.dto.project.ProjectUpdateRequestDto;
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
public class ProjectService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${file.upload-dir}")
    private String fileUploadPath;

    @Value("${file.project-dir}")
    private String subFileUploadPath;

    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectResponseDto getProject(Long id) {
        Project project = projectRepository.findById(id).get();
        ProjectResponseDto dto = new ProjectResponseDto(project);
        return dto;
    }

    @Transactional
    public Long save(ProjectSaveRequestDto requestDto, MultipartFile file) throws Exception {
        logger.info("project save start");

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
        Long projectId = projectRepository.save(requestDto.toEntity()).getProjectId();
        logger.info("[3] project info DB 등록");

        // [4] file transfer
        file.transferTo(new File(savePath));
        logger.info("[4] file transfer");

        logger.info("project save end");
        return projectId;
    }

    @Transactional
    public Long update(Long id, ProjectUpdateRequestDto requestDto, MultipartFile file) throws Exception {
        logger.info("project update start");
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 프로젝트 정보가 없습니다."));

        // 순서값이 변경 된 경우
        if(project.getLevel() != requestDto.getLevel()){
            int originLevel = project.getLevel();
            int changedLevel = requestDto.getLevel();

            // 원래 순서 값이 변경할 순서 값보다 큰 경우
            if (originLevel > changedLevel) {
                // 원래 값부터 변경할 순서 값보다 작은 순서의 칼럼의 순서값을 1 증가
                List<Project> rangeRows = projectRepository.findByLevelBetween(changedLevel, originLevel-1);
                for(Project row : rangeRows){
                    row.levelUp();
                }
            }
            // 원래 순서 값이 변경할 순서 값보다 작은 경우
            else {
                // 원래 값보다 크고 변경할 순서 값보다 작은 순서의 칼럼의 순서값을 1 감소
                List<Project> rangeRows = projectRepository.findByLevelBetween(originLevel+1, changedLevel);
                for(Project row : rangeRows){
                    row.levelDown();
                }
            }
        }
        logger.info("[1] 순서 변경");

        // 첨부된 파일이 없는 경우
        if(file == null || file.isEmpty()) {
            logger.info("첨부된 파일 없음");

            // [2] 기존 정보 셋팅
            requestDto.updateFileInfoSetting(project.getFilePath(), project.getFileOriginName());
            logger.info("[2] 기존 정보 셋팅");

            // [3] project info DB update
            project.update(requestDto.getProjectTitle(), requestDto.getProjectContent(), requestDto.getProjectPostScript(),
                    requestDto.getFilePath(), requestDto.getFileOriginName(), requestDto.getLevel(), requestDto.getMemberId());
            logger.info("[3] project info DB update");

            logger.info("project update end");
            return id;
        }

        // 첨부된 파일이 있는 경우
        logger.info("첨부된 파일 있음");

        // [2] file parameter setting
        String originalName = FileUtil.cutFileName(file.getOriginalFilename(), 100);
        String saveName = FileUtil.getRandomFileName(originalName);
        String saveDir = fileUploadPath + "/" + subFileUploadPath;
        String savePath =  saveDir +"/"+ saveName;
        String preExistingFilePath = project.getFilePath();
        requestDto.updateFileInfoSetting(savePath, originalName);
        logger.info("[2] file parameter setting");

        // [3] file 디렉토리 생성
        FileUtil.createDir(saveDir);
        logger.info("[3] file 디렉토리 생성");

        // [4] project info DB update
        project.update(requestDto.getProjectTitle(), requestDto.getProjectContent(), requestDto.getProjectPostScript(),
                requestDto.getFilePath(), requestDto.getFileOriginName(), requestDto.getLevel(), requestDto.getMemberId());
        logger.info("[4] project info DB update");

        // [5] file transfer
        file.transferTo(new File(savePath));
        logger.info("[5] file transfer");

        // [6] pre-existing file delete
        FileUtil.deleteFile(preExistingFilePath);
        logger.info("[6] pre-existing file delete");

        logger.info("project update end");
        return id;
    }

    @Transactional
    public void delete(Long id) {
        logger.info("project delete start");

        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 프로젝트 정보가 없습니다."));
        String preExistingFilePath = project.getFilePath();

        // [1] project info DB delete
        projectRepository.delete(project);
        logger.info("[1] project info DB delete");

        // [2] pre-existing file delete
        FileUtil.deleteFile(preExistingFilePath);
        logger.info("[2] pre-existing file delete");

        logger.info("member delete end");
    }
    
    @Transactional
    public List<ProjectResponseDto> findAll() {
        return projectRepository.findAll().stream().map(ProjectResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public ProjectResponseDto findById(Long id) {
        Project entity = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 프로젝트 정보가 없습니다."));
        return new ProjectResponseDto(entity);
    }
}
