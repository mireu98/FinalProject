package hotel.management.v1.mall.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

public class OrdersDto {
	@Data
	@ToString
	public static class Order {
		private String pickupDay;
		private String[] tbodyArray;
	}

	@Data
	public static class OrderList {
		private Integer orderNo;
		private String itemName;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private LocalDate orderDate;
		private Integer orderEA;
	}

	@Data
	public static class OrderDetail {
		private String itemImage;
		private Integer orderNo;
		private String itemName;
		private Integer orderEA;
		private Integer orderPrice;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private LocalDate orderDate;
		@JsonFormat(pattern = "yyyy-MM-dd")
		private LocalDate pickupDay;
	}

	@Data
	@ToString
	public static class Items {
		private Integer itemea;
		private Integer itemno;
	}

}
