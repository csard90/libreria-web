package egg.curso.ej01libreriaweb;

import egg.curso.ej01libreriaweb.excepciones.WebExcepcion;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ej01LibreriaWebApplication {

	public static void main(String[] args) throws WebExcepcion {
		SpringApplication.run(Ej01LibreriaWebApplication.class, args);                 
	}

}
