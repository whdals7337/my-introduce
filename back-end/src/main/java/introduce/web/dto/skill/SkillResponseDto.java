package introduce.web.dto.skill;

import introduce.domain.skill.Skill;
import lombok.Getter;

@Getter
public class SkillResponseDto {
    private Long skillId;
    private String skillName;
    private String skillImagePath;
    private String imageOriginName;
    private Integer skillLevel;
    private Integer level;
    private Long memberId;

    public SkillResponseDto(Skill entity) {
        this.skillId = entity.getSkillId();
        this.skillName = entity.getSkillName();
        this.skillImagePath = entity.getSkillImagePath();
        this.imageOriginName = entity.getImageOriginName();
        this.skillLevel = entity.getSkillLevel();
        this.level= entity.getLevel();
        this.memberId =entity.getMemberId();
    }
}
