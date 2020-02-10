import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ExplicacionFicherosBinarios {
	
	static Scanner t = new Scanner(System.in);
	static String nombreFT = "Almacen.txt";
	static String nombreFB = "Almacen.bin";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int opcion = 0;
		do {
			System.out.println("0-Salir");
			System.out.println("1-Crear fichero binario a partir de "
					+ "fichero de texto");
			System.out.println("2-Leer/Mostrar");
			System.out.println("3-Insertar/Añadir material");
			System.out.println("4-Modificar material");
			System.out.println("5-Borrar material");
			
			opcion=t.nextInt();t.nextLine();
			switch(opcion) {
				case 1:
					crearFicheroBinario();
					break;
				case 2:
					mostrarFicheroBinario();
					break;
				case 3:
					insertarFicheroBinario();
					break;
				case 4:
					modificarBorrarFicheroBinario(false);
					break;
				case 5:
					modificarBorrarFicheroBinario(true);
					break;
			}
			
		}while(opcion!=0);
	}

	private static void modificarBorrarFicheroBinario(boolean borrar) {
		// TODO Auto-generated method stub
		//Declaramos ficheros
		DataInputStream ficheroO=null;
		DataOutputStream ficheroTmp = null;
		
		try {
			//Abrimos ficheros
			ficheroO = new DataInputStream(new FileInputStream(nombreFB));
			//Append = false para sobreescribir el fichero si existe
			ficheroTmp = new DataOutputStream(
					new FileOutputStream("almacen.tmp",false));
			//Pedimos el registro a tratar
			System.out.println("Código a modificar/borrar");
			String codigoUs = t.nextLine();
			//Leemos todos los registros del fichero original
			while(true) {
				//leemos Código
				String codigo = "";
				char letra;
				while((letra=ficheroO.readChar())!='\n') {
					codigo+=letra;
				}
				//leemos nombre
				String nombre = "";
				while((letra=ficheroO.readChar())!='\n') {
					nombre+=letra;
				}
				//leemos precio
				float precio = ficheroO.readFloat();
				//leemos stock
				int stock = ficheroO.readInt();
				//leemos alta
				boolean alta = ficheroO.readBoolean();
				//Comprobamos si el código leído es el código a tratar
				if(codigo.equalsIgnoreCase(codigoUs)) {
					if(!borrar){
						//Modificamos solamente el nombre
						System.out.println("Nuevo nombre");
						nombre = t.nextLine();
						ficheroTmp.writeChars(codigo+"\n");
						ficheroTmp.writeChars(nombre+"\n");
						ficheroTmp.writeFloat(precio);
						ficheroTmp.writeInt(stock);
						ficheroTmp.writeBoolean(alta);
					}
					
				}
				else {
					//Escribimos los datos en el fichero temporal
					//tal y como se han leído
					ficheroTmp.writeChars(codigo+"\n");
					ficheroTmp.writeChars(nombre+"\n");
					ficheroTmp.writeFloat(precio);
					ficheroTmp.writeInt(stock);
					ficheroTmp.writeBoolean(alta);
				}
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
			if(ficheroO!=null) {
				try {
					ficheroO.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(ficheroTmp!=null) {
				try {
					ficheroTmp.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//borrar fichero original
		File fO = new File(nombreFB);
		if(!fO.delete()) {
			System.out.println("Error al borrar " + nombreFB);
		}
		
		//renombrar el fichero temporal
		File fTmp = new File("almacen.tmp");
		if(!fTmp.renameTo(fO)) {
			System.out.println("Error al renombrar almacen.tmp");
		}
		
	}

	private static void insertarFicheroBinario() {
		// TODO Auto-generated method stub
		//Declaramos el fichero
		DataOutputStream fichero=null;
		
		try {
			//Abrimos para añadir
			fichero = new DataOutputStream(
					new FileOutputStream(nombreFB,true));
			//Pedimos datos y escribimos
			System.out.println("Código");
			fichero.writeChars(t.nextLine()+"\n");
			System.out.println("Nombre");
			fichero.writeChars(t.nextLine()+"\n");
			System.out.println("Precio");
			fichero.writeFloat(t.nextFloat());t.nextLine();
			System.out.println("Stock");
			fichero.writeInt(t.nextInt());t.nextLine();
			//Se crea siempre de alta
			fichero.writeBoolean(true);
			
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

	private static void mostrarFicheroBinario() {
		// TODO Auto-generated method stub
		//Declaramos fichero
		DataInputStream fichero=null;
		
		try {
			//Abrimos el fichero
			fichero = new DataInputStream(new FileInputStream(nombreFB));
			while(true) {
				//Leemos en el mismo orden que están los datos en el fichero
				//Leemos el código
				String codigo="";
				char letra;
				while((letra=fichero.readChar())!='\n') {
					codigo+=letra;
				}
				//Leemos el nombre
				String nombre="";
				while((letra=fichero.readChar())!='\n') {
					nombre+=letra;
				}
				//Leemos el precio
				float precio = fichero.readFloat();
				//Leemos el stock
				int stock = fichero.readInt();
				//Leemos alta
				boolean alta = fichero.readBoolean();
				//Mostramos registro
				System.out.println("Código:"+codigo+
						"\tNombre:"+nombre+
						"\tPrecio:"+precio+
						"\tStock"+stock+
						"\tAlta"+alta);
				
			}
		} 
		//Capturamos excepción al llegar al final de fichero
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

	private static void crearFicheroBinario() {
		// TODO Auto-generated method stub
		//Declaramos los objetos para trabajar con los ficheros
		BufferedReader ficheroT = null;
		DataOutputStream ficheroB = null;
		
		try {
			//Abrimos ficheros
			ficheroT = new BufferedReader(new FileReader(nombreFT));
			//Siempre creamos el fichero binario (append=false)
			ficheroB = new DataOutputStream(
					new FileOutputStream(nombreFB,false));
			//Recorremos todas las líneas del fichero de texto
			String linea;
			while((linea=ficheroT.readLine())!=null) {
				//Obtenemos los campos de la línea de texto leída
				String campos[] = linea.split(";");
				//Escribimos los campos en el fichero binario
				//Código
				ficheroB.writeChars(campos[0]+"\n");
				//Nombre
				ficheroB.writeChars(campos[1]+"\n");
				//Precio
				ficheroB.writeFloat(Float.parseFloat(campos[2]));
				//Stock
				ficheroB.writeInt(Integer.parseInt(campos[3]));
				//Alta
				if(campos[4].equalsIgnoreCase("Sí"))
					ficheroB.writeBoolean(true);
				else
					ficheroB.writeBoolean(false);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Fichero no encontrado "+nombreFT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(ficheroT!=null) {
				try {
					ficheroT.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(ficheroB!=null) {
				try {
					ficheroB.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	
}
