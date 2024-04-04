package com.example.demo.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Member;
import com.example.demo.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MemberJoinDto {
	@NotEmpty(message = "아이디는 필수입력입니다. ")
	@Pattern(regexp = "^[A-Za-z0-9]{6,10}$", message = "영숫자 6~10자 입니다.")
	private String username;

	@NotEmpty(message = "비밀번호는 필수입력입니다. ")
	@Pattern(regexp = "^[A-Za-z0-9]{8,10}$", message = "영숫자 8~10자 입니다.")
	private String password;

	@NotEmpty(message = "이메일은 필수입력입니다. ")
	@Email(message = "잘못된 이메일입니다.")
	private String email;

	// html로 날짜를 선택하면 2020-10-20 형식의 문자열
	// @Pattern(regexp="[0-9]{4}-[0-9]{2}-[0-9]{2}")
	@NotNull(message = "생일은 필수입력입니다. ")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;

	// 사진을 받아올 것이다.
	private MultipartFile profile;

	public Member toEnity(String 암호화비밀번호, String profile) {
		return new Member(username, 암호화비밀번호, email, birthday, LocalDate.now(), profile, Role.USER);
	}

	// dto로 빼지 않으면 싸울 확률이 높아진다.

}
