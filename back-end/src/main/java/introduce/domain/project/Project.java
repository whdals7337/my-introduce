package introduce.domain.project;

import introduce.domain.BaseTimeEntity;
import introduce.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString(exclude = {"member"})
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column(length = 100, nullable = false)
    private String projectTitle;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String projectContent;

    @Column(length = 500, nullable = false)
    private String projectPostScript;

    @Column(length = 500, nullable = false)
    private String filePath;

    @Column(length = 100, nullable = false)
    private String fileOriginName;

    @Column(length = 500, nullable = false)
    private String fileUrl;

    @Column(length = 100)
    private String projectLink;

    @Column(nullable = false)
    private int level;

    @ManyToOne
    private Member member;

    public void update(Project project) {
        this.projectTitle = project.getProjectTitle();
        this.projectContent = project.getProjectContent();
        this.projectPostScript = project.getProjectPostScript();
        this.filePath = project.getFilePath();
        this.fileOriginName = project.getFileOriginName();
        this.fileUrl = project.getFileUrl();
        this.projectLink = project.getProjectLink();
        this.level = project.getLevel();
        this.member = project.getMember();
    }

    public void levelUp() {
        this.level += 1;
    }

    public void levelDown() {
        this.level -= 1;
    }
}
