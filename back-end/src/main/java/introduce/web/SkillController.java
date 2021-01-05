package introduce.web;

import introduce.service.SkillService;
import introduce.web.dto.skill.SkillRequestDto;
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
@RequestMapping("/skill")
public class SkillController {

    private final SkillService skillService;

    @GetMapping("/skillForm")
    public String  skill() {
        return "skill/skill-form";
    }

    @GetMapping("/skillList")
    public ModelAndView  skillList(SkillRequestDto requestDto, @PageableDefault(sort="rgDate", direction = Sort.Direction.DESC, size = 300)Pageable pageable) {
        ModelAndView mav = new ModelAndView("skill/skill-list");
        mav.addObject("result", skillService.findAll(requestDto, pageable));
        return mav;
    }

    @GetMapping("/skillDetail")
    public ModelAndView  skillDetail(@RequestParam(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("skill/skill-form");
        mav.addObject("result", skillService.findById(id));
        return mav;
    }
}
