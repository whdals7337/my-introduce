package introduce.web;

import introduce.domain.project.ProjectRepository;
import introduce.web.dto.project.ProjectRequestDto;
import introduce.web.dto.project.ProjectResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/project")
public class ProjectApiController extends CrudController<ProjectRequestDto, ProjectResponseDto, ProjectRepository> {
}
