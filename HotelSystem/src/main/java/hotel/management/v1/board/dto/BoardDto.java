package hotel.management.v1.board.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import hotel.management.v1.board.entity.Board;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

// 관리자 = 김동욱

public class BoardDto {

	@Data
	@Builder
	@AllArgsConstructor
	public static class Write {
		private String title;
		private String content;
		private String username;
		
		public Board toEntity(String title, String content, String username) {
			return Board.builder().title(title).content(content).username(username).build();
		}
	}
	
	@Data
	@Builder
	public static class Update{
		@NotNull
		private Integer boardNo;
		@NotEmpty
		private String title;
		@NotEmpty
		private String content;
	}
	
	@Data
	@Builder
	@AllArgsConstructor
	@ToString
	public static class Pagination{
		private Integer prev; 
		private Integer start;
		private Integer end;
		private Integer next;
		private List<FindAll> board;
		private String msg;
	}
	
	@Data
	@Builder
	@AllArgsConstructor
	@ToString
	public static class FindAll {
		private Integer rnum;
		private Integer boardNo;
		private String username;
		@JsonFormat(pattern = "MM/dd")
		private LocalDate writeDay;
		private String title; 
	}
}