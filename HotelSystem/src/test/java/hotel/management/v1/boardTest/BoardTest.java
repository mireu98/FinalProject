package hotel.management.v1.boardTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hotel.management.v1.board.dao.BoardDao;
import hotel.management.v1.board.dto.BoardDto;

@SpringBootTest
public class BoardTest {

	@Autowired
	private BoardDao dao;
	
//	@Test
//	public void test() {
//		BoardDto.Write write= new BoardDto.Write("aa","aaa","aaa");
//		for (int i = 0; i < 100; i++) {
//			dao.write(write);
//		}
//	}
	
}
