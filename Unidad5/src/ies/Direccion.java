package ies;

public class Direccion {
	private String tipoV, nombreV;
	private int numero, cp;
	public Direccion() {
		
	}
	public Direccion(String tipoV, String nombreV, int numero, int cp) {
		super();
		this.tipoV = tipoV;
		this.nombreV = nombreV;
		this.numero = numero;
		this.cp = cp;
	}
	public String getTipoV() {
		return tipoV;
	}
	public void setTipoV(String tipoV) {
		this.tipoV = tipoV;
	}
	public String getNombreV() {
		return nombreV;
	}
	public void setNombreV(String nombreV) {
		this.nombreV = nombreV;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getCp() {
		return cp;
	}
	public void setCp(int cp) {
		this.cp = cp;
	}
	
	
}
