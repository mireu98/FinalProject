package hotel.management.v1.manager.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import hotel.management.v1.manager.dto.ManagerDto;
import hotel.management.v1.manager.dto.ManagerDto.alarm;
import hotel.management.v1.manager.dto.ManagerDto.bookSearchCondition;
import hotel.management.v1.manager.dto.ManagerDto.roomList;
import hotel.management.v1.manager.dto.ManagerDto.userDetail;

@Mapper
public interface ManagerDao {

    public List<ManagerDto.findBookList> findBookList();

    public List<ManagerDto.findUserList> findUserList(String name);

    public Integer bookCancel(String tel);

    public String findTelByName(String name);
 
    public void checkOut(String name);

	public void updateBreakfast(Integer bookNo);

    public void updateDinner(Integer bookNo , Integer totalCount);

    public ManagerDto.findBookNoCount findBookNoCountByTel(String tel);

    public void cancelBreakfast(Integer bookNo);

    public void cancelDinner(Integer bookNo);

    public Integer searchRes(Integer resNo);

    public List<ManagerDto.roomList> roomList(String roomGrade);

    public void setRoom(Integer roomNo , Integer bookNo);

    public Integer findBookNoByTel(String tel);

    public void changeBookStatus(Integer bookNo);

    public void changeRoomStatusCheckIn(Integer roomNo);

    public void changeRoomStatusEmpty(Integer roomNo);

    public Optional<ManagerDto.userDetail> memberDetail(String name , String tel);

    public void blackBtn(String name);

    public void vipBtn(String name);

    public void ableBtn(String name);

    public List<ManagerDto.findBookList> bookSearch(ManagerDto.bookSearchCondition dto);

    public List<alarm> checkOutAlarm();

    public void addBookCount(String tel);
    
    
}
