package Alejandro;

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

	Collection col = null;
	String url="xmldb:exist://localhost:8080/exist/xmlrpc/db",
			usuario="admin",
			clave="admin",
			nameCollection="GIDIDP";
	XPathQueryService consulta; 
	
	public Modelo() {
		Class driver;
		try {
			driver = Class.forName("org.exist.xmldb.DatabaseImpl");

			Database db = (Database) driver.newInstance();

			DatabaseManager.registerDatabase(db);

			Collection padre = DatabaseManager.getCollection(url,usuario,clave);

			col = padre.getChildCollection(nameCollection);
			
			if(col==null) {
				CollectionManagementService servicio = (CollectionManagementService) 
						padre.getService("CollectionManagementService", "1.0");
				
				col=servicio.createCollection(nameCollection);
				
				//Load XMl File (personas.xml)
				File fichero = new File("personas.xml");
				//Create Resource
				Resource recurso =col.createResource(fichero.getName(), "XMLResource");
				recurso.setContent(fichero);
				col.storeResource(recurso);
				
				//Load XMl File (asignaturas.xml)
				fichero = new File("asignaturas.xml");
				//Create resource
				recurso = col.createResource(fichero.getName(), "XMLResource");
				recurso.setContent(fichero);
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
	
	public void cerrar() {
		try {
			col.close();
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

	public boolean existeDni(String id_p,String tipo_p) {
		boolean resultado = false;
		
		try {
			consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			
			ResourceSet r = consulta.query("/personas/persona[@id='"+id_p+"' and @tipo='"+tipo_p+"']");
			
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

	public boolean insertarProfesor(String id_p, String tipo_p, String nombre, int exp) {
		boolean resultado = false;
		
		try {	
			consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			
			consulta.query("update insert "
					+ "<persona tipo='"+tipo_p+"' id='"+id_p+"'>"
					      + "<nombre>"+nombre+"</nombre>"
					      + "<experiencia años='"+exp+"'/>"
					+"</persona>"
					+ "into /personas");
			
			resultado=true;
			
		}catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	public boolean existeCodigo(String code_as) {
		boolean resultado = false;
		
		try {
			consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			
			ResourceSet r = consulta.query("/asignaturas/asignatura[@codigo='"+code_as+"']");
			
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

	public void mostrarPersonas(String tipo_p) {

		try {
			consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			
			ResourceSet r = consulta.query("/personas/persona[@tipo='"+tipo_p+"']");
			
			ResourceIterator i = r.getIterator();
			while(i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean insertarAsignatura(String code_as, String id_p, String nombre, int creditos, int curso) {
		boolean resultado = false;
		
		try {	
			consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			
			consulta.query("update insert "
					+ "<asignatura codigo='"+code_as+"'>"
						+ "<profesor id='"+id_p+"'/>"
						+ "<nombre>"+nombre+"</nombre>"
						+ "<creditos>"+creditos+"</creditos>"
						+ "<curso>"+curso+"</curso>"
					+ "</asignatura>"
					+ "into /asignaturas");
			
			resultado=true;
			
		}catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	public void mostrarAsignaturas() {
		try {
			consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			
			ResourceSet r = consulta.query("/asignaturas/asignatura");
			
			ResourceIterator i = r.getIterator();
			while(i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean insertarAlumno(String id_p, String tipo_p, String nombre) {
		boolean resultado = false;
		
		try {	
				consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
				
				consulta.query("update insert "
						+ "<persona tipo='"+tipo_p+"' id='"+id_p+"'>"
						    + "<nombre>"+nombre+"</nombre>"
						    +"<asignaturas></asignaturas>"
						+"</persona>"
						+ "into /personas");
				
				resultado=true;

			
			
		}catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	public boolean matricularAlumno(String id_p, String code_as) {
		boolean resultado = false;	
		try {	
			consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			
			consulta.query("update insert "
					+ "<asignatura id='"+code_as+"' nota='-1' />"
					+ "into /personas/persona[@id='"+id_p+"']/asignaturas");
			
			resultado=true;
	}catch (XMLDBException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		return resultado;
	}

	
	public boolean modificarNota(String id_p, String code_as, int nota) {
		boolean resultado = false;	
		try {	
			consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			consulta.query("update delete //persona[@id='"+id_p+"']//asignatura[@id='"+code_as+"']");
			consulta.query("update insert <asignatura id='"+code_as+"' nota='"+nota+"'/> into //persona[@id='"+id_p+"']/asignaturas"); 
			
			resultado=true;
	}catch (XMLDBException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		return resultado;
	}

	public boolean existeAsignaturaAlumno(String id_p, String code_as) {
		boolean resultado = false;
		
		try {
			consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			
			ResourceSet r = consulta.query("//persona[@id='"+id_p+"']//asignatura[@id='"+code_as+"']");
			
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

	public void contarCreditosAlumno(String id_p) {
		
		try {
			consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r = consulta.query("for $c in //persona[@id='"+id_p+"']\r\n" + 
					"for $a in $c//asignatura[@nota>=5]\r\n" + 
					"for $s in /asignaturas/asignatura[@codigo=$a/@id]\r\n" + 
					"return <asignatura nombre='{$s/nombre}'nota='{$a/@nota}' creditos='{$s/creditos}'/>");
			
			ResourceIterator i = r.getIterator();
			
			while(i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent());
			}
			r = consulta.query("for $c in //persona[@id='"+id_p+"']\r\n" + 
					"let $a := $c//asignatura[@nota>=5]\r\n" + 
					"let $s := /asignaturas/asignatura[@codigo=$a/@id]\r\n" + 
					"return <totalcreditos>{sum(data($s/creditos))}></totalcreditos>");
			
			i = r.getIterator();
			while(i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void borrarAlumno(String id_p) {
		try {	
			consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			consulta.query("update delete //persona[@id='"+id_p+"']");
			 
	}catch (XMLDBException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		
	}
	
}