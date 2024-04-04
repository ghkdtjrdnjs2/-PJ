package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.dto.BoardReadDto;
import com.example.demo.entity.Board;

@Mapper
public interface BoardDao {
	public Long save(Board board);

	@Select("select count(bno) from board")
	public Long count();

	public List<Board> findAll(Long startRownum, Long endRownum);

	public BoardReadDto findByBno(Long bno);

	@Select("select writer from board where bno=#{bno} and rownum=1")
	public String findWriterByBno(Long bno);

	@Select("update board set read_cnt=read_cnt+1 where bno=#{bno} and rownum=1")
	public void increaseReadCnt(Long bno);

	@Update("update board set title=#{title}, content=#{content} where bno=#{bno} and rownum=1")
	public Long update(String title, String content, Long bno);

	@Delete("delete from board where bno=#{bno} and rownum=1")
	public Long delete(Long bno);

	@Update("update board set good_cnt = good_cnt +1 where bno = #{bno} and rownum=1")
	public void increaseGoodCnt(Long bno);

	@Select("select good_cnt from board where bno=#{bno} and rownum=1")
	public Long findGoodCntByBno(Long bno);
}
