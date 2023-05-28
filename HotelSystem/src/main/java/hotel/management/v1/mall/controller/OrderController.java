package hotel.management.v1.mall.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hotel.management.v1.mall.service.OrderService;

@Controller
@RequestMapping("hotel")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@GetMapping("/mall/orderDetail")
	public ModelAndView orderDetail(Integer orderNo) {
		return new ModelAndView("hotel/mall/orderDetail").addObject("orderDetail",
				orderService.findByOrderNo(orderNo));

	}

	@GetMapping("/mall/orderList")
	public String list(Model model, Principal principal, RedirectAttributes re) {
		if (principal == null) {
			re.addFlashAttribute("msg", "로그인후 이용가능합니다.");
			return "redirect:/hotel/member/login";
		}
		model.addAttribute("orderlist", orderService.findAllOrder(principal.getName()));
		return "hotel/mall/orderList";
	}

}
