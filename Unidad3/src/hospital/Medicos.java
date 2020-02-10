package hospital;

import java.util.ArrayList;

public class Medicos {
	private int numColegiado;
	private String nombre;
	private String especialidad;
	public Medicos() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Medicos(int numColegiado, String nombre, String especialidad) {
		super();
		this.numColegiado = numColegiado;
		this.nombre = nombre;
		this.especialidad = especialidad;
	}
	public int getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(int numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	@Override
	public String toString() {
		return "NumColegiado: " + numColegiado + "\tNombre: " + nombre + "\tEspecialidad: " + especialidad;
	}
	
	
}
