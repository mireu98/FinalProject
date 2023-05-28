package hotel.management.v1.pay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TossPayVo {
	private String orderId;
	private String paymentKey;
	private Integer amount;
	private String username;
}
