package ColleccionVentas;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;

public class Modelo {
	
	Collection col = null;
	String url="xmldb:exist://localhost:8080/exist/xmlrpc/db/Ventas", 
		   usuario="admin", 
		   clave="admin";
	public Modelo() {
		
		Class driver;
		try {
			//Cargar el driver
			driver = Class.forName("org.exist.xmldb.DatabaseImpl");
			//Crear una instancia de la BD
			Database db = (Database) driver.newInstance();
			//Registrar la BD
			DatabaseManager.registerDatabase(db);
			
			col = DatabaseManager.getCollection(url,usuario,clave);
			
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
	public void mostrarClientes() {
		// TODO Auto-generated method stub
		try {
			//Creamos un servicio para hacer una consulta
			XPathQueryService consulta = 
					(XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			
			//Ejecutamos la consulta XPATH/XQUERY
			ResourceSet r = consulta.query("//clien");
			
			//Recuperamos el resultado
			ResourceIterator i = r.getIterator();
			while(i.hasMoreResources()) {
				Resource cliente = i.nextResource();
				System.out.println(cliente.getContent());
			}
			
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean insertarCliente(String nombre, String poblacion, String telefono, String direccion) {
		// TODO Auto-generated method stub
		boolean resultado = true;
		int id = obtenerNuevoIdCliente();
		try {
			XPathQueryService consulta = (XPathQueryService) 
					col.getService("XPathQueryService", "1.0");
			String textoConsulta ="update insert "
					+ "<clien numero='"+id+"'>"
					+ "<nombre>"+nombre+"</nombre>"
					+ "<poblacion>"+poblacion+"</poblacion>"
					+ "<tlf>"+telefono+"</tlf>"
					+ "<direccion>"+direccion+"</direccion>"
					+"</clien> into /clientes"; 
			consulta.query(textoConsulta);
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			resultado=false;
			e.printStackTrace();
		}
		
		return resultado;
	}
	private int obtenerNuevoIdCliente() {
		// TODO Auto-generated method stub
		int resultado = 1;
		try {
			XPathQueryService consulta = 
					(XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("string(//clien[last()]/@numero)");
			ResourceIterator i = r.getIterator();
			if(i.hasMoreResources()) {
				String valor = i.nextResource().getContent().toString();
				if(!valor.equals("")) {
					resultado = Integer.parseInt(valor)+1;
				}
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	public boolean existeCliente(int id) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		
		try {
			XPathQueryService consulta = (XPathQueryService)
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("//clien[@numero='"+id+"']");
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
	public boolean modificarNombreCliente(int id, String nuevoNombre) {
		// TODO Auto-generated method stub
		boolean resultado = true;
		try {
			XPathQueryService consulta = (XPathQueryService)
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("update replace "
					+ "//clien[@numero='"+id+"']/nombre with "
					+ "<nombre>"+nuevoNombre + "</nombre>");
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			resultado = false;
			e.printStackTrace();
		}
		return resultado;
	}
	public boolean borrarCliente(int id) {
		// TODO Auto-generated method stub
		boolean resultado = true;
		try {
			XPathQueryService consulta = (XPathQueryService)
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("update delete "
					+ "//clien[@numero = '"+id+"']");
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			resultado = false;
			e.printStackTrace();
		}
		return resultado;
	}
	public void mostrarFacturas(int id) {
		// TODO Auto-generated method stub
		try {
			XPathQueryService consulta = (XPathQueryService)
					col.getService("XPathQueryService", "1.0");
			
			ResourceSet r = consulta.query("for $c in /clientes/clien[@numero='"+id+"'],\r\n" + 
					"    $f in /facturas/factura[numcliente=$c/@numero]\r\n" + 
					"let $p := /detallefacturas//producto/..[@numero=$f/@numero]\r\n" + 
					"return <factura fecha=\"{$f/fecha}\">\r\n" + 
					"    {$p/producto}\r\n" + 
					"</factura>");
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
