package hotel.management.v1.manager.dto;

import java.sql.Date;
import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class ManagerDto {
    @Data
    @ToString
    @Builder
	public static class findBookList {
        private Integer bookNo;
        private Integer rbBookNo;
        private String booker;
        private String bookTel;
        private Integer totalCount;
        private Integer roomNo;
        private LocalDate bookDate;
        private String bookRoomGrade; //gradename이 예약에 있어야한다.
        private LocalDate checkIn;
        private LocalDate checkOut;
        private Integer breakfast;
        private Integer resNo;
        private String bookStatus;
    }

    @Data
    @ToString
    @Builder
	public static class findUserList {
        private String name;
        private String tel;
        private String email;
        private String username;
        private Boolean black;
        private Boolean VIP;
    }
    
    @Data
    @ToString
    @Builder
	public static class bookSearchCondition {
        private Boolean isStay; //roombooking (bookroomgrade null)
        private Boolean isRestaurant; //dinner
        private String fromDate; //roombooking
        private String toDate; //roombooking
        private Boolean todayCheckBox;
        private Integer roomNum; //roombooking
        private String name; //book -> booker
        private Integer listType; //           
		
    }

    

    @Data
    @ToString
    @Builder
	public static class findBookNoCount {
        private Integer bookNo;
        private Integer totalCount;
    }

    @Data
    @ToString
    @Builder
	public static class roomList {
        private Integer roomNo;
        private String gradeName;
        private String roomStatus;
    }

    @Data
    @ToString
    @Builder
	public static class userDetail {
        private String userName;
        private String name;
        private String tel;
        private String email;
        private String personalId;
        private Boolean disabled;
        private String userLevel;
        private Integer bookCount;
        private Boolean black;
        private Boolean vip;
    }

    @Data
    @ToString
    @Builder
	public static class alarm {
        private String booker;
        private String booktel;
        private Integer roomno;
    }
}
