package com.example.demo.dto;

import lombok.Data;

@Data
public class CommentReadDto {
	private Long cno;
	private String content;
	private String writer;
	private String writeTime;
}
