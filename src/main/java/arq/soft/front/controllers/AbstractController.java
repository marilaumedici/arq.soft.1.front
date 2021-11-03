package arq.soft.front.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import arq.soft.front.clientes.Categoria;
import arq.soft.front.clientes.Producto;
import arq.soft.front.clientes.Usuario;
import arq.soft.front.clientes.Vendedor;
import arq.soft.front.exceptions.EmailEnUsoException;
import arq.soft.front.forms.AddProductoForm;
import arq.soft.front.forms.AddUsuarioForm;
import arq.soft.front.forms.AddVendedorForm;

public abstract class AbstractController {

	//private WebClient webClient = WebClient.create("http://localhost:8081");
	 private WebClient webClient =
	WebClient.create("https://arqsoftlibreback.herokuapp.com");

	// *********************VENDEDOR*****************************//

	protected void agregarNuevoVendedor(AddVendedorForm form) throws EmailEnUsoException {
		try {

			Vendedor vNew = new Vendedor();
			vNew.setRazonSocial(form.getRazonSocial());
			vNew.setEmail(form.getEmail());

			getWebClient().post().uri("/vendedores").syncBody(vNew).retrieve().bodyToMono(Vendedor.class).block();
		} catch (ResponseStatusException ex) {
			throw ex;
		} catch (WebClientResponseException ex) {
	        if(ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
	        	throw new EmailEnUsoException();
	        }
			throw ex;
		} catch (Exception ex) {
			throw ex;
		}
	}

	protected List<Vendedor> obtenerVendedores() {
		try {
			return getWebClient().get().uri("/vendedores").retrieve().bodyToFlux(Vendedor.class).collectList().block();
		} catch (WebClientResponseException ex) {
			// log.error("Error Response code is : {} and the message is {}",
			// ex.getRawStatusCode(), ex.getResponseBodyAsString());
			// log.error("WebClientResponseException in retrieveAllEmployees", ex);
			throw ex;
		} catch (Exception ex) {
			// log.error("Exception in retrieveAllEmployees ", ex);
			throw ex;
		}
	}

	protected Vendedor buscarVendedor(long id) {
		try {
			return getWebClient().get().uri("/vendedores/" + id).retrieve().bodyToMono(Vendedor.class).block();
		} catch (WebClientResponseException ex) {
			// log.error("Error Response code is : {} and the message is {}",
			// ex.getRawStatusCode(), ex.getResponseBodyAsString());
			// log.error("WebClientResponseException in retrieveEmployeeById", ex);
			throw ex;
		} catch (Exception ex) {
			// log.error("Exception in retrieveEmployeeById ", ex);
			throw ex;
		}
	}

	// *********************USUARIO*****************************//
	protected void agregarNuevoUsuario(AddUsuarioForm form) throws EmailEnUsoException {
		try {

			Usuario uNew = new Usuario();
			uNew.setNombre(form.getNombre());
			uNew.setApellido(form.getApellido());
			uNew.setEmail(form.getEmail());

			getWebClient().post().uri("/usuarios").syncBody(uNew).retrieve().bodyToMono(Usuario.class).block();
		} catch (ResponseStatusException ex) {
			throw ex;
		} catch (WebClientResponseException ex) {
			 if(ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
		        	throw new EmailEnUsoException();
		        }
			throw ex;
		} catch (Exception ex) {
			// log.error("Exception in addNewEmployee ", ex);
			throw ex;
		}
	}

	protected List<Usuario> obtenerUsuarios() {
		try {
			return getWebClient().get().uri("/usuarios").retrieve().bodyToFlux(Usuario.class).collectList().block();
		} catch (WebClientResponseException ex) {
			// log.error("Error Response code is : {} and the message is {}",
			// ex.getRawStatusCode(), ex.getResponseBodyAsString());
			// log.error("WebClientResponseException in retrieveAllEmployees", ex);
			throw ex;
		} catch (Exception ex) {
			// log.error("Exception in retrieveAllEmployees ", ex);
			throw ex;
		}
	}
	
	// *********************PRODUCTOS**************************//

	protected void updateProducto(Producto producto) {
		try {
			getWebClient().put().uri("/productos").syncBody(producto).retrieve().bodyToMono(Producto.class).block();
		} catch (WebClientResponseException ex) {
			// log.error("Error Response code is : {} and the message is {}",
			// ex.getRawStatusCode(), ex.getResponseBodyAsString());
			// log.error("WebClientResponseException in updateEmployee", ex);
			throw ex;
		} catch (Exception ex) {
			// log.error("Exception in updateEmployee ", ex);
			throw ex;
		}

	}

