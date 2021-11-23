package arq.soft.front;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import arq.soft.front.exceptions.InvalidCantidadProductoException;
import arq.soft.front.exceptions.InvalidDetalleNombreException;
import arq.soft.front.exceptions.InvalidPrecioException;
import arq.soft.front.exceptions.InvalidProductoNombreException;
import arq.soft.front.utils.Utils;



@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilsTest {
	
	private Utils utils = new Utils();
	
	
	@Test
	public void nombre_producto_muy_corto() {
		
		InvalidProductoNombreException thrown = assertThrows(
				InvalidProductoNombreException.class,
		           () -> utils.validarNombreProducto("Kong"),
		           "Expected doThing() to throw, but it didn't"
		    );
	}
	
	@Test
	public void nombre_producto_muy_largo() {
		
		InvalidProductoNombreException thrown = assertThrows(
				InvalidProductoNombreException.class,
		           () -> utils.validarNombreProducto("Kongoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"),
		           "Expected doThing() to throw, but it didn't"
		    );
	}
	
	@Test
	public void detalle_producto_muy_corto() {
		
		InvalidDetalleNombreException thrown = assertThrows(
				InvalidDetalleNombreException.class,
		           () -> utils.validarDetalleProducto("Bala"),
		           "Expected doThing() to throw, but it didn't"
		    );
	}
	
	@Test
	public void invalid_precio_producto() {
		
		InvalidPrecioException thrown = assertThrows(
				InvalidPrecioException.class,
		           () -> utils.validarPrecioProducto("123"),
		           "Expected doThing() to throw, but it didn't"
		    );
	}
	
	@Test
	public void invalid_cantidad_max_producto() {
		
		InvalidCantidadProductoException thrown = assertThrows(
				InvalidCantidadProductoException.class,
		           () -> utils.validarCantidadProducto("9999999"),
		           "Expected doThing() to throw, but it didn't"
		    );
	}
	

	public Utils getUtils() {
		return utils;
	}

	public void setUtils(Utils utils) {
		this.utils = utils;
	}

}
