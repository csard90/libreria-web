package egg.curso.ej01libreriaweb.controladores;

import egg.curso.ej01libreriaweb.entidades.Libro;
import egg.curso.ej01libreriaweb.excepciones.WebExcepcion;
import egg.curso.ej01libreriaweb.servicios.AutorServicio;
import egg.curso.ej01libreriaweb.servicios.EditorialServicio;
import egg.curso.ej01libreriaweb.servicios.LibroServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    
    @Autowired
    private AutorServicio autorServicio;
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/lista")
    public String libroLista(ModelMap model){
        model.addAttribute("libros", libroServicio.listarLibros());
        return "libro-lista";
    }
    
    @GetMapping("/crear")
    public String libroCrear(ModelMap model, @RequestParam(required = false) String id){
        if (id == null) {
            model.addAttribute("libro", new Libro());            
        } else {
            Libro libro = libroServicio.buscarLibroPorId(id);
            model.addAttribute("libro", libro);
        }
        model.addAttribute("autores", autorServicio.listarAutoresTrue());
        model.addAttribute("editoriales", editorialServicio.listarEditorialesTrue());        
        return "libro-crear";
    }
    
    @PostMapping("/guardar")
    public String libroGuardar(RedirectAttributes redirectAttributes, @ModelAttribute Libro libro){
        try {
            libroServicio.guardarLibro(libro);            
            redirectAttributes.addFlashAttribute("guardadoCorrecto", "El libro se guardo correctamente");
        } catch (WebExcepcion ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("guardadoIncorrecto", ex.getMessage());
        }                
        return "redirect:/libro/lista";
    }
    
    @GetMapping("/eliminar")
    public String libroEliminar(@RequestParam String id){
        libroServicio.eliminarLibro(id);
        return "redirect:/libro/lista";
    }
    
    @GetMapping("/darAlta")
    public String libroDarAlta(@RequestParam String id){
        libroServicio.darAltaLibro(id);
        return "redirect:/libro/lista";
    }
    
     @GetMapping("/darBaja")
    public String libroDarBaja(@RequestParam String id){
        libroServicio.darBajaLibro(id);
        return "redirect:/libro/lista";
    }
    
}
