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

import arq.soft.front.clientes.Vendedor;
import arq.soft.front.forms.AddVendedorForm;

@Controller
public class VendedorController extends AbstractController {

    @RequestMapping("/vendedores")
    public String welcome(Map<String, Object> model) {
    	
    	List<Vendedor> vendedores = obtenerVendedores();
    	
    	model.put("command", new AddVendedorForm());
        model.put("vendedores", vendedores);
        return "vendedor";
    }
    
	@RequestMapping(value = "/addVendedorForm", method = RequestMethod.POST)
    public Object addVendedor(@ModelAttribute("addVendedorForm") AddVendedorForm form, BindingResult result) {
  
		agregarNuevoVendedor(form);
		
    	ModelAndView model = new ModelAndView("vendedor");
    	List<Vendedor> vendedores = obtenerVendedores();
    	model.addObject("command", new AddVendedorForm());
        model.addObject("vendedores", vendedores);
	    return model; 
    }
	
}
