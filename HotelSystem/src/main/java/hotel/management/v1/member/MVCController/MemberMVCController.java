package hotel.management.v1.member.MVCController;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hotel.management.v1.member.dto.MemberDto;
import hotel.management.v1.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("hotel")
public class MemberMVCController {
	@Autowired
	private MemberService service;
	
	// 회원가입 페이지 불러오는 메소드
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/join")
	public void join() {
		
	}
	
	// 회원가입을 처리하고 회원가입 완료페이지로 보내주는 메소드
	@PreAuthorize("isAnonymous()")
	@PostMapping("/member/join")
	public String join(MemberDto.Join dto) {
		service.join(dto);
		return "redirect:/hotel/member/joincomplete";
	}
	
	// 로그인 페이지 불러오는 메소드
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/login")
	public String login(HttpSession session, Model model) {
		// 로그인 페이지에서 알림을 띄우기 위해 사용
		if (session.getAttribute("msg") != null) {
			model.addAttribute("msg", session.getAttribute("msg"));
			session.removeAttribute("msg");
			return "hotel/member/login";
		}
		return "hotel/member/login";
	}
	
	// 회원가입 완료 페이지 불러오는 메소드
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/joincomplete")
	public void joinComplete() {

	}

	// 로그인이 되어있을 때 마이페이지(나의 등급) 불러오는 메소드
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/member/myPage")
	public ModelAndView myPage(Principal principal) {
		MemberDto.Read dto = service.read(principal.getName());
		return new ModelAndView("hotel/member/myPage").addObject("member", dto);
	}

	
	// 비밀번호 변경 페이지를 불러오는 메소드
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/member/changepassword")
	public void changePassword(HttpSession session, Model model) {
		// 비밀번호 변경 페이지에서 알림을 띄우기 위해 사용
		if (session.getAttribute("msg") != null) {
			model.addAttribute("msg", session.getAttribute("msg"));
			session.removeAttribute("msg");
		}
	}
	
	// 비밀번호를 변경시켜주는 메소드
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/member/changepassword")
	public String changePassword(HttpSession session, String newpassword, Principal principal) {
		service.changePassword(newpassword, principal.getName());
		return "redirect:/hotel/member/myPage";
	}
	
	
	// 회원 탈퇴를 시켜주는 메소드
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/member/delete")
	public String delete(SecurityContextLogoutHandler handler, HttpServletRequest req, HttpServletResponse res,
			Authentication auth, RedirectAttributes ra) {
		service.delete(auth.getName());
		handler.logout(req, res, auth);
		ra.addFlashAttribute("msg", "감사합니다. 꼭 다시 한번 뵙고 싶습니다.");
		return "redirect:/hotel/main";
	}

	// 비밀번호가 세션에 들어와있으면 프로필 변경 페이지로 보내주고 비밀번호가 들어와있지 않으면 프로필 변경(비밀번호 입력 페이지)으로 보내주는 메소드
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/member/profileupdate")
	public ModelAndView checkPassword(HttpSession session) {
		if (session.getAttribute("isPasswordCheck") != null)
			return new ModelAndView("redirect:/hotel/member/read");
		return new ModelAndView("hotel/member/profileupdate");
	}
	
	// 프로필 변경 페이지에서 비밀번호를 입력하면 내 비밀번호와 비교한 후 일치하면 프로필변경 페이지로 이동 일치하지 않으면 비밀번호를 잘못 입력하셨습니다.라는 메세지를 띄우고 프로필변경(비밀번호 입력 페이지)로 보내준다.
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/member/profileupdate")
	public String checkPassword(String password, Principal principal, HttpSession session, RedirectAttributes ra) {
		if (session.getAttribute("isPasswordCheck") != null)
			return "redirect:/hotel/member/read";
		Boolean result = service.checkPassword(password, principal.getName());
		if (result == true) {
			session.setAttribute("isPasswordCheck", true);
			return "redirect:/hotel/member/read";
		} else {
			ra.addFlashAttribute("msg", "비밀번호를 잘못 입력하셨습니다.");
			return "redirect:/hotel/member/profileupdate";
		}
	}
	
	// 프로필 변경(비밀번호 입력 후의 페이지)페이지를 불러오는 메소드
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/member/read")
	public ModelAndView read(Principal principal, HttpSession session) {
		if (session.getAttribute("isPasswordCheck") == null) {
			return new ModelAndView("redirect:/hotel/member/profileupdate");
		}
		// dto에서 정보를 읽어와서 html 프로필변경(비밀번호 확인 후 페이지)에서 th:text="${member.username}"으로 읽은 정보를 불러오기 위해 사용
		MemberDto.Read dto = service.read(principal.getName());
		return new ModelAndView("hotel/member/read").addObject("member", dto);
	}
	
	

}
