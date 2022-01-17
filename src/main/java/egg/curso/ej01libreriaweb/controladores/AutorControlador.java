package egg.curso.ej01libreriaweb.controladores;

import egg.curso.ej01libreriaweb.entidades.Autor;
import egg.curso.ej01libreriaweb.excepciones.WebExcepcion;
import egg.curso.ej01libreriaweb.servicios.AutorServicio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/lista")
    public String autorLista(Model model){
        model.addAttribute("autores", autorServicio.listarAutores());
        return "autor-lista";
    }
    
    @GetMapping("/crear")
    public String autorCrear(ModelMap model, @RequestParam(required = false) String id){
        if (id != null) {
            Optional<Autor> optionalAutor = autorServicio.buscarAutorPorId(id);
            if (optionalAutor.isPresent()) {
                model.addAttribute("autor", optionalAutor.get());
            } else {
                return "redirect:autor/lista";
            }
        } else {            
            model.addAttribute("autor", new Autor());            
        }
        return "autor-crear";
    }
    
    @PostMapping("/guardar")
    public String guardar(ModelMap model, RedirectAttributes redirectAttributes, @ModelAttribute Autor autor){   
        System.out.println(autor.getNombre());
        try {            
            autorServicio.guardarAutor(autor);
            redirectAttributes.addFlashAttribute("validacion", "El autor se guardo correctamente");
        } catch (WebExcepcion ex) {
            redirectAttributes.addFlashAttribute("excepcion", ex.getMessage());
            ex.printStackTrace();
        }
        return "redirect:/autor/lista";
    }

    @GetMapping("/eliminar")
    public String eliminar(@RequestParam (required = true) String id) { // required --> si o si se requiere el parametro
        autorServicio.eliminarAutorPorId(id);        
        return "redirect:lista";
    }
    
    @GetMapping("/darBaja")
    public String darBaja(@RequestParam String id){
        autorServicio.darDeBajaAutor(id);
        return "redirect:/autor/lista";
    }
    
    @GetMapping("/darAlta")
    public String darAlta(@RequestParam String id){
        autorServicio.darDeAltaAutor(id);
        return "redirect:/autor/lista";
    }
    
}
