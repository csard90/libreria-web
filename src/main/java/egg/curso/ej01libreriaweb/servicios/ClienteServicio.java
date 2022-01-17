package egg.curso.ej01libreriaweb.servicios;

import egg.curso.ej01libreriaweb.entidades.Cliente;
import egg.curso.ej01libreriaweb.excepciones.WebExcepcion;
import egg.curso.ej01libreriaweb.repositorios.ClienteRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @Transactional
    public Cliente guardarCliente(Cliente cliente) throws WebExcepcion{
                
        if (cliente.getDocumento() == null) {
            throw new WebExcepcion("El documento no puede estar null");
        }
        
        if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
            throw new WebExcepcion("El nombre no puede estar null o vacio");
        }
        
        if (cliente.getApellido() == null || cliente.getApellido().isEmpty()) {
            throw new WebExcepcion("El apellido no puede estar null o vacia");
        }
        
        if (cliente.getTelefono() == null || cliente.getTelefono().isEmpty()) {
            throw new WebExcepcion("El numero de telefono no puede estar null o vacio");
        }
        
        if (cliente.getId().isEmpty()) {
            cliente.setAlta(true);            
        }                
        
        return clienteRepositorio.save(cliente);
    }    
    
    public Optional<Cliente> buscarClientePorId(String id){
        return clienteRepositorio.findById(id);
    }
    
    public List<Cliente> listarClientes() {
        return clienteRepositorio.findAll();
    }
    
    public void eliminarClientePorId(String id) {                        
        clienteRepositorio.deleteById(id);
    }
    
    public void darDeBajaCliente(String id){
        Optional<Cliente> optionalCliente = clienteRepositorio.findById(id);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            cliente.setAlta(false);            
            clienteRepositorio.save(cliente);
        }                        
    }
    
    public void darDeAltaCliente(String id){
        Optional<Cliente> optionalCliente = clienteRepositorio.findById(id);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            cliente.setAlta(true);            
            clienteRepositorio.save(cliente);
        }                        
    }

}
