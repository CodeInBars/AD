import java.io.File;
import java.util.Scanner;

public class Ejercicio2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner t = new Scanner(System.in);
		System.out.println("Introduce el nombre de la carpeta a mostrar");
		String nombre = t.nextLine();
		
		//Creamos el objeto file de la carpeta a mostrar
		File carpeta = new File(nombre);
		//Comprobamos que existe y es carpeta
		if(carpeta.exists() && carpeta.isDirectory()) {
			mostrarContenido(carpeta,0);
		}
	}

	private static void mostrarContenido(File carpeta, int nivel) {
		// TODO Auto-generated method stub
		//Tabulamos para mostrar ordenado
		for(int i=0;i<nivel;i++) {
			System.out.print("\t");
		}
		
		System.out.println(carpeta);
		
		//Obtenemos los hijos de la carpeta
		File[] hijos = carpeta.listFiles();
		
		//Recorremos los hijos
		for(File f:hijos) {
			//Comprobamos si el hijo es fichero
			if(f.isFile()) {
				//Tabulamos para mostrar ordenado
				for(int i=0;i<nivel+1;i++) {
					System.out.print("\t");
				}
				//Pintamos su nombre
				System.out.println(f.getName());
			}
			else {
				if(f.isDirectory()) {
					
					mostrarContenido(f,nivel+1);
				}
			}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
