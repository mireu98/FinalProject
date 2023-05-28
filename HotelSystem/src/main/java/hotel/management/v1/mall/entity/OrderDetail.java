package hotel.management.v1.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
	private Integer orderNo;
	private String orderDate;
	private String contactDate;
	private Integer orderPrice;
	private Integer orderEA;
}
