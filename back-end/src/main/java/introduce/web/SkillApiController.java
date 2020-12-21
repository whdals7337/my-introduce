package introduce.web;

import introduce.service.SkillService;
import introduce.web.dto.skill.SkillResponseDto;
import introduce.web.dto.skill.SkillSaveRequestDto;
import introduce.web.dto.skill.SkillUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SkillApiController {

    private final SkillService skillService;

    @PostMapping("api/skill")
    public Long save(SkillSaveRequestDto requestDto, @RequestParam("file") MultipartFile file) throws Exception {
        return skillService.save(requestDto, file);
    }

    @PutMapping("/api/skill/{id}")
    public Long update(@PathVariable Long id, SkillUpdateRequestDto requestDto, @RequestParam("file") MultipartFile file) throws Exception {
        return skillService.update(id, requestDto, file);
    }

    @DeleteMapping("/api/skill/{id}")
    public Long delete(@PathVariable Long id) {
        skillService.delete(id);
        return id;
    }

    @GetMapping("api/skill")
    public List<SkillResponseDto> findAll(@RequestParam("memberId") Long memberId) {
        return skillService.findAll(memberId);
    }

    @GetMapping("api/skill/{id}")
    public SkillResponseDto findById(@PathVariable Long id) {
        return skillService.findById(id);
    }
}
