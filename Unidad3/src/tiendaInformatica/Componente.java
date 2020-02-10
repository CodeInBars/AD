package tiendaInformatica;

public class Componente {
	private int codigoPieza,cantidad;

	public Componente() {
		
	}

	public Componente(int codigoPieza, int cantidad) {
		super();
		this.codigoPieza = codigoPieza;
		this.cantidad = cantidad;
	}

	public int getCodigoPieza() {
		return codigoPieza;
	}

	public void setCodigoPieza(int codigoPieza) {
		this.codigoPieza = codigoPieza;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public void mostrar() {
		System.out.println("Codigo Pieza:"+ codigoPieza + 
				"\tCantidad:"+ cantidad);
	}
}
