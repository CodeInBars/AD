package PracticaPilar_zamora;

import java.io.File;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XPathQueryService;

public class Modelo {
	
	static Collection col = null;
	String url="xmldb:exist://localhost:8080/exist/xmlrpc/db", 
		   usuario="admin", 
		   clave="admin",
		   nombreC="peliculas";
	
	public 	Modelo() {
		Class driver;
		try {
			//Cargar el driver
			driver = Class.forName("org.exist.xmldb.DatabaseImpl");
			//Crear una instancia de la BD
			Database db = (Database) driver.newInstance();
			//Registrar la BD
			DatabaseManager.registerDatabase(db);
			//Nos conectamos a la colección principal del servidor
			//porque es posible que la colección TiendaInformatica no exista
			Collection padre = DatabaseManager.getCollection(url,usuario,clave);
			//Obtenemos la coleccion TiendaInformatica
			col = padre.getChildCollection(nombreC);
			if(col==null) {
				//Creamos la coleccion TiendaInformatica en la colección padre
				CollectionManagementService servicio = 
					(CollectionManagementService) 
					padre.getService("CollectionManagementService", "1.0");
				col=servicio.createCollection(nombreC);
				//Cargamos los ficheros xml vacíos en la colección
				//Fichero peliculas.xml
				File fichero = new File("peliculas.xml");
				//Creamos el recurso
				Resource recurso = 
						col.createResource(fichero.getName(), "XMLResource");
				//Asignamos el fichero al recurso
				recurso.setContent(fichero);
				//Guardamos el recurso en la colección
				col.storeResource(recurso);
				//Fichero salas.xml
				fichero = new File("salas.xml");
				//Creamos el recurso
				recurso = 
						col.createResource(fichero.getName(), "XMLResource");
				//Asignamos el fichero al recurso
				recurso.setContent(fichero);
				//Guardamos el recurso en la colección
				col.storeResource(recurso);
			}
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Collection getCol() {
		return col;
	}
	public void setCol(Collection col) {
		this.col = col;
	}
	
	public void cerrar() {
		try {
			col.close();
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
		
		
		
	
	public boolean insertarPeliculas(peliculas p) {
		boolean resultado=false;
		p.setCodigo(obtenerCodigoPelicula());
				try {
			XPathQueryService consulta = (XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			consulta.query("update insert "
					+ "<pelicula id='"+p.getId()+"'>"
							+ "<titulo>"+p.getTitulo()+"</titulo>"
							+ "<duracion>"+p.getDuracion()+"</duracion>"
							
					+ "</peliculas>"
					+ "into /peliculas");
			resultado=true;
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				return resultado;
		// TODO Auto-generated method stub
	
	}
	private Object obtenerCodigoPelicula() {
		// TODO Auto-generated method stub
		int resultado = 1;
		try {
			XPathQueryService consulta = 
					(XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("string(/peliculas/pelicula[last()]/@codigo)");
			ResourceIterator i = r.getIterator();
			if(i.hasMoreResources()) {
				String numero =i.nextResource().getContent().toString();
				if(!numero.equals(""))
					resultado = Integer.parseInt(numero)+1;
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	
	}
	public boolean existePelicula() {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			XPathQueryService consulta = 
					(XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			String id = null;
			ResourceSet r = consulta.query("//peliculas[@id='"+id+"']");
			ResourceIterator i = r.getIterator();
			if(i.hasMoreResources()) {
				resultado = true;
			}
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
		
		}
	
	public boolean modificarNombrePeliculas(int id, String nuevoNombre) {
		// TODO Auto-generated method stub
		boolean resultado = true;
		XPathQueryService consulta;
		try {
			consulta = (XPathQueryService)
					col.getService("XPathQueryService", "1.0");
			String nuevotitulo = null;
			ResourceSet r = consulta.query("update replace "
					+ "//peliculas[@numero='"+id+"']/titulo with "
					+ "<titulo>"+nuevotitulo + "</titulo>");
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
		
	}
	
	public void mostrarPeliculas() {
		try {
			XPathQueryService consulta = 
					(XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("//peliculas");
			ResourceIterator i = r.getIterator();
			while(i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean borrarPeliculas(int id) {
		// TODO Auto-generated method stub
		boolean resultado=true;
		try {
			XPathQueryService consulta = (XPathQueryService)
			 col.getService("XPathQueryService", "1.0");
			ResourceSet r=consulta.query("update delete "+"//peliculas[@id ='"+id+"']");
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return false;
		
	}
	public boolean existeSalas() {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			XPathQueryService consulta = 
					(XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			int numero = 0;
			ResourceSet r = consulta.query("//salas[@numero='"+numero+"']");
			ResourceIterator i = r.getIterator();
			if(i.hasMoreResources()) {
				resultado = true;
			}
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
		
		}
	
	
	public boolean insertarSalas(salas s) {
		// TODO Auto-generated method stub
	
			boolean resultado=false;
			s.setNumero(obtenerNumero());
					try {
				XPathQueryService consulta = (XPathQueryService) 
						col.getService("XPathQueryService", "1.0");
				consulta.query("update insert "
						+ "<salas numero='"+s.getNumero()+"'>"
					   + "</salas>"
						+ "into /salas");
				resultado=true;
			} catch (XMLDBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					return resultado;
			// TODO Auto-generated method stub
		
	}
	private int obtenerNumero() {
		// TODO Auto-generated method stub
		int resultado=1;
		try {
			XPathQueryService consulta = 
					(XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("string(/salas/sala[last()]/@numero)");
			ResourceIterator i = r.getIterator();
			if(i.hasMoreResources()) {
				String numero =i.nextResource().getContent().toString();
				if(!numero.equals(""))
					resultado = Integer.parseInt(numero)+1;
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	
	
	
	}
	public void mostrarSalas() {
		// TODO Auto-generated method stub
		try {
			XPathQueryService consulta = 
					(XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("//salas");
			ResourceIterator i = r.getIterator();
			while(i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	}
	
		

	
	


	


	
	


		
	

	
	
	
		
	
	

