package egg.curso.ej01libreriaweb.entidades;

import egg.curso.ej01libreriaweb.enums.Role;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator (name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String apellido;    
    private String nombreDeUsuario;
    private String contrasena; // MAS ADELANTE LO ENCRIPTAMOS CON SPRING SECURITY PARA QUE NO ESTE GUARDADO COMO TEXTO PLANO
    @Enumerated(EnumType.STRING) // EnumType.STRING --> FORMA EN LA QUE SE GUARDA EL ENUM EN LA BASE DE DATOS
    private Role rol;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String apellido, String nombreDeUsuario, String contrasena, Role rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreDeUsuario = nombreDeUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", nombreDeUsuario=" + nombreDeUsuario + ", contrasena=" + contrasena + ", rol=" + rol + '}';
    }   
        
}
