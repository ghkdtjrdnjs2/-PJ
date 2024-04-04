package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.CommentReadDto;
import com.example.demo.entity.Comment;

@Mapper
public interface CommentDao {

@Insert("insert into comments(bno,cno,writer,content, write_time) values(#{bno}, comments_seq.nextval, #{writer},#{content}, sysdate)")
public Long save(Comment comment);

@Select("select cno, writer, content, to_char(write_time, 'YYYY-MM-DD HH24:MI:SS') as writeTime from comments where bno=#{bno} order by cno desc")
public List<CommentReadDto> findByBno(Long bno);

@Delete("delete from comments where cno=#{cno} and rownum=1")
public Long deleteByCno(Long cno);

@Delete("delete from comments where bno=#{bno}")
public Long deleteByBno(Long bno);

@Select("select writer from comments where cno=#{cno} and rownum=1")
public String findWriterByCno(Long cno);
}
