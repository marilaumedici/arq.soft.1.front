package arq.soft.front.controllers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import arq.soft.front.clientes.Categoria;
import arq.soft.front.clientes.Producto;
import arq.soft.front.clientes.Vendedor;
import arq.soft.front.exceptions.VendedorNoEncontradoException;
import arq.soft.front.forms.AddProductoForm;
import arq.soft.front.forms.BuscarProductoFiltroForm;
import arq.soft.front.forms.ModifyProductoForm;


@Controller
public class ProductoController extends AbstractController {
	

    @RequestMapping("/productos")
    public String productos(Map<String, Object> model) {
    	
    	List<Producto> products = obtenerProductos();
    	List<Vendedor> vendedores = obtenerVendedores();
    	List<Categoria> categorias = obtenerCategorias();
    	
    	model.put("command", new AddProductoForm());
    	model.put("vendedores", vendedores);
        model.put("productos", products);
        model.put("categorias", categorias);
        return "producto";
    }
    
	@RequestMapping(value = "/addProductoForm", method = RequestMethod.POST)
    public Object addProducto(@ModelAttribute("addProductoForm") AddProductoForm form, BindingResult result) {
		
		if(form.getIdVendedor() <= 0) {
			
			List<Vendedor> vendedores = obtenerVendedores();
			List<Producto> products = obtenerProductos();
			List<Categoria> categorias = obtenerCategorias();
			
			ModelAndView model = new ModelAndView("producto");
	    	model.addObject("command", new AddProductoForm());
	    	model.addObject("vendedores", vendedores);
	    	model.addObject("categorias", categorias);
	        model.addObject("productos", products);
	        model.addObject("error","Debe elegir un vendedor. De no existir, crear uno.");
		    return model; 
		}
		
		agregarNuevoProducto(form);
		
		List<Vendedor> vendedores = obtenerVendedoresSelectoOrdenado(form.getIdVendedor());
		List<Producto> products = obtenerProductos();//obtenerProductosByVendedor(form.getIdVendedor());//
		List<Categoria> categorias = obtenerCategorias();
    	ModelAndView model = new ModelAndView("producto");
    	model.addObject("command", new AddProductoForm());
    	model.addObject("vendedores", vendedores);
    	model.addObject("categorias", categorias);
        model.addObject("productos", products);
	    return model; 
    }
	
	@RequestMapping(value = "/redModifyProducto", method = RequestMethod.GET)
	public ModelAndView redirectModificarProducto(@RequestParam("getItem") long id){
		
		try{
			
            Producto producto = buscarProducto(id);
			
            ModifyProductoForm form = new ModifyProductoForm();
            form.setCantidad(producto.getCantidad());
            //form.setCategoria(producto.getCategoria());
            form.setDescripcion(producto.getDescripcion());
            form.setId(id);
            form.setNombre(producto.getNombre());
            form.setPrecio(producto.getPrecio());
            form.setIdVendedor(producto.getIdVendedor());
            
			ModelAndView m = new ModelAndView("modifyProducto");
			m.addObject("command",form);
			return m;
			
		}catch(Exception e){
			
			ModelAndView m = new ModelAndView("home");
			m.addObject("error", "Ocurrio un error interno, por favor comunicarse con el administrador.");
			return m;
		}
	}
	
	@RequestMapping(value = "/modifyProductoForm", method = RequestMethod.POST)
    public Object addProducto(@ModelAttribute("modifyProductoForm") ModifyProductoForm form, BindingResult result) {
  
		 Producto producto = new Producto();
		 producto.setCantidad(form.getCantidad());
		 //producto.setCategoria(form.getCategoria());
		 producto.setDescripcion(form.getDescripcion());
		 producto.setId(form.getId());
		 producto.setNombre(form.getNombre());
		 producto.setPrecio(form.getPrecio());
		
		 updateProducto(producto);
		 
		 List<Vendedor> vendedores = obtenerVendedoresSelectoOrdenado(form.getIdVendedor());
		 List<Categoria> categorias = obtenerCategorias();
    	 ModelAndView model = new ModelAndView("producto");
    	 List<Producto> products = obtenerProductos();//obtenerProductosByVendedor(form.getIdVendedor());//
    	 AddProductoForm formALta = new AddProductoForm();
    	 formALta.setIdVendedor(form.getIdVendedor());
    	 model.addObject("command", formALta);
    	 model.addObject("categorias", categorias);
    	 model.addObject("vendedores", vendedores);
         model.addObject("productos", products);
	     return model; 
    }
	

