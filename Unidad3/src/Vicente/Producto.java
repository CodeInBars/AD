package Vicente;

public class Producto {

	private int codigo;
	private String nombre;
	private int numBlisters;
	private int grPatilla;
	private float precio;
	private int stock;
	private boolean alta;
	
	public Producto() {
		
	}

	public Producto(int codigo,String nombre, int numBlisters, int grPatilla, float precio, int stock, boolean alta) {
		this.codigo=codigo;
		this.nombre = nombre;
		this.numBlisters = numBlisters;
		this.grPatilla = grPatilla;
		this.precio = precio;
		this.stock = stock;
		this.alta=alta;
	}
	
	protected int getCodigo() {
		return codigo;
	}

	protected void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	protected String getNombre() {
		return nombre;
	}

	protected void setNombre(String nombre) {
		this.nombre = nombre;
	}

	protected int getNumBlisters() {
		return numBlisters;
	}

	protected void setNumBlisters(int numBlisters) {
		this.numBlisters = numBlisters;
	}

	protected int getGrPatilla() {
		return grPatilla;
	}

	protected void setGrPatilla(int grPatilla) {
		this.grPatilla = grPatilla;
	}

	protected float getPrecio() {
		return precio;
	}

	protected void setPrecio(float precio) {
		this.precio = precio;
	}

	protected int getStock() {
		return stock;
	}

	protected void setStock(int stock) {
		this.stock = stock;
	}

	protected boolean isAlta() {
		return alta;
	}

	protected void setAlta(boolean alta) {
		this.alta = alta;
	}
	
	
	
	
}
