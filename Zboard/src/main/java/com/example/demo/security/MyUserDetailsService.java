package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.dao.MemberDao;
import com.example.demo.entity.Member;

// 사용자 정보를 읽어와 스프링 시큐리티에 넘겨주는 역할
@Component
public class MyUserDetailsService  implements UserDetailsService {
	
	// 비밀번호 암호화 객체 
	//@Autowired
	//private PasswordEncoder encoder;
	@Autowired
	private MemberDao dao;
	
	@Override
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
		Member m = dao.findByUsername(username);
		// null 처리 
		// throws : 예외를 처리하지 않겠다.
		// throw : 개발자가 예외를 발생시킨다.
		if(m==null)
			throw new UsernameNotFoundException("사용자 없음");
		
		// 스프링 시큐리티에서 권한은 단순한 문자열
		// enum을 문자열로 바꾼다. 
		String role = m.getRole().name();
		
		// 사용자 인증 정보를 담는 UserDetails 객체를 리턴 
		// UserDetails에는 최소한 아이디, 비밀번호, 권한을 담아야 한다.
		return User.builder().username(username).password(m.getPassword()).roles(role).build();
	}
}
