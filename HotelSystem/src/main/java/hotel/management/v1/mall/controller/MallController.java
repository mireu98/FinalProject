package hotel.management.v1.mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hotel.management.v1.mall.dto.OrdersDto;
import hotel.management.v1.mall.service.OrderService;

@Controller
@RequestMapping("hotel")
public class MallController {
	@Autowired
	private OrderService orderService;

	@GetMapping("/mall/itemList")
	public ModelAndView item() {
		List<OrdersDto.Items> items = orderService.findByItemNo();
		return new ModelAndView("hotel/mall/itemList").addObject("list", items);
	}

}
