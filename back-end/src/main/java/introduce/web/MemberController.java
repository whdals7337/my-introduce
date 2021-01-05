package introduce.web;

import introduce.service.MemberService;
import introduce.web.dto.member.MemberRequestDto;
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
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/memberForm")
    public String memberFrom() {
        return "member/member-form";
    }

    @GetMapping("/memberList")
    public ModelAndView  memberList(MemberRequestDto requestDto, @PageableDefault(sort="rgDate", direction = Sort.Direction.DESC, size = 300)Pageable pageable) {
        ModelAndView mav = new ModelAndView("member/member-list");
        mav.addObject("result", memberService.findAll(requestDto, pageable));
        return mav;
    }

    @GetMapping("/memberDetail")
    public ModelAndView  memberDetail(@RequestParam(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("member/member-form");
        mav.addObject("result", memberService.findById(id));
        return mav;
    }
}
