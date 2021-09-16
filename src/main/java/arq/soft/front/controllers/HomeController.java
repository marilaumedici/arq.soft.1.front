package arq.soft.front.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import arq.soft.front.clientes.Producto;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {


    private WebClient webClient = WebClient.create("http://localhost:8081");

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
    	
    	List<Producto> products = obtenerProductos();
      
        model.put("productos", products);
        return "home";
    }
    
    public List<Producto> obtenerProductos() {
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
