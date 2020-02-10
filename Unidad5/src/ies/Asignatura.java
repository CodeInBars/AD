package ies;

public class Asignatura {
	private String nombreC, nombreL;

	public Asignatura() {
		super();
	}

	public Asignatura(String nombreC, String nombreL) {
		super();
		this.nombreC = nombreC;
		this.nombreL = nombreL;
	}

	public String getNombreC() {
		return nombreC;
	}

	public void setNombreC(String nombreC) {
		this.nombreC = nombreC;
	}

	public String getNombreL() {
		return nombreL;
	}

	public void setNombreL(String nombreL) {
		this.nombreL = nombreL;
	}
	
	public void mostrar() {
		System.out.println("Asignatura:" + nombreC + " " + nombreL);
	}
}
