
package egg.curso.ej01libreriaweb.controladores;

import egg.curso.ej01libreriaweb.excepciones.WebExcepcion;
import egg.curso.ej01libreriaweb.servicios.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registro")
public class RegistroControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("")
    public String usuarioRegistro(){
        return "registro";
    }
    
    @PostMapping("")
    public String usuarioGuardar(ModelMap model, @RequestParam(name = "anombreDeUsuario") String nombreDeUsuario, @RequestParam String contrasena, @RequestParam String contrasena2){
        System.out.println(nombreDeUsuario + contrasena + contrasena2);
                
        try {
            usuarioServicio.guardar(nombreDeUsuario, contrasena, contrasena2);            
            return "redirect:/" + "?registro";
        } catch (WebExcepcion ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("nombreDeUsuario", nombreDeUsuario);
            return "registro";
        }
    }
        

}
