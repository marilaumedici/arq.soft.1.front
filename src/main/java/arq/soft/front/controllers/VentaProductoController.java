package arq.soft.front.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import arq.soft.front.clientes.Categoria;
import arq.soft.front.clientes.Producto;
import arq.soft.front.clientes.Usuario;
import arq.soft.front.clientes.Vendedor;
import arq.soft.front.forms.AddProductoForm;
import arq.soft.front.forms.AddVentaProductoForm;
import arq.soft.front.forms.BuscarProductoFiltroForm;


@Controller
public class VentaProductoController extends AbstractController {
	@RequestMapping(value = "/redComprarProducto", method = RequestMethod.GET)
	public ModelAndView redirectComprarProducto(@RequestParam("getItem") long id){
		
		try{
			
            Producto producto = buscarProducto(id);
        	List<Usuario> usuarios = obtenerUsuarios();

            AddVentaProductoForm form = new AddVentaProductoForm();
            form.setCantidadMaxima(producto.getCantidad());
            form.setIdProducto(id);
            
			ModelAndView m = new ModelAndView("ventaProducto");
			m.addObject("command",form);
	    	m.addObject("usuarios", usuarios);

			return m;
			
		}catch(Exception e){
			
			ModelAndView m = new ModelAndView("home");
			m.addObject("error", "Ocurrio un error interno, por favor comunicarse con el administrador.");
			return m;
		}
	}
	
	@RequestMapping(value = "/addVentaProductoForm", method = RequestMethod.POST)
    public Object addProducto(@ModelAttribute("addVentaProductoForm") AddVentaProductoForm form, BindingResult result) {
		if(form.getIdComprador() <= 0) {
	    	ModelAndView model = new ModelAndView("ventaProducto");
	    	model.addObject("command", new AddVentaProductoForm());
	    	
        	List<Usuario> usuarios = obtenerUsuarios();

        	model.addObject("usuarios", usuarios);
	        model.addObject("error","Debe elegir un usuario comprador. De no existir, crear uno.");
		    return model; 
		}

		agregarNuevaVentaProducto(form.getCantidadComprada(),form.getIdProducto(),form.getIdComprador()); 
		
	    List<Categoria> categorias = obtenerCategorias();
	    List<Producto> products = obtenerProductos();
		
		ModelAndView model = new ModelAndView("home");
    	model.addObject("command", new BuscarProductoFiltroForm());
        model.addObject("productos", products);
        model.addObject("categorias", categorias);
        
	    return model; 
    }
	
}
