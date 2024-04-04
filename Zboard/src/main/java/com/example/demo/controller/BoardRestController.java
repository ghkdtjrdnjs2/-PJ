package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.BoardService;

@RestController
public class BoardRestController {

	@Autowired
	private BoardService service;

	// 추천
	@Secured("ROLE_USER")
	@PostMapping("/board/good")
	public ResponseEntity<Long> good(Long bno, Principal principal) {
		Long 추천수 = service.추천(bno, principal.getName());
		return ResponseEntity.ok(추천수);
	}
	
	// 비추
}
