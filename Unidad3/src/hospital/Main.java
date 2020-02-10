package hospital;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

	private static Scanner t = new Scanner(System.in);
	public static Modelo hospital = new Modelo();
	
	public static Pacientes p;
	public static Medicos m;
	public static Consultas c;

	public static void main(String[] args) {
		if (hospital.getCol() != null) {
			int opc;
			do {

				System.out.println("0.-Salir");
				System.out.println("1.-Alta paciente");
				System.out.println("2.-Alta médico");
				System.out.println("3.-Añadir especialidad");
				System.out.println("4.-Crear consulta");
				System.out.println("5.-Registrar diagnóstico");
				System.out.println("6.-Borrar consulta");
				System.out.println("7.-Mostrar consulta");

				opc = t.nextInt();
				t.nextLine();

				switch (opc) {
				case 1:
					p = new Pacientes();
					System.out.println("Introduce el dni");
					p.setDni(t.nextLine());
					if(!hospital.comprobarPaciente(p.getDni())) {
						System.out.println("Introduce el nombre");
						p.setNombre(t.nextLine());
						System.out.println("Introduce la fecha de nacimiento");
						p.setFechaNac(t.nextLine());
						if(!hospital.altaPaciente(p)) {
							System.out.println("Error al dar de alta al paciente");
						}
					}else {
						System.out.println("Ya existe el paciente");
					}
					
					break;
				case 2:
					m = new Medicos();
					System.out.println("Introduce el número de colegiado");
					m.setNumColegiado(t.nextInt());t.nextLine();
					if(!hospital.comprobarMedicos(m.getNumColegiado())) {
						System.out.println("Introduce el nombre");
						m.setNombre(t.nextLine());
						if(!hospital.altaMedico(m)) {
							System.out.println("Error al dar de alta al médico");
						}
					}else {
						System.out.println("Ya existe el médico");
					}
					break;
				case 3:
					m = new Medicos();
					hospital.mostrarMedicos();
					System.out.println("Introduce el número de colegiado del médico");
					m.setNumColegiado(t.nextInt());t.nextLine();
					if(hospital.comprobarMedicos(m.getNumColegiado())) {
						System.out.println("Introduce la especialidad");
						m.setEspecialidad(t.nextLine());
						if(!hospital.insertarEspecialidad(m)) {
							System.out.println("Error insertando la especialidad");
						}
					}else {
						System.out.println("No existe el médico");
					}
					break;
				case 4:
					p = new Pacientes();
					m = new Medicos();
					hospital.mostrarPacientes();
					System.out.println("Introduce el dni");
					p.setDni(t.nextLine());
					if(hospital.comprobarPaciente(p.getDni())) {
						hospital.mostrarMedicos();
						System.out.println("Introduce el número de colegiado");
						m.setNumColegiado(t.nextInt());t.nextLine();
						if(hospital.comprobarMedicos(m.getNumColegiado())) {
							System.out.println("Creando consulta, espere por favor");
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							Date fecha = new Date();
							
							if(!hospital.crearConsulta(p.getDni(),m.getNumColegiado(), format.format(fecha))) {
								System.out.println("Error al crear la consulta");
							}else {
								System.out.println("Consulta creada con éxito, proceda al diagnóstico");
							}
						}else {
							System.out.println("No existe el médico");
						}
					}else {
						System.out.println("No existe el paciente");
					}
					break;
				case 5:
					c = new Consultas();
					hospital.mostrarConsultas();
					System.out.println("Elige el id de consulta");
					c.setId(t.nextInt());t.nextLine();
					if(hospital.comprobarConsulta(c.getId())) {
						System.out.println("Introduce el diagnóstico");
						c.setDiagnostico(t.nextLine());
						if(!hospital.actualizarDiagnostico(c.getId(),c.getDiagnostico())) {
							System.out.println("Error al insertar el diagnóstico");
						}
					}else {
						System.out.println("No existe la consulta");
					}
					break;
				case 6:
					c = new Consultas();
					hospital.mostrarConsultas();
					System.out.println("Elige el id de consulta");
					c.setId(t.nextInt());t.nextLine();
					if(hospital.comprobarConsulta(c.getId())) {
						if(!hospital.borrarConsulta(c.getId())) {
							System.out.println("Error al borrar la consulta");
						}
					}else {
						System.out.println("No existe la consulta");
					}
					break;
				case 7:
					c = new Consultas();
					hospital.mostrarConsultas();
					System.out.println("Elige el id de consulta");
					c.setId(t.nextInt());t.nextLine();
					if(hospital.comprobarConsulta(c.getId())) {
						hospital.mostrarConsultas(c.getId());
					}else {
						System.out.println("No existe la consulta");
					}
					break;
				}

			} while (opc != 0);
			hospital.cerrarConexion();
		} else {

		}
	}

}
