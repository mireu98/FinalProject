package hotel.management.v1.manager.MVCController;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hotel.management.v1.manager.dto.ManagerDto;
import hotel.management.v1.manager.service.ManagerService;

@Controller
@RequestMapping("/hotel")
@Secured("ROLE_ADMIN")
public class ManagerMVCController {
    @Autowired
    ManagerService service;
    
    //예약리스트 페이지를 불러오면서 금일 체크인 , 체크아웃 , 방문일(석식만 선택한 사람들을 위해) data를 같이 쏴주는 곳
    @GetMapping("/manager/bookList")
    public ModelAndView booklist(){
        List<ManagerDto.findBookList> bookList = service.findBookList();
        if(bookList == null){
            return new ModelAndView("hotel/manager/booklist").addObject("msg", "검색결과가 없습니다.");
        }
        return new ModelAndView("hotel/manager/booklist").addObject("bookList", bookList);
    }

    //고객검색 페이지 불러오는 메소드
    @GetMapping("/manager/memberSearch")
    public void memberSearch(){}
    
    //고객의 상세 정보 페이지를 불러오면서 url 받은값을 통해 정보를 함께 보내주는 메소드
    @GetMapping("/manager/memberDetail")
    public ModelAndView memberDetail(String name , String tel){
        ManagerDto.userDetail detail = service.memberDetail(name,tel);
        if(detail == null){
            return new ModelAndView("hotel/manager/memberDetail").addObject("msg", "없는 회원입니다.");
        }
        return new ModelAndView("hotel/manager/memberDetail").addObject("member", detail);
    }
}
