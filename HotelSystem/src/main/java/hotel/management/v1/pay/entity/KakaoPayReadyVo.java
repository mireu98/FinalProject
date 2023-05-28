package hotel.management.v1.pay.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoPayReadyVo {
	// response
	private String tid;
	private String next_redirect_mobile_url;
	private String next_redirect_pc_url;
	private String partner_order_id;
}
