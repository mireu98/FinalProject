package hotel.management.v1.pay.dao;

import hotel.management.v1.client.book.dto.BookDto;
import hotel.management.v1.pay.dto.PayDto;
import org.apache.ibatis.annotations.Mapper;

import hotel.management.v1.client.book.dto.BookDto.book;
import hotel.management.v1.pay.dto.PayDto.bookAddPayment;

@Mapper
public interface PayDao {

	public Integer kakaobookadd(bookAddPayment bookpayment);

	public Integer paymenttoss(bookAddPayment bookpayment);

	public Integer mangeradd(book book);

	public PayDto.payment findPaymentByBookno(Integer bookno, Integer orderno);

	public Integer deletepayment(Integer orderno, Integer bookno);

	public BookDto.checkbook findbookByBookno(Integer bookno);

	public void deletedinner(Integer bookno);

	public void deleteroombooking(Integer bookno);

	public void deletebook(Integer bookno);

	public Integer findAndCancelOrder(Integer orderno);
}