	@RequestMapping(value = "/deleteProducto", method = RequestMethod.GET)
    public Object deleteProducto(@RequestParam("getItem") long id) {
		List<Categoria> categorias = obtenerCategorias();
		Producto producto = buscarProducto(id);
		long idVendedor = producto.getIdVendedor();
		deleteProductoById(id);
		
    	ModelAndView model = new ModelAndView("producto");
    	List<Producto> products = obtenerProductos();//obtenerProductosByVendedor(idVendedor);//
		List<Vendedor> vendedores = obtenerVendedoresSelectoOrdenado(idVendedor);
    	model.addObject("command", new AddProductoForm());
    	model.addObject("vendedores", vendedores);
    	model.addObject("categorias", categorias);
    	model.addObject("productos", products);
	    return model; 
    }
	
	@RequestMapping(value = "/productos/filter", method = RequestMethod.GET)
    public Object welcome(@RequestParam("getItem") long id) {
    	
		List<Producto> products = obtenerProductosByCategoriaId(id);
		List<Categoria> categorias = obtenerCategorias();
		ModelAndView model = new ModelAndView("home");
		model.addObject("command", new BuscarProductoFiltroForm());
        model.addObject("productos", products);
        model.addObject("categorias", categorias);
        return model;
    }
	
	@RequestMapping(value = "/productos/buscarProductoDetalleForm", method = RequestMethod.POST)
    public Object addProducto(@ModelAttribute("buscarProductoDetalleForm") BuscarProductoFiltroForm form, BindingResult result) {
		
		    List<Categoria> categorias = obtenerCategorias();
		    List<Producto> products = obtenerProductosByDescripcion(form.getDescripcion());
			
			ModelAndView model = new ModelAndView("home");
	    	model.addObject("command", new BuscarProductoFiltroForm());
	        model.addObject("productos", products);
	        model.addObject("categorias", categorias);
		    return model; 
	}
	

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public Object cargarProductosMasivamente(@RequestParam("file") MultipartFile reapExcelDataFile) {
       
        XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
		} catch (IOException e) {
			
		}
        XSSFSheet worksheet = workbook.getSheetAt(0);
        
        XSSFRow usuarioRow = worksheet.getRow(0);
        String  email      = usuarioRow.getCell(0).getStringCellValue();
        Vendedor  vendedor = null;
        try {
        	
        	vendedor = buscarVendedorByEmail(email);
        	
		} catch (VendedorNoEncontradoException e) {
			
			List<Vendedor> vendedores = obtenerVendedores();
			List<Producto> products = obtenerProductos();
			List<Categoria> categorias = obtenerCategorias();
			
			ModelAndView model = new ModelAndView("producto");
	    	model.addObject("command", new AddProductoForm());
	    	model.addObject("vendedores", vendedores);
	    	model.addObject("categorias", categorias);
	        model.addObject("productos", products);
	        model.addObject("error","Debe colocar en la primera celda del excel el email de un vendedor existente. De no existir, crear uno.");
		    return model; 
		}
        
        for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {

            XSSFRow row = worksheet.getRow(i);
            
            
           // tempStudent.setId((int) row.getCell(0).getNumericCellValue());
            //tempStudent.setContent(row.getCell(1).getStringCellValue());
            //tempStudentList.add(tempStudent);   
        }
        
    	List<Vendedor> vendedores = obtenerVendedores();
		List<Producto> products = obtenerProductos();
		List<Categoria> categorias = obtenerCategorias();
		
		ModelAndView model = new ModelAndView("producto");
    	model.addObject("command", new AddProductoForm());
    	model.addObject("vendedores", vendedores);
    	model.addObject("categorias", categorias);
        model.addObject("productos", products);
        return model;
    }
	
	/**
	 * Metodo para retornar la lista de vendedores ordenadas primero
	 * por el vendedor que esta seleccionado
	 * @param idVendedor
	 * @return List<Vendedor>
	 */
	private List<Vendedor> obtenerVendedoresSelectoOrdenado(long idVendedor) {
		List<Vendedor> vendedoresSelector = new ArrayList<Vendedor>();
		 List<Vendedor> vendedoresSelectorHead = new ArrayList<Vendedor>();
		 List<Vendedor> vendedores = obtenerVendedores();

		 for(Vendedor v : vendedores) {
			 if(v.getId() == idVendedor) {
				 vendedoresSelectorHead.add(v);
			 }else {
				 vendedoresSelector.add(v);
			 }
		 }
		 vendedoresSelectorHead.addAll(vendedoresSelector);
		return vendedoresSelectorHead;
	}

}
