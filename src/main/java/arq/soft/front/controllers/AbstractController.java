package arq.soft.front.controllers;

import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import arq.soft.front.clientes.Producto;
import arq.soft.front.clientes.Vendedor;
import arq.soft.front.forms.AddProductoForm;
import arq.soft.front.forms.AddVendedorForm;

public abstract class AbstractController {
	
	private WebClient webClient = WebClient.create("http://localhost:8081");
	//private WebClient webClient = WebClient.create("https://arqsoftlibreback.herokuapp.com");
	
	//*********************VENDEDOR*****************************//
	
	protected void agregarNuevoVendedor(AddVendedorForm form) {
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
    
	protected List<Vendedor> obtenerVendedores() {
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
	
	protected Vendedor buscarVendedor(long id) {
	      try {
	            return getWebClient().get().uri("/vendedores/"+id)
	                    .retrieve()
	                    .bodyToMono(Vendedor.class)
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
	
	//*******************PRODUCTOS**********************//

	protected void updateProducto(Producto producto) {
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
	
	protected Producto buscarProducto(long id) {
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

	
	
	protected String deleteProductoById(long id) {
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
    
	protected void agregarNuevoProducto(AddProductoForm form) {
        try {
        	
        	Producto pNew = new Producto();
        	pNew.setCantidad(form.getCantidad());
        	pNew.setCategoria(form.getCategoria());
        	pNew.setNombre(form.getNombre());
        	pNew.setDescripcion(form.getDescripcion());
        	pNew.setPrecio(form.getPrecio());
        	pNew.setIdVendedor(form.getIdVendedor());
        	
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

	protected List<Producto> obtenerProductos() {
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
	
	protected List<Producto> obtenerProductosByVendedor(long id) {
        try {
            return getWebClient().get().uri("/productos/vendedor/"+id)
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
	
	
	
	/** Get&Sett **/

	public WebClient getWebClient() {
		return webClient;
	}

	public void setWebClient(WebClient webClient) {
		this.webClient = webClient;
	}

}
