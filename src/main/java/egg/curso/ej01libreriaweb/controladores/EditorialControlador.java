package egg.curso.ej01libreriaweb.controladores;

import egg.curso.ej01libreriaweb.entidades.Editorial;
import egg.curso.ej01libreriaweb.excepciones.WebExcepcion;
import egg.curso.ej01libreriaweb.servicios.EditorialServicio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/lista")
    public String editorialLista(ModelMap model){
        model.put("editoriales", editorialServicio.listarEditoriales());
        return "editorial-lista";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/crear")
    public String editorialCrear(ModelMap model, @RequestParam(required = false) String id){
        if (id != null) {
            Optional<Editorial> optionalEditorial = editorialServicio.buscarEditorialPorId(id);
            if (optionalEditorial.isPresent()) {
                model.addAttribute("editorial", optionalEditorial.get());
            } else {
                return "redirect:editorial/lista";
            }
        } else {            
            model.addAttribute("editorial", new Editorial());            
        }
        return "editorial-crear";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/guardar")
    public String editorialGuardar(ModelMap model, RedirectAttributes redirectAttributes, @ModelAttribute Editorial editorial){                               
        try {            
            editorialServicio.guardarEditorial(editorial);
            redirectAttributes.addFlashAttribute("validacion", "Los datos se han guardado correctamente");
        } catch (WebExcepcion ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            ex.printStackTrace();
        }
        return "redirect:/editorial/lista";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/eliminar")
    public String editorialEliminar(@RequestParam (required = true) String id) { // required --> si o si se requiere el parametro
        editorialServicio.eliminarEditorialPorId(id);        
        return "redirect:lista";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/darBaja")
    public String editorialDarBaja(@RequestParam String id){
        editorialServicio.darDeBajaEditorial(id);
        return "redirect:/editorial/lista";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/darAlta")
    public String editorialDarAlta(@RequestParam String id){
        editorialServicio.darDeAltaEditorial(id);
        return "redirect:/editorial/lista";
    }
    
}
