package ies;

import java.util.Date;

public class NotaExamen {
	private Date fecha;
	private float nota;
	public NotaExamen() {
		super();
	}
	public NotaExamen(Date fecha, float nota) {
		super();
		this.fecha = fecha;
		this.nota = nota;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public float getNota() {
		return nota;
	}
	public void setNota(float nota) {
		this.nota = nota;
	}
	
	
}
