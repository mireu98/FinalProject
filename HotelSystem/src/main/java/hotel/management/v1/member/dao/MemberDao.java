package hotel.management.v1.member.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import hotel.management.v1.member.entity.Member;

@Mapper
public interface MemberDao {
	// 회원가입
	public Integer save(Member member);
	
	// 아이디 존재 여부 확인
	public Boolean existsByUsername(String username);
	
	// 이메일 존재 여부 확인
	public Boolean existsByEmail(String email);
	
	// 아이디찾기
	public Optional<Member> findByEmail(String name, String email);
	
	// 내정보를 출력시키기 위해 아이디로 내정보 검색
	public Optional<Member> findByUsername(String username);
	
	// 임시비밀번호 발급
	public Optional<Member> findByPassword(String name, String username, String email);
	
	// 로그인에 성공하면 로그인 실패횟수를 초기화 시킨다
	public Integer resetLoginCnt(String username);
	
	// 비밀번호를 잘못입력해서 로그인 실패시 로그인실패횟수를 1씩 올린다
	public Integer increaseLoginFailCnt(String username);
	
	// 로그인실패횟수가 5가 되면 계정 비활성화 시킨다
	public Integer disabled(String username);
	
	// 회원탈퇴
	public Integer delete(String username);
	
	// 비밀번호 변경
	public Integer changePassword(String newpassword, String username);
	
	// 전화번호와 이메일 변경
	public Integer update(String email, String tel, String username);
}
