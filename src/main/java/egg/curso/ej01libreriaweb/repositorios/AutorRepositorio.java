package egg.curso.ej01libreriaweb.repositorios;

import egg.curso.ej01libreriaweb.entidades.Autor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String>{

    @Query("SELECT c from Autor c WHERE c.nombre = :nombre")
    public Autor buscarAutorPorNombre(@Param("nombre") String nombre);    
    
    @Query("SELECT c from Autor c WHERE c.alta = true")
    public List<Autor> listarAutoresTrue();    

}
