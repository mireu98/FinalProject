package hotel.management.v1.member.RestController.copy;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hotel.management.v1.member.service.MemberService;

@Controller
@RequestMapping("hotel")
public class MemberRestController {
	@Autowired
	private MemberService service;

	// 아이디 존재 여부를 확인하는 메소드
		@PreAuthorize("isAnonymous()")
		@GetMapping("/member/check/username")
		public ResponseEntity<Void> checkUsername(String username) {
			Boolean result = service.checkUsername(username);
			return result ? ResponseEntity.ok(null) : ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}
		
	// 이메일 존재 여부를 확인하는 메소드
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/check/email")
	public ResponseEntity<Void> emailUsername(String email) {
		Boolean result = service.checkEmail(email);
		return result ? ResponseEntity.ok(null) : ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	}
	
	// 사용자 이름과 이메일을 받아서 아이디를 찾는 메소드
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/find_id")
	public ResponseEntity<String> findId(String name, String email) {
		return ResponseEntity.ok(service.findUsername(name, email));
	}
	
	// 사용자 이름과 아이디와 이메일을 받아서 임시비밀번호를 메일로 보내주는 메소드
	@PreAuthorize("isAnonymous()")
	@PostMapping("/member/find_password")
	public ResponseEntity<String> findPassword(String name, String username, String email) {
		service.resetPassword(name, username, email);
		return ResponseEntity.ok("임시비밀번호를 이메일로 보냈습니다");
	}
	
	// 프로필 변경에서 이메일과 전화번호를 변경시켜주기 위한 메소드
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/member/update")
	public ResponseEntity<Void> update(String email, String tel, Principal principal) {
		Boolean result = service.update(email, tel, principal.getName());
		return result ? ResponseEntity.ok(null) : ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	}
	
	// 비밀번호를 변경할 때 현재사용하고 있는 비밀번호와 내가 입력한 현재 비밀번호와 동일한지 비교하는 메소드
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/member/checkNowPassword")
	public ResponseEntity<Boolean> checkNowPassword(String nowpassword , Principal principal) {
		System.out.println(nowpassword);
		Boolean result = service.checkPassword(nowpassword, principal.getName());
		if (result == true) {
			return ResponseEntity.ok(true);
		} else {
			return ResponseEntity.ok(false);
		}
	}
}
