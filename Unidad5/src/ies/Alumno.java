package ies;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Alumno extends Persona{
	private Date fechaM;

	public Alumno() {
		super();
	}

	public Alumno(int codigo, String nombre, Direccion direccion,Date fechaM) {
		super(codigo, nombre, direccion);
		this.fechaM = fechaM;
		// TODO Auto-generated constructor stub
	}

	public Date getFechaM() {
		return fechaM;
	}

	public void setFechaM(Date fechaM) {
		this.fechaM = fechaM;
	}
	public void mostrar() {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("Codigo:" + getCodigo() + 
				"\tNombre:" +  getNombre() + 
				"\tDireccion:" + getDireccion().getTipoV() + " " + getDireccion().getNombreV() + 
				"\tFecha Matricula:"+df.format(fechaM));
	}
	
}
