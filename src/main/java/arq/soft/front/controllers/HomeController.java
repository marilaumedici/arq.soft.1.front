package arq.soft.front.controllers;


import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import arq.soft.front.clientes.Categoria;
import arq.soft.front.clientes.Producto;
import arq.soft.front.clientes.Vendedor;
import arq.soft.front.forms.AddProductoForm;


@Controller
public class HomeController extends AbstractController {


	 @RequestMapping("/")
	    public String welcome(Map<String, Object> model) {
	    	
			List<Producto> products = obtenerProductos();

	        model.put("productos", products);

	        return "home";
	    }

}
