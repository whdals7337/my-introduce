package introduce.web.dto.project;

import introduce.domain.project.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectUpdateRequestDto {
    private String projectTitle;
    private String projectContent;
    private String projectPostScript;
    private String projectImagePath;
    private String imageOriginName;
    private Integer level;
    private Long memberId;

    @Builder
    public ProjectUpdateRequestDto(String projectTitle, String projectContent, String projectPostScript, String projectImagePath, String imageOriginName, int level, Long memberId) {
        this.projectTitle = projectTitle;
        this.projectContent = projectContent;
        this.projectPostScript = projectPostScript;
        this.projectImagePath = projectImagePath;
        this.imageOriginName = imageOriginName;
        this.level = level;
        this.memberId =memberId;
    }
}
