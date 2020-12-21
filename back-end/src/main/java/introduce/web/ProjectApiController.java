package introduce.web;

import introduce.service.ProjectService;
import introduce.web.dto.project.ProjectResponseDto;
import introduce.web.dto.project.ProjectSaveRequestDto;
import introduce.web.dto.project.ProjectUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProjectApiController {

    private final ProjectService projectService;

    @PostMapping("api/project")
    public Long save(ProjectSaveRequestDto requestDto, @RequestParam("file") MultipartFile file) throws Exception {
        return projectService.save(requestDto, file);
    }

    @PutMapping("/api/project/{id}")
    public Long update(@PathVariable Long id, ProjectUpdateRequestDto requestDto, @RequestParam(name="file", required=false) MultipartFile file) throws Exception {
        return projectService.update(id, requestDto, file);
    }

    @DeleteMapping("/api/project/{id}")
    public Long delete(@PathVariable Long id) {
        projectService.delete(id);
        return id;
    }

    @GetMapping("api/project")
    public List<ProjectResponseDto> findAll(@RequestParam(name = "memberId", required=false) Long memberId) {
        return projectService.findAll(memberId);
    }

    @GetMapping("api/project/{id}")
    public ProjectResponseDto findById(@PathVariable Long id) {
        return projectService.findById(id);
    }
}
