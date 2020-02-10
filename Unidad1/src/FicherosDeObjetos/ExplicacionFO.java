package FicherosDeObjetos;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ExplicacionFO {

	static Scanner t = new Scanner(System.in);
	static String nombreFO = "Almacen.obj";
	static String nombreFB = "Almacen.bin";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int opcion = 0;
		do {
			System.out.println("0-Salir");
			System.out.println("1-Crear fichero objetos a partir de "
					+ "fichero de binario");
			System.out.println("2-Leer/Mostrar");
			System.out.println("3-Insertar/Añadir material");
			System.out.println("4-Modificar material");
			System.out.println("5-Borrar material");
			
			opcion=t.nextInt();t.nextLine();
			switch(opcion) {
				case 1:
					crearFO();
					break;
				case 2:
					mostrarFO();
					break;
				case 3:
					insertarFO();
					break;
				case 4:
					
					break;
				case 5:
					
					break;
			}
			
		}while(opcion!=0);
	}

	private static void insertarFO() {
		// TODO Auto-generated method stub
		//Comprobar si existe el fichero de objetos
		File f = new File(nombreFO);
		
		Pieza p = new Pieza();
		System.out.println("Codigo:");
		p.setCodigo(t.nextLine());
		System.out.println("Nombre:");
		p.setNombre(t.nextLine());
		System.out.println("Precio:");
		p.setPrecio(t.nextFloat());t.nextLine();
		System.out.println("Stock:");
		p.setStock(t.nextInt());t.nextLine();
		p.setAlta(true);
		
		if(f.exists()) {
			MyObjectOutputStream fichero = null;
			//Abrimos para añadir
			try {
				fichero = new MyObjectOutputStream(
						new FileOutputStream(nombreFO,true));
				//Escribimos la nueva pieza en el fichero
				fichero.writeObject(p);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if(fichero!=null) {
					try {
						fichero.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		else {
			ObjectOutputStream fichero = null;
			//Abrimos para sobreescribir
			try {
				fichero = new ObjectOutputStream(
						new FileOutputStream(nombreFO,false));
				//Escribimos la nueva pieza en el fichero
				fichero.writeObject(p);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if(fichero!=null) {
					try {
						fichero.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		
	}

	private static void mostrarFO() {
		// TODO Auto-generated method stub
		//Declaramos fichero
		ObjectInputStream fichero = null;
		
		try {
			//Abrimos
			fichero= new ObjectInputStream(new FileInputStream(nombreFO));
			//Leemos todos los objetos
			while(true) {
				Pieza p = (Pieza) fichero.readObject();
				p.mostrar();
			}
		} 
		catch ( EOFException e) {
			// TODO: handle exception
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(fichero!=null) {
				try {
					fichero.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static void crearFO() {
		// TODO Auto-generated method stub
		//Declaramos ficheros
		DataInputStream fBinario =null;
		ObjectOutputStream fObjeto = null;
		
		try {
			//Abrimos ficheros
			fBinario = new DataInputStream(new FileInputStream(nombreFB));
			//Añadir a falso. Siempre se crea el fichero
			fObjeto = new ObjectOutputStream(
					new FileOutputStream(nombreFO,false));
			//Leeemos datos del binario
			while(true) {
				Pieza p = new Pieza();
				char letra;
				p.setCodigo("");
				while((letra=fBinario.readChar())!='\n') {
					p.setCodigo(p.getCodigo()+letra);
				}
				p.setNombre("");
				while((letra=fBinario.readChar())!='\n') {
					p.setNombre(p.getNombre()+letra);
				}
				p.setPrecio(fBinario.readFloat());
				p.setStock(fBinario.readInt());
				p.setAlta(fBinario.readBoolean());
				
				//Guardamos el objeto p en el fichero de objetos
				fObjeto.writeObject(p);
			}
		} 
		catch (EOFException e) {
			// TODO: handle exception
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(fBinario!=null) {
				try {
					fBinario.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fObjeto!=null) {
				try {
					fObjeto.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
