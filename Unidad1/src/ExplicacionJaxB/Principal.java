package ExplicacionJaxB;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Principal {
	static Scanner t = new Scanner(System.in);
	static String nombreFO = "Almacen.obj";
	static String nombreFXML = "Almacen2.xml";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int opcion = 0;
		do {
			System.out.println("0-Salir");
			System.out.println("1-Crear fichero xml a partir de fichero objetos");
			System.out.println("2-Leer/Mostrar");
			System.out.println("3-Insertar/Añadir material sin duplicados");
			System.out.println("4-Modificar precio material");
			System.out.println("5-Baja material");
			
			opcion=t.nextInt();t.nextLine();
			switch(opcion) {
				case 1:
					crearFicheroXML();
					break;
				case 2:
					mostrar();
					break;
				case 3:
					insertar();
					break;
				case 4:
					modificar();
					break;
				case 5:
					borrar();
					break;
			}
			
		}while(opcion!=0);
	}
	private static void borrar() {
		// TODO Auto-generated method stub
		Taller taller = unmarshal();
		
		System.out.println("Codigo de pieza a borrar");
		String codigo = t.nextLine();
		
		boolean encontrado = false;
		int contador = 0;
		
		while(contador<taller.getPiezas().size() && !encontrado) {
			if(taller.getPiezas().get(contador).getCodigo().equalsIgnoreCase(codigo)) {
				taller.getPiezas().remove(contador);
				encontrado = true;
			}
			contador ++;
		}
		marshal(taller);
	}
	private static void modificar() {
		// TODO Auto-generated method stub
		Taller taller = unmarshal();
		System.out.println("Codigo de pieza a modificar");
		String codigo = t.nextLine();
		for(Pieza p:taller.getPiezas()) {
			if(p.getCodigo().equalsIgnoreCase(codigo)) {
				System.out.println("Nuevo Precio");
				p.setPrecio(t.nextFloat());t.nextLine();
				break;
			}
		}
		
		marshal(taller);
	}
	private static void insertar() {
		// TODO Auto-generated method stub
		Taller taller = unmarshal();		
		//Añadimos una pieza
		Pieza p = new Pieza();
		System.out.println("Codigo");
		p.setCodigo(t.nextLine());
		
		boolean encontrado = false;
		//Comprobamos si ya existe
		for(Pieza p2: taller.getPiezas()) {
			if(p2.getCodigo().equalsIgnoreCase(p.getCodigo())) {
				encontrado = true;
				break;
			}
		}
		if(!encontrado) {
			System.out.println("Nombre");
			p.setNombre(t.nextLine());
			System.out.println("Precio");
			p.setPrecio(t.nextFloat());t.nextLine();
			System.out.println("Stock");
			p.setStock(t.nextInt());t.nextLine();
			p.setAlta(true);
			//Añadimos la pieza al arrylist
			taller.getPiezas().add(p);	
		}
		else {
			System.out.println("Ya existe ese código");
		}
		marshal(taller);
	}
	private static void mostrar() {
		// TODO Auto-generated method stub
		Taller taller = unmarshal();
		taller.mostrar();
		
	}
	private static Taller unmarshal() {
		// TODO Auto-generated method stub
		Taller resultado = null;
		try {
			//Especificamos la clase raiz
			JAXBContext contexto = JAXBContext.newInstance(Taller.class);
			//Creamos el transformador
			Unmarshaller u = contexto.createUnmarshaller();
			//TRansformamos
			resultado = (Taller) u.unmarshal(new File(nombreFXML));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return resultado;
	}
	private static void crearFicheroXML() {
		// TODO Auto-generated method stub
		//Creamos un objeto de la clase raiz
		Taller taller = new Taller();
		System.out.println("Nombre taller");
		taller.setNombre(t.nextLine());
		
		//Obtenemos las piezas del fichero de objetos
		taller.setPiezas(obtenerPiezas());
		
		//Generar el fichero XML => hacer un Marshal
		marshal(taller);
	}
	private static void marshal(Taller taller) {
		// TODO Auto-generated method stub
		
		try {
			//Especificar sobre qué clase se va a hacer el marshal
			JAXBContext contexto = JAXBContext.newInstance(Taller.class);
			//Creamos el transformador de objeto a xml
			Marshaller m = contexto.createMarshaller();
			//Hacemos la transformación
			m.marshal(taller, new File(nombreFXML));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static ArrayList<Pieza> obtenerPiezas() {
		// TODO Auto-generated method stub
		ArrayList<Pieza> resultado = new ArrayList<Pieza>();
		
		ObjectInputStream fichero = null;
		try {
			fichero = new ObjectInputStream(new FileInputStream(nombreFO));
			while(true) {
				FicherosDeObjetos.Pieza p = (FicherosDeObjetos.Pieza) fichero.readObject();
				Pieza p2 = new Pieza();
				p2.setCodigo(p.getCodigo());
				p2.setNombre(p.getNombre());
				p2.setPrecio(p.getPrecio());
				p2.setStock(p.getStock());
				p2.setAlta(p.isAlta());
				resultado.add(p2);
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
		
		return resultado;
	}

}
