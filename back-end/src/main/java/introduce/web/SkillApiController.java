package introduce.web;

import introduce.domain.skill.SkillRepository;
import introduce.web.dto.skill.SkillRequestDto;
import introduce.web.dto.skill.SkillResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/skill")
public class SkillApiController extends CrudController<SkillRequestDto, SkillResponseDto, SkillRepository> {
}
