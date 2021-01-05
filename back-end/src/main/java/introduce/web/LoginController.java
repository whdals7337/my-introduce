package introduce.web;

import introduce.domain.SessionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class LoginController {

    @Value("${admin.adminId}")
    private String adminId;

    @Value("${admin.password}")
    private String adminPassword;

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
}
