package introduce.web;

import introduce.service.SkillService;
import introduce.web.dto.skill.SkillResponseDto;
import introduce.web.dto.skill.SkillSaveRequestDto;
import introduce.web.dto.skill.SkillUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SkillApiController {

    private final SkillService skillService;

    @PostMapping("api/skill")
    public Long save(@RequestBody SkillSaveRequestDto requestDto) {
        return skillService.save(requestDto);
    }

    @PutMapping("/api/skill/{id}")
    public Long update(@PathVariable Long id, @RequestBody SkillUpdateRequestDto requestDto) {
        return skillService.update(id, requestDto);
    }

    @DeleteMapping("/api/skill/{id}")
    public Long delete(@PathVariable Long id) {
        skillService.delete(id);
        return id;
    }

    @GetMapping("api/skill")
    public List<SkillResponseDto> findAll() {
        return skillService.findAll();
    }

    @GetMapping("api/skill/{id}")
    public SkillResponseDto findById(@PathVariable Long id) {
        return skillService.findById(id);
    }
}
