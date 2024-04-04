package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CommentDao;
import com.example.demo.dto.CommentReadDto;
import com.example.demo.entity.Comment;

@Service
public class CommentService {

	
	@Autowired
	private CommentDao dao;
	
	public List<CommentReadDto> write(Long bno, String content, String loginId) {
		Comment c = new Comment(null, bno, content, loginId, null);
		dao.save(c);
		return dao.findByBno(bno);
	}

	public List<CommentReadDto> deleteByCno(Long bno,Long cno, String loginId) {
		System.out.println(bno);
		System.out.println(cno);
		String writer = dao.findWriterByCno(cno);
		if(writer.equals(loginId)==true) {
			dao.deleteByCno(cno);
		}
		return dao.findByBno(bno);
	}
}
