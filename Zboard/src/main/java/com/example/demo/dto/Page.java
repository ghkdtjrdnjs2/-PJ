package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.Board;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Page {
	private Long prev;
	private Long start;
	private Long end;
	private Long next;
	private Long pageno;
	private List<Board> list;
}
