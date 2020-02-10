package PracticaPilar_zamora;

import java.util.Scanner;





public class Principal {
	static Scanner t = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Modelo  peliculas = new Modelo();
		
		if (peliculas.getCol() != null) {
		int opcion = 0;
		do {
			System.out.println("0-salir");
			System.out.println("1-Crear peliculas e insertar");
			System.out.println("2-Modificar peliculas");
			System.out.println("3-Mostrar peliculas");
			System.out.println("4-Borrar peliculas");
			System.out.println("5-Crear salas e insertar salas");
			System.out.println("6-Mostrar salas");
			System.out.println("7-Crear proyecciones e insertar proyecciones");
			System.out.println("8-Mostrar proyecciones");
			opcion = t.nextInt();
			t.nextLine();
			int id;
			peliculas p;
			salas s;
			proyeccion pr;
		
			switch (opcion) {
			case 1:
				p= new peliculas();
				System.out.println("Introduce el id");
				p.setId(t.nextLine());
				System.out.println("Introduce el titulo");
				p.setTitulo(t.nextLine());
				System.out.println("Introduce la duración");
				p.setDuracion(t.nextInt());
				//Ahora para crear las peliculas
				if(!peliculas.existePelicula()) {
					
					if(!peliculas.insertarPeliculas(p)) {
						System.out.println("Error al asignar la nueva pelicula");
					}
				}
				else {
					System.out.println("Error: Ya existe el id de la pelicula");
				}
				
							
						
				break;
				
			
			case 2:
			
				System.out.println("Introduce el id de la pelicula a modificar");
				id = t.nextInt();t.nextLine();
				
					String nuevoNombre = t.nextLine();
					if(!peliculas.modificarNombrePeliculas(id,nuevoNombre)) {
						System.out.println("Error al modificar el id de la pelicula");
					}
				
				else {
					System.out.println("Error, la pelicula ya existe");
				}
				break;
			
			case 3:
				peliculas.mostrarPeliculas();
				
				break;

			case 4:
				
				System.out.println("Introduce el id de la pelicula a borrar");
				id = t.nextInt();t.nextLine();
				if(!peliculas.borrarPeliculas(id)) {
				System.out.println("Error, la pelicula ya existe");
				}
            
				
				break;
			case 5:
				s= new salas();
				System.out.println("Introduce el numero de la sala");
		
			
				if(!peliculas.existeSalas()) {
					
					if(!peliculas.insertarSalas(s)) {
						System.out.println("Error al asignar la nueva sala");
					}
				}
				else {
					System.out.println("Error: Ya existe el numero  de la sala");
				}
							
				break;
			case 6:
				peliculas.mostrarSalas();
				
				break;
			case 7:
				break;
			case 8:
				break;
			
			}
		} while (opcion != 0);

	}
		


	}
	}


