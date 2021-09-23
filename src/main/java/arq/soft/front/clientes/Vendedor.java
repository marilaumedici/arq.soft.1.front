package arq.soft.front.clientes;

public class Vendedor {
	
	private long id;
	 
	private String razonSocial;
	 
	private String email;
	
	 
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		 this.email = email;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
