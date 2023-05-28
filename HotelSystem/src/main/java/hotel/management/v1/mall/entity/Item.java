package hotel.management.v1.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	private Integer itemNo;
	private String itemName;
	private Integer itemPrice;
	private Integer itemEA;
	private String mallName;
	private String itemImage;
}
