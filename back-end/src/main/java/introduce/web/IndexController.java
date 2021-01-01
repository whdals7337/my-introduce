package introduce.web;

import introduce.domain.SessionDto;
import introduce.service.MemberService;
import introduce.service.ProjectService;
import introduce.service.SkillService;
import introduce.web.dto.member.MemberRequestDto;
import introduce.web.dto.member.MemberResponseDto;
import introduce.web.dto.project.ProjectRequestDto;
import introduce.web.dto.skill.SkillRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @Value("${admin.adminId}")
    private String adminId;

    @Value("${admin.password}")
    private String adminPassword;

    private final MemberService memberService;

    private final SkillService skillService;

    private final ProjectService projectService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login/loginForm")
    public String loginForm() {
        return "login-form";
    }

    @ResponseBody
    @PostMapping("/login/login")
    public SessionDto login(@RequestParam String id, @RequestParam String password, HttpSession session) {
        SessionDto sessionDto;
        if(id.equals(adminId) && password.equals(adminPassword)){
            sessionDto = SessionDto.builder().isAccess("access").build();
            session.setAttribute("accessObject", sessionDto);
        }
        else {
            sessionDto = SessionDto.builder().isAccess("fail").build();
        }
        return sessionDto;
    }

    @GetMapping("/login/logout")
    public ModelAndView logout (HttpSession session, HttpServletRequest request) {
        session.invalidate();
        RedirectView rv = new RedirectView(request.getContextPath() + "/login/loginForm");
        rv.setExposeModelAttributes(false);	// QueryString 없도록 설정.
        return new ModelAndView(rv);
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

    @ResponseBody
    @PostMapping("/member/select")
    public MemberResponseDto memberSelect(@RequestParam Long id) {
        return memberService.updateSelect(id);
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
