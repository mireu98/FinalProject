package hotel.management.v1.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import hotel.management.v1.board.dto.BoardDto;
import hotel.management.v1.board.entity.Board;

	// 관리자 = 김동욱

@Mapper
public interface BoardDao {

	public void write(Board board);
	
	public List<Board> list();

	public String findMail(String username);
	
	public Board findByNo(Integer boardNo);
	
	public Integer count();
	
	public void update(Integer boardNo, String replyContent);
	
	public List<BoardDto.FindAll> findAll(Integer startRownum, Integer endRownum);
	
	public Integer delete(Integer boardNo);
	}