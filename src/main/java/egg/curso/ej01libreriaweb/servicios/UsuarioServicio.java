
package egg.curso.ej01libreriaweb.servicios;

import egg.curso.ej01libreriaweb.entidades.Usuario;
import egg.curso.ej01libreriaweb.enums.Role;
import egg.curso.ej01libreriaweb.excepciones.WebExcepcion;
import egg.curso.ej01libreriaweb.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio implements UserDetailsService{ // HAY QUE HACERLO QUE IMPLEMENTE ESTA INTERFAZ PARA TENER EL METODO loadByUserName

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
        
    // RECIBE UN NOMBRE DE USUARIO Y DOS CONTRASEÑAS, UNA PARA VALIDAR
    @Transactional
    public Usuario guardar(String nombreDeUsuario, String contrasena, String contrasena2) throws WebExcepcion{
        
        if (nombreDeUsuario == null || nombreDeUsuario.isEmpty()) {
            throw new WebExcepcion("El nombre de usuario no puede ser null o estar vacio");
        }
        
        if (usuarioRepositorio.buscarUsuarioPorNombreDeUsuario(nombreDeUsuario) != null) {
            throw new WebExcepcion("El nombre de usuario ya esta registrado, intente con otro");
        }
        
        if (contrasena == null || contrasena.isEmpty()) {
            throw new WebExcepcion("La contraseña no puede ser null o estar vacia");
        }
        
        if (contrasena2 == null || contrasena2.isEmpty()) {
            throw new WebExcepcion("La confirmacion de contraseña no puede ser null o estar vacia");
        }                
        
        if (!contrasena.equals(contrasena2)) {
            throw new WebExcepcion("Las contraseñas deben ser iguales");
        }
        
        Usuario usuario = new Usuario();
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // CLASE NECESARIA PARA ENCRIPTAR LA CONTRASEÑA
        
        usuario.setNombreDeUsuario(nombreDeUsuario);
        usuario.setContrasena(encoder.encode(contrasena)); // ENCRIPTACION DE LA CONTRASEÑA A TRAVES DEL METODO encode()
        usuario.setRol(Role.USER);
        
        return usuarioRepositorio.save(usuario);
    }
    
    public Usuario buscarUsuarioPorNombreDeUsuario(String nombreDeUsuario){
        return usuarioRepositorio.buscarUsuarioPorNombreDeUsuario(nombreDeUsuario);
    }

    @Override
    public UserDetails loadUserByUsername(String nombreDeUsuario) throws UsernameNotFoundException { // METODO AL QUE SE LLAMA CUANDO UN USUARIO SE QUIERA LOGUEAR        
        try {
            Usuario usuario = usuarioRepositorio.buscarUsuarioPorNombreDeUsuario(nombreDeUsuario);
            User user; // LO CREAMOS PORQUE EL METODO DEVUELVE UserDetails
            
            // CREANDO PERMISOS=
            
            List<GrantedAuthority> authorities = new ArrayList<>(); // ACA GUARDAREMOS LOS PERMISOS
            
            authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol())); // PERMISO 1 SimpleGrantedAuthority CLASE QUE REPRESENTA LOS PREMISOS? ENTRE PARENTESIS EL NOMBRE DEL ENUM DEL ROL?
            
//            authorities.add(new SimpleGrantedAuthority("CLIENTE")); // PERMISO 2
//            authorities.add(new SimpleGrantedAuthority("CLIENTE")); // PERMISO 3
            
            return new User(nombreDeUsuario, usuario.getContrasena(), authorities); // LOS authorities SON LOS PERMISOS
        } catch (Exception e) {
            throw new UsernameNotFoundException("El usuario solicitado no existe");
        }
    }
    
}
