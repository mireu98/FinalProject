package hotel.management.v1.manager.RestController;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.management.v1.exception.NotFoundBookListException;
import hotel.management.v1.exception.NotFoundBookNoException;
import hotel.management.v1.exception.NotFoundCheckInBookException;
import hotel.management.v1.exception.NotFoundUserListException;
import hotel.management.v1.manager.dto.ManagerDto;
import hotel.management.v1.manager.service.ManagerService;

@RestController
@RequestMapping("/hotel")
public class ManagerRestController {
    @Autowired
    ManagerService service;

    //검색조건들을 받아서 조건을 이용해 쿼리 조회후 list로 return하는 메소드 c
    @PostMapping("/manager/bookSearch")
    public ResponseEntity<List<ManagerDto.findBookList>> bookSearch(ManagerDto.bookSearchCondition dto) {
        List<ManagerDto.findBookList> list = service.bookSearch(dto);
        return ResponseEntity.ok(list);
    }

    //고객의 이름을 받아서 이름에 해당하는 list를 return하는 메소드 (Exception 처리 완료) c
    @PostMapping("/manager/memberSearch")
    public ResponseEntity<List> userSearch(String name) {
        List<ManagerDto.findUserList> list = service.userSearch(name);
        return ResponseEntity.ok(list);
    }

    //예약취소 예약자 전화번호를 받아서 조회후 취소 c
    @PostMapping("/manager/bookCancel")
    public ResponseEntity<?> bookCancel(String bookTel) {
        Integer bookCancel = service.bookCancel(bookTel);
        return ResponseEntity.ok("예약취소가 완료되었습니다.");
    }

    //체크아웃 버튼 클릭시 Post c
    @PostMapping("/manager/checkOut")
    public ResponseEntity<?> checkOut(String bookTel , String roomNo) {
        service.checkOut(bookTel , roomNo);
        return ResponseEntity.ok("");
    }

    //예약변경(조식 , 석식)을 위한 메소드
    @PostMapping("/manager/changeBook")
    public ResponseEntity<?> changeBook(boolean breakfast , boolean dinner, String bookTel) {
        service.changeBook(breakfast , dinner , bookTel);
        return ResponseEntity.ok("");
    }

    //고객의 roomGrade를 불러와서 해당 roomGrade를 갖고있는 방들을 list로 보내준다.
    @PostMapping("/manager/checkBtn")
    public ResponseEntity<?> roomList(String roomGrade){ 
        List<ManagerDto.roomList> list = service.roomList(roomGrade);
        return ResponseEntity.ok(list);
    }

    //checkIn 버튼 클릭후 해당 방을 클릭할시 그방의 roomNo와 예약자의 이름을 받아서 방을 배정해준다.
    @PostMapping("/manager/checkIn")
    public ResponseEntity<?> checkIn(String roomNo , String name){ 
        service.checkIn(roomNo , name);
        return ResponseEntity.ok("");
    }

    //blackBtn 클릭시 이름으로 고객을 검색해서 블랙리스트를 활성화 해준다. 이미 블랙리스트로 지정되었을때는 반대로 변경
    @PostMapping("/manager/blackBtn")
    public ResponseEntity<?> blackBtn(String name){
        service.blackBtn(name);
        return ResponseEntity.ok("변경이 완료되었습니다.");
    }

    //vipBtn 클릭시 이름으로 고객을 검색해서 블랙리스트를 활성화 해준다. 이미 블랙리스트로 지정되었을때는 반대로 변경
    @PostMapping("/manager/vipBtn")
    public ResponseEntity<?> vipBtn(String name){
        service.vipBtn(name);
        return ResponseEntity.ok("");
    }

    //ableBtn 클릭시 이름으로 고객을 검색해서 블랙리스트를 활성화 해준다. 이미 블랙리스트로 지정되었을때는 반대로 변경
    @PostMapping("/manager/ableBtn")
    public ResponseEntity<?> ableBtn(String name){
        service.ableBtn(name);
        return ResponseEntity.ok("");
    }

    @PostMapping("/manager/alarm")
    public ResponseEntity<?> alarm(){
        List<ManagerDto.alarm> list = service.checkOutAlarm();
        return ResponseEntity.ok(list);
    }

    //고객 이름 검색시 없을경우 발생하는 Exception
    @ExceptionHandler(NotFoundUserListException.class)
    public ResponseEntity<String> handleNotFoundUserListException(NotFoundUserListException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }
	
    //예약 검색시 없을 경우 발생하는 Exception
    @ExceptionHandler(NotFoundBookListException.class)
    public ResponseEntity<String> handleNotFoundBookListException(NotFoundBookListException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @ExceptionHandler(NotFoundBookNoException.class)
    public ResponseEntity<String> handleNotFoundBookNoException(NotFoundBookNoException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }

    @ExceptionHandler(NotFoundCheckInBookException.class)
    public ResponseEntity<String> handleNotFoundCheckInBookException(NotFoundCheckInBookException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }
}
