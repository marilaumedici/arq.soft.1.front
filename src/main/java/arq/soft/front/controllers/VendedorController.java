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
	
	// Metodos privados
    private void agregarNuevoVendedor(AddVendedorForm form) {
        try {
        	
        	Vendedor vNew = new Vendedor();
        	vNew.setRazonSocial(form.getRazonSocial());
        	vNew.setEmail(form.getEmail());
        	
            getWebClient().post().uri("/vendedores")
                    .syncBody(vNew)
                    .retrieve()
                    .bodyToMono(Vendedor.class)
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
    
	private List<Vendedor> obtenerVendedores() {
        try {
            return getWebClient().get().uri("/vendedores")
                    .retrieve()
                    .bodyToFlux(Vendedor.class)
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
