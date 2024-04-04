package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dao.BoardDao;
import com.example.demo.entity.Board;


@SpringBootTest
public class BoardDaoTest {
	@Autowired
	private BoardDao dao;
	
	//@Test
	public void countTest() {
		dao.count();
	}
	
	//@Test
	public void saveTest() {
		// Collections, Arrays...클래스 이름뒤에 s
		// asList로 생성하면 읽기 전용 리스트 
		List<String> writers = Arrays.asList("spring","summer","winter");
		for(int i=0;i<123; i++) {
			dao.save(new Board(null, i+"번째글", "내용없음", writers.get(i%3), LocalDateTime.now(), 0L, 0L, 0L));
		}
	}
	//@Test
	public void findAllTest() {
		assertEquals(10, dao.findAll(11L, 20L).size());
	}
	@Test
	public void readTest() {
		System.out.println(dao.findByBno(142L));
	}
}
