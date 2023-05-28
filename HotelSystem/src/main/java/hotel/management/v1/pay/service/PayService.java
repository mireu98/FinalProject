package hotel.management.v1.pay.service;

import java.util.Map;

import hotel.management.v1.client.book.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import hotel.management.v1.pay.dao.PayDao;
import hotel.management.v1.pay.dto.PayDto;
import hotel.management.v1.pay.dto.PayDto.bookAddPayment;
import hotel.management.v1.pay.entity.KakaoPayApproveVO;
import hotel.management.v1.pay.entity.KakaoPayCancelVo;
import hotel.management.v1.pay.entity.KakaoPayReadyVo;
import hotel.management.v1.pay.entity.PayType;
import hotel.management.v1.pay.entity.TossPayVo;
import jakarta.servlet.http.HttpSession;

@Service
public class PayService {
	@Autowired
	private PayDao dao;

	public KakaoPayReadyVo kakaoPay(Map<String, Object> params, String uuid, String username, String[] tbodyArray) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK a9f2ff79213b981cd3e1ade179808e25");

		MultiValueMap<String, Object> payParams = new LinkedMultiValueMap<String, Object>();
		
		payParams.add("cid", "TC0ONETIME");
		payParams.add("partner_order_id", uuid);
		payParams.add("partner_user_id", username);
		payParams.add("item_name", params.get("item_name"));
		payParams.add("quantity", params.get("quantity"));
		payParams.add("total_amount", params.get("total_amount"));
		payParams.add("tax_free_amount", params.get("tax_free_amount"));
		payParams.add("approval_url", "http://reacthms.kro.kr/pay/success"); // 성공하면 예약성공페이지로
		payParams.add("cancel_url", "http://reacthms.kro.kr/hotel/main"); // 취소시 예약페이지
		payParams.add("fail_url", "http://reacthms.kro.kr/hotel/main"); // 실패시 예약페이지
		
		HttpEntity<Map> request1 = new HttpEntity<Map>(payParams, headers);

		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/ready";
		KakaoPayReadyVo res = template.postForObject(url, request1, KakaoPayReadyVo.class);
		String amount = (String) params.get("total_amount");
		String itmename = (String) params.get("item_name");
		if (tbodyArray==null) {
			PayDto.bookAddPayment bookpayment = new bookAddPayment(res.getTid(), uuid, itmename, Integer.parseInt(amount),
					PayType.KAKAO);
			dao.kakaobookadd(bookpayment);
		}
		return res;
	}

	public KakaoPayApproveVO kakaoPayApprove(String pgToken, HttpSession session, String username) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK a9f2ff79213b981cd3e1ade179808e25");
		headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, Object> payParams = new LinkedMultiValueMap<String, Object>();
		/* tid 불러오는 로직 추가 및 결제번호는 준비 api와 일치해야함 */
		payParams.add("cid", "TC0ONETIME");
		payParams.add("tid", session.getAttribute("tid"));
		payParams.add("partner_order_id", session.getAttribute("partner_order_id"));
		payParams.add("partner_user_id", username);
		payParams.add("pg_token", pgToken);

		HttpEntity<Map> request = new HttpEntity<Map>(payParams, headers);

		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/approve";

		KakaoPayApproveVO res = template.postForObject(url, request, KakaoPayApproveVO.class);

		return res;
	}

	public void tossPayApprove(String orderId, String paymentKey, Integer amount, String gradename) {
		TossPayVo tp = new TossPayVo(orderId, paymentKey, amount, gradename);

		PayDto.bookAddPayment bookpayment = new bookAddPayment(orderId, paymentKey, gradename, amount, PayType.TOSS);
		dao.paymenttoss(bookpayment);

	}
	// 환불

	public KakaoPayCancelVo canclePay(PayDto.payment payment) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK a9f2ff79213b981cd3e1ade179808e25");
		headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, Object> payParams = new LinkedMultiValueMap<String, Object>();
		payParams.add("cid", "TC0ONETIME");
		payParams.add("tid", payment.getTid());
		payParams.add("cancel_amount", payment.getTotal_amount());
		payParams.add("partner_order_id", payment.getPartner_order_id());
		payParams.add("cancel_tax_free_amount", 0);
		HttpEntity<Map> request = new HttpEntity<Map>(payParams, headers);

		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/cancel";
		KakaoPayCancelVo res = template.postForObject(url, request, KakaoPayCancelVo.class);

		return res;
	}

	public PayDto.payment findBypayment(Integer bookno, Integer orderno) {
		PayDto.payment payment = dao.findPaymentByBookno(bookno, orderno);
		return payment;
	}

	public void deletepayment(Integer bookno, Integer orderno) {
		dao.deletepayment(orderno, bookno);
	}

	public void findAndDeleteByBookByBookno(Integer bookno) {
		BookDto.checkbook bookdata = dao.findbookByBookno(bookno);
		System.out.println(bookdata.toString());
		if (bookdata.getRoombookno() != null)
			dao.deleteroombooking(bookno);
		if (bookdata.getResno() != null)
			dao.deletedinner(bookno);
		dao.deletebook(bookno);
	}

	public void findAndCancelOrder(Integer orderno) {
		dao.findAndCancelOrder(orderno);
	}
}
