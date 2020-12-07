package introduce.web;

import introduce.service.ProjectService;
import introduce.web.dto.project.ProjectResponseDto;
import introduce.web.dto.project.ProjectSaveRequestDto;
import introduce.web.dto.project.ProjectUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProjectApiController {

    private final ProjectService projectService;

    @PostMapping("api/project")
    public Long save(@RequestBody ProjectSaveRequestDto requestDto) {
        return projectService.save(requestDto);
    }

    @PutMapping("/api/project/{id}")
    public Long update(@PathVariable Long id, @RequestBody ProjectUpdateRequestDto requestDto) {
        return projectService.update(id, requestDto);
    }

    @DeleteMapping("/api/project/{id}")
    public Long delete(@PathVariable Long id) {
        projectService.delete(id);
        return id;
    }

    @GetMapping("api/project")
    public List<ProjectResponseDto> findAll() {
        return projectService.findAll();
    }

    @GetMapping("api/project/{id}")
    public ProjectResponseDto findById(@PathVariable Long id) {
        return projectService.findById(id);
    }
}
