package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CommentReadDto;
import com.example.demo.service.CommentService;




@RestController
public class CommentController {
	
	@Autowired
	private CommentService service;
	
	// 댓글 작성
	// 댓글 작성한 댓글을 다시 뿌린다. 
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/comment/write")
	public ResponseEntity<List<CommentReadDto>> write(Long bno, String content, Principal principal){
		List<CommentReadDto> list = service.write(bno, content, principal.getName());
				return ResponseEntity.ok(list); 
	}
	
	// 댓글 삭제 -> 댓글을 다시 뿌린다. 
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/comment/delete")
	public ResponseEntity<List<CommentReadDto>> deleteByCno(Long bno, Long cno, Principal principal){
		List<CommentReadDto> list = service.deleteByCno(bno,cno, principal.getName());
		return ResponseEntity.ok(list); 
		
	}

}
