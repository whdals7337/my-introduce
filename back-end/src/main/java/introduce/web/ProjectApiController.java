package introduce.web;

import introduce.ifs.CrudWithFileInterface;
import introduce.network.Header;
import introduce.service.ProjectService;
import introduce.web.dto.project.ProjectResponseDto;
import introduce.web.dto.project.ProjectRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProjectApiController implements CrudWithFileInterface<ProjectRequestDto, ProjectResponseDto> {

    private final ProjectService projectService;

    @PostMapping("api/project")
    public Header<ProjectResponseDto> save(ProjectRequestDto requestDto, @RequestParam("file") MultipartFile file) {
        return projectService.save(requestDto, file);
    }

    @PutMapping("/api/project/{id}")
    public Header<ProjectResponseDto> update(ProjectRequestDto requestDto, @PathVariable Long id, @RequestParam(name="file", required=false) MultipartFile file) {
        return projectService.update(requestDto, id, file);
    }

    @DeleteMapping("/api/project/{id}")
    public Header<ProjectResponseDto> delete(@PathVariable Long id) {
        return projectService.delete(id);
    }

    @GetMapping("api/project")
    public Header<List<ProjectResponseDto>> findAll(@RequestParam(name = "memberId", required=false) Long memberId) {
        return projectService.findAll(memberId);
    }

    @GetMapping("api/project/{id}")
    public Header<ProjectResponseDto> findById(@PathVariable Long id) {
        return projectService.findById(id);
    }
}
