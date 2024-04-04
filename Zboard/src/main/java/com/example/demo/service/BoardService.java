package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.BoardDao;
import com.example.demo.dao.CommentDao;
import com.example.demo.dao.MemberBoarDao;
import com.example.demo.dto.BoardReadDto;
import com.example.demo.dto.BoardUpdateDto;
import com.example.demo.dto.BoardWriteDto;
import com.example.demo.dto.Page;
import com.example.demo.entity.Board;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private MemberBoarDao memberBoardDao;

	@Value("10")
	private Long BOARD_PER_PAGE;
	@Value("5")
	private Long PAGES_PER_BLOCK;

	// 이 함수는 읽기만 한다 -> 데이터를 보호할 필요가 없다.
	// 서비스에 걸어야한다. @Transactional를 걸기 위한 곳이다.
	@Transactional(readOnly = true)
	public Page findAll(Long pageno) {
		// 1 -> 1~ 10, 2-> 11~20.....
		Long startRownum = (pageno - 1) * BOARD_PER_PAGE + 1;
		Long endRownum = startRownum + BOARD_PER_PAGE - 1;

		List<Board> list = boardDao.findAll(startRownum, endRownum);
		Long count = boardDao.count();

		// 페이지의 개수
		// 118->12, 119->12, 120-> 12, 121->13
		Long countOfPage = count / BOARD_PER_PAGE + 1;

		/*
		 * pageno prev start end next 1~5 0 1 5 6 6~10 5 6 10 11 11~13 10 11 13 0
		 */

		Long prev = ((pageno - 1) / PAGES_PER_BLOCK) * PAGES_PER_BLOCK;
		Long start = prev + 1;
		Long end = prev + PAGES_PER_BLOCK;
		Long next = end + 1;

		// pageno가 11~13일 경우 end는 15, next는 16
		// end, next가 페이지의 개수보다 같거나 큰 경우를 보정
		if (end >= countOfPage) {
			end = countOfPage;
			next = 0L;
		}
		return new Page(prev, start, end, next, pageno, list);
	}

	public Long save(BoardWriteDto dto, String loginId) {
		Board board = new Board(null, dto.getTitle(), dto.getContent(), loginId, LocalDateTime.now(), 0L, 0L, 0L);
		boardDao.save(board);
		// selectKey : 마이바티스가 자동 생성한 값을 엔티티에 담아준다.
		return board.getBno();
	}

	// 글번호와 로그인 아이디를 전달받는다.
	// 로그인 아이디는 null일 수 있다(비로그인)

	// 글이 없으면 return null;
	// 글이 있고 (비로그인 또는 작성자) return BoardReadDto
	// 글이 있고 (작성자가 아니라면) 조회수 증가시키고 조회수 증가시키고 return BoardReadDto

	// 1. 글이 있다. + 2. 로그인했다. + 3. 작성자가 아니다. => 읽기회수 증가
	public BoardReadDto read(Long bno, String loginId) {
		// 글이 존재하는지 확인
		String writer = boardDao.findWriterByBno(bno);
		// 1. 글이 없으면 null 리턴
		if (writer == null)
			return null;
		// 로그인 했고 작성자가 아니라면 조회수 증가
		if (loginId != null && loginId.equals(writer) == false) {
			boardDao.increaseReadCnt(bno);
		}
		BoardReadDto dto = boardDao.findByBno(bno);
		dto.setComments(commentDao.findByBno(bno));
		return dto;
	}

	// 사람이 생각하는 논리적인 작업은 여러줄의 sql : transaction
	// 물건을 산다 -> 돈을 준다 + 물건을 받는다 + 거스름돈을 받는다.
	// transaction을 구성하는 동작은 모두 성공하거나 모두 실패해야 한다.
	@Transactional
	public Boolean delete(Long bno, String loginId) {
		String writer = boardDao.findWriterByBno(bno);
		if (writer == null) {
			return false;
		}
		if (writer.equals(loginId) == false) {
			return false;
		}
		commentDao.deleteByBno(bno);
		return boardDao.delete(bno) == 1L;
	}

	public Boolean update(BoardUpdateDto dto, String loginId) {
		String writer = boardDao.findWriterByBno(dto.getBno());
		if (writer == null)
			return false;
		if (writer.equals(loginId) == false)
			return false;
		return boardDao.update(dto.getTitle(), dto.getContent(), dto.getBno()) == 1L;

	}

	public Long 추천(Long bno, String loginId) {
		// 글을 추천하면 MemberBoardDao를 이용해 이미 추천/비추한 글인 확인
		// 새로운 추천/비추면
		// MemberBoardDao로 아이디 - 글번호를 저장
		// BoardDao로 추천수 증가
		// BoardDao에서 추천수를 읽어서 리턴
		Boolean exist = memberBoardDao.existsByUsernameAndBno(loginId, bno);
		if (exist == false) {
			memberBoardDao.save(loginId, bno);
			boardDao.increaseGoodCnt(bno);
		}
		return boardDao.findGoodCntByBno(bno);
	}
}
