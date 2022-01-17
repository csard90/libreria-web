package egg.curso.ej01libreriaweb;

// * CLASE ESPECIFICA PARA LA CONFIGURACION DE SEGURIDAD (POR EJEMPLO PODEMOS SACAR PARA QUE NOS PIDA USUARIO Y CONTRASEÑA PARA INGRESAR A LA PAGINA)

// EN ESTA CLASE SE TRABAJA CON: AUTENTICACION Y AUTORIZACIONES.
    
    // * AUTENTICACION: QUE HAYA UN USUARIO QUE SE PUEDA LOGUEAR, UNA SESION DE USUARIO.
    
    // * AUTORIZACION: LO QUE TIENE PERMITIDO EL USUARIO (PUEDE HABER CIERTOS ROLES, CADA UNO VA A TENER CIERTOS PERMISOS)
    
    // USAREMOS:
    // * UserDetailService --> NOS PROVEE DEL METODO loadByUser() (QUE VAMOS A SOBRESCRIBIR PARA QUE USUARIO SE LOGUEE) --> LO APLICAMOS EN USUARIOSERVICIO, PERO SE PODRIA HACER UNA CLASE APARTE PARA APLICARLO. 
    // * UN METODO QUE VA A CONFIGURAR LA AUTENTICACION. LE VAMOS A PASAR QUIEN ES EL USERDETAILSERVICE Y QUE CLASE DE ENCRIPTACION USAREMOS.
    // CON ESOS DOS DATOS SPRING SECURITY PUEDE TRABAJAR Y DECIR A ESTE USUARIO LO LOGUEAMOS DE ESTA FORMA
    // * LA CONFIGURACION DE LAS PETICIONES (ACCESOS PERMITIDOS, CUAL SERA EL LOGIN Y LOGOUT DE NUESTRA PAGINA )

import egg.curso.ej01libreriaweb.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// * TIENE QUE SER HERMANA DEL MAIN

@Configuration // ANOTACION QUE LE INFORMA A SPRING QUE ESTA CLASE ES DE CONFIGURACION (DE LA SEGURIDAD)
@EnableWebSecurity // HABILITA SPRING SECURITY EN NUESTRO PROYECTO
@EnableGlobalMethodSecurity(prePostEnabled = true) // PERMITE AGREGAR UNA SERIA DE PROPIEDADES A NUESTRA APP. prePostEnable = true NOS PERMITE PODER AUTORIZAR LAS URLS CON EL preAuthorize

public class Seguridad extends WebSecurityConfigurerAdapter{ // HACIENDOLA HEREDAR DE ESTA CLASE LE INDICAMOS A SPRING QUE LA CLASE SEGURIDAD ES LA ENCARGADA DE LA SEGURIDAD
        
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { // METODO DE AUTENTICACION
        auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder()); 
    }

    @Override // 
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/css/*", "/img/*", "/js/*").permitAll() /* CON ESTA LINEA PODRIAMOS INGRESAR A NUESTRA APP EVITANDO EL LOGIN/OUT PREDETERMINADO */ .and().formLogin() /* DESPUES DEL AND Y A PARTIR DEL formLogin CONFIGURA EL LOGIN */ .loginPage("/login") /* (URL DE NUESTRO LOGIN)*/ .usernameParameter("nombreDeUsuario") /* (NOMBRE DE USUARIO DEL USUARIO, DE LA VARIABLE?) */ .passwordParameter("contrasena").defaultSuccessUrl("/?login") /* (DONDE VAMOS A IR CUANDO EL USUARIO SE LOGUEE CORRECTAMENTE) */ .loginProcessingUrl("/logincheck") /* (URL QUE VA A PROCESAR EL LOGUEO, POR DEFECTO HAY QUE ESCRIBIR ESO. SERIA LA URL A LA QUE HAY QUE MANDAR EL FORM DE LOGIN) */ .failureUrl("/login?error=error") /* (A DONDE VA A IR SI FALLA EL LOGUEO, error=error SI LLEGA ESE PARAMETRO MANDAMOS UN MENSAJE DETERMINADO, ESTO SIGNIFICA QUE HUBO UN INTENTO FALLIDO DE LOGUEO) */ .permitAll().and().logout() /* A PARTIR DE AQUI CONFIGURAMOS EL LOGOUT*/ .logoutUrl("/logout") /* (URL DEL LOGOUT) */ .logoutSuccessUrl("/login?logout").and().csrf().disable(); /* DESHABILITAMOS PROTECCION PARA DISMINUIR COMPLEJIDAD */    
    }
    
    
}
