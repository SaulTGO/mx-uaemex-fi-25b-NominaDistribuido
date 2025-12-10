package mx.uaemex.fi.isii.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller. For managing the home/user page
 */
@RequestMapping("/user")
@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(){
        return "home";
    }

}
