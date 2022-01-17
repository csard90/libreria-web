//package egg.curso.ej01libreriaweb.servicios;
//
//import egg.curso.ej01libreriaweb.entidades.Cliente;
//import egg.curso.ej01libreriaweb.entidades.Libro;
//import egg.curso.ej01libreriaweb.entidades.Prestamo;
//import egg.curso.ej01libreriaweb.excepciones.WebExcepcion;
//import java.util.Date;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PrestamoServicio {
//
//    @Autowired
//    LibroServicio libroServicio;
//    
//    @Autowired
//    ClienteServicio clienteServicio;
//    
//    public void guardarPrestamo(Long documentoCliente, Libro libro) throws WebExcepcion{
//        
//        if (documentoCliente == null) {
//            throw new WebExcepcion("El documento del cliente no puede ser null");
//        }
//        
//        if (libro == null) {
//            throw new WebExcepcion("El libro no puede ser null");
//        }
//        
//        if () {
//            
//        }
//        
//        if (libro.getEjemplaresRestantes() < 1) {
//            throw new WebExcepcion("No quedan ejemplares del libro disponibles para prestar");
//        }                
//        
//        Prestamo prestamo = new Prestamo();
//        
//        prestamo.setAlta(true);
//        prestamo.setFechaPrestamo(new Date());
//        prestamo.setLibro(libro);
//        
//        
//    }
//
//}
