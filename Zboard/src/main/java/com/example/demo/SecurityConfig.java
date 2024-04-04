package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.example.demo.security.MyLoginFailureHandler;

// 스프링 시큐리티로 웹보안을 활서오하 + @PreAuthorize, @Secured 활성화

// prePostEnabled -> @PreAuthorize와 @PostAuthorize 어노토에션을 사용할 수 있게 해준다.
// PostAuthorize는 함수를 실행하고 클라이언트에게 응답을 하기 전에 권한이 있는지 확인하는 것이다. 

// 예)
// 로그인한 상태에서 요청을 했는지 아니면 비로그인 상태에서 요청을 했는지 같은 것을 확인

// PreAuthorize는 함수가 실행하기 전에 권한을 검사하는 것이다. 
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private MyLoginFailureHandler myLoginFailureHandler;

	// 스프링에 등록하는 것을 @Bean이라고 한다.
	// @Bean은 리턴값을 스프링빈으로 등록
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// csrf 끄기
		// http.csrf(csrf -> csrf.disable());

		// form로그인(우리가 보통 말하는 로그인)
		// 로그인 페이지 url, 로그인 처리 url, 성공했을 때, 실패했을 때
		http.formLogin(s -> s.loginPage("/member/login").loginProcessingUrl("/member/login").defaultSuccessUrl("/")
				.failureHandler(myLoginFailureHandler));
		http.logout(logout -> logout.logoutUrl("/member/logout").logoutSuccessUrl("/"));

		// 403처리
		http.exceptionHandling(e -> e.accessDeniedHandler(accessDeniedHandler));

		return http.build();

	}
}
