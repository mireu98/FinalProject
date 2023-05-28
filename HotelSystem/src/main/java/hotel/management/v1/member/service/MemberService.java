package hotel.management.v1.member.service;

import java.util.NoSuchElementException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hotel.management.v1.member.dao.MemberDao;
import hotel.management.v1.member.dto.MemberDto;
import hotel.management.v1.member.entity.Member;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.NotEmpty;

@Service
public class MemberService {
	@Autowired
	private MemberDao dao;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private JavaMailSender mailSender;
	
	// 메일을 발송해주기 위한 메소드
	private void sendMail(String from, String to, String title, String text) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(title);
			helper.setText(text, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	// 회원가입 정보를 입력하면 users 테이블에 회원가입 정보들을 저장
	public void join(MemberDto.Join dto) {
		String encodedPassword = encoder.encode(dto.getPassword());
		Member member = dto.toEntity(encodedPassword);
		dao.save(member);
	}
	
	// 아이디 존재 여부를 확인하기 위한 메소드
	public Boolean checkUsername(String username) {
		return !dao.existsByUsername(username);
	}
	
	// 이메일 존재 여부를 확인하기 위한 메소드
	public Boolean checkEmail(String email) {
		return !dao.existsByEmail(email);
	}
	
	// 이름과 이메일을 받아와서 이름과 이메일이 존재하는지 확인하는 메소드
	public String findUsername(String name, String email) {
		try {
			return dao.findByEmail(name, email).get().getUsername();
		} catch(NoSuchElementException e) {
			throw e;
		}
	}
	
	// 이름과 아이디,이메일을 받아와서 존재한다면 임시비밀번호를 이메일로 발송해주는 메소드
	public void resetPassword(String name, String username, String email) {
		try {
			Member member = dao.findByPassword(name, username, email).get();
			String newPassword = RandomStringUtils.randomAlphanumeric(20);
			String newEncodedPassword = encoder.encode(newPassword);
			dao.changePassword(newEncodedPassword, username);
			sendMail("cpsua97@naver.com",member.getEmail(), "임시비밀번호", newPassword);
		} catch(NoSuchElementException e) {
			throw e;
		}
	}
	
	// 회원탈퇴를 하는 메소드
	public void delete(String username) {
		try {
			dao.delete(username);
		} catch(NoSuchElementException e) {
			throw e;
		}
	}
	
	// 새 비밀번호를 받아와서 암호화 시킨 다음 비밀번호를 변경시켜주는 메소드
	public void changePassword(String newpassword, String username) {
		String encodedPassword = encoder.encode(newpassword);
		dao.changePassword(encodedPassword, username);
	}
	
	// 비밀번호와 아이디를 받아와서 비밀번호가 일치하는지 비교하는 메소드
	public Boolean checkPassword(String password, String username) {
		try {
			Member member = dao.findByUsername(username).get();
			return encoder.matches(password, member.getPassword());
		} catch(NoSuchElementException e) {
			return false;
		}
	}
	
	// 프로필변경(비밀번호 확인 후) 페이지에서 나의 정보를 출력시키기 위한 메소드
	public MemberDto.Read read(String username) {
		Member member = dao.findByUsername(username).get();
		return member.toReadDto();
	}
	
	// 이메일과 전화번호를 변경시키기 위한 메소드
	public Boolean update(String email, String tel, String username) {
		return dao.update(email, tel, username)==1;
	}
}
