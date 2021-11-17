package arq.soft.front.forms;

public class AddVentaProductoForm {
	
	private long idComprador;
	private long idProducto;
	private int cantidadComprada;
	private int cantidadMaxima; 
	
	public int getCantidadComprada() {
		return cantidadComprada;
	}
	public void setCantidadComprada(int cantidadComprada) {
		this.cantidadComprada = cantidadComprada;
	}
	public long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}
	public long getIdComprador() {
		return idComprador;
	}
	public void setIdComprador(long idComprador) {
		this.idComprador = idComprador;
	}
	public int getCantidadMaxima() {
		return cantidadMaxima;
	}
	public void setCantidadMaxima(int cantidadMaxima) {
		this.cantidadMaxima = cantidadMaxima;
	}
	
}