	protected Producto buscarProducto(long id) {
		try {
			return getWebClient().get().uri("/productos/" + id).retrieve().bodyToMono(Producto.class).block();
		} catch (WebClientResponseException ex) {
			// log.error("Error Response code is : {} and the message is {}",
			// ex.getRawStatusCode(), ex.getResponseBodyAsString());
			// log.error("WebClientResponseException in retrieveEmployeeById", ex);
			throw ex;
		} catch (Exception ex) {
			// log.error("Exception in retrieveEmployeeById ", ex);
			throw ex;
		}
	}

	protected String deleteProductoById(long id) {
		try {
			return getWebClient().delete().uri("/productos/" + id).retrieve().bodyToMono(String.class).block();
		} catch (WebClientResponseException ex) {
			// log.error("Error Response code is : {} and the message is {}",
			// ex.getRawStatusCode(), ex.getResponseBodyAsString());
			// log.error("WebClientResponseException in updateEmployee", ex);
			throw ex;
		} catch (Exception ex) {
			// log.error("Exception in updateEmployee ", ex);
			throw ex;
		}
	}

	protected void agregarNuevoProducto(AddProductoForm form) {
		try {

			Producto pNew = new Producto();
			pNew.setCantidad(form.getCantidad());
			Categoria c = new Categoria();
			c.setId(form.getIdCategoria());
			pNew.setCategoria(c);
			pNew.setNombre(form.getNombre());
			pNew.setDescripcion(form.getDescripcion());
			pNew.setPrecio(form.getPrecio());
			pNew.setIdVendedor(form.getIdVendedor());

			getWebClient().post().uri("/productos").syncBody(pNew).retrieve().bodyToMono(Producto.class).block();
		} catch (WebClientResponseException ex) {
			// log.error("Error Response code is : {} and the message is {}",
			// ex.getRawStatusCode(), ex.getResponseBodyAsString());
			// log.error("WebClientResponseException in addNewEmployee", ex);
			throw ex;
		} catch (Exception ex) {
			// log.error("Exception in addNewEmployee ", ex);
			throw ex;
		}
	}

	protected List<Producto> obtenerProductos() {
		try {
			return getWebClient().get().uri("/productos").retrieve().bodyToFlux(Producto.class).collectList().block();
		} catch (WebClientResponseException ex) {
			// log.error("Error Response code is : {} and the message is {}",
			// ex.getRawStatusCode(), ex.getResponseBodyAsString());
			// log.error("WebClientResponseException in retrieveAllEmployees", ex);
			throw ex;
		} catch (Exception ex) {
			// log.error("Exception in retrieveAllEmployees ", ex);
			throw ex;
		}
	}
	
	protected List<Producto> obtenerProductosByDescripcion(String descripcion) {
		try {
			return getWebClient().get().uri("/productos/find/"+descripcion).retrieve().bodyToFlux(Producto.class).collectList().block();
		} catch (WebClientResponseException ex) {
			// log.error("Error Response code is : {} and the message is {}",
			// ex.getRawStatusCode(), ex.getResponseBodyAsString());
			// log.error("WebClientResponseException in retrieveAllEmployees", ex);
			throw ex;
		} catch (Exception ex) {
			// log.error("Exception in retrieveAllEmployees ", ex);
			throw ex;
		}
	}
	
	protected List<Producto> obtenerProductosByCategoriaId(long id) {
		try {
			return getWebClient().get().uri("/productos/filter/"+id).retrieve().bodyToFlux(Producto.class).collectList().block();
		} catch (WebClientResponseException ex) {
			// log.error("Error Response code is : {} and the message is {}",
			// ex.getRawStatusCode(), ex.getResponseBodyAsString());
			// log.error("WebClientResponseException in retrieveAllEmployees", ex);
			throw ex;
		} catch (Exception ex) {
			// log.error("Exception in retrieveAllEmployees ", ex);
			throw ex;
		}
	}

	protected List<Producto> obtenerProductosByVendedor(long id) {
		try {
			return getWebClient().get().uri("/productos/vendedor/" + id).retrieve().bodyToFlux(Producto.class)
					.collectList().block();
		} catch (WebClientResponseException ex) {
			// log.error("Error Response code is : {} and the message is {}",
			// ex.getRawStatusCode(), ex.getResponseBodyAsString());
			// log.error("WebClientResponseException in retrieveAllEmployees", ex);
			throw ex;
		} catch (Exception ex) {
			// log.error("Exception in retrieveAllEmployees ", ex);
			throw ex;
		}
	}

	protected List<Categoria> obtenerCategorias() {
		try {
			return getWebClient().get().uri("/categorias").retrieve().bodyToFlux(Categoria.class).collectList().block();
		} catch (WebClientResponseException ex) {
			// log.error("Error Response code is : {} and the message is {}",
			// ex.getRawStatusCode(), ex.getResponseBodyAsString());
			// log.error("WebClientResponseException in retrieveAllEmployees", ex);
			throw ex;
		} catch (Exception ex) {
			// log.error("Exception in retrieveAllEmployees ", ex);
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
