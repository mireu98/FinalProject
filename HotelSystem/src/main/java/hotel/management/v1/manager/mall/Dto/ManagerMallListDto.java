package hotel.management.v1.manager.mall.Dto;

import java.time.LocalDate;
import java.util.List;

import hotel.management.v1.board.dto.BoardDto;
import hotel.management.v1.board.dto.BoardDto.FindAll;
import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

public class ManagerMallListDto {

	@Data
	@AllArgsConstructor
	@Getter
	@Builder
	@ToString
	public static class MallListSearch {
		private String username;
		@NonNull
		private String tel;
		@NonNull
		private String name;
		@NonNull
		private Integer orderNo;
		private LocalDate orderDate;
		private LocalDate contactDate;
		private Integer orderPrice;
		private Integer orderEa;
	}

	@Data
	@AllArgsConstructor
	@Builder
	@ToString
	public static class FindMallList {
		private String name;
		private String tel;
		private Integer orderNo;
	}

	@Data
	@AllArgsConstructor
	@Builder
	@ToString
	public static class orderDetail {
		private Integer orderNo;
		private String name;
		private String tel;
		private Integer itemNo;
		private String itemName;
		private Integer orderPrice;
		private Integer orderEa;
	}

	@Data
	@ToString
	public static class ItemUpdate {
		private Integer itemno;
		private String itemname;
		private Integer itemea;
	}
}
