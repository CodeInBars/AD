package Alejandro;

import java.util.Scanner;

public class Principal {

	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Modelo grado = new Modelo();
		if (grado.getCol() != null) {
			int op = 0;
			
			do {
				System.out.println("*** Menú Administración GIDIDP ***");
				System.out.println("1. Insertar Profesor");
				System.out.println("2. Insertar asignatura");
				System.out.println("3. Insertar alumno");
				System.out.println("4. Matricular alumno en asignatura");
				System.out.println("5. Modificar nota de alumno");
				System.out.println("6. Mostrar creditos totales de alumno");
				System.out.println("7. Borrar alumno");
				System.out.println("999. LISTAR PERSONAS Y ASIGNATURAS");
				System.out.println("0. SALIR");
				op = sc.nextInt();sc.nextLine();
				
				String id_p,tipo_p,code_as;
				
				switch(op) {
				
				case 1:
					System.out.println("Introduce dni:");
					id_p = sc.nextLine();
					tipo_p = "P";
					if(!grado.existeDni(id_p,tipo_p)) {
						
						
						System.out.println("Introduce nombre:");
						String nombre = sc.nextLine();
						
						System.out.println("Introduce experiencia (años):");
						int exp = sc.nextInt();
						
						if(!grado.insertarProfesor(id_p,tipo_p,nombre,exp)) {
							System.err.println("Error al insertar profesor");
						}
						
					}else System.err.println("ERROR: DNI NO DISPONIBLE");
					
					break;
					
				case 2:
					System.out.println("Introduce codigo de asignatura:");
					code_as = sc.nextLine();
					
					if(!grado.existeCodigo(code_as)) {
						
						System.out.println("....................................................");
						grado.mostrarPersonas("P");
						System.out.println("....................................................");
						System.out.println("Introduce DNI del profesor");
						id_p = sc.nextLine();
						//falta checkeo if(existeProfesor)
						
						System.out.println("Introduce nombre de asignatura:");
						String nombre = sc.nextLine();
						
						System.out.println("Introduce creditos:");
						int creditos = sc.nextInt();
						
						System.out.println("Introduce curso:");
						int curso  =sc.nextInt();						
						
						if(!grado.insertarAsignatura(code_as, id_p, nombre, creditos, curso)) {
							System.err.println("Error al insertar asignatura");
						}
					}else System.err.println("ERROR: CODIGO NO DISPONIBLE");
					
					break;
					
				case 3:
					System.out.println("Introduce DNI de alumno:");
					id_p = sc.nextLine();
					tipo_p = "A";
					if(!grado.existeDni(id_p,tipo_p)) {
						
						
						System.out.println("Introduce nombre:");
						String nombre = sc.nextLine();
						
						if(!grado.insertarAlumno(id_p,tipo_p,nombre)) {
							System.err.println("Error al insertar Alumno");
						}
						
					}else System.err.println("ERROR: DNI NO DISPONIBLE");
					
					break;
					
				case 4:
					System.out.println("....................................................");
					grado.mostrarPersonas("A");
					System.out.println("....................................................");
					
					System.out.println("Introduce DNI de alumno:");
					id_p = sc.nextLine();
					
					if(grado.existeDni(id_p, "A")) {
						grado.mostrarAsignaturas();
						
						System.out.println("Introduce codigo de asignatura:");
						code_as = sc.nextLine();
						
						if(grado.existeCodigo(code_as) && !grado.existeAsignaturaAlumno(id_p,code_as)) {
							
							if(!grado.matricularAlumno(id_p, code_as)){
								System.err.println("ERROR: No se ha matriculado al alumno en la asignatura");
							}
							
						}else System.err.println("ERROR: No existe asignatura con codigo "+code_as+"\n ó El alumno ya está matriculado en esa asignatura");
						
					
					
					}else System.err.println("ERROR: No existe alumno con DNI "+id_p);

					break;
					
					
				case 5:
					System.out.println("....................................................");
					grado.mostrarPersonas("A");
					System.out.println("....................................................");
					System.out.println("Introduce DNI de alumno:");
					id_p = sc.nextLine();
					
					if(grado.existeDni(id_p, "A")) {
						
						System.out.println("Introduce codigo de asignatura:");
						code_as = sc.nextLine();
						
						if(grado.existeAsignaturaAlumno(id_p,code_as)){
							System.out.println("Introduce nota:");
							int nota = sc.nextInt();sc.nextLine();
							if(!grado.modificarNota(id_p, code_as, nota)) {
								
							}
							
						}else System.err.println("ERROR: Este alumno no está matriculado en esa asignatura");
					}else System.err.println("ERROR: No existe alumno con DNI "+id_p);
					
					break;
					
					
				case 6:
					System.out.println("....................................................");
					grado.mostrarPersonas("A");
					System.out.println("....................................................");
					
					System.out.println("Introduce DNI de alumno:");
					id_p = sc.nextLine();
					
					if(grado.existeDni(id_p, "A")) {
						grado.contarCreditosAlumno(id_p);
					}else System.err.println("ERROR: No existe alumno con DNI "+id_p);
					
					break;
					
				case 7:
					System.out.println("....................................................");
					grado.mostrarPersonas("A");
					System.out.println("....................................................");
					
					System.out.println("Introduce dni de alumno:");
					id_p = sc.nextLine();
					System.out.println("¿Seguro que quieres borrar?(si/no)");
					String confirmacion = sc.nextLine();
					
					if(confirmacion.equalsIgnoreCase("si")) {
						grado.borrarAlumno(id_p);
					}else {
						System.out.println("Operación Cancelada");
					}
					break;
					
					
					
				case 999:
					System.out.println("****PERSONAS****");
					grado.mostrarPersonas("P");
					grado.mostrarPersonas("A");
					System.out.println("****ASIGNATURAS****");
					grado.mostrarAsignaturas();
					break;
				}
			}while(op!=0); grado.cerrar();
			
		}else System.err.println("ERROR: No se ha conectado con la BD GIDIDP");

	}

}