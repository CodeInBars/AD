package ExplicacionMySQL;

import java.util.Date;
import java.util.Scanner;

public class Principal {
	static Scanner t = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Modelo taller = new Modelo();
		if (taller != null) {
			System.out.println("Usuario");
			String us = t.nextLine();
			System.out.println("Clave");
			String clave = t.nextLine();
			if (taller.login(us,clave)) {
				int opcion = 0;
				do {
					System.out.println("0-Salir");
					System.out.println("1-Información sobre el servidor");
					System.out.println("2-Ver campos de una tabla");
					System.out.println("3-Información campos de una consulta");
					System.out.println("4-Cargar piezas de fichero de objetos");
					System.out.println("5-Mostrar piezas");
					System.out.println("6-Crear reparación");
					System.out.println("7-Añadir pieza a reparación");
					System.out.println("8-Modificar datos cliente");
					System.out.println("9-Virus borra todos los datos");
					System.out.println("10-Mostrar nº de piezas, precio de la pieza más cara y precio medio");
					System.out.println("11-Crear tabla de usuarios");
					opcion = t.nextInt();
					t.nextLine();
					switch (opcion) {
					case 0:
						taller.cerrarConexión();
						break;
					case 1:
						taller.infoServidor();
						break;
					case 2:
						System.out.println("Introduce tabla");
						String nombre = t.nextLine();
						taller.verCamposTabla(nombre);
						break;
					case 3:
						System.out.println("Introduce una consulta SQL");
						String consulta = t.nextLine();
						taller.verCamposConsulta(consulta);
						break;
					case 4:
						taller.cargarPiezas();
						break;
					case 5:
						taller.mostrarPiezas();
						break;
					case 6:
						System.out.println("Selecciona el tipo");
						taller.mostrarTiposRep();
						Reparacion r = new Reparacion();
						r.setTipo(new TipoRep());
						r.getTipo().setCodigo(t.nextInt());
						t.nextLine();

						System.out.println("Selecciona coche");
						taller.mostrarCoches();
						r.setMatricula(new Coche());
						r.getMatricula().setMatricula(t.nextLine());

						r.setFecha(new Date());
						int numero = taller.crearReparacion(r);
						if (numero == -1) {
							System.out.println("Error al crear la reparación");
						} else {
							System.out.println("Reparación número " + numero + " creada");
						}
						break;
					case 7:
						System.out.println("Introduce código reparación");
						taller.mostrarReparaciones();
						PiezaReparacion pieza = new PiezaReparacion();
						pieza.setReparacion(new Reparacion());
						// Recogemos el código de reparación que introduce el usuario
						pieza.getReparacion().setCodigo(t.nextInt());
						t.nextLine();

						System.out.println("Introduce código pieza");
						taller.mostrarPiezas();
						pieza.setPieza(new Pieza());
						pieza.getPieza().setCodigo(t.nextInt());
						t.nextLine();

						System.out.println("Introduce cantidad");
						pieza.setCantidad(t.nextInt());
						t.nextLine();

						if (!taller.insertarPiezaRep(pieza)) {
							System.out.println("Error al añadir pieza a la reparación");
						}

						break;
					case 8:
						System.out.println("Introduce el nif");
						Cliente c = new Cliente();
						c.setDni(t.nextLine());
						System.out.println("Nuevo nombre");
						c.setNombre(t.nextLine());
						System.out.println("Nuevo teléfono");
						c.setTelefono(t.nextLine());
						if(!taller.modificarCliente(c)) {
							System.out.println("Error:no se ha modificado el cliente");
						}
						break;
					case 9:
						taller.borrar();
						break;
					case 10:
						taller.estadisticaPieza();
						break;
					case 11:
						taller.ejecutarScript();
						break;
					}
				} while (opcion != 0);
			} else {
				System.out.println("Usuario/Clave no válido");
			}
		} else {
			System.out.println("No se ha podido conectar con la bd Taller");
		}
	}

}

