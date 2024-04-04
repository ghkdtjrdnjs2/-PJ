package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.MemberDao;
import com.example.demo.dto.MemberJoinDto;
import com.example.demo.dto.MemberReadDto;
import com.example.demo.entity.Member;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	@Autowired
	private PasswordEncoder encoder;

	// 자바 메일 발송 객체
	// 메일을 보내려면 SMTP서버가 필요 -> gmail을 이용해서 메일을 보내자
	@Autowired
	private JavaMailSender mailSender;

	// 저장할 폴더
	@Value("c:/upload/profile/")
	private String PROFILE_FOLDER;
	
	// 기본 프로필 사진
	@Value("default.png")
	private String DEFAULT_PROFLIE;
	
	// 주소
	@Value("http://localhost:8081/profile/")
	private String PROFILE_URL;

	public Boolean idCheck(String username) {
		return !memberDao.existsByUsername(username);
	}

	public Boolean join(MemberJoinDto dto) {
		MultipartFile mf = dto.getProfile();
		String profileName = DEFAULT_PROFLIE;

		// 사진을 업로드하지 않은 경우
		// <input type="file" name="profile>이 비어있는 상태로 업로드된다.
		// 이 경우 MultipartFile은 null이 아니라 비어있다. -> isEmpty()
		if (mf.isEmpty() == false) {
			String 확장자 = FilenameUtils.getExtension(mf.getOriginalFilename());
			// 저장하기 전에 파일이름을 미리 수정하는 것
			String 저장이름 = UUID.randomUUID().toString() + "." + 확장자;
			System.out.println(저장이름);

			// 사진을 저장하기위해 파일 객체를 생성
			File file = new File(PROFILE_FOLDER, 저장이름);

			// 예외처리
			// try ~ catch : 개발자가 처리
			// throws : 스프링이 처리 -> 작업이 실패했어요

			try {
				// 업로드된 파일을 file에 저장한다. 
				mf.transferTo(file);
				profileName = 저장이름;
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		String 암호화비밀번호 = encoder.encode(dto.getPassword());
		Member member = dto.toEnity(암호화비밀번호, PROFILE_URL + profileName);
		return memberDao.save(member) == 1L;
	}

	public String findId(String email) {
		return memberDao.findUsernameByEmail(email);
	}

	// 보내는 사람, 받는 사람, 제목, 내용
	// 메일을 직접 보내는 것이 아니라 gmail을 이용해 간접적으로 보내기 때문에 
	// from은 여러분의 이메일 아이디로 바뀐다. 
	// 역시 같은 이유로 이메일 발송에 실패해도 우리는 알 수가 없다. 
	// Mime : 이메일 보내는 표준 
	private Boolean sendMail(String from, String to, String title, String text) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			// MimeMessageHelper는 메일의 다양한 속성을 설정할 수 있도록 도와주는 클래스
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(title);
			// html 활성화하도록 설정 : <a href="/aaa">aaa</a>
			helper.setText(text, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return true;
	}

	// 실패 : 아이디가 틀렸다, 비밀번호가 틀렸다. 
	public Boolean resetPassword(String username) {
		Member m = memberDao.findByUsername(username);
		if(m==null) {
			return false;
		}
		String 임시비밀번호 = RandomStringUtils.randomAlphanumeric(20);
		String 암호화비밀번호 = encoder.encode(임시비밀번호);
		memberDao.changePassword(암호화비밀번호, username);
		
		return sendMail("admin", m.getEmail(), "임시비밀번호", "임시비밀번호 : " + 임시비밀번호 );
	}

	public MemberReadDto read(String loginId) {
		Member member = memberDao.findByUsername(loginId);
		return member.toReadDto();
	}

	public Boolean checkPassword(String password, String loginId) {
		String 암호화비밀번호 = memberDao.findByUsername(loginId).getPassword();
		//비밀번호 비교
		return encoder.matches(password, 암호화비밀번호);
	}

	public Boolean delete(String loginId) {
		return memberDao.delete(loginId) ==1L;
	}
	
	public Boolean changeEmail(String email, String loginId) {
		return memberDao.changeEmail(email, loginId) ==1L;
	}
}
