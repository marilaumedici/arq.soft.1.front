package arq.soft.front.controllers;


import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController extends AbstractController {


	 @RequestMapping("/")
	    public String welcome(Map<String, Object> model) {
	    	
	        return "home";
	    }

}
