package arq.soft.front.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import arq.soft.front.exceptions.InvalidCantidadProductoException;
import arq.soft.front.exceptions.InvalidDetalleNombreException;
import arq.soft.front.exceptions.InvalidPrecioException;
import arq.soft.front.exceptions.InvalidProductoNombreException;


public  final class  Utils {
	
	static Pattern nombreProducto = Pattern.compile("^([0-9a-zA-Z ]{5,30})$");
	static Pattern detalleProducto = Pattern.compile("^([0-9a-zA-Z ]{5,100})$");
	static Pattern precioProducto = Pattern.compile("[1-9]{0,1}[0-9]{1,10}\\.[0-9]{2}");
	static Pattern cantidadProducto = Pattern.compile("^[0-9][0-9]{0,5}$");

	public static void validarNombreProducto(String nombre) throws InvalidProductoNombreException {
		Matcher mat = nombreProducto.matcher(nombre);
		if (!mat.find()) {
			throw new InvalidProductoNombreException();
		}
	}
	
	public static void validarDetalleProducto(String detalle) throws InvalidDetalleNombreException {
		Matcher mat = detalleProducto.matcher(detalle);
		if (!mat.find()) {
			throw new InvalidDetalleNombreException();
		}
	}
	
	public static void validarPrecioProducto(String precio) throws InvalidPrecioException {
		Matcher mat = precioProducto.matcher(precio);
		if (!mat.find()) {
			throw new InvalidPrecioException();
		}
	}
	
	public static void validarCantidadProducto(String precio) throws InvalidCantidadProductoException {
		Matcher mat = cantidadProducto.matcher(precio);
		if (!mat.find()) {
			throw new InvalidCantidadProductoException();
		}
	}

}
