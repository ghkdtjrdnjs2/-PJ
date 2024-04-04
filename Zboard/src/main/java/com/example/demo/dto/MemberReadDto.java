package com.example.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberReadDto {
	private String email;
	private LocalDate birthday;
	private LocalDate joinday;
	private Long days;
	private String profile;
}
