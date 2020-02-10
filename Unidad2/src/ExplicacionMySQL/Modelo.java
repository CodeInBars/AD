package ExplicacionMySQL;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import FicherosDeObjetos.ExplicacionFO;

public class Modelo {
	private Connection conexion = null;
	//private String url = "jdbc:mysql://localhost:3306/taller?serverTimezone=UTC";
	//String de conexión si queremos ejecutar scripts
	private String 
	url = "jdbc:mysql://localhost:3306/taller?allowMultiQueries=true&serverTimezone=UTC";
	private String usuario = "root";
	private String clave = "root";

	public Modelo() {
		try {
			//Cargamos el driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Creamos la conexión a la BD
			conexion = DriverManager.getConnection(url, usuario, clave);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error al cargar el driver");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public void infoServidor() {
		// TODO Auto-generated method stub
		
		try {
			//Obtener metadatos
			DatabaseMetaData metadatos= conexion.getMetaData();
			System.out.println("Sistema gestor de BD " + metadatos.getDatabaseProductName());
			System.out.println("Versión " + metadatos.getDatabaseProductVersion());
			
			System.out.println("Bases de datos ");
			ResultSet bds = metadatos.getCatalogs();
			while(bds.next()) {
				//Mostramos el dato que hay en la 1ª columna del resultset
				//Hay que ver la ayuda ya que cada ResultSet tiene una estructura
				//En este caso hay una única columna con el nombre de cada BD
				System.out.println(bds.getString(1));
			}
			System.out.println("Tablase de la BD");
			//El método necesita 4 parámetros -> ver la ayuda
			//1.- La BD de la que queremos recuperar las tablas o null para todas las bd
			//2.- Patrón con el nombre del esquema o null para todos los esquemas
			//3.- Patrón con el nombre de las tablas que queremos recuperar o null para todas las tablas
			//4.- Tipo de tabla o null para todos los tipos de tablas
			ResultSet tablas = metadatos.getTables("taller", null, null, null);
			while(tablas.next()) {
				//Mostramos la columna 3 del result set que es donde está
				//el nombre de la tabla -> Ver ayuda del método
				System.out.println(tablas.getString(3));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void verCamposTabla(String tabla) {
		// TODO Auto-generated method stub
		//Obtenemos los metadatos
		try {
			DatabaseMetaData metadatos = conexion.getMetaData();
			//Obtenemos los campos de la tabla
			ResultSet campos = metadatos.getColumns("taller", null, tabla, null);
			while(campos.next()) {
				System.out.println("Campo:"+ campos.getString(4)+
						"Tipo SQL:"+campos.getInt(5)+
						"Tipo:"+campos.getString(6) + 
						"Tamaño:"+campos.getInt(7));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void verCamposConsulta(String consulta) {
		// TODO Auto-generated method stub
		try {
			//Declaramos la consulta a ejecutar
			Statement c = conexion.createStatement();
			//Ejecutamos la consulta
			ResultSet resultado = c.executeQuery(consulta);
			//Obtenemos metadatos
			ResultSetMetaData metadatos= resultado.getMetaData();
			System.out.println("Columnas:"+metadatos.getColumnCount());
			//DAtos de las columnas
			for(int i=1;i<=metadatos.getColumnCount();i++) {
				System.out.println("Campo:"+ metadatos.getColumnName(i)+
						"\tTipo:"+metadatos.getColumnTypeName(i)+
						"\tTabla:"+metadatos.getTableName(i));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarPiezas() {
		// TODO Auto-generated method stub
		ObjectInputStream fichero=null;
		String consulta = "insert into pieza values ";
		boolean okConsulta = false;
		try {
			
			fichero = new ObjectInputStream(new FileInputStream("Almacen.obj"));
			while(true) {
				okConsulta = true;
				
				FicherosDeObjetos.Pieza p = (FicherosDeObjetos.Pieza) fichero.readObject();
				consulta+="(null,'"+p.getNombre()+"',"+p.getPrecio()+
						","+p.getStock()+","+p.isAlta()+"),";
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
		if(okConsulta) {
			try {
				//Quitar la última , de la consulta
				consulta = consulta.substring(0, consulta.length()-1);
				System.out.println(consulta);
				Statement sentencia = conexion.createStatement();
				int ok = sentencia.executeUpdate(consulta);
				System.out.println("Se han creado " + ok + "piezas");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void mostrarPiezas() {
		// TODO Auto-generated method stub
		Statement sentencia;
		try {
			sentencia = conexion.createStatement();
			ResultSet r = sentencia.executeQuery("select * from pieza");
			
			while(r.next()) {
				Pieza p = new Pieza();
				p.setCodigo(r.getInt(1));
				p.setNombre(r.getString(2));
				p.setPrecio(r.getFloat(3));
				p.setStock(r.getInt(4));
				p.setAlta(r.getBoolean(5));
				p.mostrar();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void estadisticaPieza() {
		// TODO Auto-generated method stub
		try {
			//Creamos una sentecia con un parámetro en el where
			//Los parámetros se ponen con ?
			PreparedStatement sentencia = 
					conexion.prepareStatement("select count(*), max(precio), "
							+ "avg(precio) "
							+ "from pieza "
							+ "where alta = ?");
			//Antes de ejecutar hay que rellenar los parámetros
			sentencia.setBoolean(1, true);
			//Ejecutamos la sentencia
			ResultSet r = sentencia.executeQuery();
			//mostrar datos
			//Esta consulta lo máximo que devuelve es 1 fila por lo que 
			//podemos usar un if en vez de un while
			if(r.next()) {
				System.out.println("Nº de piezas:"+r.getInt(1)+
						"\tPrecio Máximo:"+r.getFloat(2)+
						"\tPrecio Medio:"+r.getFloat(3));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void mostrarTiposRep() {
		// TODO Auto-generated method stub
		Statement sentencia;
		try {
			sentencia = conexion.createStatement();
			ResultSet r = sentencia.executeQuery("select * from tiporep");
			
			while(r.next()) {
				TipoRep t = new TipoRep();
				t.setCodigo(r.getInt(1));
				t.setNombre(r.getString(2));
				
				t.mostrar();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mostrarCoches() {
		// TODO Auto-generated method stub
		Statement sentencia;
		try {
			sentencia = conexion.createStatement();
			ResultSet r = sentencia.executeQuery("select * "
					+ "from coche join cliente "
					+ "on cliente=dni");
			
			while(r.next()) {
				Coche c = new Coche();
				c.setMatricula(r.getString(1));
				c.setMarca(r.getString(2));
				c.setModelo(r.getString(3));
				c.setCliente(new Cliente());
				c.getCliente().setDni(r.getString(4));
				c.getCliente().setNombre(r.getString(6));
				c.mostrar();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int crearReparacion(Reparacion r) {
		// TODO Auto-generated method stub
		int resultado = -1;
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("insert into reparacion values "
					+ "(null,?,?,?)",Statement.RETURN_GENERATED_KEYS);
					//Rellenamos parámetros
					sentencia.setInt(1, r.getTipo().getCodigo());
					sentencia.setString(2, r.getMatricula().getMatricula());
					sentencia.setDate(3, new java.sql.Date(r.getFecha().getTime()));
					//Ejecutamos sentencia
					sentencia.executeUpdate();
					ResultSet codigos = sentencia.getGeneratedKeys();
					//Devolvemos el código de la reparación creada
					if(codigos.next()) {
						resultado=codigos.getInt(1);
					}
						
					
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	public void mostrarReparaciones() {
		// TODO Auto-generated method stub
		Statement sentencia;
		try {
			sentencia = conexion.createStatement();
			ResultSet r = sentencia.executeQuery("select * "
					+ "from reparacion r join tiporep t "
					+ "on r.tipo = t.codigo");
			while(r.next()) {
				Reparacion rep = new Reparacion();
				rep.setCodigo(r.getInt(1));
				rep.setTipo(new TipoRep());
				rep.getTipo().setCodigo(r.getInt(2));
				rep.getTipo().setNombre(r.getString(6));
				rep.setMatricula(new Coche());
				rep.getMatricula().setMatricula(r.getString(3));
				rep.setFecha(new java.util.Date(r.getDate(4).getTime()));
				rep.mostrar();
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean insertarPiezaRep(PiezaReparacion pieza) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		
		try {
			//Chequeamos que existe la repación
			PreparedStatement sentencia = 
					conexion.prepareStatement("select * from reparacion "
							+ "where codigo = ?");
			sentencia.setInt(1, pieza.getReparacion().getCodigo());
			ResultSet r = sentencia.executeQuery();
			if(r.next()) {
				//Chequeamos y obtenemos los datos de la pieza
				sentencia = 
						conexion.prepareStatement("select * from pieza "
								+ "where codigo = ?");
				sentencia.setInt(1, pieza.getPieza().getCodigo());
				r = sentencia.executeQuery();
				if(r.next()) {
					//Chequeamos que haya suficiente cantidad
					if(r.getInt(4)>=pieza.getCantidad()) {
						//Iniciamos transacción
						conexion.setAutoCommit(false);
						//Insertamos reparación
						sentencia = 
								conexion.prepareStatement("insert into "
										+ "piezareparacion values "
										+ "(?,?,?,?)");
						sentencia.setInt(1, pieza.getPieza().getCodigo());
						sentencia.setInt(2, pieza.getReparacion().getCodigo());
						sentencia.setFloat(3, r.getFloat(3));
						sentencia.setInt(4, pieza.getCantidad());
						int numReg = sentencia.executeUpdate();
						if(numReg==1) {
							//Modificamos el stock de la pieza
							sentencia = 
								conexion.prepareStatement("update pieza set "
										+ "stock = stock - ? "
										+ "where codigo = ?");
							sentencia.setInt(1, pieza.getCantidad());
							sentencia.setInt(2, pieza.getPieza().getCodigo());
							numReg=sentencia.executeUpdate();
							if(numReg==1) {
								conexion.commit();
								resultado=true;
							}
							else {
								conexion.rollback();
							}
							
						}
						else {
							System.out.println("Error: No se puede añadir la "
									+ "pieza a la reparación");
						}
						
					}
					else {
						System.out.println("Error: Stock insuficiente");
					}
				}
				else {
					System.out.println("Error: No existe la pieza");
				}
			}
			else {
				System.out.println("Error: No existe la reparación");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}

	public void ejecutarScript() {
		// TODO Auto-generated method stub
		//Cargamos en un String el script que está en un fichero
		BufferedReader f = null;
		try {
			f = new BufferedReader(new FileReader("scriptUsuario.sql"));
			String linea;
			String script = "";
			while((linea = f.readLine())!=null) {
				script+=linea+"\n";
			}
			if(script.equals("")) {
				System.out.println("Error: No se ha cargado el script");
			}
			else {
				System.out.println(script);
				Statement sentencia = conexion.createStatement();
				sentencia.executeUpdate(script);
				//System.out.println(r);
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(f!=null) {
				try {
					f.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public boolean login(String us, String clave2) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		
		//Preparamos un String con la llamada a la función
		//Se ponene entre {}
		//Al ser una función debe empezar por ?=
		//Depués call nombreFuncion(parámetros)	
		String consulta = "{? = call validarUS(?,?)}";
		
		try {
			//Creamos la sentencia
			CallableStatement sentencia = conexion.prepareCall(consulta);
			//Registramos parámetros de entrada
			sentencia.setString(2, us);
			sentencia.setString(3, clave2);
			//Registramos el tipo del parámetro de salida
			sentencia.registerOutParameter(1, java.sql.Types.INTEGER);
			//Ejecutamos la sentencia
			sentencia.executeUpdate();
			//Recuperamos el valor devuelto por la función
			if(sentencia.getInt(1)==0) {
				resultado = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}

	public void cerrarConexión() {
		// TODO Auto-generated method stub
		try {
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void borrar() {
		// TODO Auto-generated method stub
		String consulta = "{call borrarTablas()}";
		
		try {
			CallableStatement sentencia = conexion.prepareCall(consulta);
			ResultSet r = sentencia.executeQuery();
			if(r.next()) {
				System.out.println(r.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean modificarCliente(Cliente c) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("update cliente "
							+ "set nombre = ?, telefono = ? "
							+ "where dni = ?");
			sentencia.setString(1, c.getNombre());
			sentencia.setString(2, c.getTelefono());
			sentencia.setString(3, c.getDni());
			
			int r = sentencia.executeUpdate();
			if(r==1) {
				resultado=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	
	
}
