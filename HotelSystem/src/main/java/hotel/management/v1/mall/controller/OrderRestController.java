package hotel.management.v1.mall.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hotel.management.v1.exception.NotFoundUserException;
import hotel.management.v1.mall.dto.OrdersDto;
import hotel.management.v1.mall.service.OrderService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("hotel")
public class OrderRestController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/mall/checkUser")
    public ResponseEntity<?> checkUser(Principal principal) {
        if (principal == null) {
            throw new NotFoundUserException("상품은 회원만 구매가 가능합니다. 로그인을 해주세요.");
        }
        return ResponseEntity.ok("회원");
    }

    @PostMapping("/mall/order")
    public ResponseEntity<?> order(OrdersDto.Order order, Principal principal, HttpSession session) {
        session.setAttribute("tbodyArray", order.getTbodyArray());
        session.setAttribute("pickupDay", order.getPickupDay());
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/mall/orderDelete")
    public ResponseEntity<?> orderDelete(Integer orderNo, String itemName, Integer orderEA) {
        orderService.orderDelete(orderNo, itemName, orderEA);
        return ResponseEntity.ok(null);
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<String> handleNotFoundUserListException(NotFoundUserException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

}