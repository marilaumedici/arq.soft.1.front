package arq.soft.front.clientes;


public class FiltrosBusqueda {
	
	 

	public void setPrecioMinimo(double precioMinimo) {
		this.precioMinimo = precioMinimo;
	}

	private String descripcion;

	private double precioMinimo;
	
	private double precioMaximo;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecioMinimo() {
		return precioMinimo;
	}

	public double getPrecioMaximo() {
		return precioMaximo;
	}

	public void setPrecioMaximo(double precioMaximo) {
		this.precioMaximo = precioMaximo;
	}

}
