package introduce.domain.project;

import introduce.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column(length = 100, nullable = false)
    private String projectTitle;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String projectContent;

    @Column(length = 100, nullable = false)
    private String projectPostScript;

    @Column(length = 500, nullable = false)
    private String filePath;

    @Column(length = 100, nullable = false)
    private String fileOriginName;

    @Column(nullable = false)
    private Integer level;

    @Column
    private Long memberId;

    @Builder
    public Project(String projectTitle, String projectContent, String projectPostScript, String filePath, String fileOriginName, int level, Long memberId) {
        this.projectTitle = projectTitle;
        this.projectContent = projectContent;
        this.projectPostScript = projectPostScript;
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
        this.level = level;
        this.memberId = memberId;
    }

    public void update(String projectTitle, String projectContent, String projectPostScript, String filePath,String fileOriginName, int level, Long memberId) {
        this.projectTitle = projectTitle;
        this.projectContent = projectContent;
        this.projectPostScript = projectPostScript;
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
        this.level = level;
        this.memberId = memberId;
    }

    public void levelUp() {
        this.level += 1;
    }

    public void levelDown() {
        this.level -= 1;
    }
}
