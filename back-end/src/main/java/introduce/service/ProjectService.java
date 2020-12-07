package introduce.service;

import introduce.domain.project.Project;
import introduce.domain.project.ProjectRepository;
import introduce.web.dto.project.ProjectResponseDto;
import introduce.web.dto.project.ProjectSaveRequestDto;
import introduce.web.dto.project.ProjectUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public Long save(ProjectSaveRequestDto requestDto) {
        return projectRepository.save(requestDto.toEntity()).getProjectId();
    }

    @Transactional
    public Long update(Long id, ProjectUpdateRequestDto requestDto) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 프로젝트 정보가 없습니다."));
        if(project.getLevel() != requestDto.getLevel()){
            int originLevel = project.getLevel();
            int changedLevel = requestDto.getLevel();

            // 원래 순서 값이 변경할 순서 값보다 큰 경우
            if (originLevel > changedLevel) {
                // 원래 값부터 변경할 순서 값보다 작은 순서의 칼럼의 순서값을 1 증가
                List<Project> rangeRows = projectRepository.findByLevelBetween(changedLevel, originLevel-1);
                for(Project row : rangeRows){
                    row.update(row.getProjectTitle(), row.getProjectContent(), row.getProjectPostScript(),
                            row.getProjectImagePath(), row.getImageOriginName(), row.getLevel() + 1, row.getMemberId());
                }
            }
            // 원래 순서 값이 변경할 순서 값보다 작은 경우
            else {
                // 원래 값보다 크고 변경할 순서 값보다 작은 순서의 칼럼의 순서값을 1 감소
                List<Project> rangeRows = projectRepository.findByLevelBetween(originLevel+1, changedLevel);
                for(Project row : rangeRows){
                    row.update(row.getProjectTitle(), row.getProjectContent(), row.getProjectPostScript(),
                            row.getProjectImagePath(), row.getImageOriginName(), row.getLevel() - 1, row.getMemberId());
                }
            }
        }

        // 변경 요청된 값 update
        project.update(requestDto.getProjectTitle(), requestDto.getProjectContent(), requestDto.getProjectPostScript(),
                requestDto.getProjectImagePath(), requestDto.getImageOriginName(), requestDto.getLevel(), requestDto.getMemberId());

        return id;
    }

    @Transactional
    public void delete(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 프로젝트 정보가 없습니다."));
        projectRepository.delete(project);
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
