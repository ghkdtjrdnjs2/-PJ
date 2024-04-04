package com.example.demo.entity;

import java.time.*;

import lombok.*;

@Data
@AllArgsConstructor
public class Comment {
	private Long cno;
	private Long bno;
	private String content;
	private String writer;
	private LocalDateTime writeTime;
	
}
