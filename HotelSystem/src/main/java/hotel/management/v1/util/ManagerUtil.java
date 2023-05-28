package hotel.management.v1.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hotel.management.v1.manager.dto.ManagerDto;

public class ManagerUtil {

    public ManagerDto.bookSearchCondition DateFormat(ManagerDto.bookSearchCondition dto){
        SimpleDateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yy/MM/dd");
        try {
            // fromDate값을 받았으면 그값의 format을 변경해주고 dto에 값을 추가해준다.
            if(dto.getFromDate() != ""){
                Date fromDateof = originalFormat.parse(dto.getFromDate());
                String fromDate = targetFormat.format(fromDateof);
                dto.setFromDate(fromDate);
            }
            // toDate값을 받았으면 그값의 format을 변경해주고 dto에 값을 추가해준다.
            if(dto.getToDate() != ""){
                Date toDateof = originalFormat.parse(dto.getToDate());
                String toDate = targetFormat.format(toDateof);
                dto.setToDate(toDate);
            }
            return dto;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
