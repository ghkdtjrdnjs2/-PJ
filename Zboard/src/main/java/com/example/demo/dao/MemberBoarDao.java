package com.example.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberBoarDao {
	@Select(" select count(*) from member_board where username = #{username} and bno =#{bno} and rownum = 1 ")
	public Boolean existsByUsernameAndBno(String username, Long bno);
	
	@Insert("insert into member_board(username,bno) values(#{username}, #{bno})")
	public Long save(String username, Long bno);

}
