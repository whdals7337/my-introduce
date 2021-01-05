package introduce.web;

import introduce.service.ProjectService;
import introduce.web.dto.project.ProjectRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/projectForm")
    public String  projectForm() {
        return "project/project-form";
    }

    @GetMapping("/projectList")
    public ModelAndView  projectList(ProjectRequestDto requestDto, @PageableDefault(sort="rgDate", direction = Sort.Direction.DESC, size = 300)Pageable pageable) {
        ModelAndView mav = new ModelAndView("project/project-list");
        mav.addObject("result", projectService.findAll(requestDto, pageable));
        return mav;
    }

    @GetMapping("/projectDetail")
    public ModelAndView  projectDetail(@RequestParam(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("project/project-form");
        mav.addObject("result", projectService.findById(id));
        return mav;
    }
}
