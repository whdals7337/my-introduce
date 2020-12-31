package introduce.web;

import introduce.service.MemberService;
import introduce.service.ProjectService;
import introduce.service.SkillService;
import introduce.web.dto.member.MemberRequestDto;
import introduce.web.dto.project.ProjectRequestDto;
import introduce.web.dto.skill.SkillRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/member/memberForm")
    public String memberFrom() {
        return "member/member-form";
    }

    @GetMapping("/member/memberList")
    public ModelAndView  memberList(MemberRequestDto requestDto, @PageableDefault(sort="rgDate", direction = Sort.Direction.DESC, size = 300)Pageable pageable) {
        ModelAndView mav = new ModelAndView("member/member-list");
        mav.addObject("result", memberService.findAll(requestDto, pageable));
        return mav;
    }

    @GetMapping("/member/memberDetail")
    public ModelAndView  memberDetail(@RequestParam(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("member/member-form");
        mav.addObject("result", memberService.findById(id));
        return mav;
    }

    @GetMapping("/project/projectForm")
    public String  projectForm() {
        return "project/project-form";
    }

    @GetMapping("/project/projectList")
    public ModelAndView  projectList(ProjectRequestDto requestDto, @PageableDefault(sort="rgDate", direction = Sort.Direction.DESC, size = 300)Pageable pageable) {
        ModelAndView mav = new ModelAndView("project/project-list");
        mav.addObject("result", projectService.findAll(requestDto, pageable));
        return mav;
    }

    @GetMapping("/member/projectDetail")
    public ModelAndView  projectDetail(@RequestParam(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("project/project-form");
        mav.addObject("result", projectService.findById(id));
        return mav;
    }

    @GetMapping("/skill/skillForm")
    public String  skill() {
        return "skill/skill-form";
    }

    @GetMapping("/skill/skillList")
    public ModelAndView  skillList(SkillRequestDto requestDto, @PageableDefault(sort="rgDate", direction = Sort.Direction.DESC, size = 300)Pageable pageable) {
        ModelAndView mav = new ModelAndView("skill/skill-list");
        mav.addObject("result", skillService.findAll(requestDto, pageable));
        return mav;
    }

    @GetMapping("/skill/skillDetail")
    public ModelAndView  skillDetail(@RequestParam(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("skill/skill-form");
        mav.addObject("result", skillService.findById(id));
        return mav;
    }
}
