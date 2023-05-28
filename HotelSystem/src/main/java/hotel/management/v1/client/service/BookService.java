package hotel.management.v1.client.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hotel.management.v1.client.book.dao.BookDao;
import hotel.management.v1.client.book.dto.BookDto;
import hotel.management.v1.client.book.dto.BookDto.addbook;
import hotel.management.v1.client.book.dto.BookDto.book;
import hotel.management.v1.client.book.dto.BookDto.checkbookbyusername;
import hotel.management.v1.client.book.dto.BookDto.dinner;
import hotel.management.v1.client.book.dto.BookDto.finduser;
import hotel.management.v1.client.book.dto.BookDto.managercheckroom;
import hotel.management.v1.client.book.dto.BookDto.myInFo;
import hotel.management.v1.client.book.dto.BookDto.mypagedinner;
import hotel.management.v1.pay.dao.PayDao;

@Service
public class BookService {
    @Autowired
    private BookDao dao;

    @Autowired
    private PayDao pdao;

    public List<BookDto.findRoom> findRoom(String from, String to) {
        List<BookDto.findRoom> list = dao.findRoomFromAndTo(from, to);
        return list;
    }

    public void add(book book, String pal) {
        BookDto.addRoomBook rb = new BookDto.addRoomBook(book.getTo(), book.getBfcheckbox(), book.getGradename(), pal, book.getFrom());
        BookDto.addbook ab = new BookDto.addbook(pal, book.getFrom(), book.getTotalcnt(), book.getBooktel(),
                book.getBooker());

        dao.addBook(ab);
        dao.addRoomBooking(rb);
        BookDto.whtyouname name = new BookDto.whtyouname(pal, book.getBooktel(), book.getBooker(), book.getFrom(),
                book.getTo());
        BookDto.dinner data = new dinner(name.getUsername(), dao.findByUsernameAndBookdate(name.getUsername(), name.getFrom()), name.getFrom());
        if (book.getDicheckbox()) {
            dao.addDinner(data);
        }
    }

    public Integer addDinner(BookDto.addbookfromDinner book, String name) {
        BookDto.addbook bo = new addbook(name, book.getFrom(), book.getTotalcnt(), book.getBooktel(), book.getBooker());
        dao.addBook(bo);
        BookDto.dinner din = new dinner(name, dao.findmydinnerByusernameAndfrom2(name,book.getFrom()), book.getFrom());
        dao.addDinner(din);
        return null;
    }

    public finduser findByusername(String name) {
        return dao.findByusername(name);
    }

    public List<BookDto.mydinnerlist> findMydinnerByusername(String name) {
        return dao.findMydinnerByusername(name);
    }

    public Integer chekbook(String username, String from, String to) {
        return dao.chekbook(username, from, to);
    }

    public myInFo myinfoByUsername(String name) {
        return dao.findUsersByUsername(name);
    }

    public Integer manageradd(book book) {
        // 만약 포스기에서 tid와 orderid가 넘어오면 여기서 넣어서 보내주기
        return pdao.mangeradd(book);


    }

    public List<mypagedinner> findMyBookByUsername(String name) {

        return dao.findMyBookByusername(name);
    }

    public List<mypagedinner> findmybookByfromAndto(managercheckroom date, String username) {
        BookDto.checkbookbyusername data = new checkbookbyusername();
        data.setFrom(date.getFrom());
        data.setTo(date.getTo());
        data.setUsername(username);
        return dao.findmybookByfromAndto(data);
    }




    public Integer findmydinnerByusernameAndfrom(String from, String name) {
        return dao.findmydinnerByusernameAndfrom(name, from);
    }

    public Integer managerdinner(BookDto.managerdinnerbook book) {
        dao.managerdinner(book);
        return dao.manageraddDinnr(book);

    }

    public Integer findUserByUsername(String username) {
        return dao.findUserByUsername(username);
    }
}
