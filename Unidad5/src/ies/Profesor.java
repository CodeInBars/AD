package ies;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Profesor extends Persona{
	private String especialidad;
	
	public Profesor() {
		super();
	}

	public Profesor(int codigo, String nombre, Direccion direccion,
			String esp) {
		super(codigo, nombre, direccion);
		this.especialidad = esp;
		// TODO Auto-generated constructor stub
	}
	
	
	
	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public void mostrar() {
		
		System.out.println("Codigo:" + getCodigo() + 
				"\tNombre:" +  getNombre() + 
				"\tDireccion:" + getDireccion().getTipoV() + " " + getDireccion().getNombreV() + 
				"\tEspecialidad:"+ especialidad);
	}
	
}
