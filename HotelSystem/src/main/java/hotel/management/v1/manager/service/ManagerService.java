package hotel.management.v1.manager.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.expr.Instanceof;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hotel.management.v1.exception.NotFoundBookListException;
import hotel.management.v1.exception.NotFoundBookNoException;
import hotel.management.v1.exception.NotFoundCheckInBookException;
import hotel.management.v1.exception.NotFoundUserListException;
import hotel.management.v1.manager.dao.ManagerDao;
import hotel.management.v1.manager.dto.ManagerDto;
import hotel.management.v1.manager.dto.ManagerDto.alarm;
import hotel.management.v1.util.ManagerUtil;


@Service
public class ManagerService {
   @Autowired
   ManagerDao dao;

    public List<ManagerDto.findBookList> findBookList(){
            List<ManagerDto.findBookList> list = dao.findBookList();
            return list;
    }

    public List<ManagerDto.findUserList> userSearch(String name) {
        List<ManagerDto.findUserList> list = dao.findUserList(name);
        if(list.size() == 0){
            throw new NotFoundUserListException("고객을 찾을수 없습니다.");
        }
        return list;
    }

    public Integer bookCancel(String tel) {
        return dao.bookCancel(tel);
    }

    public void checkOut(String tel, String roomNo) {
        Integer intRoomNo = Integer.parseInt(roomNo);
        dao.changeRoomStatusEmpty(intRoomNo);
        dao.checkOut(tel);
        dao.addBookCount(tel);
    }

    public void changeBook(boolean breakfast, boolean dinner, String tel) {
        try {
            ManagerDto.findBookNoCount bookNoCount = dao.findBookNoCountByTel(tel);
            if(breakfast == true){
                dao.updateBreakfast(bookNoCount.getBookNo());
            } else {
                dao.cancelBreakfast(bookNoCount.getBookNo());
            }
    
            Integer searchRes = dao.searchRes(bookNoCount.getBookNo());
            if (dinner == true){
                if(searchRes == 0){
                    dao.updateDinner(bookNoCount.getBookNo() , bookNoCount.getTotalCount());
                }
            } else {
                dao.cancelDinner(bookNoCount.getBookNo());
            }
        } catch (Exception e) {
            throw new NotFoundBookNoException("금일 예약된 회원만 예약을 변경할수있습니다.");
        }
        
    }

    public List<ManagerDto.roomList> roomList(String roomGrade) {
        List<ManagerDto.roomList> list = dao.roomList(roomGrade);
        return list;
    }

    public void checkIn(String roomNo , String tel ) {
        tel = StringUtils.strip(tel);
        Integer bookNo = dao.findBookNoByTel(tel);
        Integer intRoomNo = Integer.parseInt(roomNo);
        dao.setRoom(intRoomNo , bookNo);
        dao.changeBookStatus(bookNo);
        dao.changeRoomStatusCheckIn(intRoomNo);
    }

    //member이름 클릭시 정보 받아오는 로직
    public ManagerDto.userDetail memberDetail(String name,String tel) {
        try {
            ManagerDto.userDetail userDetail = dao.memberDetail(name,tel).get();
            return userDetail;
        } catch (NoSuchElementException e) {
            return null;
        } catch (MyBatisSystemException m){
            return null;
        }
    }

    public void blackBtn(String name) {
        dao.blackBtn(name);
    }

    public void vipBtn(String name) {
        dao.vipBtn(name);
    }

    public void ableBtn(String name) {
        dao.ableBtn(name);
    }

    //검색 조건들을 dto로 받는 메소드
    public List<ManagerDto.findBookList> bookSearch(ManagerDto.bookSearchCondition dto) {
        //dto로 받은 값들의 날짜 데이터를 db의 형식과 맞춰준다.
        ManagerUtil managerUtil = new ManagerUtil();
        dto = managerUtil.DateFormat(dto);
        
        //set된 dto의 조건들을 dao에 연결해준다.
        List<ManagerDto.findBookList> list = dao.bookSearch(dto);
        if(list.size() == 0){
            throw new NotFoundBookListException("검색정보가 없습니다.");
        }
        return list;  
    }

    public List<ManagerDto.alarm> checkOutAlarm() {
        if(dao.checkOutAlarm().size() == 0){
            throw new NotFoundCheckInBookException("모든 체크아웃이 완료되었습니다.");
        }
        return dao.checkOutAlarm();
    }
}
