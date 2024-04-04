package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.service.BoardService;

@SpringBootTest
public class BoardServiceTest {
	@Autowired
	private BoardService service;
	
	@Test
	public void findAllTest() {
		System.out.println(service.findAll(1L));
		System.out.println(service.findAll(6L));
		System.out.println(service.findAll(11L));
	}
}
