package arq.soft.front.controllers;


import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import arq.soft.front.clientes.Categoria;
import arq.soft.front.clientes.Producto;
import arq.soft.front.clientes.Vendedor;
import arq.soft.front.forms.AddProductoForm;
import arq.soft.front.forms.BuscarProductoFiltroForm;


@Controller
public class HomeController extends AbstractController {


	    @RequestMapping("/")
	    public String welcome(Map<String, Object> model) {
	    	
			List<Producto> products = obtenerProductos();
			List<Categoria> categorias = obtenerCategorias();

			model.put("command", new BuscarProductoFiltroForm());
	        model.put("productos", products);
	        model.put("categorias", categorias);
	        return "home";
	    }

}
