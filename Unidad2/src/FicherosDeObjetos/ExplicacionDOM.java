package FicherosDeObjetos;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class ExplicacionDOM {
	
	static Scanner t = new Scanner(System.in);
	static String nombreFT = "Almacen.txt";
	static String nombreFXML = "Almacen.xml";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int opcion = 0;
		do {
			System.out.println("0-Salir");
			System.out.println("1-Crear fichero xml a partir de fichero texto");
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
					MostrarFicheroXML();
					break;
				case 3:
					insertarXML();
					break;
				case 4:
					modificarPrecio();
					break;
				case 5:
					borrarPieza();
					break;
			}
			
		}while(opcion!=0);
	}

	private static void borrarPieza() {
		// TODO Auto-generated method stub
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document documento = db.parse(nombreFXML);
			
			//Pedimos el código de pieza a modificar
			System.out.println("Introduce el codigo");
			String codigo = t.nextLine();
			
			//Obtenemos los elementos pieza
			NodeList nodosPieza = documento.getElementsByTagName("pieza");
			for(int i=0;i<nodosPieza.getLength();i++) {
				Element pieza = (Element) nodosPieza.item(i);
				
				//Comprobamos atributo código
				if(pieza.getAttribute("codigo").equalsIgnoreCase(codigo)) {
					//Borramos el nodo pieza
					pieza.getParentNode().removeChild(pieza);
					pasarDOMaXML(documento, nombreFXML);
					break;
				}
				
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void modificarPrecio() {
		// TODO Auto-generated method stub
		//Creamos el constructor de documento
		DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document documento = db.parse(nombreFXML);
			
			//Pedimos el código de pieza a modificar
			System.out.println("Introduce el codigo");
			String codigo = t.nextLine();
			
			//Recuperamos los nodos pieza
			NodeList nodosPieza  = documento.getElementsByTagName("pieza");
			for(int i=0;i<nodosPieza.getLength();i++) {
				//Obtenemos atributos
				NamedNodeMap atributos = nodosPieza.item(i).getAttributes();
				if(atributos.getNamedItem("codigo").getNodeValue().equalsIgnoreCase(codigo)) {
					//Pedimos el nuevo precio
					System.out.println("Introduce el nuevo precio");
					float precio = t.nextFloat();t.nextLine();
					//Recuperamos el nodo precio
					Element nodoPrecio = (Element) nodosPieza.item(i).getChildNodes().item(1);
					nodoPrecio.setTextContent(Float.toString(precio));
					pasarDOMaXML(documento, nombreFXML);
					break;
				}
				
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void insertarXML() {
		// TODO Auto-generated method stub
		//Creamos el constructor de documento
		DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document documento = db.parse(nombreFXML);
			//Pedimos el código a crear
			System.out.println("Introduce el códgio");
			String codigo = t.nextLine();
			if(!existe(documento,codigo)) {
				//Creamos el elemento y lo rellenamos con los datos
				Element pieza = documento.createElement("pieza");
				//Creamos atributos
				pieza.setAttribute("codigo", codigo);
				pieza.setAttribute("alta", "Sí");
				//Añadimos pieza a piezas
				documento.getDocumentElement().getElementsByTagName("piezas").item(0).appendChild(pieza);
				//Nombre de la pieza
				Element nombre = documento.createElement("nombre");
				pieza.appendChild(nombre);
				System.out.println("Introduce el nombre de la pieza");
				Text nombrePieza = documento.createTextNode(t.nextLine());
				nombre.appendChild(nombrePieza);
				
				//Precio de la pieza
				Element precio = documento.createElement("precio");
				pieza.appendChild(precio);
				System.out.println("Introduce el precio de la pieza");
				Text precioPieza = documento.createTextNode(Float.toString(t.nextFloat()));
				t.nextLine();
				precio.appendChild(precioPieza);
				
				//Stock de la pieza
				Element stock = documento.createElement("nombre");
				pieza.appendChild(stock);
				System.out.println("Introduce el stock de la pieza");
				Text stockPieza = documento.createTextNode(Integer.toString(t.nextInt()));
				t.nextLine();
				stock.appendChild(stockPieza);
				//PAsamos el árbol DOM a fichero
				pasarDOMaXML(documento,nombreFXML);
				
			}
			else {
				System.out.println("Error: Ya existe una pieza con ese código");
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static boolean existe(Document documento, String codigo) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		
		//Obtenemos todos los nodos pieza
		NodeList piezas = documento.getElementsByTagName("pieza");
		//Para cada pieza comprobamos si el atribut código 
		//coincide con el buscado
		int contador = 0;
		while(!resultado && contador<piezas.getLength()) {
			//Obtenemos atributos
			NamedNodeMap atributos = piezas.item(contador).getAttributes();
			for(int i=0;i<atributos.getLength();i++) {
				if(atributos.item(i).getNodeName().equals("codigo")) {
					if(atributos.item(i).getNodeValue().equalsIgnoreCase(codigo)) {
						resultado = true;
					}
				}
			}
			contador++;
		}
		return resultado;
	}

	private static void MostrarFicheroXML() {
		// TODO Auto-generated method stub
		
		try {
			//Creamos el constructor de documento
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document documento = db.parse(nombreFXML);
			//Obtenemos raiz
			Element raiz = documento.getDocumentElement();
			//Mostramos la raiz con un método recursivo que pinta en nodo
			//y sus nodos hijos
			mostrarElemento(raiz,0);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void mostrarElemento(Element nodo, int tabulaciones) {
		// TODO Auto-generated method stub
		//tabulamos
		for(int i=0;i<tabulaciones;i++) {
			System.out.print("\t");
		}
		//Pintamos el nombre del nodo
		System.out.print(nodo.getNodeName()+"(");
		//Obtenemos los atributos
		NamedNodeMap atributos = nodo.getAttributes();
		//Mostramos los atributos
		for(int i=0;i<atributos.getLength();i++) {
			System.out.print(atributos.item(i).getNodeName()+"="+
					atributos.item(i).getNodeValue()+" ");
		}
		System.out.println(")");
		//Obtenemos los hijos del nodo para llamar de forma recursiva 
		// al método para cada hijo
		NodeList nodos = nodo.getChildNodes();
		for(int i=0;i<nodos.getLength();i++) {
			//Comprobamos si el hijo es de tipo texto o de tipo elemento
			if(nodos.item(i).getNodeType()==Node.TEXT_NODE) {
				for(int j=0;j<tabulaciones+1;j++) {
					System.out.print("\t");
				}
				System.out.println(nodos.item(i).getNodeValue());
			}
			else {
				mostrarElemento((Element)nodos.item(i), tabulaciones+1);
			}
		}
	}

	private static void crearFicheroXML() {
		// TODO Auto-generated method stub
		//Pedimos el nombre del taller
		System.out.println("Introduce el nombre del taller");
		String nombreTaller = t.nextLine();
		//Declaramos el fichero de texto para leer
		BufferedReader fichero = null;
		
		//Declaramos el arbol DOM para el documento
		Document documento = null;
		
		try {
			//Creamos el constructor de documento
			DocumentBuilder db = 
					DocumentBuilderFactory.newInstance().newDocumentBuilder();
			//Creamos el documento xml vacío
			documento = db.newDocument();
			//Establecemos versión XML
			documento.setXmlVersion("1.0");
			
			//Creamos elementos raiz
			Element raiz = documento.createElement("taller");
			//Asignamos la raiz al árbol
			documento.appendChild(raiz);
			
			//Creamos el elemento nombre
			Element nombre = documento.createElement("nombre");
			//Añadimos el elemento nombre a la raiz
			raiz.appendChild(nombre);
			//Creamos el nodo de texto para el nombre del taller
			Text tNombre = documento.createTextNode(nombreTaller);
			//Añadimos el nodo de texto al elemento nombre
			nombre.appendChild(tNombre);
			
			//Creamos el elemento piezas
			Element piezas = documento.createElement("piezas");
			//Añadimos el elemento piezas a la raiz
			raiz.appendChild(piezas);
			
			//Abrimos el fichero de texto para crear los
			// nodos pieza
			fichero = new BufferedReader(new FileReader(nombreFT));
			String linea;
			while((linea=fichero.readLine())!=null) {
				String campos[] = linea.split(";");
				
				//Creamos elemento pieza
				Element pieza = documento.createElement("pieza");
				//Asignamos pieza a piezas
				piezas.appendChild(pieza);
				//Creamos los atributos de pieza
				pieza.setAttribute("codigo", campos[0]);
				pieza.setAttribute("alta", campos[4]);
				
				//Creamos el elemento nombre de pieza
				Element nombrePieza = documento.createElement("nombre");
				//Asignamos nombrePieza a pieza
				pieza.appendChild(nombrePieza);
				//Creamos el nodo texto con el nombre de la pieza
				Text textoNombrePieza = documento.createTextNode(campos[1]);
				nombrePieza.appendChild(textoNombrePieza);
				
				//Creamos el elemento precio de pieza
				Element precio = documento.createElement("precio");
				//Asignamos precio a pieza
				pieza.appendChild(precio);
				//Creamos el nodo texto con el precio de la pieza
				Text textoPrecio = documento.createTextNode(campos[2]);
				precio.appendChild(textoPrecio);
				
				//Creamos el elemento stock de pieza
				Element stock = documento.createElement("stock");
				//Asignamos stock a pieza
				pieza.appendChild(stock);
				//Creamos el nodo texto con el precio de la pieza
				Text textoStock = documento.createTextNode(campos[3]);
				stock.appendChild(textoStock);
			}
			//PAsamos el árbol DOM a fichero
			pasarDOMaXML(documento,nombreFXML);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	private static void pasarDOMaXML(Document documento, String nombreF) {
		// TODO Auto-generated method stub
		//Crear una fuente/origen con el árbol DOM
		Source fuente = new DOMSource(documento);
		//Crear el destino de la transformación:
		Result ficheroXML = new StreamResult(new File(nombreF));
		//Crear un transformador
		try {
			Transformer t =TransformerFactory.newInstance().newTransformer();
			//Transformar la fuente en el resultado
			t.transform(fuente, ficheroXML);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
