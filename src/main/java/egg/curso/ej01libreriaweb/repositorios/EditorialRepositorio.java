package egg.curso.ej01libreriaweb.repositorios;

import egg.curso.ej01libreriaweb.entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EditorialRepositorio extends JpaRepository<Editorial, String>{

    @Query("SELECT c from Editorial c WHERE c.nombre = :nombre")
    public Editorial buscarEditorialPorNombre(@Param("nombre") String nombre);
    
    @Query("SELECT c from Editorial c WHERE c.alta = true")
    public List<Editorial> listarEditorialesTrue();    
    
}
