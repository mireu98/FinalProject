package hotel.management.v1.pay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import hotel.management.v1.pay.entity.PayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

public class PayDto {
	@Data
	public static class payment {
		String tid;
		String partner_order_id;
		Integer total_amount;
		Integer bookno;
		Integer orderno;
	}

	@Data
	@AllArgsConstructor
	public static class bookAddPayment {
		String tid;
		String orderid;
		String itemname;
		Integer amount;
		PayType type;
	}


	@Data
	@ToString
	public static class kakaoexecption{
		@JsonProperty("msg")
		String msg;
		@JsonProperty("code")
		Integer code;
	}


}
