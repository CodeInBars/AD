package ColleccionVentas;

import java.util.Date;
import java.util.Scanner;

public class Principal {
	static Scanner t = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Modelo ventas = new Modelo();
		if (ventas.getCol() != null) {
			int opcion = 0;
			do {
				System.out.println("0-Salir");
				System.out.println("1-Mostrar clientes");
				System.out.println("2-Insertar cliente");
				System.out.println("3-Modificar nombre del cliente");
				System.out.println("4-Borrar cliente");
				System.out.println("5-Mostrar Facturas de un cliente");
				opcion = t.nextInt();
				t.nextLine();
				int id;
				switch (opcion) {	
				case 1:
					ventas.mostrarClientes();
					break;
				case 2:
					System.out.println("Nombre");
					String nombre = t.nextLine();
					System.out.println("Poblacion");
					String poblacion = t.nextLine();
					System.out.println("Teléfono");
					String telefono = t.nextLine();
					System.out.println("Dirección");
					String direccion = t.nextLine();
					if(!ventas.insertarCliente(nombre,poblacion,
							telefono,direccion)) {
						System.out.println("Error al crear el cliente");
					}
					break;
				case 3:
					ventas.mostrarClientes();
					System.out.println("Introduce el id del cliente a modificar");
					id = t.nextInt();t.nextLine();
					if(ventas.existeCliente(id)) {
						System.out.println("Nuevo Nombre");
						String nuevoNombre = t.nextLine();
						if(!ventas.modificarNombreCliente(id,nuevoNombre)) {
							System.out.println("Error al modificar el cliente");
						}
					}
					else {
						System.out.println("Error, el cliente no existe");
					}
					break;
				case 4:
					ventas.mostrarClientes();
					id = t.nextInt();t.nextLine();
					if(ventas.existeCliente(id)) {
						if(!ventas.borrarCliente(id)) {
							System.out.println("Error al borrar el cliente");
						}
					}
					else {
						System.out.println("Error, el cliente no existe");
					}
					break;
				case 5:
					ventas.mostrarClientes();
					System.out.println("Introduce id cliente");
					id = t.nextInt();t.nextLine();
					if(ventas.existeCliente(id)) {
						ventas.mostrarFacturas(id);
					}
					else {
						System.out.println("Error, no existe el cliente");
					}
					break;

				}
			} while (opcion != 0);
			ventas.cerrar();
		} else {
			System.out.println("No se ha podido conectar con la bd Ventas");
		}

	}

}
