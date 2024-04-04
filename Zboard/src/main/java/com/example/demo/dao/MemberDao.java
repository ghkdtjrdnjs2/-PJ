package com.example.demo.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.Member;

@Mapper
public interface MemberDao {
	// 아이디가 존재하는가?
	// 리턴이 Boolean : 결과가 0이면 거짓, 1이상이면 참 
	@Select("select count(*) from member where username=#{username} and rownum=1")
	public Boolean existsByUsername(String username);
	
	
	public Long save(Member member);
	
	// 이메일로 아이디 찾기
	@Select("select username from member where email=#{email} and rownum=1")
	public String findUsernameByEmail(String email);
	
	// 비밀번호 변경 : 비밀번호 찾기, 비밀번호 변경에서 사용
	@Update("update member set password=#{password} where username=#{username} and rownum=1")
	public Long changePassword(String password, String username);
	
	@Select("select * from member where username=#{username} and rownum=1")
	public Member findByUsername(String username);
	
	// 이메일 변경 : 변겨기능은 이메일 변경만 만들겠다. 
	@Update("update member set email=#{email} where username=#{username} and rownum=1")
	public Long changeEmail(String email, String username);
	
	// 삭제 : 회원 탈퇴에서 사용
	@Delete("delete from member where username=#{username} and rownum=1")
	public Long delete(String username);
}
