package hotel.management.v1.manager.mall.MVCController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import hotel.management.v1.exception.NotFoundMallListException;
import hotel.management.v1.manager.mall.Dto.ManagerMallListDto;
import hotel.management.v1.manager.mall.Service.ManagerMallListService;

@Controller
@Secured("ROLE_ADMIN")
public class ManagerMallListMVCController {
	@Autowired
	private ManagerMallListService service;
	
	@GetMapping("/hotel/manager/itemUpdate")
	public ModelAndView itemUpdate() {
		List<ManagerMallListDto.ItemUpdate> itemList = service.findItemList();
		return new ModelAndView("hotel/manager/itemUpdate").addObject("itemList", itemList);
	}

	@GetMapping("/hotel/manager/managerMallList")
	public ModelAndView contactmallList(Integer pageno) {
		List<ManagerMallListDto.MallListSearch> list = service.contactmallList();
		if (list.size() == 0) {
			return new ModelAndView("hotel/manager/managerMallList").addObject("msg", "검색결과가 없습니다.");
		}
		return new ModelAndView("hotel/manager/managerMallList").addObject("mallListArea", list);
	}

	@GetMapping("/hotel/manager/managerorderDetail")
	public ModelAndView managerorderDetail(Integer orderNo) {
		ManagerMallListDto.orderDetail detail = service.orderDetail(orderNo);
		return new ModelAndView("hotel/manager/managerorderDetail").addObject("detail", detail);
	}

	@ExceptionHandler(NotFoundMallListException.class)
	public ResponseEntity<String> NotFoundMallListException(NotFoundMallListException ex) {
		String message = ex.getMessage();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
	}

}
