package hotel.management.v1.main;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/hotel")
public class MainController {
    
    @GetMapping("/main")
    public ModelAndView viewMain(HttpSession session,Model model){
    	if(session.getAttribute("msg")!=null) {
			String msg= (String)session.getAttribute("msg");
			session.removeAttribute("msg");
			return new ModelAndView("hotel/main").addObject("msg", msg);
		}
    	return new ModelAndView("hotel/main");
    }

    @GetMapping("/contact")
    public String contact(){
        return "hotel/contact";
    }
    
    @GetMapping("/hotelintroduce")
    public String hotelIntroduce(){
        return "hotel/hotelintroduce";
    }
    
    @GetMapping("/hotelstay")
    public String hotelStay(){
        return "hotel/hotelstay";
    }
    
    @GetMapping("/internalfacilities")
    public String internalFacilities(){
        return "hotel/internalfacilities";
    }
    
    @GetMapping("/report")
    public String report(){
        return "hotel/report";
    }
    
    @GetMapping("/luxuryhall")
    public String luxuryHall(){
        return "hotel/luxuryhall";
    }
    
    @GetMapping("/howtocome")
    public String howToCome(){
        return "hotel/howtocome";
    }
    
    @GetMapping("/test")
    public void index(){}
    
    // 예약완료 페이지 불러오는 메소드
 	@PreAuthorize("isAuthenticated()")
 	@GetMapping("/reservationcomplete")
 	public void reservationComplete() {}
 	
 	// 다이닝완료 페이지 불러오는 메소드
 	@PreAuthorize("isAuthenticated()")
 	@GetMapping("/diningreservationcomplete")
 	public void diningReservationComplete() {}
 	
 	// 명품관주문완료 페이지 불러오는 메소드
 	@PreAuthorize("isAuthenticated()")
 	@GetMapping("/luxurymallcomplete")
 	public void luxuryMallComplete() {}
}
