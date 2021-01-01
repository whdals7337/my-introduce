package introduce.service;

import introduce.domain.member.Member;
import introduce.domain.member.MemberRepository;
import introduce.domain.network.Header;
import introduce.domain.network.Pagination;
import introduce.domain.project.Project;
import introduce.domain.project.ProjectRepository;
import introduce.utill.FileUtil;
import introduce.web.dto.project.ProjectRequestDto;
import introduce.web.dto.project.ProjectResponseDto;
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
public class ProjectService extends BaseService<ProjectRequestDto, ProjectResponseDto, ProjectRepository> {

    @Value("${file.upload-dir}")
    private String fileUploadPath;

    @Value("${server-domain}")
    private String domain;

    @Value("${file.images-dir}")
    private String dirType;

    @Value("${file.project-dir}")
    private String subFileUploadPath;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Header<ProjectResponseDto> save(ProjectRequestDto requestDto, MultipartFile file) {
        log.info("project save start");

        // [1] file parameter setting
        String originalName = FileUtil.cutFileName(file.getOriginalFilename(), 100);
        String saveName = FileUtil.getRandomFileName(originalName);
        String fileUrl = domain + "/" + dirType + "/" + subFileUploadPath + "/" + saveName;
        String saveDir = fileUploadPath + subFileUploadPath;
        String savePath =  saveDir +"/"+ saveName;
        log.info("[1] file parameter setting");

        // [2] file 디렉토리 생성
        FileUtil.createDir(saveDir);
        log.info("[2] file 디렉토리 생성");

        // [3] project info DB 등록
        requestDto.settingFileInfo(savePath, originalName);
        Project project = baseRepository.save(requestDto.toEntity(memberRepository.getOne(requestDto.getMemberId())));
        log.info("[3] project info DB 등록");

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
        return Header.OK(response(project, fileUrl));
    }

    @Override
    @Transactional
    public Header update(ProjectRequestDto requestDto, Long id, MultipartFile file) {
        log.info("project update start");
        Optional<Project> optional = baseRepository.findById(id);

        return optional.map(project -> {
            String saveName;

            // 순서값이 변경 된 경우
            if(project.getLevel() != requestDto.getLevel()){
                int originLevel = project.getLevel();
                int changedLevel = requestDto.getLevel();

                // 원래 순서 값이 변경할 순서 값보다 큰 경우
                if (originLevel > changedLevel) {
                    // 원래 값부터 변경할 순서 값보다 작은 순서의 칼럼의 순서값을 1 증가
                    List<Project> rangeRows = baseRepository.findByLevelBetween(changedLevel, originLevel-1);
                    for(Project row : rangeRows){
                        row.levelUp();
                    }
                }
                // 원래 순서 값이 변경할 순서 값보다 작은 경우
                else {
                    // 원래 값보다 크고 변경할 순서 값보다 작은 순서의 칼럼의 순서값을 1 감소
                    List<Project> rangeRows = baseRepository.findByLevelBetween(originLevel+1, changedLevel);
                    for(Project row : rangeRows){
                        row.levelDown();
                    }
                }
            }
            log.info("[1] 순서 변경");

            // 첨부된 파일이 없는 경우
            if(file == null || file.isEmpty()) {
                log.info("첨부된 파일 없음");

                // [2] 기존 정보 셋팅
                requestDto.settingFileInfo(project.getFilePath(), project.getFileOriginName());
                saveName = project.getFilePath().substring(project.getFilePath().lastIndexOf("/")+1);
                log.info("[2] 기존 정보 셋팅");

                // [3] project info DB update
                project.update(requestDto.toEntity(memberRepository.getOne(requestDto.getMemberId())));
                log.info("[3] project info DB update");
            }
            // 첨부된 파일이 있는 경우
            else {
                log.info("첨부된 파일 있음");

                // [2] file parameter setting
                String originalName = FileUtil.cutFileName(file.getOriginalFilename(), 100);
                saveName = FileUtil.getRandomFileName(originalName);
                String saveDir = fileUploadPath + subFileUploadPath;
                String savePath =  saveDir +"/"+ saveName;
                String preExistingFilePath = project.getFilePath();
                requestDto.settingFileInfo(savePath, originalName);
                log.info("[2] file parameter setting");

                // [3] file 디렉토리 생성
                FileUtil.createDir(saveDir);
                log.info("[3] file 디렉토리 생성");

                // [4] project info DB update
                project.update(requestDto.toEntity(memberRepository.getOne(requestDto.getMemberId())));
                log.info("[4] project info DB update");

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
            String fileUrl = domain + "/" + dirType + "/" + subFileUploadPath + "/" + saveName;


            log.info("project update end");
            return Header.OK(response(project, fileUrl));
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    @Transactional
    public Header delete(Long id) {
        log.info("project delete start");
        Optional<Project> optional = baseRepository.findById(id);

        return optional.map(project -> {
            String preExistingFilePath = project.getFilePath();

            // [1] project info DB delete
            baseRepository.delete(project);
            log.info("[1] project info DB delete");

            // [2] pre-existing file delete
            FileUtil.deleteFile(preExistingFilePath);
            log.info("[2] pre-existing file delete");

            log.info("project delete end");
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<ProjectResponseDto> findById(Long id) {
        log.info("project findById start");
        log.info("project findById end");
        return baseRepository.findById(id)
                .map(project -> {
                    String saveName = project.getFilePath().substring(project.getFilePath().lastIndexOf("/")+1);
                    String fileUrl = domain + "/" + dirType + "/" + subFileUploadPath + "/" + saveName;
                    return response(project, fileUrl);
                })
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    @Transactional
    public Header<List<ProjectResponseDto>> findAll(ProjectRequestDto requestDto, Pageable pageable) {
        log.info("project findAll start");
        Page<Project> projects;
        // 특정 멤버 id 값이 들어온 경우
        if(requestDto.getMemberId() != null && requestDto.getMemberId() > 0) {
            log.info("exist memberId");
            Member member = memberRepository.findById(requestDto.getMemberId()).get();
            projects = baseRepository.findAllByMember(member, pageable);
        }
        else {
            log.info("no memberId");
            projects = baseRepository.findAll(pageable);
        }

        List<ProjectResponseDto> projectResponseDtoList = projects.stream()
                        .map(project -> {
                            String saveName = project.getFilePath().substring(project.getFilePath().lastIndexOf("/")+1);
                            String fileUrl = domain + "/" + dirType + "/" + subFileUploadPath + "/" + saveName;
                            return response(project, fileUrl);
                        })
                        .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(projects.getTotalPages())
                .totalElements(projects.getTotalElements())
                .currentPage(projects.getNumber())
                .currentElements(projects.getNumberOfElements())
                .build();

        log.info("project findAll end");
        return Header.OK(projectResponseDtoList, pagination);
    }

    public Project getProject(Long id) {
        Project project = baseRepository.findById(id).get();
        return project;
    }

    public ProjectResponseDto response(Project project, String fileUrl) {
        ProjectResponseDto responseDto = ProjectResponseDto.builder()
                .projectId(project.getProjectId())
                .projectTitle(project.getProjectTitle())
                .projectContent(project.getProjectContent())
                .projectPostScript(project.getProjectPostScript())
                .fileUrl(fileUrl)
                .fileOriginName(project.getFileOriginName())
                .projectLink(project.getProjectLink())
                .level(project.getLevel())
                .memberId(project.getMember().getMemberId())
                .build();

        return responseDto;
    }
}
