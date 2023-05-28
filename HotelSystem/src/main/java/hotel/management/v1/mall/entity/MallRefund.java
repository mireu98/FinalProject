package hotel.management.v1.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MallRefund {
	private Integer orderNo;
	private String refundReason;
}
