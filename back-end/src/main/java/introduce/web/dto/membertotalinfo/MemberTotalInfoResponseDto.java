package introduce.web.dto.membertotalinfo;

import introduce.web.dto.member.MemberResponseDto;
import introduce.web.dto.project.ProjectResponseDto;
import introduce.web.dto.skill.SkillResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberTotalInfoResponseDto {

    private MemberResponseDto memberResponseDto;
    private List<SkillResponseDto> skillResponseDtoList;
    private List<ProjectResponseDto> projectResponseDtoList;
}
