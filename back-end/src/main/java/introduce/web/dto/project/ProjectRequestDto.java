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
    private String projectLink;
    private Integer level;
    private Long memberId;

    public Project toEntity(Member member, String filePath, String fileOriginName, String fileUrl) {
        return Project.builder()
                .projectTitle(projectTitle)
                .projectContent(projectContent)
                .projectPostScript(projectPostScript)
                .filePath(filePath)
                .fileOriginName(fileOriginName)
                .fileUrl(fileUrl)
                .projectLink(projectLink)
                .level(level)
                .member(member)
                .build();
    }
}
