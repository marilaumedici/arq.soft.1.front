package arq.soft.front.controllers;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import arq.soft.front.exceptions.CategoriaNotFoundException;
import arq.soft.front.exceptions.InvalidCantidadProductoException;
import arq.soft.front.exceptions.InvalidDetalleNombreException;
import arq.soft.front.exceptions.InvalidPrecioException;
import arq.soft.front.exceptions.InvalidProductoNombreException;
import arq.soft.front.exceptions.VendedorNoEncontradoException;
import arq.soft.front.forms.AddProductoForm;
import arq.soft.front.forms.BuscarProductoFiltroForm;
import arq.soft.front.forms.ModifyProductoForm;
import arq.soft.front.utils.Utils;
import io.micrometer.core.instrument.util.StringUtils;


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

		agregarNuevoProducto(form.getCantidad(),form.getIdCategoria(),form.getNombre(),form.getDescripcion(),form.getPrecio(),form.getIdVendedor()); 
		
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
		
		    if(StringUtils.isBlank(form.getDescripcion()) && form.getPrecioMaximo() <= 0 && form.getPrecioMinimo() <= 0) {
		    	List<Categoria> categorias = obtenerCategorias();
		        List<Producto> products = obtenerProductos();
		    	ModelAndView model = new ModelAndView("home");
		    	model.addObject("command", new BuscarProductoFiltroForm());
		        model.addObject("productos", products);
		        model.addObject("categorias", categorias);
		        model.addObject("error", "Debe buscar por detalle o precio.");
			    return model; 
		    }
		
		    List<Categoria> categorias = obtenerCategorias();
		    List<Producto> products = obtenerProductosByDescripcionPrecios(form.getDescripcion(),form.getPrecioMinimo(),form.getPrecioMaximo());
			
			ModelAndView model = new ModelAndView("home");
	    	model.addObject("command", new BuscarProductoFiltroForm());
	        model.addObject("productos", products);
	        model.addObject("categorias", categorias);
		    return model; 
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.GET)
    public ResponseEntity<Resource>  devolverModeloExcelCargarProductos() {
    	
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet("productos");
		
		XSSFRow rowVendedor = spreadsheet.createRow(0);
		XSSFCell celVendedor = rowVendedor.createCell(0);
		celVendedor.setCellValue("Email vendedor");
		XSSFRow emptySpace = spreadsheet.createRow(1);
		XSSFRow rowHeader = spreadsheet.createRow(2);
		XSSFCell celCategoria = rowHeader.createCell(0);
		celCategoria.setCellValue("Categoria");
		XSSFCell celCantidad = rowHeader.createCell(1);
		celCantidad.setCellValue("Cantidad");
		XSSFCell celNombre = rowHeader.createCell(2);
		celNombre.setCellValue("Nombre");
		XSSFCell celDetalle = rowHeader.createCell(3);
		celDetalle.setCellValue("Descripcion");
		XSSFCell celPrecio = rowHeader.createCell(4);
		celPrecio.setCellValue("Precio");
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			workbook.write(out);
		} catch (IOException e) {

		}
		 
		String filename = "productos.xlsx";
		InputStreamResource file = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));

		return ResponseEntity.ok()
		        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
		        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
		        .body(file);

    }
	

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public Object cargarProductosMasivamente(@RequestParam("file") MultipartFile reapExcelDataFile) {
       
    	List<String> errores = new ArrayList<String>();
    	
        XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
		} catch (IOException e) {
			
		}
        XSSFSheet worksheet = workbook.getSheetAt(0);
        
        XSSFRow usuarioRow = worksheet.getRow(1);
        String  email      = usuarioRow.getCell(0).getStringCellValue();
        Vendedor  vendedor = null;
        try {
        	
        	vendedor = buscarVendedorByEmail(email);
        	
		} catch (VendedorNoEncontradoException e) {
			
			return errorNoEncontroVendedor(); 
		}
        
        int rows = worksheet.getPhysicalNumberOfRows() - 3;
        if(rows > 20) {
        	return errorMaxCantidadProductosIntroducida();
        }
        
        int i = 3;
        for(int x=1;x<= rows ;x++) {

            XSSFRow row = worksheet.getRow(i);
            
            try {
            	
            	String categoriaNombre =  row.getCell(0).getStringCellValue();
            	Categoria categoria = buscarCategoriaByNombre(categoriaNombre);
            	
                int cantidadNum =  (int) row.getCell(1).getNumericCellValue();
                String cantidad = String.valueOf(cantidadNum);
                Utils.validarCantidadProducto(cantidad);
                
                String productoNombre =  row.getCell(2).getStringCellValue();
                Utils.validarNombreProducto(productoNombre);
                
                String descripcion =  row.getCell(3).getStringCellValue();
                Utils.validarDetalleProducto(descripcion);
                
                String precioProducto = row.getCell(4).getStringCellValue();
                Utils.validarPrecioProducto(precioProducto);
                
        		agregarNuevoProducto(Integer.valueOf(cantidad),categoria.getId(),productoNombre,descripcion,Double.parseDouble(precioProducto),vendedor.getId()); 

            }catch(CategoriaNotFoundException e) {
            	int num = i+1;
            	errores.add("La categoria del producto de la fila "+ num + " no existe. ");
            } catch (InvalidCantidadProductoException e) {
            	int num = i+1;
            	errores.add("La cantidad del producto de la fila "+ num + " es invalida, la cantidad maxima permitida es 999999. ");
			} catch (InvalidProductoNombreException e) {
				int num = i+1;
				errores.add("El nombre del producto de la fila "+ num + " es invalido, debe tener tama??o 5 a 30. ");
			} catch (InvalidDetalleNombreException e) {
				int num = i+1;
				errores.add("El detalle del producto de la fila "+ num + " es invalido, debe tener tama??o 5 a 100. ");
			} catch (InvalidPrecioException e) {
				int num = i+1;
				errores.add("El precio del producto de la fila "+ num + " es invalido, usa 2 decimales y punto, ejemplo 34.50. ");
			}catch(IllegalStateException e) {
				int num = i+1;
				errores.add("Algunos de los valores del producto de la fila "+ num +" no respetan el formato de la columna. ");
			} catch (Exception e) {
				int num = i+1;
				errores.add("Hubo un error, no se pudo realizar la carga del producto de la fila "+ num);
			}
            i = i + 1;
        }
        
    	List<Vendedor> vendedores = obtenerVendedores();
		List<Producto> products = obtenerProductos();
		List<Categoria> categorias = obtenerCategorias();
		
		ModelAndView model = new ModelAndView("producto");
    	model.addObject("command", new AddProductoForm());
    	model.addObject("vendedores", vendedores);
    	model.addObject("categorias", categorias);
        model.addObject("productos", products);
        
        if(!errores.isEmpty()) {
        	
        	String mensajeError = "Algunos de los productos no pudo crearse:" + "\n";
        	for(String e : errores) {
        		mensajeError = mensajeError + e + "\n";
        	}
    		model.addObject("error",mensajeError);
        }
        
        return model;
    }

	private Object errorMaxCantidadProductosIntroducida() {
		List<Vendedor> vendedores = obtenerVendedores();
		List<Producto> products = obtenerProductos();
		List<Categoria> categorias = obtenerCategorias();
		
		ModelAndView model = new ModelAndView("producto");
		model.addObject("command", new AddProductoForm());
		model.addObject("vendedores", vendedores);
		model.addObject("categorias", categorias);
		model.addObject("productos", products);
		model.addObject("error","El excel solo admite hasta 20 productos.");
		return model;
	}

	private Object errorNoEncontroVendedor() {
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
