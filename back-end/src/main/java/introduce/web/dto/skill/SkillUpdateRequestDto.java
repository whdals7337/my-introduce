package introduce.web.dto.skill;

import introduce.domain.skill.Skill;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SkillUpdateRequestDto {
    private String skillName;
    private String filePath;
    private String fileOriginName;
    private Integer skillLevel;
    private Integer level;
    private Long memberId;

    @Builder
    public SkillUpdateRequestDto(String skillName, String filePath, String fileOriginName, int skillLevel, int level, Long memberId) {
        this.skillName = skillName;
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
        this.skillLevel = skillLevel;
        this.level= level;
        this.memberId =memberId;
    }

    // file 정보 셋팅 메서드
    public void updateFileInfoSetting(String filePath, String fileOriginName) {
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
    }
}
