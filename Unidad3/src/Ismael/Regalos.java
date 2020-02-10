package Ismael;

public class Regalos {

	private int codigo, stock;
	private String nombre, tipo;
	
	public Regalos() {

	}

	public Regalos(int codigo, int stock, String nombre, String tipo) {

		this.codigo = codigo;
		this.stock = stock;
		this.nombre = nombre;
		this.tipo = tipo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Regalos [codigo=" + codigo + ", stock=" + stock + ", nombre=" + nombre + ", tipo=" + tipo + "]";
	}

	
	
	
	
}
