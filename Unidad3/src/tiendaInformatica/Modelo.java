package tiendaInformatica;

import java.io.File;
import java.text.SimpleDateFormat;

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

	Collection col = null;
	String url="xmldb:exist://localhost:8080/exist/xmlrpc/db", 
		   usuario="admin", 
		   clave="admin",
		   nombreC="TiendaInformatica";
	public Modelo() {
		
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
				//Fichero Piezas.xml
				File fichero = new File("piezas.xml");
				//Creamos el recurso
				Resource recurso = 
						col.createResource(fichero.getName(), "XMLResource");
				//Asignamos el fichero al recurso
				recurso.setContent(fichero);
				//Guardamos el recurso en la colección
				col.storeResource(recurso);
				//Fichero Ordenadors.xml
				fichero = new File("ordenadores.xml");
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
	public boolean insertarPieza(Pieza p) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		
		p.setCodigo(obtenerCodigoPieza());
		p.setAlta(true);
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			consulta.query("update insert "
					+ "<pieza codigo='"+p.getCodigo()+"' alta='"+p.isAlta()+"'>"
							+ "<nombre>"+p.getNombre()+"</nombre>"
							+ "<stock>"+p.getStock()+"</stock>"
							+ "<precio>"+p.getPrecio()+"</precio>"
					+ "</pieza>"
					+ "into /piezas");
			resultado=true;
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	private int obtenerCodigoPieza() {
		// TODO Auto-generated method stub
		int resultado = 1;
		try {
			XPathQueryService consulta = 
					(XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("string(/piezas/pieza[last()]/@codigo)");
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
	public void mostrarPiezas() {
		// TODO Auto-generated method stub
		try {
			XPathQueryService consulta = 
					(XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("/piezas/pieza");
			ResourceIterator i = r.getIterator();
			while(i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean existeOrdenador(String codigo) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			XPathQueryService consulta = 
					(XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("//ordenador[@codigo='"+codigo+"']");
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
	public boolean insertarOrdenador(Ordenador o) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			consulta.query("update insert "
					+ "<ordenador codigo='"+o.getCodigo()+"' fecha='"+
					              formato.format(o.getFecha())+"'>"
					      + "<piezas/>" +
					        "<precio>0</precio>"      
					+"</ordenador>"
					+ "into /ordenadores");
			resultado=true;
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	public void mostrarOrdenadores() {
		// TODO Auto-generated method stub
		try {
			XPathQueryService consulta = 
					(XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("//ordenador");
			ResourceIterator i = r.getIterator();
			while(i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean existePieza(int codigo) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			XPathQueryService consulta = 
					(XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("/piezas/pieza[@codigo='"+codigo+"']");
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
	public int obtenerStock(int codigo) {
		// TODO Auto-generated method stub
		int resultado = 0;
		try {
			XPathQueryService consulta = 
					(XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("string(/piezas/pieza[@codigo='"+codigo+"']/stock)");
			ResourceIterator i = r.getIterator();
			if(i.hasMoreResources()) {
				String numero =i.nextResource().getContent().toString();
				if(!numero.equals(""))
					resultado = Integer.parseInt(numero);
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	public boolean addPieza(Ordenador o, Pieza p, int cantidad) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
					
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			consulta.query("update insert "
					+ "<pieza codigo='"+p.getCodigo()+"' cantidad='"+
					              cantidad+"'/>"
					+ "into //ordenador[@codigo='"+o.getCodigo()+"']/piezas");
			resultado=true;
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	public boolean actualizarPrecioOrd(Ordenador o, Pieza p, int cantidad) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		float precio = obtenerPrecioPieza(p.getCodigo());
		float precioOrdenador = obtenerPrecioOrdenador(o.getCodigo());
		
		try {
			XPathQueryService consulta = (XPathQueryService)
					col.getService("XPathQueryService", "1.0");
			consulta.query("update replace //ordenador[@codigo='"+ o.getCodigo()+
					"']/precio with <precio>" + 
					(precioOrdenador + precio * cantidad) +"</precio>");
			resultado = true;
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}
	private float obtenerPrecioOrdenador(String codigo) {
		// TODO Auto-generated method stub
		float resultado = 0;
		try {
			XPathQueryService consulta = 
					(XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("string(//ordenador[@codigo='"+
					codigo+"']/precio)");
			ResourceIterator i = r.getIterator();
			if(i.hasMoreResources()) {
				String numero =i.nextResource().getContent().toString();
				if(!numero.equals(""))
					resultado = Float.parseFloat(numero);
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	private float obtenerPrecioPieza(int codigo) {
		// TODO Auto-generated method stub
		float resultado = 0;
		try {
			XPathQueryService consulta = 
					(XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("string(/piezas/pieza[@codigo='"+
					codigo+"']/precio)");
			ResourceIterator i = r.getIterator();
			if(i.hasMoreResources()) {
				String numero =i.nextResource().getContent().toString();
				if(!numero.equals(""))
					resultado = Float.parseFloat(numero);
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	public boolean actualizarStock(Pieza p, int cantidad) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			XPathQueryService consulta = (XPathQueryService)
					col.getService("XPathQueryService", "1.0");
			consulta.query("update replace /piezas/pieza[@codigo='"+ p.getCodigo()+
					"']/stock with <stock>" + 
					(p.getStock()-cantidad) +"</stock>");
			resultado = true;
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
}
