package hotel.management.v1.client.book.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import hotel.management.v1.client.book.dto.BookDto;
import hotel.management.v1.client.book.dto.BookDto.addRoomBook;
import hotel.management.v1.client.book.dto.BookDto.addbook;
import hotel.management.v1.client.book.dto.BookDto.checkbookbyusername;
import hotel.management.v1.client.book.dto.BookDto.dinner;
import hotel.management.v1.client.book.dto.BookDto.managercheckroom;
import hotel.management.v1.client.book.dto.BookDto.myInFo;
import hotel.management.v1.client.book.dto.BookDto.mypagedinner;
import hotel.management.v1.client.book.dto.BookDto.whtyouname;

@Mapper
public interface BookDao {

	public List<BookDto.findRoom> findRoomFromAndTo(String from, String to);

	public Integer addBook(addbook ab);

	public Integer addRoomBooking(addRoomBook rb);

	public Integer addDinner(dinner dinner);

	public Integer addDinnerbook(String username, Integer resno);

	public Integer findByUsernameAndBookdate(String username, String bookdate);

	public BookDto.finduser findByusername(String name);

	public dinner findBookdata(whtyouname name);


	public List<BookDto.mydinnerlist> findMydinnerByusername(String name);

	public Integer chekbook(String username, String from, String to);

	public myInFo findUsersByUsername(String name);

	public List<mypagedinner> findMyBookByusername(String name);

	public List<mypagedinner> findmybookByfromAndto(checkbookbyusername data);


   public   Integer findmydinnerByusernameAndfrom(String name, String from);
   public  Integer findmydinnerByusernameAndfrom2(String name ,String from);

  public   Integer managerdinner(BookDto.managerdinnerbook book);

	public Integer manageraddDinnr(BookDto.managerdinnerbook book);

	public Integer findUserByUsername(String username);
}
