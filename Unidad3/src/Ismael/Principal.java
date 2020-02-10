package Ismael;
import java.util.Date;
import java.util.Scanner;


public class Principal {

	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {

		Modelo tienda = new Modelo();
		if (tienda.getCol() != null) {
			int opcion = 0;
			do {
				System.out.println("\n");
				System.out.println("0-Salir");
				System.out.println("1-Insertar Regalos");
				System.out.println("2-Mostrar Regalos");
				System.out.println("3-Crear Cajas");
				System.out.println("4-Mostrar Cajas");
				System.out.println("5-Insertar Regalos a Cajas");
				

				opcion = scan.nextInt();
				scan.nextLine();
				
				
				switch (opcion) {
				
				case 1:
					Regalos r = new Regalos();
					System.out.println("Inserte el nombre del regalo");
					r.setNombre(scan.nextLine());
					System.out.println("Inserte el tipo (Alimento/Objeto)");
					String tip = scan.nextLine();
					if(tip.equalsIgnoreCase("alimento") || tip.equalsIgnoreCase("objeto")) {
						r.setTipo(tip);
						System.out.println("Insertar Stock");
						r.setStock(scan.nextInt());scan.nextLine();
						if(r.getStock()<0) {
							System.err.println("Stock no valido");
						}else {
							if(!tienda.insertarRegalo(r)) {
								System.err.println("ERROR: Regalo no insertado");
							}else {
								System.out.println("Regalo Insertado");
								
							}
						}
					}else {
						System.err.println("Tipo de Regalo no valido");
					}
					
					break;
					
				case 2:
					tienda.mostrar();
					break;
					
				case 3:
					Cajas c = new Cajas();
					System.out.println("Introduce el codigo de caja");
					c.setCodigo(scan.nextLine());
					c.setFecha(new Date());
					System.out.println("Introduce el precio de la caja");
					c.setPrecio(scan.nextFloat());scan.nextLine();
					if(!tienda.insertarCajas(c)) {
						System.err.println("ERROR: La caja no se a podido Insertar");
					}else {
						System.out.println("Caja insertada");
					}
					
					
					break;
					
				case 4:
					tienda.mostrarCajas();
					break;
					
				case 5:
				
					tienda.mostrar();
					Regalos reg = new Regalos();
					System.out.println("Introduce id de regalo");
					reg.setCodigo(scan.nextInt());scan.nextLine();
					
					if(tienda.comprobarRegalo(reg.getCodigo())) {
						
						tienda.mostrarCajas();
						System.out.println("Introduce id caja");
						int idCaja = scan.nextInt();scan.nextLine();
						
						
						if(tienda.comprobarCaja(idCaja)) {
							System.out.println("Añadir cantidad");
							int cantidad = scan.nextInt();scan.nextLine();
							reg.setStock(tienda.comprobarStock(reg.getCodigo()));
							
							if(reg.getStock()>=cantidad) {
								if(tienda.añadirRegalo(reg.getCodigo(), idCaja, cantidad)) {
									System.out.println("Regalo añadido");
									tienda.actualizarStock(reg, cantidad);
								}else {
									System.err.println("ERROR: no se a podido añadir");
								}
							}else {
								System.err.println("ERROR: Stock no valido");
							}
							
						}else {
							System.err.println("ERROR: ID de caja no valido");
						}
					}else {
						System.err.println("ERROR: ID de regalo no valido");
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
