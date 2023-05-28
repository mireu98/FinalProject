package hotel.management.v1.security;

import java.io.*;

import org.springframework.security.access.*;
import org.springframework.security.web.access.*;
import org.springframework.stereotype.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

//권한없음이 발생했을 때 /error/e403으로 유도할 AccessDenialHandler
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// ajax 인지 확인
		String ajaxHeader = request.getHeader("x-requested-with");
		Boolean isAjax = ajaxHeader!=null && ajaxHeader.equals("x-requested-with");
		// ajax면 403 상태코드로, ajax가 아니면 오류 메시지와 함께 루트 페이지로 이동
		if(isAjax)
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		else {
			HttpSession session = request.getSession();
			session.setAttribute("msg", "작업을 수행할 권한이 없습니다");
			System.out.println(session.getAttribute("msg"));
			response.sendRedirect("/hotel/main");
		}
	}
}

