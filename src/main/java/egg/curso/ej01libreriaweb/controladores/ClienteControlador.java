package egg.curso.ej01libreriaweb.controladores;

import egg.curso.ej01libreriaweb.entidades.Cliente;
import egg.curso.ej01libreriaweb.excepciones.WebExcepcion;
import egg.curso.ej01libreriaweb.servicios.ClienteServicio;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/lista")
    public String clienteLista(ModelMap model){        
        model.addAttribute("clientes", clienteServicio.listarClientes());
        return "cliente-lista";
    }
    
    @GetMapping("/crear")
    public String clienteCrear(ModelMap model, @RequestParam(required = false) String id){        
        if (id == null) {
            model.addAttribute("cliente", new Cliente());            
        } else {
            Optional<Cliente> optionalCliente = clienteServicio.buscarClientePorId(id);
            if (optionalCliente.isPresent()) {
                model.addAttribute("cliente", optionalCliente.get());
            } else {
                model.addAttribute("cliente", new Cliente());
            }
        }        
        return "cliente-crear";
    }
    
    @PostMapping("/guardar")
    public String clienteGuardar(RedirectAttributes redirectAttributes, @ModelAttribute Cliente cliente){
        
        try {
            clienteServicio.guardarCliente(cliente);
            redirectAttributes.addFlashAttribute("validacion", "El cliente se guardo correctamente");
        } catch (WebExcepcion ex) {
            redirectAttributes.addFlashAttribute("excepcion", ex.getMessage());
            ex.printStackTrace();
            ex.getMessage();
        }        
        return "redirect:/cliente/lista";
    }
    
    @GetMapping("/eliminar")
    public String clienteEliminar(@RequestParam String id){
        clienteServicio.eliminarClientePorId(id);
        return "redirect:/cliente/lista";
    }
    
    @GetMapping("/darBaja")
    public String darBaja(@RequestParam String id){
        clienteServicio.darDeBajaCliente(id);
        return "redirect:/cliente/lista";
    }
    
    @GetMapping("/darAlta")
    public String darAlta(@RequestParam String id){
        clienteServicio.darDeAltaCliente(id);
        return "redirect:/cliente/lista";
    }
    
}
