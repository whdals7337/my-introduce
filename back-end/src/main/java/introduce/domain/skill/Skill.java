package introduce.domain.skill;

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
public class Skill extends BaseTimeEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    @Column(length = 100, nullable = false)
    private String skillName;

    @Embedded
    private FileInfo fileInfo;

    @Column(nullable = false)
    private Integer skillLevel;

    @Column(nullable = false)
    private int level;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    public void update(Skill skill) {
        this.skillName = skill.getSkillName();
        this.fileInfo = skill.getFileInfo();
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
