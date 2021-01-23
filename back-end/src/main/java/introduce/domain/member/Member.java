package introduce.domain.member;

import introduce.domain.BaseTimeEntity;
import introduce.domain.FileInfo;
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

    @Embedded
    private FileInfo fileInfo;

    @Column(length = 50, nullable = false)
    private String subIntroduction;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String introduction;

    @Column(length = 14)
    private String phoneNumber;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 1, nullable = false)
    private String selectYN;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Project> projectList;

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Skill> skillList;

    public void update(Member member){
        this.comment = member.getComment();
        this.fileInfo = member.getFileInfo();
        this.subIntroduction = member.getSubIntroduction();
        this.introduction =member.getIntroduction();
        this.phoneNumber = member.getPhoneNumber();
        this.email = member.getEmail();
        this.selectYN = member.getSelectYN();
    }

    public void select() {
        this.selectYN ="Y";
    }

    public void unSelect() {
        this.selectYN ="N";
    }
}
