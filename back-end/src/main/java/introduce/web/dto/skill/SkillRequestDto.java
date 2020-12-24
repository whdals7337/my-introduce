package introduce.web.dto.skill;

import introduce.domain.member.Member;
import introduce.domain.skill.Skill;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillRequestDto {
    private String skillName;
    private String filePath;
    private String fileOriginName;
    private Integer skillLevel;
    private Integer level;
    private Long memberId;

    public Skill toEntity(Member member) {
        return Skill.builder()
                .skillName(skillName)
                .filePath(filePath)
                .fileOriginName(fileOriginName)
                .skillLevel(skillLevel)
                .level(level)
                .member(member)
                .build();
    }

    public void settingFileInfo(String filePath, String fileOriginName) {
        this.filePath = filePath;
        this.fileOriginName = fileOriginName;
    }
}
