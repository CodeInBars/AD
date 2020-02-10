package Ismael;
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
	String url="xmldb:exist://localhost:8080/exist/xmlrpc/db", usuario="admin", clave="admin", nombreC = "TiendaSurprise";
	
	public Modelo() {
		Class driver;
		try {
			//Cargar el driver
			driver = Class.forName("org.exist.xmldb.DatabaseImpl");
			//Crear una instancia de la BD
			Database db = (Database) driver.newInstance();
			//Registrar la BD
			DatabaseManager.registerDatabase(db);
			
			//Nos conectamos a la coleccion principal del servidor
			//Porque es posible que la coleccion TiendaSurprise no exista
			Collection padre = DatabaseManager.getCollection(url,usuario,clave);
			//Obtenemos la coleccion tienda surprise
			col = padre.getChildCollection(nombreC);
			if(col==null) {
				
				//Creamos la coleccion TiendaSurprise en la coleccion padre
				CollectionManagementService servicio = (CollectionManagementService) padre.getService("CollectionManagementService", "1.0");
				col = servicio.createCollection(nombreC);
				//Cargamos los ficheros xml vacios en la coleccion
				File fichero = new File("regalos.xml");
				//Creamos el recurso
				Resource recurso = col.createResource(fichero.getName(), "XMLResource");
				//Asignamos el fichero al recurso
				recurso.setContent(fichero);
				//Guardamos el recurso en la colección
				col.storeResource(recurso);
				
				fichero = new File("cajas.xml");
				//Creamos el recurso
				recurso = col.createResource(fichero.getName(), "XMLResource");
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

	/**
	 * ==================================
	 * ==	METODOS DE LA APLICACIÓN   ==    
	 * ==================================
	 */
	
	public boolean insertarRegalo(Regalos r) {
		
		boolean result = false;
		r.setCodigo(obtenerCodigoRegalo(r));
		
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			consulta.query("update insert <regalo codigo='"+r.getCodigo() +"'>"
					+ "<nombre>"+r.getNombre()+"</nombre>"
					+ "<stock>" + r.getStock()+"</stock>"					
					+ "</regalo> into /regalos");
			result = true;
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public int obtenerCodigoRegalo(Regalos r) {
		
		int result = 0;
		
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet resour = consulta.query("string(/regalos/regalo[last()]/@codigo)");
			ResourceIterator i = resour.getIterator();
			if(i.hasMoreResources()) {
				String numero = i.nextResource().getContent().toString();
				if(!numero.equals("")) {
					result = Integer.parseInt(numero) + 1;
				}
				
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void mostrar() {
		
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("/regalos/regalo");
			ResourceIterator i = r.getIterator();
			while(i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean insertarCajas(Cajas c) {
		
		boolean result = false;
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			consulta.query("update insert <caja codigo ='"+ c.getCodigo() +"' fecha= '" +format.format(c.getFecha()) + "'>"
					+ "<regalos/>" + "<precio>"+c.getPrecio()+"</precio>"
					+ "</caja> into /cajas");
			result = true;
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public void mostrarCajas() {
		
		try {
			//Creamos un servicio para hacer una consulta
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			//Ejecutamos la consulta XPATH/XQUERY
			ResourceSet r = consulta.query("for $c in /cajas/caja,\r\n" + 
					"$r in /regalos/regalo[@codigo=$c/regalos/regalo/@codigo]\r\n" + 
					"let $s := /regalos/regalo[@codigo=$r/@codigo]\r\n" + 
					"\r\n" + 
					"return \r\n" + 
					"<caja codigo=\"{$c/@codigo}\" >\r\n" + 
					"	<regalos>\r\n" + 
					"		{$s/nombre}\r\n" + 
					"	</regalos>\r\n" + 
					"</caja>");
			
			//Recuperamos el resultado
			ResourceIterator i = r.getIterator();
			while(i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent());
			}
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean comprobarRegalo(int idRegalo) {
		
		boolean resultado = false;
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("/regalos/regalo[@codigo='"+idRegalo+"']");
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

	public boolean comprobarCaja(int idCaja) {
		
		boolean resultado = false;
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("/cajas/caja[@codigo='"+idCaja+"']");
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

	public boolean añadirRegalo(int idRegalo, int idCaja, int cantidad) {
		
		boolean resultado = false;
		
		XPathQueryService consulta;
		try {
			consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			consulta.query("update insert <regalo codigo ='"+ idRegalo +"' cantidad= '"+ cantidad + "'/>"
					+ "into /cajas/caja[@codigo='"+idCaja+"']/regalos");
			resultado = true;
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	public int comprobarStock(int idRegalo) {
		
		int resultado = 0;
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("string(/regalos/regalo[@codigo='"+idRegalo+"']/stock)");
			ResourceIterator i = r.getIterator();
			if(i.hasMoreResources()) {
				String numero = i.nextResource().getContent().toString();
				if(!numero.equals("")) {
					resultado = Integer.parseInt(numero);
				}
				
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	public boolean actualizarStock(Regalos reg, int cantidad) {
		
		boolean resultado = false;
		
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			consulta.query("update replace /regalos/regalo[@codigo='"+reg.getCodigo()+"']/stock with <stock>" + (reg.getStock() - cantidad) + "</stock>");
			
			resultado = true;
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}
	
	
}
