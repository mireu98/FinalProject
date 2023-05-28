package hotel.management.v1.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hotel.management.v1.member.dao.MemberDao;
import hotel.management.v1.member.entity.Member;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Value("${admin.username}")
	private String adminUsername;
	@Value("${admin.password}")
	private String adminPassword;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(username.equals(adminUsername)) {
			return new MyUserDetails(username, passwordEncoder.encode(adminPassword), true, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
		}
		try {
			Member member = memberDao.findByUsername(username).get();
			String role = "ROLE_" + member.getRole().name();
			Collection<GrantedAuthority> list = new ArrayList<>();
			list.add(new SimpleGrantedAuthority(role));
			
			return new MyUserDetails(username,member.getPassword(),!member.getDisabled(),list);
		} catch(NoSuchElementException e) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
		}
	}

}




