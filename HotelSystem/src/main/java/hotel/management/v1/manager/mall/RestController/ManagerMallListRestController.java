package hotel.management.v1.manager.mall.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.management.v1.exception.NotFoundMallListException;
import hotel.management.v1.manager.mall.Dto.ManagerMallListDto;
import hotel.management.v1.manager.mall.Service.ManagerMallListService;

@RestController
public class ManagerMallListRestController {
	@Autowired
	private ManagerMallListService service;

	@PostMapping("hotel/manager/mall/updateItemEA")
	public ResponseEntity<?> updateItemEA(Integer itemno) {
		service.updateItemEA(itemno);
		return ResponseEntity.ok(null);
	}

	@PostMapping("/hotel/manager/managerMallList")
	public ResponseEntity<List<ManagerMallListDto.MallListSearch>> mallList(ManagerMallListDto.FindMallList dto) {
		List<ManagerMallListDto.MallListSearch> list = service.mallsearch(dto);
		System.out.println(dto.getName());
		return ResponseEntity.ok(list);
	}

	@PostMapping("/hotel/manager/delete")
	public ResponseEntity<?> delete(String orderNo) {
		Integer orderno = Integer.parseInt(orderNo);
		service.orderdetaildelete(orderno);
		return ResponseEntity.ok("");
	}

	@ExceptionHandler(NotFoundMallListException.class)
	public ResponseEntity<String> NotFoundMallListException(NotFoundMallListException ex) {
		String message = ex.getMessage();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
	}

}
