package ies;

public class Persona {
	private int codigo;
	private String nombre;
	private Direccion direccion;
	
	public Persona() {
		super();
	}
	public Persona(int codigo, String nombre, Direccion direccion) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.direccion = direccion;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Direccion getDireccion() {
		return direccion;
	}
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	
	
	
}
