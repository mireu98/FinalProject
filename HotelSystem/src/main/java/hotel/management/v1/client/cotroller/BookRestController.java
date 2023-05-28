package hotel.management.v1.client.cotroller;

import java.security.Principal;
import java.util.List;

import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hotel.management.v1.client.book.dto.BookDto;
import hotel.management.v1.client.book.dto.BookDto.findRoom;
import hotel.management.v1.client.book.dto.BookDto.mypagedinner;
import hotel.management.v1.client.service.BookService;
import hotel.management.v1.pay.entity.PayType;
import jakarta.servlet.http.HttpSession;

import static java.lang.System.out;

@Controller
@RequestMapping("hotel")
public class BookRestController {
    @Autowired
    private BookService service;

    @PostMapping("/client/chekin")
    public ResponseEntity<?> chekin(BookDto.book book, Principal pal, HttpSession httpSession) {
        service.add(book, pal.getName());
        httpSession.setAttribute("gradename", book.getGradename());
        return ResponseEntity.ok(null);

    }

    @PostMapping("/client/findmybook")
    public ResponseEntity<List<mypagedinner>> findmybook(BookDto.managercheckroom data, Principal pal) {
        List<mypagedinner> list = service.findmybookByfromAndto(data, pal.getName());
        return ResponseEntity.ok(list);
    }

    @PostMapping(value = "/client/roombook", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<findRoom>> roomBook(String from, String to, HttpSession session, Principal pal) {
        if (service.chekbook(pal.getName(), from, to) != 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            List<BookDto.findRoom> dto = null;
            if (session.getAttribute("from") == null && session.getAttribute("to") == null) {
                if (from.equals("") || to.equals("")) {

                } else {
                    dto = service.findRoom(from, to);
                }

            } else {
                dto = service.findRoom(session.getAttribute("from").toString(), session.getAttribute("to").toString());
                out.println("세션있음");
            }
            return ResponseEntity.ok(dto);
        }
    }

    @PostMapping(value = "/client/dinnerbook", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> dinnerbook(BookDto.addbookfromDinner book, Principal pal) {
        if (service.findmydinnerByusernameAndfrom(book.getFrom(), pal.getName()) > 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        service.addDinner(book, pal.getName());
        return ResponseEntity.ok(null);
    }

    @PostMapping(value = "/client/ckinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> ckinfo(Principal pal) {
        service.findByusername(pal.getName());
        return ResponseEntity.ok(null);
    }

    @PostMapping(value = "/manager/checkroom", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkroom(BookDto.managercheckroom room) {
        List<BookDto.findRoom> roomlist = service.findRoom(room.getFrom(), room.getTo());
        return roomlist != null ? ResponseEntity.ok(roomlist) : ResponseEntity.status(HttpStatus.CONFLICT).body(null);

    }



    @PostMapping("/manager/checkbookbyusername")
    public ResponseEntity<?> checkbookbyusername(BookDto.checkbookbyusername check) {
        try {
            if (service.chekbook(check.getUsername(), check.getFrom(), check.getTo()) != 0) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            return ResponseEntity.ok(null);
        } catch (TooManyResultsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

    }
    @PostMapping(value = "/client/managerdinner", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> managerdiiner(BookDto.managerdinnerbook book){
        if(service.findUserByUsername(book.getBooker())==0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    out.println(book.toString());
        String date[] =  book.getFrom().split("-");
        String from = date[1]+"/"+date[2]+"/"+date[0];
       if (service.findmydinnerByusernameAndfrom(from, book.getBooker()) > 0)
         return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
       service.managerdinner(book);
        return ResponseEntity.ok(null);
    };
    @PostMapping(value = "/client/myinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> myinfo(Principal pal, String username) {
        BookDto.myInFo info = null;
        if (username != null) {
            info = service.myinfoByUsername(username);
        }
        if (pal != null) {
            info = service.myinfoByUsername(pal.getName());
        }
        return ResponseEntity.ok(info);
    }

    @PostMapping("/manager/checkin")
    public ResponseEntity<?> managercheckin(BookDto.managerbook book) {
        BookDto.book bo = new BookDto.book(book.getFrom(), book.getTo(), book.getTotalcnt(), book.getGradename(), book.getBfcheckbox(), book.getDicheckbox(), book.getBooker(), book.getBooktel(), null, PayType.YET);
        service.add(bo, book.getUsername());
        service.manageradd(bo);
        return ResponseEntity.ok(null);
    }
}
