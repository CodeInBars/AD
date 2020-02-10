package Vicente;

import java.util.Scanner;

public class Principal {

	public static Scanner leer=new Scanner(System.in);
	public static Modelo far=new Modelo();
	public static Producto pro;
	public static void main(String[] args) {
		// TODO Auto-generated method stus
		int opcion=0;
		if(far.getCol()!=null) {
			do {
				System.out.println("0.-Salir");
				System.out.println("1.-AGREGAR MEDICAMENTO AL INVENTARIO.");
				System.out.println("2.-MOSTRAR INVENTARIO.");
				System.out.println("3.-FACTURAR.");
				System.out.println("4.-MOSTRAR FACTURAS.");
				System.out.println("5.-BORRAR UN MEDICAMENTO DEL INVENTARIO.");
				System.out.println();
				System.out.print("Introduce una de las opciones anteriores: ");
				opcion=leer.nextInt();leer.nextLine();
				System.out.println();
				switch(opcion) {
				case 1:
					pro=new Producto();
					//Pedimos los datos para agregar el nuevo medicamento.
					System.out.println("Introduce el nombre del medicamento: ");
					pro.setNombre(leer.nextLine());
					System.out.println("Numero de blisters que hay en la caja: ");
					pro.setNumBlisters(leer.nextInt());leer.nextLine();
					System.out.println("Peso de la pastilla: ");
					pro.setGrPatilla(leer.nextInt());leer.nextLine();
					System.out.println("Precio del medicamento: ");
					pro.setPrecio(leer.nextFloat());leer.nextLine();
					System.out.println("Stock en el almacen: ");
					pro.setStock(leer.nextInt());leer.nextLine();
					
					if(!far.agregarMedicamento(pro)) {
						System.err.println("ERROR: NO SE HA INGRESADO CORRECTAMENTE.");
					}
					
					break;
				case 2:	
					far.mostrarMedicamentos();
					break;
				case 3:	
					//Para facturar debemos chequear que el medicamento existe y esta dado de alta.
					//Con lo cual mostraremos los medicamentos y pediremos el codigo del medicamento.
					far.mostrarMedicamentos();
					pro=new Producto();
					
					System.out.println("Introduce el codigo del medicamento: ");
					pro.setCodigo(leer.nextInt());leer.nextLine();
					if(far.existeMedicamento(pro.getCodigo())) {
						if(far.medicamentoEstaAlta(pro.getCodigo())) {
							//Ahora pediremos la cantidad de ese medicamento.
							System.out.println("¿Cuantas unidades desea de ese medicamento?");
							int cantidad=leer.nextInt();leer.nextLine();
							//Obtenemos el stock.
							int stockProducto=far.obtenerStock(pro.getCodigo());							
							//Comprobamos que hay sufuciente Stock para vender esas unidades.
							if(cantidad<=stockProducto) {
								/*Una vez comprobado, lo que vamos hacer es que si hay 2 unidades y se venden
								 * ambas, se modifica el atributo alta a baja.
								 * Por otro lado tambien se modifica el stock del producto.*/
								if(far.venderProducto(cantidad,stockProducto,pro)) {
									
								}else {
									System.err.println("ERROR: NO SE HA HECHO LA VENTA CORRECTAMENTE.");
								}
							}else {
								System.err.println("ERROR: NO HAY SUFICIENTE STOCK.");
							}
						}else {
							System.err.println("ERROR: MEDICAMENTO DADO DE BAJA.");
						}
					}else {
						System.err.println("ERROR: NO EXISTE EL MEDICAMENTO.");
					}
					break;
				case 4:
					/*Mostraremos todas las facturas que se han hecho segun el codigo de un medicamento.*/
					pro=new Producto();
					far.mostrarCodigosMedicamento();
					System.out.println("Introduce un codigo de un medicamento: ");
					pro.setCodigo(leer.nextInt());leer.nextLine();
					if(far.existeMedicamento(pro.getCodigo())) {
						far.mostrarFacturas(pro.getCodigo());
					}else {
						System.err.println("ERROR: NO EXISTE ESE MEDICAMENTO.");
					}
					
					break;
				case 5:
					//Para borrar un medicamento del inventario se debe de pedir su codigo.
					far.mostrarMedicamentos();
					pro=new Producto();
					System.out.println("Introduce el codigo del medicamento que va borrar: ");
					pro.setCodigo(leer.nextInt());leer.nextLine();
					//Chequeamos que existe el codigo del medicamento.
					if(far.existeMedicamento(pro.getCodigo())) {
						//Debemos chequear que hay facturas para ese medicamento, en cuyo caso no se podra borrar.
						if(far.borrarMedicamento(pro.getCodigo())) {
							
						}else {
							System.err.println("ERROR: NO SE PUDO ELIMINAR DEL INVENTARIO.");
						}
					}else {
						System.err.println("ERROR: NO EXISTE EL MEDICAMENTO.");
					}
					
					
					break;
				}
			}while(opcion!=0);
			far.cerrar();
		}
	}
	
}
