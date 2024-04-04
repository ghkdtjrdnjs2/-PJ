package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.BoardReadDto;
import com.example.demo.dto.BoardUpdateDto;
import com.example.demo.dto.BoardWriteDto;
import com.example.demo.dto.Page;
import com.example.demo.service.BoardService;

import jakarta.validation.Valid;

// 전체 검증을 활성화
@Validated
@Controller
public class BoardController {
	@Autowired
	BoardService service;

	// B-001 글 읽기
	@GetMapping("/")
	public ModelAndView list(@RequestParam(defaultValue = "1") Long pageno) {
		Page page = service.findAll(pageno);
		return new ModelAndView("board/list").addObject("page", page);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/board/write")
	public void write() {
	}

	// B-002. 글 작성
	@Secured("ROLE_USER")
	@PostMapping("/board/write")
	// Authentication auth -> 사용자의 모든 것을 가져온다.
	public ModelAndView write(@Valid BoardWriteDto dto, BindingResult br, Principal principal) {
		// System.out.println(dto);

		// dao가 생성하는 값을 리턴받아보자.
		Long bno = service.save(dto, principal.getName());
		System.out.println(bno);
		return new ModelAndView("redirect:/");
	}

	// B-003. 글읽기
	@GetMapping("/board/read")
	public ModelAndView read(Long bno, Principal principal) {
		String loginId = principal == null ? null : principal.getName();
		BoardReadDto dto = service.read(bno, loginId);

		if (dto == null) {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("board/read").addObject("dto", dto);
	}

	// B-004. 글변경(작성자인 경우만 변경 가능)
	@Secured("ROLE_USER")
	@PostMapping("/board/update")
	public ModelAndView update(@ModelAttribute @Valid BoardUpdateDto dto, BindingResult br, Principal principal, RedirectAttributes ra) {
		Boolean result = service.update(dto, principal.getName());
		if (result == false) {
			ra.addFlashAttribute("msg", "잘못된 작업입니다.");
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView("redirect:/board/read?bno=" + dto.getBno());
	}

	// B-005. 글삭제(작성자인 경우만 삭제 가능)
}
