package arq.soft.front.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.ModelAndView;
import arq.soft.front.clientes.Producto;
import arq.soft.front.forms.AddProductoForm;
import arq.soft.front.forms.ModifyProductoForm;


@Controller
public class ProductoController extends AbstractController {
	

    @RequestMapping("/productos")
    public String welcome(Map<String, Object> model) {
    	
    	List<Producto> products = obtenerProductos();
    	
    	model.put("command", new AddProductoForm());
        model.put("productos", products);
        return "producto";
    }
    
	@RequestMapping(value = "/addProductoForm", method = RequestMethod.POST)
    public Object addProducto(@ModelAttribute("addProductoForm") AddProductoForm form, BindingResult result) {
  
		agregarNuevoProducto(form);
		
    	ModelAndView model = new ModelAndView("producto");
    	List<Producto> products = obtenerProductos();
    	model.addObject("command", new AddProductoForm());
        model.addObject("productos", products);
	    return model; 
    }
	
	@RequestMapping(value = "/redModifyProducto", method = RequestMethod.GET)
	public ModelAndView redirectModificarProducto(@RequestParam("getItem") long id){
		
		try{
			
            Producto producto = buscarProducto(id);
			
            ModifyProductoForm form = new ModifyProductoForm();
            form.setCantidad(producto.getCantidad());
            form.setCategoria(producto.getCategoria());
            form.setDescripcion(producto.getDescripcion());
            form.setId(id);
            form.setNombre(producto.getNombre());
            form.setPrecio(producto.getPrecio());
            
			ModelAndView m = new ModelAndView("modifyProducto");
			m.addObject("command",form);
			return m;
			
		}catch(Exception e){
			
			ModelAndView m = new ModelAndView("home");
			m.addObject("errorG", "OcurriÃ³ un error interno, por favor comunicarse con el administrador.");
			return m;
		}
	}
	
	@RequestMapping(value = "/modifyProductoForm", method = RequestMethod.POST)
    public Object addProducto(@ModelAttribute("modifyProductoForm") ModifyProductoForm form, BindingResult result) {
  
		 Producto producto = new Producto();
		 producto.setCantidad(form.getCantidad());
		 producto.setCategoria(form.getCategoria());
		 producto.setDescripcion(form.getDescripcion());
		 producto.setId(form.getId());
		 producto.setNombre(form.getNombre());
		 producto.setPrecio(form.getPrecio());
		
		 updateProducto(producto);
		 
    	 ModelAndView model = new ModelAndView("producto");
    	 List<Producto> products = obtenerProductos();
    	 model.addObject("command", new AddProductoForm());
         model.addObject("productos", products);
	     return model; 
    }
	

	@RequestMapping(value = "/deleteProducto", method = RequestMethod.GET)
    public Object deleteProducto(@RequestParam("getItem") long id) {
  
		deleteProductoById(id);
		
    	ModelAndView model = new ModelAndView("producto");
    	List<Producto> products = obtenerProductos();
    	model.addObject("command", new AddProductoForm());
        model.addObject("productos", products);
	    return model; 
    }
	
	
	private void updateProducto(Producto producto) {
        try {
            getWebClient().put().uri("/productos")
                    .syncBody(producto)
                    .retrieve()
                    .bodyToMono(Producto.class)
                    .block();
        } catch (WebClientResponseException ex) {
            //log.error("Error Response code is : {} and the message is {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            //log.error("WebClientResponseException in updateEmployee", ex);
            throw ex;
        } catch (Exception ex) {
            //log.error("Exception in updateEmployee ", ex);
            throw ex;
        }
		
	}
	
	private Producto buscarProducto(long id) {
	      try {
	            return getWebClient().get().uri("/productos/"+id)
	                    .retrieve()
	                    .bodyToMono(Producto.class)
	                    .block();
	        } catch (WebClientResponseException ex) {
	            //log.error("Error Response code is : {} and the message is {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
	            //log.error("WebClientResponseException in retrieveEmployeeById", ex);
	            throw ex;
	        } catch (Exception ex) {
	           // log.error("Exception in retrieveEmployeeById ", ex);
	            throw ex;
	        }
	}

	
	
    public String deleteProductoById(long id) {
        try {
            return getWebClient().delete().uri("/productos/"+id)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException ex) {
            //log.error("Error Response code is : {} and the message is {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            //log.error("WebClientResponseException in updateEmployee", ex);
            throw ex;
        } catch (Exception ex) {
            //log.error("Exception in updateEmployee ", ex);
            throw ex;
        }
    }
    
    private void agregarNuevoProducto(AddProductoForm form) {
        try {
        	
        	Producto pNew = new Producto();
        	pNew.setCantidad(form.getCantidad());
        	pNew.setCategoria(form.getCategoria());
        	pNew.setNombre(form.getNombre());
        	pNew.setDescripcion(form.getDescripcion());
        	pNew.setPrecio(form.getPrecio());
        	
            getWebClient().post().uri("/productos")
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
            return getWebClient().get().uri("/productos")
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
