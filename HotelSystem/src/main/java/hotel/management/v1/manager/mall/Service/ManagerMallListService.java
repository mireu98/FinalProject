package hotel.management.v1.manager.mall.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hotel.management.v1.manager.mall.Dao.ManagerMallListDao;
import hotel.management.v1.manager.mall.Dto.ManagerMallListDto;
import hotel.management.v1.manager.mall.Dto.ManagerMallListDto.ItemUpdate;

@Service
public class ManagerMallListService {
	@Autowired
	private ManagerMallListDao dao;

	public List<ManagerMallListDto.MallListSearch> mallsearch(ManagerMallListDto.FindMallList dto) {
		List<ManagerMallListDto.MallListSearch> list = dao.mallsearch(dto);
		return list;
	}

	public List<ManagerMallListDto.MallListSearch> contactmallList() {
		List<ManagerMallListDto.MallListSearch> list = dao.contactmallList();
		return list;
	}

	public Integer orderdetaildelete(Integer orderNo) {
		return dao.orderdetaildelete(orderNo);
	}

	public ManagerMallListDto.orderDetail orderDetail(Integer orderNo) {
		return dao.orderDetail(orderNo);
	}

	public List<ItemUpdate> findItemList() {
		return dao.findItemList();
	}

	public Integer updateItemEA(Integer itemno) {
		return dao.updateItemEA(itemno);
	}

}
