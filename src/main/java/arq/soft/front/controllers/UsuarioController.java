package arq.soft.front.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import arq.soft.front.clientes.Usuario;
import arq.soft.front.exceptions.EmailEnUsoException;
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
    public Object addUsuario(@ModelAttribute("addUsuarioForm") AddUsuarioForm form, BindingResult result) {
		ModelAndView model = new ModelAndView("usuario");

		try {
			agregarNuevoUsuario(form);
			
	    	List<Usuario> usuarios = obtenerUsuarios();
	    	model.addObject("command", new AddUsuarioForm());
	        model.addObject("usuarios", usuarios);
		}catch(EmailEnUsoException e){
		 	List<Usuario> usuarios = obtenerUsuarios();
	    	model.addObject("command", new AddUsuarioForm());
	        model.addObject("usuarios", usuarios);
	        model.addObject("error","Debe elegir un elegir un email que no se encuentre en el sistema.");
		}catch(Exception e){
		 	List<Usuario> usuarios = obtenerUsuarios();
	    	model.addObject("command", new AddUsuarioForm());
	        model.addObject("usuarios", usuarios);
			model.addObject("error", "Ocurrio un error interno, por favor comunicarse con el administrador.");
		}

		return model; 
    }	
}
