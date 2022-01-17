package egg.curso.ej01libreriaweb.controladores;

import egg.curso.ej01libreriaweb.excepciones.WebExcepcion;
import egg.curso.ej01libreriaweb.repositorios.AutorRepositorio;
import egg.curso.ej01libreriaweb.repositorios.LibroRepositorio;
import egg.curso.ej01libreriaweb.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainController {                 
    
    @Autowired
    private LibroServicio libroServicio;
    
    @Autowired
    private LibroRepositorio libroRepositorio;
    
    @Autowired
    private AutorRepositorio ar;
    
    @GetMapping("")
    public String index(@RequestParam(required = false) String login) throws WebExcepcion {   
        return "index";
    }

}
