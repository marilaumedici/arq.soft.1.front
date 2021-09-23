package arq.soft.front.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.ModelAndView;

import arq.soft.front.clientes.Usuario;
import arq.soft.front.forms.AddUsuarioForm;

@Controller
public class UsuarioController extends AbstractController {

    @RequestMapping("/usuarios")
    public String welcome(Map<String, Object> model) {
    	
    	List<Usuario> usuarios = obtenerUsuarios();
    	
    	model.put("command", new AddUsuarioForm());
        model.put("usuarios", usuarios);
        return "usuario";
    }
    
	@RequestMapping(value = "/addUsuarioForm", method = RequestMethod.POST)
    public Object addVendedor(@ModelAttribute("addUsuarioForm") AddUsuarioForm form, BindingResult result) {
  
		agregarNuevoUsuario(form);
		
    	ModelAndView model = new ModelAndView("usuario");
    	List<Usuario> usuarios = obtenerUsuarios();
    	model.addObject("command", new AddUsuarioForm());
        model.addObject("usuarios", usuarios);
	    return model; 
    }
	
	// Metodos privados
    private void agregarNuevoUsuario(AddUsuarioForm form) {
        try {
        	
        	Usuario uNew = new Usuario();
        	uNew.setNombre(form.getNombre());
        	uNew.setApellido(form.getApellido());
        	uNew.setEmail(form.getEmail());
        	
            getWebClient().post().uri("/usuarios")
                    .syncBody(uNew)
                    .retrieve()
                    .bodyToMono(Usuario.class)
                    .block();
        } catch (WebClientResponseException ex) {
            //log.error("Error Response code is : {} and the message is {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            //log.error("WebClientResponseException in addNewEmployee", ex);
            throw ex;
        } catch (Exception ex) {
            //log.error("Exception in addNewEmployee ", ex);
            throw ex;
        }
	}
    
	private List<Usuario> obtenerUsuarios() {
        try {
            return getWebClient().get().uri("/usuarios")
                    .retrieve()
                    .bodyToFlux(Usuario.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            //log.error("Error Response code is : {} and the message is {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            //log.error("WebClientResponseException in retrieveAllEmployees", ex);
            throw ex;
        } catch (Exception ex) {
            //log.error("Exception in retrieveAllEmployees ", ex);
            throw ex;
        }
    }
	
}
