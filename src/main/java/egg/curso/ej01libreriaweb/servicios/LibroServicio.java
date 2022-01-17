package egg.curso.ej01libreriaweb.servicios;

import egg.curso.ej01libreriaweb.entidades.Libro;
import egg.curso.ej01libreriaweb.excepciones.WebExcepcion;
import egg.curso.ej01libreriaweb.repositorios.LibroRepositorio;
import egg.curso.ej01libreriaweb.servicios.AutorServicio;
import egg.curso.ej01libreriaweb.servicios.EditorialServicio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {
    
    @Autowired
    AutorServicio autorServicio;
    
    @Autowired
    EditorialServicio editorialServicio;
    
    @Autowired
    LibroRepositorio libroRepositorio;
    
    @Transactional
    public Libro guardarLibro(Libro libro) throws WebExcepcion{                                                
        
        if (libro.getIsbn() == null || libro.getIsbn() < 1) {
            throw new WebExcepcion("El ISBN del libro no puede ser null o negativo");
        }                
        
        if (libro.getTitulo().isEmpty() || libro.getTitulo() == null) {
            throw new WebExcepcion("El titulo del libro no puede ser null o estar vacio");
        }
        
        if (libro.getAnio() == null) {
            throw new WebExcepcion("El año de publicacion del libro no puede ser null");
        }                                               
        
        if (libro.getEjemplares() == null || libro.getEjemplares() < 1) {
            throw new WebExcepcion("Los ejemplares disponibles del libro no pueden ser null o negativos");
        }                     
        
        if (libro.getEjemplaresPrestados() == null || libro.getEjemplaresPrestados() > libro.getEjemplares() || libro.getEjemplaresPrestados() < 0) {
            throw new WebExcepcion("Los ejemplares prestados del libro no pueden ser null, superar en cantidad a la cantidad de los ejemplares disponibles o ser menores que 0");
        }
                
        libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());        
        if (libro.getAlta() == null) {
            libro.setAlta(true);
        }
        libro.setAutor(autorServicio.buscarAutorPorId(libro.getAutor().getId()).get());        
        libro.setEditorial(editorialServicio.buscarEditorialPorId(libro.getEditorial().getId()).get());

        return libroRepositorio.save(libro);
                    
        
        
    }
    
    public List<Libro> listarLibros(){
        return libroRepositorio.findAll();
    }
    
    @Transactional
    public Libro darAltaLibro(String id) {
        Libro libro = libroRepositorio.findById(id).get();
        libro.setAlta(true);
        return libroRepositorio.save(libro);
    }
    
    @Transactional
    public void darAltaLibrosPorIdEditorial(String id) {        
        List<Libro> libros = libroRepositorio.buscarLibrosPorIdEditoriales(id);
        for (Libro libro : libros ) {
            libro.setAlta(true);
            libroRepositorio.save(libro);
        }       
    }    
    
    @Transactional
    public void darAltaLibrosPorIdAutor(String id) {        
        List<Libro> libros = libroRepositorio.buscarLibrosPorIdAutor(id);
        for (Libro libro : libros ) {
            libro.setAlta(true);
            libroRepositorio.save(libro);
        }       
    }    
    
    @Transactional
    public Libro darBajaLibro(String id) {
        Libro libro = libroRepositorio.findById(id).get();
        libro.setAlta(false);        
        return libroRepositorio.save(libro);
    }
    
//    @Transactional
//    public void darBajaLibrosPorIdEditorial(String id) {        
//        libroRepositorio.darBajaLibrosPorIdEditorial(id);
//    }        

    @Transactional
    public void darBajaLibrosPorIdEditorial(String id) {        
        List<Libro> libros = libroRepositorio.buscarLibrosPorIdEditoriales(id);
        for (Libro libro : libros ) {
            libro.setAlta(false);
            libroRepositorio.save(libro);
        }       
    }    
    
    @Transactional
    public void darBajaLibrosPorIdAutor(String id) {        
        List<Libro> libros = libroRepositorio.buscarLibrosPorIdAutor(id);
        for (Libro libro : libros ) {
            libro.setAlta(false);
            libroRepositorio.save(libro);
        }       
    }    
    
    
    @Transactional
    public void eliminarLibro(String id){
        libroRepositorio.deleteById(id);
    }        
    
    public Libro buscarLibroPorId(String id){
        return libroRepositorio.findById(id).get();
    }
    
    public void eliminarLibroPorIdAutor(String id){
        List<Libro> libros = libroRepositorio.buscarLibrosPorIdAutor(id);
        for (Libro libro : libros ) {
            libroRepositorio.deleteById(libro.getId());
        }        
    }
    
     public void eliminarLibroPorIdEditorial(String id){
        List<Libro> libros = libroRepositorio.buscarLibrosPorIdEditoriales(id);
        for (Libro libro : libros ) {
            libroRepositorio.deleteById(libro.getId());
        }        
    }
    
}
