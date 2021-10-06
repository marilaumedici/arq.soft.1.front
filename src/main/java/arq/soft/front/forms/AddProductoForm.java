package arq.soft.front.forms;

public class AddProductoForm {
	
	private long idVendedor;
	private long idCategoria;
	private String nombre;
	private int cantidad;
	private String descripcion;
	private String precio;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public long getIdVendedor() {
		return idVendedor;
	}
	public void setIdVendedor(long idVendedor) {
		this.idVendedor = idVendedor;
	}
	public long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

}
