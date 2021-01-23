package introduce.web.dto.skill;

import introduce.domain.FileInfo;
import introduce.domain.member.Member;
import introduce.domain.skill.Skill;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillRequestDto {
    private String skillName;
    private Integer skillLevel;
    private Integer level;
    private Long memberId;

    public Skill toEntity(Member member, String filePath, String fileOriginName, String fileUrl) {
        return Skill.builder()
                .skillName(skillName)
                .fileInfo(new FileInfo(filePath, fileOriginName, fileUrl))
                .skillLevel(skillLevel)
                .level(level)
                .member(member)
                .build();
    }
}
