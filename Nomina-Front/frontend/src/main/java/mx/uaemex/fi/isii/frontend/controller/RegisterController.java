package mx.uaemex.fi.isii.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller. For managing the register page
 */
@RequestMapping("/user")
@Controller
public class RegisterController {

    /**
     * Serves /register template
     * @return login template
     */
    @GetMapping("/register")
    public String register(){
        return "register";
    }

}
