package hotel.management.v1.board.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import hotel.management.v1.board.dao.BoardDao;
import hotel.management.v1.board.dto.BoardDto;
import hotel.management.v1.board.entity.Board;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

//관리자 = 김동욱

@Service
public class BoardService {
	@Autowired
	private BoardDao dao;
	
	@Autowired
	private JavaMailSender mailSender;
	
	private final static Integer PAGESIZE = 10;
	private final static Integer BLOCKSIZE = 5;
	
	//게시물을 작성하기 위한 메소드
	public void write(BoardDto.Write dto, String username) throws IllegalArgumentException {
			Board board = dto.toEntity(dto.getTitle(),dto.getContent(), username);
			dao.write(board);
	}

	//NoSuchElementException
	public List<Board> list() {
		List<Board> list = dao.list();
		return list;
	}
	//게시물을 읽기 위해 번호를 찾기 위한 메소드
	public Board findByNo(Integer boardNo) {
			return dao.findByNo(boardNo);
	}
	
	//리스트에서 페이징네이션처리를 하기 위한 메소드
	//NoSuchElementException
	public BoardDto.Pagination pagination(Integer pageno) {
		Integer boardCnt = dao.count();
		Integer boardPageCnt = (boardCnt - 1) / PAGESIZE + 1;
		String msg = "";
		if (boardPageCnt<pageno||pageno==null) {
			 msg = "잘못된경로";
		}
		pageno = Math.abs(pageno);
		if(pageno>boardPageCnt)
			pageno = boardPageCnt;
		Integer startRownum = (pageno-1) * PAGESIZE;
		Integer endRownum = startRownum + PAGESIZE + 1; 
		List<BoardDto.FindAll> board = dao.findAll(startRownum, endRownum);
		Integer prev = (pageno - 1) / BLOCKSIZE * BLOCKSIZE;
		Integer start = prev + 1;
		Integer end = prev + BLOCKSIZE;
		Integer next = end + 1;
		if (end >= boardPageCnt) {
			end = boardPageCnt;
			next = 0;
		}
		prev = prev == 0 ? start : prev;
		next = next == 0 ? end : next;
		return new BoardDto.Pagination(prev, start, end, next, board, msg);
		}

	//관리자의 답변을 달기 위한 메소드
	public void replyUpdate(Integer boardNo, String replyContent, String username) {
		String email = dao.findMail(username);
		dao.update(boardNo , replyContent);
		sendMail("admin@zmall.com",email,"회원님의 문의사항 답변이 달렸습니다.","홈페이지를 통해 확인해주세요.");
	}
	
	//게시물을 삭제하는 메소드
	public Integer delete(String boardNo) {
		int intBoardNo = Integer.parseInt(boardNo);
			Board board = dao.findByNo(intBoardNo);
			if(board.getBoardNo().equals(intBoardNo)) {
				dao.delete(intBoardNo);
			}
			return intBoardNo;
		}
	
	
	// 이메일을 발송하기 위한 메소드
	private void sendMail(String from, String to, String title, String text) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(title);
			helper.setText(text, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}