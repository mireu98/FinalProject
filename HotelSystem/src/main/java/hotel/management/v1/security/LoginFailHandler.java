package hotel.management.v1.security;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import hotel.management.v1.member.dao.MemberDao;
import hotel.management.v1.member.entity.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
		HttpSession session = request.getSession();
		if(e instanceof BadCredentialsException) {
			String username = request.getParameter("username");
			try {
				Member member = memberDao.findByUsername(username).get();			
				if(member.getLoginFailCount() <4) {
					memberDao.increaseLoginFailCnt(username);
					String msg = "비밀번호를 잘못 입력하셨습니다. 로그인에 " + (member.getLoginFailCount()+1) 
						+ "회 실패했습니다. 5회 실패 시 계정이 비활성화됩니다";
					session.setAttribute("msg", msg);
				} else {
					memberDao.increaseLoginFailCnt(username);
					memberDao.disabled(username);
					session.setAttribute("msg", "로그인에 5회 실패해 계정이 비활성화되었습니다. 관리자에게 문의해주세요.");
				}
			} catch(NoSuchElementException e1) {
				session.setAttribute("msg", "아이디 또는 비밀번호를 잘못 입력했습니다");
			}
			
		} else if(e instanceof DisabledException) {
			session.setAttribute("msg", "비활성화된 계정입니다. 관리자에게 문의하세요");
		}
		response.sendRedirect("/hotel/member/login");
	}
}













