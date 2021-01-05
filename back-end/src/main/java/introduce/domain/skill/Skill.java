package introduce.domain.skill;

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
public class Skill extends BaseTimeEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    @Column(length = 100, nullable = false)
    private String skillName;

    @Column(length = 500, nullable = false)
    private String filePath;

    @Column(length = 100, nullable = false)
    private String fileOriginName;

    @Column(length = 500, nullable = false)
    private String fileUrl;

    @Column(nullable = false)
    private Integer skillLevel;

    @Column(nullable = false)
    private int level;

    @ManyToOne
    private Member member;


    public void update(Skill skill) {
        this.skillName = skill.getSkillName();
        this.filePath = skill.getFilePath();
        this.fileOriginName = skill.getFileOriginName();
        this.fileUrl = skill.getFileUrl();
        this.skillLevel = skill.getSkillLevel();
        this.level= skill.getLevel();
        this.member= skill.getMember();
    }

    public void levelUp() {
        this.level += 1;
    }

    public void levelDown() {
        this.level -=1;
    }
}
