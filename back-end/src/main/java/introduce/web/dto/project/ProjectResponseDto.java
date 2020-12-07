package introduce.web.dto.project;

import introduce.domain.project.Project;
import lombok.Getter;

@Getter
public class ProjectResponseDto {
    private Long projectId;
    private String projectTitle;
    private String projectContent;
    private String projectPostScript;
    private String projectImagePath;
    private String imageOriginName;
    private Integer level;
    private Long memberId;

    public ProjectResponseDto(Project entity) {
        this.projectId = entity.getProjectId();
        this.projectTitle = entity.getProjectTitle();
        this.projectContent = entity.getProjectContent();
        this.projectPostScript = entity.getProjectPostScript();
        this.projectImagePath = entity.getProjectImagePath();
        this.imageOriginName = entity.getImageOriginName();
        this.level = entity.getLevel();
        this.memberId = entity.getMemberId();
    }
}
