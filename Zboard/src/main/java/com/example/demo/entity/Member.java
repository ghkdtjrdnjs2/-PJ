package com.example.demo.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.example.demo.dto.MemberReadDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Member {
	private String username;
	private String password;
	private String email;
	private LocalDate birthday;
	private LocalDate joinday = LocalDate.now();
	private String profile;
	
	// User와 Admin으로 구성된 enum
	private Role role = Role.USER;

	public MemberReadDto toReadDto() {
		// ChronoUnit은 날짜 계산하는 클래스 
		Long days = ChronoUnit.DAYS.between(joinday, LocalDate.now());
		return new MemberReadDto(email, birthday, joinday, days, profile);
	}
}