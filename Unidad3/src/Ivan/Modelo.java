package Ivan;

import java.io.File;

import javax.xml.crypto.dsig.DigestMethod;

import org.apache.commons.codec.digest.DigestUtils;
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
			nombreC = "Chat";
	
	public Modelo() {
		
		
		try {
			//Cargar el driver
			Class driver = Class.forName("org.exist.xmldb.DatabaseImpl");
			//Crear una instancia de la BD
			Database db = (Database) driver.newInstance();
			//Registrar la BD
			DatabaseManager.registerDatabase(db);
			//Conexion a la coleccion principal del servidor
			Collection padre = DatabaseManager.getCollection(url, usuario, clave);
			//Obtener coleccion Chat
			col = padre.getChildCollection(nombreC);
			//Si es nula, crear coleccion Chat
			if(col==null) {
				//Crear la coleccion Chat en la colección padre
				CollectionManagementService servicio = (CollectionManagementService) padre.getService("CollectionManagementService", "1.0");
				col = servicio.createCollection(nombreC);
				//Cargar los ficheros xml vacíos en la colección
				File fichero = new File("usuarios.xml");
				//Crear el recurso
				Resource recurso = col.createResource(fichero.getName(), "XMLResource");
				//Asignar el fichero al recurso
				recurso.setContent(fichero);
				//Guardar el recurso en la colección
				col.storeResource(recurso);
				
				//Igual con el otro fichero
				fichero = new File("mensajes.xml");
				//Crear el recurso
				recurso = col.createResource(fichero.getName(), "XMLResource");
				//Asignar el fichero al recurso
				recurso.setContent(fichero);
				//Guardar el recurso en la colección
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

	public boolean existeNick(String userName) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("/usuarios/usuario[@nick='"+userName+"']");
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

	public boolean registrarUsuario(String userName, String nombre, String pass) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		
		String encriptPass = DigestUtils.md5Hex(pass);
		
		if(!existeNick(userName)) {
			try {
				XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
				ResourceSet r = consulta.query("update insert "
								+ "<usuario nick='" + userName + "' >"
										+ "<nombre>"+ nombre + "</nombre>"
										+ "<clave>"+ encriptPass +"</clave>"
								+ "</usuario>"
								+ "into /usuarios");
				resultado = true;
			} catch (XMLDBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return resultado;
	}

	public boolean iniciarSesion(String userName, String pass) {
		boolean resultado = false;
		
		String encriptPass = DigestUtils.md5Hex(pass);
		
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("string(/usuarios/usuario[@nick='"+userName+"']/clave)");
			ResourceIterator i = r.getIterator();
			if(i.hasMoreResources()) {
				if(encriptPass.equals(i.nextResource().getContent().toString())) {
					resultado = true;
				}
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}

	public void mostrarUsuarios(String usuario) {
		// TODO Auto-generated method stub
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("for $u in //usuario\r\n" + 
											"return string($u/@nick)");
			ResourceIterator i = r.getIterator();
			while(i.hasMoreResources()) {
				String nick = i.nextResource().getContent().toString();
				if(!(nick.equals(usuario))) {
					System.out.println(nick);
				}
				
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean enviarMensaje(String userName, String destinatario, String mensaje) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		
		int id = obtenerId();
		
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("update insert "
							+ "<mensaje id='" + id + "' >"
								+ "<de>"+userName+"</de>"
								+ "<para>"+destinatario+"</para>"
								+ "<contenido>"+mensaje+"</contenido>"
							+ "</mensaje>"
							+ "into /mensajes");
			resultado = true;
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}

	private int obtenerId() {
		
		int resultado = 1;
		
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("string(/mensajes/mensaje[last()]/@id)");
			ResourceIterator i = r.getIterator();
			if(i.hasMoreResources()) {
				String numero = i.nextResource().getContent().toString();
				if(!numero.equals("")) {
					resultado = Integer.parseInt(numero) + 1;
				}
				
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}

	public void mensajesRecibidos(String userName) {
		
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("for $m in //mensaje[para='"+ userName +"'],\r\n" + 
											"	$u in //usuario[@nick=$m/de]\r\n" + 
											"return <mensaje id='{$m/@id}'>\r\n" + 
											"			<de nick=\"{$m/de}\">{$u/nombre}</de>\r\n" + 
											"			<para>{string($m/para)}</para>\r\n" + 
											"			<contenido>{string($m/contenido)}</contenido>\r\n" + 
											"		</mensaje>");
			ResourceIterator i = r.getIterator();
			while(i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void mensajesEnviados(String userName) {
		
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("for $m in //mensaje[de='"+ userName +"'],\r\n" + 
											"	$u in //usuario[@nick=$m/de]\r\n" + 
											"return <mensaje id='{$m/@id}'>\r\n" + 
											"			<de nick=\"{$m/de}\">{$u/nombre}</de>\r\n" + 
											"			<para>{string($m/para)}</para>\r\n" + 
											"			<contenido>{string($m/contenido)}</contenido>\r\n" + 
											"		</mensaje>");
			ResourceIterator i = r.getIterator();
			while(i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean borrarMensaje(int id) {
		
		boolean resultado = true;
		try {
			XPathQueryService consulta = (XPathQueryService)
					col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("update delete "
					+ "//mensaje[@id='"+id+"']");
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			resultado = false;
			e.printStackTrace();
		}
		return resultado;
		
		
	}

	public String comprobarMensaje(int id) {
		String resultado = null;
		
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("string(/mensajes/mensaje[@id='"+id+"']/de)");
			ResourceIterator i = r.getIterator();
			if(i.hasMoreResources()) {
				resultado = i.nextResource().getContent().toString();
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}

	public boolean modificarClave(String userName, String nuevaPass) {
		boolean resultado = true;
		
		String encriptPass = DigestUtils.md5Hex(nuevaPass);
		
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("update replace //usuario[@nick='"+userName+"']/clave with "
					+ "<clave>"+encriptPass+"</clave>");
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			resultado = false;
			e.printStackTrace();
		}
		
		return resultado;
	}

	public boolean modificarNombre(String userName, String nombre) {
		boolean resultado = true;
		
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("update replace //usuario[@nick='"+userName+"']/nombre with "
					+ "<nombre>"+nombre+"</nombre>");
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			resultado = false;
			e.printStackTrace();
		}
		
		return resultado;
	}

}
