package hotel.management.v1.pay.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoPayCancelVo {
	String cid;
	String tid;
	Integer cancel_amount;
	Integer cancel_tax_free_amount;
}
