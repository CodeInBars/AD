package Vicente;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	private Collection col=null;
	private String url="xmldb:exist://localhost:8080/exist/xmlrpc/db";
	private String usuario="admin";
	private String clave="admin";
	private String nombreC="Farmacia";
	
	public Modelo() {
		//Cargar el driver
				try {
					System.out.println("");
					Class driver = Class.forName("org.exist.xmldb.DatabaseImpl");
					//Crear una instancia de la BD
					Database db = (Database) driver.newInstance();
					//Registrar la BD
					DatabaseManager.registerDatabase(db);
					//Nos conectamos a la coleccion principal del servidor.
					//Si la coleccion a la que accedemos no existe se crea.
					Collection padre=	col=DatabaseManager.getCollection(url, usuario, clave);
					col=padre.getChildCollection(nombreC);
					if(col==null) {
						//Creamos la coleccion TiendaInformatica en la coleccion padre.
						CollectionManagementService servicio=(CollectionManagementService) padre.getService("CollectionManagementService", "1.0");
						col=servicio.createCollection(nombreC);
						
						//CARGAMOS PRIMERO EL FICHERO PIEZAS.XML
						//Cargamos los documentos xml vacios en la coleccion.
						File fichero=new File("medicamentos.xml");
						Resource recurso=col.createResource(fichero.getName(), "XMLResource");
						//Asiganmso el fichero al recurso.
						recurso.setContent(fichero);
						col.storeResource(recurso);
						
						//HACEMOS LO MISMO CON EL FICHERO DE ORDENADORES.XML
						fichero=new File("facturas.xml");
						recurso=col.createResource(fichero.getName(), "XMLResource");
						recurso.setContent(fichero);
						col.storeResource(recurso);
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XMLDBException e) {
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

			protected Collection getCol() {
				return col;
			}

			protected void setCol(Collection col) {
				this.col = col;
			}
			
			
	
	//METODOS.		
/*--------------------------------------------------------------------------------------------*/	
	protected void cerrar() {
				try {
					col.close();
				} catch (XMLDBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	protected boolean agregarMedicamento(Producto pro) {
		// TODO Auto-generated method stub
		boolean resultado=false;
		//Obtenemos el resto de atributos.
		pro.setCodigo(obtenerCodigo());
		pro.setAlta(true);
		try {
			XPathQueryService consulta=(XPathQueryService) col.getService("XPathQueryService", "1.0");
			consulta.query("update insert"
					+"<medicamento codigo='"+pro.getCodigo()+"' alta='"+pro.isAlta()+"'>"
						+"<nombre>"+pro.getNombre()+"</nombre>"
						+"<blisters>"+pro.getNumBlisters()+"</blisters>"
						+"<PesoPastilla>"+pro.getGrPatilla()+"</PesoPastilla>"
						+"<precio>"+pro.getPrecio()+"</precio>"
						+"<stock>"+pro.getStock()+"</stock>"
					+"</medicamento>"
					+ "into /medicamentos");
			resultado=true;
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	private int obtenerCodigo() {
		// TODO Auto-generated method stub
		int resultado=1;
		try {
			XPathQueryService consulta=(XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r=consulta.query("string(//medicamento[last()]/@codigo)");
			ResourceIterator i=r.getIterator();
			if(i.hasMoreResources()) {
				String numero=i.nextResource().getContent().toString();
					if(!numero.equals("")) {
						resultado=Integer.parseInt(numero)+1;
					}
				}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}

	protected void mostrarMedicamentos() {
		// TODO Auto-generated method stub
		try {
			XPathQueryService consulta=(XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r=consulta.query("//medicamento");
			ResourceIterator i=r.getIterator();
			while(i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected boolean existeMedicamento(int codigo) {
		// TODO Auto-generated method stub
		boolean resultado=false;
		try {
			XPathQueryService consulta=(XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r=consulta.query("//medicamento[@codigo='"+codigo+"']");
			ResourceIterator i=r.getIterator();
			if(i.hasMoreResources()) {
				resultado=true;
				}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	protected boolean medicamentoEstaAlta(int codigo) {
		// TODO Auto-generated method stub
		boolean resultado=false;
		try {
			XPathQueryService consulta=(XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r=consulta.query("string(//medicamento[@codigo='"+codigo+"']/@alta)");
				ResourceIterator i=r.getIterator();
				if(i.hasMoreResources()) {
					resultado=Boolean.parseBoolean(i.nextResource().getContent().toString());
					}
			
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	protected void mostrarFacturas(int codigo) {
		// TODO Auto-generated method stub
		try {
			XPathQueryService consulta=(XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r=consulta.query("for $m in /medicamentos/medicamento[@codigo='"+codigo+"'],\r\n" + 
					"$f in /facturas/factura[id_medicamento=$m/@codigo]\r\n" + 
					"return \r\n" + 
					"<medicamneto>\r\n" + 
					"{$f/@fecha}\r\n" + 
					"{$m/nombre}\r\n" + 
					"{$m/stock}\r\n" + 
					"{$f/precio}\r\n" + 
					"{$f/unidades_vendidas}\r\n" + 
					"</medicamneto>");
			ResourceIterator i=r.getIterator();
			while(i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected int obtenerStock(int codigo) {
		// TODO Auto-generated method stub
		int resultado=0;
		try {
			XPathQueryService consulta=(XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r=consulta.query("string(//medicamento[@codigo='"+codigo+"']/stock)");
			ResourceIterator i=r.getIterator();
			if(i.hasMoreResources()) {
				resultado=Integer.parseInt(i.nextResource().getContent().toString());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	
	
	protected boolean venderProducto(int cantidad, int stockProducto,Producto pro) {
		// TODO Auto-generated method stub
		boolean resultado=false;
		try {
			XPathQueryService consulta=(XPathQueryService) col.getService("XPathQueryService", "1.0");
			int result=stockProducto-cantidad;
			if(result==0) {
				consulta.query("update replace "
						+ "//medicamento[@codigo='"+pro.getCodigo()+"']/stock "
								+ "with <stock>"+0+"</stock>");
				
				consulta.query("update replace "
						+ "//medicamento[@codigo='"+pro.getCodigo()+"']/@alta "
								+ "with 'false'");
			}else {
				consulta.query("update replace "
						+ "//medicamento[@codigo='"+pro.getCodigo()+"']/stock "
								+ "with <stock>"+(stockProducto-cantidad)+"</stock>");
			}
			
			int codigoFactura=obtenerCodigoFactura(); //Obtenemos el codigo de la factura.
			SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd");
			Date fecha=new Date();
			 //Le damos formato a al fecha.
			float precioProducto=obtenerPrecio(pro);
			float precioFinal=cantidad*precioProducto; //Precio final de lo que has vendido.
			String texto="update insert "
					+"<factura codigo='"+codigoFactura+"' fecha='"+formato.format(fecha)+"'>"
						+"<id_medicamento>"+pro.getCodigo()+"</id_medicamento>"
						+"<unidades_vendidas>"+cantidad+"</unidades_vendidas>"
						+"<precio>"+precioFinal+"</precio>"
					+"</factura>"
					+ "into /facturas";
			consulta.query(texto);
			
			
			resultado=true;
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	private float obtenerPrecio(Producto pro) {
		float resultado=0;
		try {
			XPathQueryService consulta=(XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r=consulta.query("string(//medicamento[@codigo='"+pro.getCodigo()+"']/precio)");
			ResourceIterator i=r.getIterator();
			if(i.hasMoreResources()) {
				resultado=Float.parseFloat(i.nextResource().getContent().toString());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	private int obtenerCodigoFactura() {
		// TODO Auto-generated method stub
		int resultado=1;
		try {
			XPathQueryService consulta=(XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r=consulta.query("string(/facturas/factura[last()]/@codigo)");
			ResourceIterator i=r.getIterator();
			if(i.hasMoreResources()) {
				String numero=i.nextResource().getContent().toString();
					if(!numero.equals("")) {
						resultado=Integer.parseInt(numero)+1;
					}
				}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	protected boolean borrarMedicamento(int codigo) {
		// TODO Auto-generated method stub
		boolean resultado=true;
		try {
			XPathQueryService consulta=(XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r=consulta.query("update delete "
					+ "/medicamentos/medicamento[@codigo='"+codigo+"']");
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			resultado=false;
			e.printStackTrace();
		}
		return resultado;
	}

	protected void mostrarCodigosMedicamento() {
		// TODO Auto-generated method stub
		try {
			XPathQueryService consulta=(XPathQueryService) col.getService("XPathQueryService", "1.0");
			ResourceSet r=consulta.query("for $m in /medicamentos/medicamento[@codigo]\r\n" + 
					"return <codigo>{$m/@codigo}</codigo>");
			ResourceIterator i=r.getIterator();
			while(i.hasMoreResources()) {
				System.out.println(i.nextResource().getContent());
			}
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	
	
	
	
	
}
