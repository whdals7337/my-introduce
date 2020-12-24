package introduce.web.dto.project;

import introduce.domain.member.Member;
import introduce.domain.project.Project;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequestDto {
    private String projectTitle;
    private String projectContent;
    private String projectPostScript;
    private String filePath;
    private String fileOriginName;
    private String projectLink;
    private Integer level;
    private Long memberId;

    public Project toEntity(Member member) {
        return Project.builder()
                .projectTitle(projectTitle)
                .projectContent(projectContent)
                .projectPostScript(projectPostScript)
                .filePath(filePath)
                .fileOriginName(fileOriginName)
                .projectLink(projectLink)
                .level(level)
                .member(member)
                .build();
    }

    // file 정보 셋팅 메서드
    public void settingFileInfo(String filePath, String fileOriginName) {
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
    }
}
