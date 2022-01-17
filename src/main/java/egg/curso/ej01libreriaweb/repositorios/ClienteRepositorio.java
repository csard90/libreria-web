package egg.curso.ej01libreriaweb.repositorios;

import egg.curso.ej01libreriaweb.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String>{

    @Query("SELECT c FROM Cliente c WHERE c.documento = :documento")
    public Cliente buscarClientePorDocumento(@Param("documento") Long documento);
    
}
