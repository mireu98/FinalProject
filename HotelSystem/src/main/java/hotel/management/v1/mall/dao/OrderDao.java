package hotel.management.v1.mall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import hotel.management.v1.mall.dto.OrdersDto;
import hotel.management.v1.mall.dto.OrdersDto.OrderList;
import hotel.management.v1.pay.entity.PayType;

@Mapper
public interface OrderDao {

	public Integer addOrder(String itemName, String username);

	public Integer addOrderDetail(Integer price, String pickupDay, Integer orderEA);

	public List<OrderList> findAllOrder(String username);

	public OrdersDto.OrderDetail findByOrderNo(Integer orderNo);

	public Boolean orderDelete(Integer orderNo);

	public Boolean orderDetailDelete(Integer orderNo);

	public Integer minusEA(String itemName, Integer orderEA);

	public Integer plusEA(String itemName, Integer orderEA);

	public Integer orderPay(String tid, String orderId, String itemName, Integer price, PayType payType);

	public List<OrdersDto.Items> findByItemNo();

}