package arq.soft.front.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import arq.soft.front.clientes.Producto;
import arq.soft.front.forms.AddProductoForm;


@Controller
public class HomeController {


    //private WebClient webClient = WebClient.create("http://localhost:8081");
	private WebClient webClient = WebClient.create("https://arqsoftlibreback.herokuapp.com");

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
    	
    	List<Producto> products = obtenerProductos();
    	
    	AddProductoForm form = new AddProductoForm();
    	model.put("command", form);
        model.put("productos", products);
        return "home";
    }
    
	@RequestMapping(value = "/addProductoForm", method = RequestMethod.POST)
    public Object addProducto(@ModelAttribute("addProductoForm") AddProductoForm form, BindingResult result) {
  
		agregarNuevoProducto(form);
		
    	ModelAndView model = new ModelAndView("home");
    	List<Producto> products = obtenerProductos();
    	model.addObject("command", form);
        model.addObject("productos", products);
	    return model; 
    }
    
    private void agregarNuevoProducto(AddProductoForm form) {
        try {
        	
        	Producto pNew = new Producto();
        	pNew.setCantidad(form.getCantidad());
        	pNew.setCategoria(form.getCategoria());
        	pNew.setNombre(form.getNombre());
        	
            webClient.post().uri("/productos")
                    .syncBody(pNew)
                    .retrieve()
                    .bodyToMono(Producto.class)
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

	private List<Producto> obtenerProductos() {
        try {
            return webClient.get().uri("/productos")
                    .retrieve()
                    .bodyToFlux(Producto.class)
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
