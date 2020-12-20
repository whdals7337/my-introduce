package introduce.web.dto.project;

import introduce.domain.project.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectSaveRequestDto {
    private String projectTitle;
    private String projectContent;
    private String projectPostScript;
    private String filePath;
    private String fileOriginName;
    private String projectLink;
    private Integer level;
    private Long memberId;

    @Builder
    public ProjectSaveRequestDto(String projectTitle, String projectContent, String projectPostScript, String filePath,String fileOriginName, String projectLink, int level, Long memberId) {
        this.projectTitle = projectTitle;
        this.projectContent = projectContent;
        this.projectPostScript = projectPostScript;
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
        this.projectLink = projectLink;
        this.level = level;
        this.memberId =memberId;
    }

    public Project toEntity() {
        return Project.builder()
                .projectTitle(projectTitle)
                .projectContent(projectContent)
                .projectPostScript(projectPostScript)
                .filePath(filePath)
                .fileOriginName(fileOriginName)
                .projectLink(projectLink)
                .level(level)
                .memberId(memberId)
                .build();
    }

    // file 정보 셋팅 메서드
    public void saveFileInfoSetting(String filePath, String fileOriginName) {
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
    }
}
