package hotel.management.v1.client.book.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import hotel.management.v1.pay.entity.PayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class BookDto {
	@Data
	 public static class addbookfromDinner {
		Integer totalcnt;
		String from;
		String booktel;
		String booker;
	}
	@Data
	@AllArgsConstructor
	public static class adddinner{
		Integer resno;
		Integer rescnt;
		Integer resprice;
	}
	@Data
	@AllArgsConstructor
	public static class whtyouname {
		String username;
		String booktel;
		String booker;
		String from;
		String to;
	}
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class data {
		String username;
		Integer bookno;
	}
	@Data
	@AllArgsConstructor
	public static class addRoomBook {
		String checkOut;
		Boolean bfcheckbox;
		String gradename;
		String username;
		String checkin;
	}
	
	@Data
	@AllArgsConstructor
	public static class addbook {
		String username;
		String from;
		Integer totalcnt;
		String booktel;
		String booker;
	}
	@Data 
	public static class managerbook{
		String username;
		String from;
		String to;
		String gradename;
		Integer totalcnt;
		String booker;
		String booktel;
		Boolean bfcheckbox;
		Boolean dicheckbox;
	}
	@Data
	public static class checkbook{
		Integer bookno;
		Integer roombookno;
		Integer resno;
	}
	
	@Data
	@ToString
	@AllArgsConstructor
	public static class book {
		String from;
		String to;
		Integer totalcnt;
		String gradename;
		Boolean bfcheckbox;
		Boolean dicheckbox;
		String booker;
		String booktel;
		Integer paycode;
		PayType paytype;
		
	}
	@Data
	@ToString
	public static class dinnerbook{
		String bookdate;
		String booktel;
		Integer totalcnt;
		String booker;
	}
	
	@Data
	@ToString
	public static class findRoom{
		String gradeName;
		Boolean reservableOrNot;
		Integer breakfastprice;
		Integer gradeprice;
	}

	@Data
	@AllArgsConstructor
	public static class dinner{
		String username;
		Integer bookno;
		String from;
	}
	@Data
	public static class finduser {
		String username;
		String userlevel;
		String tel;
	}
	@Data
	 public static class mypagedinner {
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate checkin;
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate checkout;
		String booker;
		String booktel;
		String bookroomgrade;
		Integer bookno;
	}
	
	@Data
	public static class myInFo {
		String name;
		String tel;
	}
	
	@Data
	public static class checkbookbyusername{
		String from;
		String to;
		String username;
	}
	
	
	@Data
	public static class managercheckroom {
		String from;
		String to;
	}


	@Data
	@ToString
	public static class mydinnerlist{
		String booker;
		@JsonFormat(pattern = "yyyy-MM-dd")
		LocalDate bookdate;
		String booktel;
		Integer resno;
	}
	@Data
	@ToString
	public static class managerdinnerbook{
		String booker;
		String from;
		Integer totalcnt;
	}
}
