package tiendaInformatica;

public class Pieza {
	private int codigo,stock;
	private boolean alta;
	private String nombre;
	private float precio;
	public Pieza() {
		
	}
	public Pieza(int codigo, int stock, boolean alta, String nombre, float precio) {
		super();
		this.codigo = codigo;
		this.stock = stock;
		this.alta = alta;
		this.nombre = nombre;
		this.precio = precio;
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
	public boolean isAlta() {
		return alta;
	}
	public void setAlta(boolean alta) {
		this.alta = alta;
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
	
	public void mostrar() {
		System.out.println("Codigo:"+codigo + 
				"\tAlta:"+alta+
				"\tNombre:"+nombre+
				"\tStock:"+stock+
				"\tPrecio:"+precio);
	}
	
}
