package egg.curso.ej01libreriaweb.servicios;

import egg.curso.ej01libreriaweb.entidades.Editorial;
import egg.curso.ej01libreriaweb.excepciones.WebExcepcion;
import egg.curso.ej01libreriaweb.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Autowired
    private LibroServicio libroServicio;  
    
    public Editorial guardarEditorial(Editorial editorial) throws WebExcepcion {                      
               
        if (editorial.getNombre() == null || editorial.getNombre().isEmpty()) {
            throw new WebExcepcion("No ha ingresado ningun nombre para la editorial");
        }                                              
        
        if (!editorial.getId().isEmpty()) {  
            return editorialRepositorio.save(editorial);            
        } else {                    
            editorial.setAlta(true);                       
            return editorialRepositorio.save(editorial);            
        }                            
    }                
    
    @Transactional
    public Editorial darDeBajaEditorial(String id){                        
        libroServicio.darBajaLibrosPorIdEditorial(id);
        Editorial editorial = editorialRepositorio.findById(id).get();
        editorial.setAlta(false);
        return editorialRepositorio.save(editorial);
    }
    
      @Transactional
    public Editorial darDeAltaEditorial(String id){ 
        libroServicio.darAltaLibrosPorIdEditorial(id);
        Editorial editorial = editorialRepositorio.findById(id).get();
        editorial.setAlta(true);
        return editorialRepositorio.save(editorial);
    }
    
    @Transactional
    public void eliminarEditorial(Editorial editorial) {
        editorialRepositorio.delete(editorial);
    }
    
    @Transactional
    public void eliminarEditorialPorId(String id) {
        libroServicio.eliminarLibroPorIdEditorial(id);
        editorialRepositorio.deleteById(id);
    }

    public List<Editorial> listarEditoriales(){
        return editorialRepositorio.findAll();
    }
    
    public List<Editorial> listarEditorialesTrue(){
        return editorialRepositorio.listarEditorialesTrue();
    }
    
    public Optional<Editorial> buscarEditorialPorId (String id) {
        return editorialRepositorio.findById(id);        
    }
    
}
