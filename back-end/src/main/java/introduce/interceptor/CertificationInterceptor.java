package introduce.interceptor;

import introduce.domain.SessionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Slf4j
@Component
public class CertificationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("###CertificationInterceptor preHandle####");
        // AJAX 요청인 경우
        if(isAjaxRequest(request)) return true;

        // Rest api의 Get방식을 통한 데이터 조회의 경우
        if(request.getRequestURI().contains("/api/") && request.getMethod().equals("GET")) return true;

        HttpSession session = request.getSession();
        SessionDto accessObject = (SessionDto) session.getAttribute("accessObject");

        // 로그인 세션이 없는 경우 로그인 페이지로 리다이렉트
        if(ObjectUtils.isEmpty(accessObject)){
            PrintWriter printwriter = response.getWriter();
            printwriter.print("<script>alert('login please');</script>");
            printwriter.print("<script>window.location.href='/login/loginForm'</script>");
            printwriter.flush();
            printwriter.close();
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isAjaxRequest(HttpServletRequest req) {
        String header = req.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }
}
