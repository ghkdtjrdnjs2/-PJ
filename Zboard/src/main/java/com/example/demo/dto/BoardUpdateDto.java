package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BoardUpdateDto {
	@NotNull(message="글번호를 입력하세요")
	private Long bno;
	@NotNull(message="제목을 입력하세요")
	private String title;
	@NotNull(message="내용을 입력하세요")
	private String content;
}
