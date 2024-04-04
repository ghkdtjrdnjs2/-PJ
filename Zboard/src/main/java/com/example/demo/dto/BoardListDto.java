package com.example.demo.dto;

import lombok.Data;

@Data
public class BoardListDto {
	private Long bno;
	private String title;
	private String writer;
	private String writeTime;
	private Long readCnt;

}
