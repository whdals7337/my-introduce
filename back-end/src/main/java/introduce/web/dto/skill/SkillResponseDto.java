package introduce.web.dto.skill;

import introduce.domain.skill.Skill;
import lombok.Getter;

@Getter
public class SkillResponseDto {
    private Long skillId;
    private String skillName;
    private String filePath;
    private String fileOriginName;
    private Integer skillLevel;
    private Integer level;
    private Long memberId;

    public SkillResponseDto(Skill entity) {
        this.skillId = entity.getSkillId();
        this.skillName = entity.getSkillName();
        this.filePath = entity.getFilePath();
        this.fileOriginName = entity.getFileOriginName();
        this.skillLevel = entity.getSkillLevel();
        this.level= entity.getLevel();
        this.memberId =entity.getMemberId();
    }
}
