package introduce.domain.skill;

import introduce.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Skill extends BaseTimeEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    @Column(length = 100, nullable = false)
    private String skillName;

    @Column(length = 500, nullable = false)
    private String skillImagePath;

    @Column(length = 100, nullable = false)
    private String imageOriginName;

    @Column(nullable = false)
    private Integer skillLevel;

    @Column(nullable = false)
    private Integer level;

    @Column
    private Long memberId;

    @Builder
    public Skill(String skillName, String skillImagePath, String imageOriginName, int skillLevel, int level, Long memberId) {
        this.skillName = skillName;
        this.skillImagePath = skillImagePath;
        this.imageOriginName = imageOriginName;
        this.skillLevel = skillLevel;
        this.level= level;
        this.memberId = memberId;
    }

    public void update(String skillName, String skillImagePath, String imageOriginName, int skillLevel, int level, Long memberId) {
        this.skillName = skillName;
        this.skillImagePath = skillImagePath;
        this.imageOriginName = imageOriginName;
        this.skillLevel = skillLevel;
        this.level= level;
        this.memberId = memberId;
    }
}
