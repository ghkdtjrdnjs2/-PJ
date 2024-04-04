package com.example.demo;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dao.MemberDao;
import com.example.demo.entity.Member;
import com.example.demo.entity.Role;

// 시큐리티를 위한 회원 추가

@SpringBootTest
public class SecurityTest {
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private MemberDao dao;
	
	@Test
	public void joinTest() {
		String encodedPassword = encoder.encode("1234");
		Member m = new Member("spring", encodedPassword,
				"agavond2580@naver.com", LocalDate.now(),LocalDate.now(), "spring.jpg", Role.USER);
				dao.save(m);
				
	}
}
