package hotel.management.v1.security;

import java.util.*;

import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;

import lombok.*;

// UserDetails : 사용자 정보와 권한 정보를 담은 객체
// UserDetailsService : 로그인 처리를 하는 객체가 아니다
//						DB에 저장된 정보를 읽어서 UserDetails를 SS에 넘겨주는 역할
//						로그인 작업은 SS가 한다

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyUserDetails implements UserDetails {
	private String username;
	private String password;
	private Boolean disabled;
	private Collection<GrantedAuthority> authorities;
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return disabled;
	}


}
