package hotel.management.v1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity(securedEnabled=true)
@EnableWebSecurity
@Configuration
public class SecurityConfig {
	@Autowired
	private LoginFailHandler loginFailHandler;
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	@Autowired
	private MyAccessDeniedHandler accessDeniedHandler;
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		http.formLogin().loginPage("/hotel/member/login")
				.loginProcessingUrl("/hotel/member/login")
				.successHandler(loginSuccessHandler)
				.failureHandler(loginFailHandler);
		http.logout().logoutUrl("/hotel/member/logout").logoutSuccessUrl("/hotel/main");
		return http.build();
	}
}
