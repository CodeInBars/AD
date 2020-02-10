package ies;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Nota {
	private Alumno alumno;
	private Asignatura asig;
	private ArrayList<String[]> notas = new ArrayList<String[]>();
	private ArrayList<NotaExamen> notas2 = new ArrayList<NotaExamen>();
	public Nota() {
		
	}
	public Nota(Alumno alumno, Asignatura asig, ArrayList<String[]> notas) {
	
		this.alumno = alumno;
		this.asig = asig;
		this.notas = notas;
	}
	
	public Nota(Alumno alumno, Asignatura asig, ArrayList<String[]> notas, ArrayList<NotaExamen> notas2) {
		super();
		this.alumno = alumno;
		this.asig = asig;
		this.notas = notas;
		this.notas2 = notas2;
	}
	public ArrayList<NotaExamen> getNotas2() {
		return notas2;
	}
	public void setNotas2(ArrayList<NotaExamen> notas2) {
		this.notas2 = notas2;
	}
	public Alumno getAlumno() {
		return alumno;
	}
	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
	public Asignatura getAsig() {
		return asig;
	}
	public void setAsig(Asignatura asig) {
		this.asig = asig;
	}
	public ArrayList<String[]> getNotas() {
		return notas;
	}
	public void setNotas(ArrayList<String[]> notas) {
		this.notas = notas;
	}
	
	public void mostrar() {
		System.out.println("Alumno:" + alumno.getCodigo() + 
				"\tAsig:"+asig.getNombreC());
		for(String[] n:notas) {
			System.out.println("Fecha:"+n[0] + "\tNota:"+n[1]);
		}
	}
	public void mostrar(boolean dos) {
		System.out.println("Alumno:" + alumno.getCodigo() + 
				"\tAsig:"+asig.getNombreC());
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		for(NotaExamen e:notas2) {
			System.out.println("Fecha:"+ df.format(e.getFecha())+ 
					"\tNota:"+e.getNota());
		}
	}

}
