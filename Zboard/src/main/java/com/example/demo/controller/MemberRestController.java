package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.MemberService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotEmpty;

// rest 전용 컨트롤러
// 나눠서 사용하는 것이 좋다.
// 분리시키는 방법도 하나의 방법이다. 
@RestController
public class MemberRestController {
	
	@Autowired
	private MemberService service;
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String>
	CvEHandler(ConstraintViolationException e){
		// 자바 람다, 스트림 api를 공부해야한다.
		// 일단은 많은 에러중 하나를 꺼내는 방식이다. 
		/// 예외객체에서 첫번째 오류 메시지를 꺼내는 코드
		String msg = e.getConstraintViolations().stream().findFirst().get().getMessage();
		return ResponseEntity.status(409).body(msg);
	}

	// ResponseEntity : 응답 데이터 + 상태코드 -> 결과 + 상태코드
	// 서버에서 결과가 나오면 200이다.
	@GetMapping("/member/id-check")
	public ResponseEntity<String> idCheck(@NotEmpty(message="아이디는 필수입력입니다.")String username) {
		
		Boolean result = service.idCheck(username);
		if (result == true) {
			return ResponseEntity.ok("사용가능합니다.");
		}
		return ResponseEntity.status(409).body("사용중입니다.");
		
//		service가 없을 경우 사용한다. 		
//		// 아이디가 spring이면 사용불가능, 아니면 사용가능
//		if (username.equals("spring")) {
//			// 보통 409는 사용하지 않아서 사용하는 것이다.
//			return ResponseEntity.status(409).body("사용중입니다.");
//		}
//		return ResponseEntity.status(200).body("사용가능합니다.");
	}
	
	// 1. byte[]은 뭐야? 파일을 출력할 때 byte의 배열로 지정한다. 
	// 2, @GetMapping()이 왜 복잡하냐?
	// - 어노테이션은 변형된 클래스 
	// - @~Mapping 어노테이션에는 path라는 필드가 기본 필드 
	// - produces 필드는 ResponseEntity에 담긴 데이터 형식을 웹 브라우저에 알려준다
	// OCTET은 바이트라고 한다. 
	//produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)을 지정하지 않으면 데이터가 깨지거나 이상하게 온다. 
	@GetMapping(path="/profile/{image}", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> viewImage(@PathVariable String image) {
		try {
			Path path = Paths.get("c:/upload/profile/" + image);
			byte[] imageBytes = Files.readAllBytes(path);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
		} catch (IOException e) {
			return ResponseEntity.notFound().build();
		}
	}
	// 3. 아이디 찾기 
	@GetMapping("/member/find-id")
	public ResponseEntity<String> findId(String email){
		String username = service.findId(email);
		if(username==null) {
			return ResponseEntity.status(409)
					.body("사용자를 찾을 수 없습니다.");
		}
		return ResponseEntity.ok(username);
	}
	
	// 4. 비밀번호 리셋 - 비밀번호 찾기 기능은 개인정보보호법 위반
	@PostMapping("/member/reset-password")
	public ResponseEntity<String> restPassword(String username){
		Boolean result = service.resetPassword(username);
		if(result==false) {
			return ResponseEntity.status(409)
					.body("사용자를 찾을 수 없습니다.");
		}
		return ResponseEntity.ok("임시비밀번호를 이메일로 보냈습니다.");
	}
	
	// Response<여기>에서 여기에는 객체만 올 수 있다.
	// 여기에 아무 객체도 안 담으려면 void에 객체 표현 Void를 사용한다.
	@Secured("ROLE_USER")
	@PostMapping("/member/change-email")
	public ResponseEntity<Void> changeEmail(String email, Principal principal){
		Boolean result=service.changeEmail(email, principal.getName());
		if(result) {
			return ResponseEntity.ok(null);
		}
		return ResponseEntity.status(409).body(null);
	}
}
