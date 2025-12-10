package mx.uaemex.fi.isii.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller. For managing the user/calcularNomina page
 */

@RequestMapping("/user")
@Controller
public class CalcularNominaController {

    /**
     * Serves /register template
     * @return login template
     */
    @GetMapping("/calcular-nomina")
    public String register(){
        return "calcular-nomina";
    }
}
