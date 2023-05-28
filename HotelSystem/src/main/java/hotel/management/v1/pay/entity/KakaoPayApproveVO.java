package hotel.management.v1.pay.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoPayApproveVO {
	private String aid;
	private String tid;
	private String partner_order_id;
	private String partner_user_id;
	private String payment_method_type;
	private LocalDateTime approved_at;
}
