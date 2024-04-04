package com.example.demo.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// AccessDeniedHandler : 권한없으면 403 -> 예외처리 담당 
// 스프링 시큐리티는 서블릿 베이스로 동작한다.
// (스프링 시큐리티는 스프링에 인수됐지만 독립적인 제품 -> 자바에서 사용 가능)
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse res,
	AccessDeniedException accessDeniedException) throws IOException, ServletException{		
		// request.getHeader("X-Requested-With") 값이 null이 아니면 ajax
		String 요청방식 = request.getHeader("X-Requested-With");
		if(요청방식==null) {
			res.sendRedirect("/");
		} else {
			res.sendError(403,"권한 없음");
		}
	}
}
