package ExplicacionMySQL;

public class Pieza {
	private int codigo;
	private String nombre;
	private float precio;
	private int stock;
	private boolean alta;
	public Pieza() {
		super();
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
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public boolean isAlta() {
		return alta;
	}
	public void setAlta(boolean alta) {
		this.alta = alta;
	}
	
	public void mostrar() {
		System.out.println("Codigo:"+codigo+
				"\tNombre:"+nombre+
				"\tPrecio:"+precio+
				"\tStock:"+stock+
				"\tAlta:"+alta);
	}
	
}
