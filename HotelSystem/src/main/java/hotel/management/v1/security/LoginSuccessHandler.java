package hotel.management.v1.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import hotel.management.v1.member.dao.MemberDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private MemberDao memberDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    
        
        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            String redirectUrl = request.getContextPath() + "/hotel/manager/bookList";
            if (request.isRequestedSessionIdValid() && !isMobile(request)) { // 700px 이상인 경우
                response.sendRedirect(redirectUrl);
            } else { // 700px 이하인 경우
               // response.setContentType("text/html;charset=UTF-8");
                response.sendRedirect(redirectUrl);
            }
            return;
        }

        if (request.isRequestedSessionIdValid() && !isMobile(request)) { // 700px 이상인 경우
            response.sendRedirect("/hotel/main");
        } else { // 700px 이하인 경우
            //response.setContentType("text/html;charset=UTF-8");
            response.sendRedirect("/hotel/main");
        }
        memberDao.resetLoginCnt(authentication.getName());
    }

    // 모바일 기기 체크 메서드
    private boolean isMobile(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.matches(".*Mobile.*") || userAgent.matches(".*Android.*")) {
            return true;
        }
        return false;
    }
}





