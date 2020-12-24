package introduce.domain.member;

import introduce.domain.BaseTimeEntity;
import introduce.domain.project.Project;
import introduce.domain.skill.Skill;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString(exclude = {"projectList", "skillList"})
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 50, nullable = false)
    private String comment;

    @Column(length = 500, nullable = false)
    private String filePath;

    @Column(length = 100, nullable = false)
    private String fileOriginName;

    @Column(length = 50, nullable = false)
    private String subIntroduction;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String introduction;

    @Column(length = 14)
    private String phoneNumber;

    @Column(length = 100, nullable = false)
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Project> projectList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Skill> skillList;

    public void update(Member member){
        this.comment = member.getComment();
        this.filePath = member.getFilePath();
        this.fileOriginName = member.getFileOriginName();
        this.subIntroduction = member.getSubIntroduction();
        this.introduction =member.getIntroduction();
        this.phoneNumber = member.getPhoneNumber();
        this.email = member.getEmail();
    }
}
