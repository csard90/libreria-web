
package egg.curso.ej01libreriaweb.servicios;

import egg.curso.ej01libreriaweb.entidades.Autor;
import egg.curso.ej01libreriaweb.excepciones.WebExcepcion;
import egg.curso.ej01libreriaweb.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;         
    
    @Autowired
    private LibroServicio libroServicio;         
    
    @Transactional
    public Autor guardarAutor(Autor autor) throws WebExcepcion {                      
        
        if (autor.getNombre() == null || autor.getNombre().isEmpty()) {
            throw new WebExcepcion("No ha ingresado ningun nombre para el autor");
        }                
                                         
        if (!autor.getId().isEmpty()) {  
            return autorRepositorio.save(autor);            
        } else {                    
            autor.setAlta(true);                       
            return autorRepositorio.save(autor);            
        }                            
    }          
    
    @Transactional
    public Autor darDeBajaAutor(String id){                
        libroServicio.darBajaLibrosPorIdAutor(id);
        Autor autor = autorRepositorio.findById(id).get();
        autor.setAlta(false);
        return autorRepositorio.save(autor);
    }
    
      @Transactional
    public Autor darDeAltaAutor(String id){                        
        libroServicio.darAltaLibrosPorIdAutor(id);
        Autor autor = autorRepositorio.findById(id).get();
        autor.setAlta(true);
        return autorRepositorio.save(autor);
    }
    
    @Transactional
    public void eliminarAutor(Autor autor) {
        autorRepositorio.delete(autor);
    }
    
    @Transactional
    public void eliminarAutorPorId(String id) {
        libroServicio.eliminarLibroPorIdAutor(id);
        autorRepositorio.deleteById(id);
    }
            
    public List<Autor> listarAutores(){
        return autorRepositorio.findAll();
    }
    
    public List<Autor> listarAutoresTrue(){
        return autorRepositorio.listarAutoresTrue();
    }
    
    public Optional<Autor> buscarAutorPorId (String id) {
        return autorRepositorio.findById(id);        
    }
    
    
    
}
