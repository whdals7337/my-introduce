package introduce.web.dto.project;

import introduce.domain.project.Project;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponseDto {
    private Long projectId;
    private String projectTitle;
    private String projectContent;
    private String projectPostScript;
    private String filePath;
    private String fileOriginName;
    private String projectLink;
    private Integer level;
    private Long memberId;

    public ProjectResponseDto(Project entity) {
        this.projectId = entity.getProjectId();
        this.projectTitle = entity.getProjectTitle();
        this.projectContent = entity.getProjectContent();
        this.projectPostScript = entity.getProjectPostScript();
        this.filePath = entity.getFilePath();
        this.fileOriginName = entity.getFileOriginName();
        this.projectLink = entity.getProjectLink();
        this.level = entity.getLevel();
        this.memberId= entity.getMember().getMemberId();
    }
}
