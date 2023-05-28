package hotel.management.v1.pay.controller;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hotel.management.v1.pay.dto.PayDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hotel.management.v1.mall.service.OrderService;
import hotel.management.v1.pay.entity.KakaoPayApproveVO;
import hotel.management.v1.pay.entity.KakaoPayReadyVo;
import hotel.management.v1.pay.entity.PayType;
import hotel.management.v1.pay.service.PayService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.client.HttpClientErrorException;

@Controller
public class PayController {
	@Autowired
	private PayService payService;

	@Autowired
	private OrderService orderService;

	@GetMapping("/pay")
	public String ready() {
		return "pay/ready";
	}

	@GetMapping("/pay/start")
	public @ResponseBody KakaoPayReadyVo kakaoPay(@RequestParam Map<String, Object> params, HttpSession session,
			Principal pal) {
		String uuid = UUID.randomUUID().toString();
		String[] tbodyArray = (String[]) session.getAttribute("tbodyArray");
		KakaoPayReadyVo res = payService.kakaoPay(params, uuid, pal.getName(),tbodyArray);
		session.setAttribute("partner_order_id", uuid);
		session.setAttribute("tid", res.getTid());
		return res;
	}

	@GetMapping(value = "/pay/success", produces = MediaType.APPLICATION_JSON_VALUE)
	public String Success(@RequestParam("pg_token") String pgToken, HttpSession session, Principal principal) {
		String pickupDay = (String) session.getAttribute("pickupDay");
		String[] tbodyArray = (String[]) session.getAttribute("tbodyArray");

		if (tbodyArray != null) {
			orderService.mallOrder(tbodyArray, pickupDay, principal.getName(), (String) session.getAttribute("tid"),
					(String) session.getAttribute("partner_order_id"), PayType.KAKAO);
			return "hotel/luxurymallcomplete";
		}
		KakaoPayApproveVO res = payService.kakaoPayApprove(pgToken, session, principal.getName());
		session.removeAttribute("tid");
		session.removeAttribute("partner_order_id");
		session.removeAttribute("pickupDay");
		session.removeAttribute("tbodyArray");
		return "hotel/reservationcomplete";
	}

	@GetMapping("/pay/toss_success")
	public String tosssuccess(@RequestParam("orderId") String orderId, @RequestParam("paymentKey") String paymentKey,
			@RequestParam("amount") Integer amount, Principal principal, HttpSession session) {
		String gradename = (String) session.getAttribute("gradename");
		String pickupDay = (String) session.getAttribute("pickupDay");
		String[] tbodyArray = (String[]) session.getAttribute("tbodyArray");
		if (tbodyArray != null) {
			orderService.mallOrder(tbodyArray, pickupDay, principal.getName(), paymentKey, orderId, PayType.TOSS);
			return "hotel/luxurymallcomplete";
		}
		payService.tossPayApprove(orderId, paymentKey, amount, gradename);
		session.removeAttribute("gradename");

		return "hotel/reservationcomplete";
	}

	@GetMapping("/pay/cancel")
	public String Cancel() {
		return "redirect:/hotel/main";
	}

	@GetMapping("/pay/fail")
	public String Fail() {
		return "redirect:/hotel/mall/itemlist";
	}

	@PostMapping("/pay/cancel_do")
	public ResponseEntity<?> canclePay(Integer bookno, Integer orderno) throws JsonProcessingException {
		PayDto.payment payment = payService.findBypayment(bookno, orderno);
		payService.deletepayment(payment.getBookno(), payment.getOrderno());
		if (orderno != null && bookno == null) {
			payService.findAndCancelOrder(orderno);
		}
		if (orderno == null && bookno != null) {
			payService.findAndDeleteByBookByBookno(bookno);
		}
		try {
			payService.canclePay(payment);
		} catch (HttpClientErrorException.BadRequest e) {
			String kakao = e.getResponseBodyAsString();

			ObjectMapper objectMapper = new ObjectMapper();
			PayDto.kakaoexecption response = objectMapper.readValue(kakao, PayDto.kakaoexecption.class);
			String msg = response.getMsg();
			Integer code = response.getCode();
			if (code ==-404){
				System.out.println("404발생");
				return ResponseEntity.ok(null);
			}

			return ResponseEntity.ok(null);
		}
		return ResponseEntity.ok(null);
	}

}
