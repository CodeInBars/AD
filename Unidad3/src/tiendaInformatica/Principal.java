package tiendaInformatica;

import java.util.Scanner;



public class Principal {
	static Scanner t = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Modelo  tienda = new Modelo();
		if (tienda.getCol() != null) {
			int opcion = 0;
			do {
				System.out.println("0-Salir");
				System.out.println("1-Insertar Pieza");
				System.out.println("2-Mostrar Piezas");
				System.out.println("3-Crear Ordenador");
				System.out.println("4-Mostrar Ordenadores");
				System.out.println("5-Añadir pieza a ordenador");
				opcion = t.nextInt();
				t.nextLine();
				int id;
				Pieza p;
				Ordenador o;
				switch (opcion) {	
				case 1:
					p=new Pieza();
					System.out.println("Introduce Nombre");
					p.setNombre(t.nextLine());
					System.out.println("Introduce stock");
					p.setStock(t.nextInt());t.nextLine();
					System.out.println("Introduce Precio");
					p.setPrecio(t.nextFloat());t.nextLine();
					if(!tienda.insertarPieza(p)) {
						System.out.println("Error al insertar la pieza");
					}
					break;
				case 2:
					tienda.mostrarPiezas();
					break;
				case 3:
					o= new Ordenador();
					System.out.println("Introduce Codigo");
					o.setCodigo(t.nextLine());
					if(!tienda.existeOrdenador(o.getCodigo())) {
						if(!tienda.insertarOrdenador(o)) {
							System.out.println("Error al insertar el ordenador");
						}
					}
					else {
						System.out.println("Error: Ya existe un ordenador con ese código");
					}
					break;
				case 4:
					tienda.mostrarOrdenadores();
					break;
				case 5:
					//Pedimos ordenador
					tienda.mostrarOrdenadores();
					System.out.println("Introduce el código del ordenador");
					o = new Ordenador();
					o.setCodigo(t.nextLine());
					if(tienda.existeOrdenador(o.getCodigo())) {
						//Si existe pedimos piezas
						tienda.mostrarPiezas();
						p = new Pieza();
						System.out.println("Introduce código de pieza");
						p.setCodigo(t.nextInt());t.nextLine();
						//Si existe y hay stock suficiente, 
						if(tienda.existePieza(p.getCodigo())) {
							p.setStock(tienda.obtenerStock(p.getCodigo()));
							System.out.println("Introduce cantidad");
							int cantidad = t.nextInt();t.nextLine();
							if(p.getStock()>=cantidad) {
								//añadimos pieza a ordenador,
								if(!tienda.addPieza(o,p,cantidad)){
									System.out.println("Error al añadir pieza");
								}
								else {
									//actualizamos el precio
									if(!tienda.actualizarPrecioOrd(o,p,cantidad)) {
										System.out.println("Error al actualizar el precio del ordenador");
									}
									// y actualizamos el stock de la pieza
									if(!tienda.actualizarStock(p,cantidad)) {
										System.out.println("Error al actualizar el stock de la pieza");
									}
									
								}
							}
						}
						else {
							System.out.println("Error: No existe la pieza");
						}
					}
					else {
						System.out.println("Error: No existe el ordenador");
					}
					break;

				}
			} while (opcion != 0);
			tienda.cerrar();
		} else {
			System.out.println("No se ha podido conectar con la bd Ventas");
		}
	}
}
