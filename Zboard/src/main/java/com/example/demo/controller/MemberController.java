package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.MemberJoinDto;
import com.example.demo.dto.MemberReadDto;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

// 스프링 예외처리를 활성화
@Validated
@Controller
public class MemberController {

	@Autowired
	private MemberService service;

	@GetMapping("/member/login")
	public ModelAndView login() {
		return new ModelAndView("member/login");
	}

	// @PreAuthorize는 메서드를 실행하기 전에 검토를 먼저 받는 것이다. 검토 후 확인되면 그때 메소드를 사용할 수 있다.
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/join")
	public ModelAndView join() {
		return new ModelAndView("member/join");
	}

	// 정확하게 @PreAuthorize를 설명하면 사용자가 인증되었는지 아닌지를 확인하는 것이다.
	// 만약 isAnonymous()를 사용하면 익명인 사용자를 의미한다. isAuthenticated()는 로그인되어있으면 접근할 수 있게 해준다. 
	@PreAuthorize("isAnonymous()")
	@GetMapping("/member/find")
	public ModelAndView find() {
		return new ModelAndView("member/find");
	}

	// @ModelAttribute는 사용자가 뷰에 입력한 값을 서버로 보내서 저장시키는 역할이다. 
	// 클라이언트가 전송한 요청 매개변수를 자바 객체로 변환하는 어노테이션이다. 
	
	// @Valid는 데이터 유효성을 검사하는 것이다. MemberJoinDto가 정해놓은 규칙이 있으면 그 규칙에 맞는지 확인하는 것이다. 
	// BindingResult는 @Valio에서 검출된 오류들을 저장하는 역할을 한다. 
	@PreAuthorize("isAnonymous()")
	@PostMapping("/member/join")
	public ModelAndView join(@ModelAttribute @Valid MemberJoinDto dto, BindingResult br) {
		//System.out.println(dto);

		service.join(dto);
		return new ModelAndView("redirect:/member/login");
	}

	@Secured("ROLE_USER")
	@GetMapping("/member/check-password")
	public ModelAndView checkPassword(HttpSession session) {
		if (session.getAttribute("check") != null) {
			return new ModelAndView("redirect:/member/read");
		}
		return new ModelAndView("member/check-password");
	}

	@Secured("ROLE_USER")
	@PostMapping("/member/check-password")
	public ModelAndView checkPassword(String password, Principal principal, HttpSession session,
			RedirectAttributes ra) {
		Boolean result = service.checkPassword(password, principal.getName());
		if (result == true) {
			session.setAttribute("check", true);
			return new ModelAndView("redirect:/member/read");
		}
		ra.addFlashAttribute("msg", "비밀번호를 확인하세요");
		return new ModelAndView("redirect:/member/check-password");
	}

//	@Secured("ROLE_USER")
//	@PostMapping("/member/check-password")
//	public ModelAndView checkPassword(String password, Principal principal, HttpSession session,
//			RedirectAttributes ra) {
//		Boolean result = service.checkPassword(password, principal.getName());
//		if (result == true) {
//			session.setAttribute("check", true);
//			return new ModelAndView("redirect:/member/read");
//		}
//		ra.addFlashAttribute("msg", "비밀번호를 확인하세요!");
//		return new ModelAndView("redirect:/member/check-password");
//	}

	// @PreAuthorize("isAuthenticated()")
	// @Secured이 없으면 null 체크를 해야한다.
	@Secured("ROLE_USER")
	@GetMapping("/member/read")
	public ModelAndView read(Principal principal, HttpSession session) {
		// 비밀번호를 확인하면 세션에 check 값을 true로 설정
		if (session.getAttribute("check") == null) {
			return new ModelAndView("redirect:/member/check-password");
		}
		// 스프링 시큐리티 제공해주는 객체
		// Authentication : 로그인한 사용자의 모든 정보를 담은 객체
		// Principal : 로그인한 사용자의 아이디
		// 비로그인이라면 Principal은 null이 된다
		// 스프링 시큐리티가 제공하는 Principal : 로그인한 사용자의 아이디
		MemberReadDto member = service.read(principal.getName());
		return new ModelAndView("member/read").addObject("member", member);
	}

	// 검증 실패 시
	@ExceptionHandler(ConstraintViolationException.class)
	public ModelAndView CVEHanlder2(ConstraintViolationException e, RedirectAttributes ra) {
		// RedirectAttributes를 만들지 말고 그냥 있는 것을 사용하는게 좋다.
		ra.addFlashAttribute("msg", "프로필 사진을 제외한 모든 값은 필수입니다.");
		return new ModelAndView("redirect:/member/join");
	}
	/*
	 * @ExceptionHandler(ConstraintViolationException.class) public ModelAndView
	 * CVEHanlder2(ConstraintViolationException e) { String msg =
	 * e.getConstraintViolations().stream().findFirst().get().getMessage(); return
	 * new ModelAndView("redirect:/member/join").addObject("msg", msg); }
	 */

	@Secured("ROLE_USER")
	@PostMapping("/member/delete")
	public ModelAndView 탈퇴(Principal principal, HttpSession session) {
		// 로그아웃 후 삭제
		session.invalidate();
		service.delete(principal.getName());
		return new ModelAndView("redirect:/");
	}
}
