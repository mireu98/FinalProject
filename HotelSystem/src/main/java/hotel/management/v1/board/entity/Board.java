package hotel.management.v1.board.entity;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonFormat;

import hotel.management.v1.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 관리자 = 김동욱

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Board {
	
	private Integer boardNo;
	private String username;
	@JsonFormat(pattern = "MM/dd")
	private LocalDate writeDay;
	private String replyContent;
	@JsonFormat(pattern = "MM/dd")
	private LocalDate replywriteDay;
	private String title; 
	private String content;	
	
	
	
}
