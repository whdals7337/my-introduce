package introduce.web.dto.skill;

import introduce.domain.skill.Skill;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SkillSaveRequestDto {
    private String skillName;
    private String skillImagePath;
    private String imageOriginName;
    private Integer skillLevel;
    private Integer level;
    private Long memberId;

    @Builder
    public SkillSaveRequestDto(String skillName, String skillImagePath, String imageOriginName, int skillLevel, int level, Long memberId) {
        this.skillName = skillName;
        this.skillImagePath = skillImagePath;
        this.imageOriginName = imageOriginName;
        this.skillLevel = skillLevel;
        this.level= level;
        this.memberId =memberId;
    }

    public Skill toEntity() {
        return Skill.builder()
                .skillName(skillName)
                .skillImagePath(skillImagePath)
                .imageOriginName(imageOriginName)
                .skillLevel(skillLevel)
                .level(level)
                .memberId(memberId)
                .build();
    }
}
