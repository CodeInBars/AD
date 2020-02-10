package ExplicacionMySQL;

public class PiezaReparacion {
	private Pieza pieza;
	private Reparacion reparacion;
	private float precio;
	private int cantidad;
	
	public PiezaReparacion() {
		super();
	}
	public Pieza getPieza() {
		return pieza;
	}
	public void setPieza(Pieza pieza) {
		this.pieza = pieza;
	}
	public Reparacion getReparacion() {
		return reparacion;
	}
	public void setReparacion(Reparacion reparacion) {
		this.reparacion = reparacion;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
}
