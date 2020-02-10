package hospital;

import java.io.File;
import java.util.ArrayList;

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

	String url = "xmldb:exist://localhost:8080/exist/xmlrpc/db", usuario = "admin", clave = "admin";
	public static String nombreC = "CentroSalud";

	public Modelo() {

		Class driver;
		try {
			driver = Class.forName("org.exist.xmldb.DatabaseImpl");

			Database db = (Database) driver.newInstance();
			DatabaseManager.registerDatabase(db);

			Collection padre = DatabaseManager.getCollection(url, usuario, clave);
			col = padre.getChildCollection(nombreC);

			if (col == null) {// No existe la collection
				CollectionManagementService servicio = (CollectionManagementService) padre
						.getService("CollectionManagementService", "1.0");

				col = servicio.createCollection(nombreC);
				// Subiendo los recursos de la collection
				File fichero = new File("consultas.xml");
				Resource recurso = col.createResource(fichero.getName(), "XMLResource");

				recurso.setContent(fichero);

				col.storeResource(recurso);

				fichero = new File("medicos.xml");
				recurso = col.createResource(fichero.getName(), "XMLResource");

				recurso.setContent(fichero);

				col.storeResource(recurso);

				fichero = new File("pacientes.xml");
				recurso = col.createResource(fichero.getName(), "XMLResource");

				recurso.setContent(fichero);

				col.storeResource(recurso);
			}

		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cerrarConexion() {
		try {
			this.col.close();
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Collection getCol() {
		return this.col;
	}

	public void setCol(Collection col) {
		this.col = col;
	}

	public boolean comprobarPaciente(String dni) {
		boolean res = false;

		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");

			ResourceSet rs = consulta.query("//paciente[@dni='" + dni + "']");

			ResourceIterator i = rs.getIterator();

			if (i.hasMoreResources()) {
				res = true;
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	public boolean altaPaciente(Pacientes p) {
		boolean res = false;

		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");

			consulta.query("update insert <paciente dni='" + p.getDni() + "'><nombre>" + p.getNombre()
					+ "</nombre><fechaN>" + p.getFechaNac() + "</fechaN></paciente> into /pacientes");

			res = true;
		} catch (XMLDBException e) {
			e.printStackTrace();
		}

		return res;
	}

	public boolean comprobarMedicos(int numColegiado) {
		boolean res = false;

		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");

			ResourceSet rs = consulta.query("//medico[@numColegiado='" + String.valueOf(numColegiado) + "']");

			ResourceIterator i = rs.getIterator();

			if (i.hasMoreResources()) {
				res = true;
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	public boolean altaMedico(Medicos m) {
		boolean res = false;

		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");

			consulta.query("update insert <medico numColegiado='" + String.valueOf(m.getNumColegiado()) + "'><nombre>"
					+ m.getNombre() + "</nombre><especilidades/></medico> into /medicos");

			res = true;
		} catch (XMLDBException e) {
			e.printStackTrace();
		}

		return res;
	}

	public void mostrarMedicos() {
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");

			ResourceSet rs = consulta.query("/medicos/medico");

			ResourceIterator i = rs.getIterator();

			while (i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent().toString());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean insertarEspecialidad(Medicos m) {
		boolean res = false;

		try {

			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");

			consulta.query("update insert <especialidad>" + m.getEspecialidad() + "</especialidad> "
					+ "into //medico[@numColegiado='" + String.valueOf(m.getNumColegiado()) + "']/especilidades");

			res = true;
		} catch (XMLDBException e) {
			e.printStackTrace();
		}

		return res;
	}

	public void mostrarPacientes() {
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");

			ResourceSet rs = consulta.query("/pacientes/paciente");

			ResourceIterator i = rs.getIterator();

			while (i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent().toString());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean crearConsulta(String dni, int numColegiado, String fecha) {
		boolean res = false;

		try {

			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");

			consulta.query(
					"update insert <consulta id='" + getIdConsulta() + "'><paciente>" + dni + "</paciente>" + "<medico>"
							+ String.valueOf(numColegiado) + "</medico><fecha>" + fecha + "</fecha><diagnostico/></consulta>" + "into /consultas");

			res = true;
		} catch (XMLDBException e) {
			e.printStackTrace();
		}

		return res;
	}

	private int getIdConsulta() {
		int id = 1;

		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");

			ResourceSet rs = consulta.query("string(/consultas/consulta[last()]/@id)");

			ResourceIterator i = rs.getIterator();

			if (i.hasMoreResources()) {
				String res = i.nextResource().getContent().toString();
				if (!res.equals("")) {
					id = Integer.parseInt(res) + 1;
				}
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id;
	}

	public void mostrarConsultas() {
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");

			ResourceSet rs = consulta.query("//consulta");

			ResourceIterator i = rs.getIterator();

			while (i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent().toString());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void mostrarConsultas(int id) {
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");

			ResourceSet rs = consulta.query("for $c in /consultas/consulta[@id=" + String.valueOf(id) + "],\r\n" + 
					"    $p in /pacientes/paciente[@dni=$c/paciente],\r\n" + 
					"    $m in /medicos/medico[@numColegiado=$c/medico]\r\n" + 
					"    return <consulta id=\"{data($c/@id)}\">\r\n" + 
					"    <paciente nif=\"{data($c/paciente)}\">{data($p/nombre)}</paciente>\r\n" + 
					"    <medico numColegiado=\"{data($c/medico)}\">{data($m/nombre)}</medico>\r\n" + 
					"    <fecha>{data($c/fecha)}</fecha>\r\n" + 
					"    <diagnostico>{data($c/diagnostico)}</diagnostico>\r\n" + 
					"    </consulta>");

			ResourceIterator i = rs.getIterator();

			while (i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent().toString());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean comprobarConsulta(int id) {
		boolean res = false;

		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");

			ResourceSet rs = consulta.query("//consulta[@id='" + String.valueOf(id) + "']");

			ResourceIterator i = rs.getIterator();

			if (i.hasMoreResources()) {
				res = true;
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	public boolean actualizarDiagnostico(int id, String diagnostico) {
		boolean res = false;
		
		try {
			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			consulta.query("update replace //consulta[@id='" + String.valueOf(id) + "']/diagnostico"
					+ " with <diagnostico>" + diagnostico + "</diagnostico>");
			res = true;
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return res;
	}

	public boolean borrarConsulta(int id) {
		boolean res = true;

		try {

			XPathQueryService consulta = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			consulta.query("update delete " + "//consulta[@id='" + id + "']");

		} catch (XMLDBException e) {
			res = false;
			e.printStackTrace();
		}

		return res;
	}

}
