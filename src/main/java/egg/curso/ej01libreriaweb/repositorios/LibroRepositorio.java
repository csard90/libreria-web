package egg.curso.ej01libreriaweb.repositorios;

import egg.curso.ej01libreriaweb.entidades.Editorial;
import egg.curso.ej01libreriaweb.entidades.Libro;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{
       
    @Query("SELECT c FROM Libro c WHERE c.autor.id = :id")
    public List<Libro> buscarLibrosPorIdAutor(@Param("id") String id);
    
    @Query("SELECT c FROM Libro c WHERE c.editorial.id = :id")
    public List<Libro> buscarLibrosPorIdEditoriales(@Param("id") String id);
//    
//    
//    @Transactional
//    @Modifying
//    @Query("UPDATE Libro c SET c.alta = false WHERE c.autor.nombre = 'PAPA NOEL'")
//    public List<Libro> darBajaLibrosPorNombreAutor();
//    
}
