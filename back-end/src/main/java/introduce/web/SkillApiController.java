package introduce.web;

import introduce.ifs.CrudWithFileInterface;
import introduce.network.Header;
import introduce.service.SkillService;
import introduce.web.dto.skill.SkillResponseDto;
import introduce.web.dto.skill.SkillRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SkillApiController implements CrudWithFileInterface<SkillRequestDto, SkillResponseDto> {

    private final SkillService skillService;

    @PostMapping("api/skill")
    public Header<SkillResponseDto> save(SkillRequestDto requestDto, @RequestParam("file") MultipartFile file) {
        return skillService.save(requestDto, file);
    }

    @PutMapping("/api/skill/{id}")
    public Header<SkillResponseDto> update(SkillRequestDto requestDto, @PathVariable Long id, @RequestParam(name="file", required=false) MultipartFile file) {
        return skillService.update(requestDto, id, file);
    }

    @DeleteMapping("/api/skill/{id}")
    public Header<SkillResponseDto> delete(@PathVariable Long id) {
        return skillService.delete(id);
    }

    @GetMapping("api/skill")
    public Header<List<SkillResponseDto>> findAll(@RequestParam(name = "memberId", required=false) Long memberId) {
        return skillService.findAll(memberId);
    }

    @GetMapping("api/skill/{id}")
    public Header<SkillResponseDto> findById(@PathVariable Long id) {
        return skillService.findById(id);
    }
}
