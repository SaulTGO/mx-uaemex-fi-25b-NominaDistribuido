package mx.uaemex.fi.isii.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller. To handle /login page
 */
@Controller
public class LoginController {

    /**
     * Serves /login template
     * @return login template
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
