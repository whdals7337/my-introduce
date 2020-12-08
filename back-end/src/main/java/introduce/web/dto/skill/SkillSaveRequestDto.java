package introduce.web.dto.skill;

import introduce.domain.skill.Skill;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SkillSaveRequestDto {
    private String skillName;
    private String filePath;
    private String fileOriginName;
    private Integer skillLevel;
    private Integer level;
    private Long memberId;

    @Builder
    public SkillSaveRequestDto(String skillName, String filePath, String fileOriginName, int skillLevel, int level, Long memberId) {
        this.skillName = skillName;
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
        this.skillLevel = skillLevel;
        this.level= level;
        this.memberId =memberId;
    }

    public Skill toEntity() {
        return Skill.builder()
                .skillName(skillName)
                .filePath(filePath)
                .fileOriginName(fileOriginName)
                .skillLevel(skillLevel)
                .level(level)
                .memberId(memberId)
                .build();
    }
}
