package introduce.domain.project;

import introduce.domain.BaseTimeEntity;
import introduce.domain.FileInfo;
import introduce.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Embedded
    private FileInfo fileInfo;

    @Column(length = 100)
    private String projectLink;

    @Column(nullable = false)
    private int level;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void update(Project project) {
        this.projectTitle = project.getProjectTitle();
        this.projectContent = project.getProjectContent();
        this.projectPostScript = project.getProjectPostScript();
        this.fileInfo = project.getFileInfo();
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
