package ies;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class Principal2 {
	public static Scanner t = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ModeloOO iesOO = new ModeloOO();
		ModeloOR iesOR = new ModeloOR();
		if(iesOO.getConexion()!=null) {
			int opcion = 0;
			do {
				System.out.println("0-Salir");
				System.out.println("1-Importar datos de Postgre SQL");
				System.out.println("2-Mostrar Alumnos");
				System.out.println("3-Modificar dirección");
				System.out.println("4-Mostrar notas de un alumno");
				System.out.println("5-Matricular alumno");
				System.out.println("6-Poner nota");
				System.out.println("7-Borrar alumnos y sus notas si tiene");
				System.out.println("8-Mostrar alumnos aprobados (media>=5) de una asignatura");
				
				
				opcion = t.nextInt();t.nextLine();
				
				Alumno a;
				Asignatura as;
				switch(opcion) {
				case 1:
					iesOO.borrarBD();
					//Importamos asignaturas
					ArrayList<Asignatura> asigs = iesOR.obtenerAsis();
					if(!iesOO.crearAsig(asigs)) {
						System.out.println("Error al importar asignaturas");
					}
					else {
						ArrayList<Alumno> alum = iesOR.obtenerAlum();
						if(!iesOO.importarAlumnos(alum)) {
							System.out.println("Error al importar alumos");
						}
						else {
							ArrayList<Nota> notas = iesOR.obtenerNotas();
							if(!iesOO.importarNotas(notas)) {
								System.out.println("Error al importar notas");
							}
						}
					}
					break;
				case 2:
					iesOO.mostrarAlumnos();
					break;
				case 3:
					iesOO.mostrarAlumnos();
					System.out.println("Introduce código de alumno");
					a= new Alumno();
					a.setCodigo(t.nextInt());t.nextLine();
					a=iesOO.obtenerAlumno(a);
					if(a!=null) {
						System.out.println("Nueva dirección");
						System.out.println("Tipo via:");
						a.getDireccion().setTipoV(t.nextLine());
						System.out.println("Nombre via:");
						a.getDireccion().setNombreV(t.nextLine());
						System.out.println("Nº:");
						a.getDireccion().setNumero(t.nextInt());
						t.nextLine();
						System.out.println("CP:");
						a.getDireccion().setCp(t.nextInt());
						t.nextLine();
						if(!iesOO.modificarAlumno(a)) {
							System.out.println("Error al modificar la dirección");
						}
					}
					else {
						System.out.println("Error, el alumno no existe");
					}
					break;
				case 4:
					iesOO.mostrarAlumnos();
					System.out.println("Introduce código de alumno");
					a= new Alumno();
					a.setCodigo(t.nextInt());t.nextLine();
					a=iesOO.obtenerAlumno(a);
					if(a!=null) {
						iesOO.mostrarNotas(a);
					}
					else {
						System.out.println("Error, el alumno no existe");
					}
					break;
				case 5:
					iesOO.mostrarAlumnos();
					System.out.println("Introduce código de alumno");
					a= new Alumno();
					a.setCodigo(t.nextInt());t.nextLine();
					a=iesOO.obtenerAlumno(a);
					if(a!=null) {
						iesOO.mostrarAsignaturas();
						as = new Asignatura();
						System.out.println("Introduce asignatura");
						as.setNombreC(t.nextLine());
						as = iesOO.obtenerAsignatura(as);
						if(as!=null) {
							if(iesOO.estaMatriculado(a,as)) {
								System.out.println("Error, ya está matriculado");
							}
							else {
								if(!iesOO.matricular(a,as)) {
									System.out.println("Error al matricular");
								}
							}
						}
						else {
							System.out.println("Error, la asignatura no existe");
						}
					}
					else {
						System.out.println("Error, el alumno no existe");
					}
					break;
				case 6:
					iesOO.mostrarAlumnos();
					System.out.println("Introduce código de alumno");
					a= new Alumno();
					a.setCodigo(t.nextInt());t.nextLine();
					a=iesOO.obtenerAlumno(a);
					if(a!=null) {
						iesOO.mostrarNotas(a);
						as = new Asignatura();
						System.out.println("Introduce asignatura");
						as.setNombreC(t.nextLine());
						Nota n = iesOO.obtenerNotas(a,as);
						if(n!=null) {
							NotaExamen ne = new NotaExamen();
							ne.setFecha(new Date());
							System.out.println("Introduce nota");
							ne.setNota(t.nextFloat());t.nextLine();
							n.getNotas2().add(ne);
							if(!iesOO.anadirNota(n)) {
								System.out.println("Error al añadir la nota");
							}
						}
						else {
							System.out.println("Error, el alumno no está matriculado");
						}
					}
					else {
						System.out.println("Error, el alumno no existe");
					}
					
					break;
				case 7:
					iesOO.mostrarAlumnos();
					System.out.println("Introduce código de alumno");
					a= new Alumno();
					a.setCodigo(t.nextInt());t.nextLine();
					a=iesOO.obtenerAlumno(a);
					if(a!=null) {
						iesOO.borrarAlumno(a);
					}
					else {
						System.out.println("Error, el alumno no existe");
					}
					break;
				case 8:
					iesOO.mostrarAsignaturas();
					System.out.println("Introduce asignatura");
					as = new Asignatura();
					as.setNombreC(t.nextLine());
					as = iesOO.obtenerAsignatura(as);
					if(as!=null){
						iesOO.mostrarAprobados(as);
					}
					else {
						System.out.println("Error, la asignatura no existe");
					}
					break;
				
				}
			}while (opcion!=0);
			iesOO.cerrar();
		}
		
	}

}
