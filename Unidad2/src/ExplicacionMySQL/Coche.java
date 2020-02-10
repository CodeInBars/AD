package ExplicacionMySQL;

public class Coche {
	private String matricula="", marca="", modelo="";
	private Cliente cliente;
	public Coche() {
		super();
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void mostrar() {
		System.out.println("Matricula:"+matricula + 
				"\tMarca:"+marca+
				"\tModelo:"+modelo+
				"\tCliente:"+cliente.getDni());
	}
}
