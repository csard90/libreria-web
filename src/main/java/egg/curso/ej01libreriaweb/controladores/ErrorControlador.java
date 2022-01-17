package egg.curso.ej01libreriaweb.controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// CONTROLADOR DE ERRORES: RECIBE PETICIONES DE ERROR Y VA A MOSTRAR HTML DE ERROR EN VEZ DE MOSTRAR PAGINA POR DEFECTO QUE NOS DA TOMCAT

@Controller
public class ErrorControlador implements ErrorController{   // 1- TIENE QUE IMPLEMENTAR ERROR CONTROLLER

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})     // @RequestMapping VA ACA SI O SI. POR QUE? PORQUE EL MISMO METODO VA A TRABAJAR TANTO CON POST COMO CON GET ATRAPANDO CADA ERROR QUE VAYA A SUCEDER. value = LA URL QUE INGRESA A ESTE METODO.    
    public String mostrarPaginaError(ModelMap modelMap, HttpServletRequest httpServletRequest) { // HttpServletRequest --> REPRESENTA A LA PETICION QUE SE MANDO POR NAVEGADOR (SEA POST O GET)
        String mensajeDeError = "";
        Integer codigoDeError = (Integer) httpServletRequest.getAttribute("javax.servlet.error.status_code"); // --> DE LA PETICION OBTENEMOS EL CODIGO DE ERROR
        switch (codigoDeError){ // DEPENDIENDO DEL CODIGO DE ERROR GENERAMOS UN MENSAJE
            case 400:
                mensajeDeError = "El recurso solicitado no existe";
                break;
            case 401:
                mensajeDeError = "No se encuentra autorizado";
                break;
            case 403:
                mensajeDeError = "No tiene permisos para acceder al recurso";
                break;
            case 404:
                mensajeDeError = "El recurso solicitado no se ha encontrado";
                break;
            case 500:
                mensajeDeError = "Error de servidor";
                break;
            default:
                mensajeDeError = "Ha ocurrido un error";
        }
        modelMap.addAttribute("codigoDeError", codigoDeError);
        modelMap.addAttribute("mensajeDeError", mensajeDeError);
        
        return "error";
    }
            
}
