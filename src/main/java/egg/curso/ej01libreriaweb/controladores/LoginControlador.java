package egg.curso.ej01libreriaweb.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginControlador {

    @GetMapping("")
    public String login(ModelMap model, @RequestParam(required = false) String error, @RequestParam(required = false) String nombreDeUsuario, @RequestParam(required = false) String logout) {
        if (error != null) {
            model.addAttribute("error", "El usuario ingresado o la contraseña son incorrectos");            
        }        
        if (nombreDeUsuario != null) {
            model.addAttribute("nombreDeUsuario", nombreDeUsuario); 
        }                
        return "login";
    }
    

}
