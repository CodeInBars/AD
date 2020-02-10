package gimnasio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Modelo {
	private Connection conexion = null;
	private String urlBd ="jdbc:mysql://localhost:3306/gimnasio?serverTimezone=Europe/Madrid",
			usuario = "gimnasio",
			clave = "gimnasio";
	
	public Modelo() {
		
		try {
			//Cargar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Creamos la conexión
			conexion = DriverManager.getConnection(urlBd, usuario, clave);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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


	public void cerrar() {
		try {
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Devuelve el tipo de usuario si el usario exsite
	//Si no existe devuelve NE
	public String comprobarUS(String us, String cl) {
		// TODO Auto-generated method stub
		String resultado = "";
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("select tipo "
							+ "from usuarios "
							+ "where usuario = ? and "
							+ "clave = sha2(?,224)");
			sentencia.setString(1, us);
			sentencia.setString(2, cl);
			
			ResultSet r = sentencia.executeQuery();
			
			if(r.next()) {
				resultado = r.getString(1);
			}
			else {
				System.out.println("Error: Usuario no existe");
				resultado = "NE";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}


	public void mostrarClientes() {
		// TODO Auto-generated method stub
		try {
			Statement sentencia = conexion.createStatement();
			ResultSet r = sentencia.executeQuery("select * from cliente");
			while(r.next()) {
				Cliente c = new 
				Cliente(r.getInt(1), 
						new Usuario(r.getString(2), null), 
						r.getString(3), r.getString(4), 
						r.getString(5), r.getString(6), r.getBoolean(7));
				c.mostrar();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	public boolean existeUS(String usuario) {
		// TODO Auto-generated method stub
		boolean resultado=false;
		
		try {
			PreparedStatement sentencia = conexion.prepareStatement
					("select * from usuarios where usuario=?");
			sentencia.setString(1, usuario);
			ResultSet r = sentencia.executeQuery();
			if(r.next()) {
				resultado=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}

	//Devuelve -1 si se produce error
	//si no, devuelve el id del cliente creado
	public int insertarCliente(Cliente c) {
		// TODO Auto-generated method stub
		int resultado = -1;
		
		try {
			//Hay que actualizar dos tablas, por lo tanto usamos transacciones
			conexion.setAutoCommit(false);
			//Insert en tabla usuarios
			PreparedStatement sentencia = 
					conexion.prepareStatement("insert into usuarios values "
							+ "(?,sha2(?,224),?)");
			sentencia.setString(1, c.getUsuario().getUsuario());
			sentencia.setString(2, c.getUsuario().getUsuario());
			sentencia.setString(3, c.getUsuario().getTipo());
			
			int r = sentencia.executeUpdate();
			if(r==1) {
				sentencia = conexion.prepareStatement(
						"insert into cliente values "
						+ "(null,?,?,?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
				sentencia.setString(1, c.getUsuario().getUsuario());
				sentencia.setString(2, c.getDni());
				sentencia.setString(3, c.getApellidos());
				sentencia.setString(4, c.getNombre());
				sentencia.setString(5, c.getTelefono());
				sentencia.setBoolean(6, false);
				r = sentencia.executeUpdate();
				if(r==1) {
					conexion.commit();
					//Devolvemos el id del cliente creado
					ResultSet ids = sentencia.getGeneratedKeys();
					if(ids.next()) {
						resultado = ids.getInt(1);
					}
				}
				else {
					conexion.rollback();
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}


	public Cliente obtenerCliente(int id) {
		// TODO Auto-generated method stub
		Cliente resultado = null;
		
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("select * from cliente "
							+ "where id = ?");
			sentencia.setInt(1, id);
			ResultSet r = sentencia.executeQuery();
			if(r.next()) {
				resultado = new Cliente(r.getInt(1),
						new Usuario(r.getString(2),null),
						r.getString(3),
						r.getString(4),
						r.getString(5),
						r.getString(6),
						r.getBoolean(7));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}


	public boolean modificarCliente(Cliente c) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("update cliente "
							+ "set nombre = ?, apellidos=?, tfno_contacto = ? "
							+ "where id = ?");
			sentencia.setString(1, c.getNombre());
			sentencia.setString(2, c.getApellidos());
			sentencia.setString(3, c.getTelefono());
			sentencia.setInt(4, c.getId());
			
			if(sentencia.executeUpdate()==1) {
				resultado=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}


	public boolean bajaCliente(Cliente c) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("update cliente "
							+ "set baja = ? "
							+ "where id = ?");
			sentencia.setBoolean(1, true);
			sentencia.setInt(2, c.getId());
			
			if(sentencia.executeUpdate()==1) {
				resultado=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}


	public void mostrarActividadesCliente(String usuario) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("select * "
							+ "from cliente c join participa p "
							+ "on c.id = p.cliente_id "
							+ "join actividad a "
							+ "on a.id = p.actividad_id "
							+ "where usuario = ?");
			sentencia.setString(1, usuario);
			ResultSet r = sentencia.executeQuery();
			while(r.next()) {
				Actividad a = new Actividad(r.getInt(10), r.getString(11), 
						r.getString(13), r.getFloat(12)); 
				a.mostrar();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void mostrarActivides() {
		// TODO Auto-generated method stub
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("select * "
							+ "from actividad ");
			ResultSet r = sentencia.executeQuery();
			while(r.next()) {
				Actividad a = new Actividad(r.getInt(1), r.getString(2), 
						r.getString(4), r.getFloat(3)); 
				a.mostrar();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Actividad obtenerActividad(int id) {
		// TODO Auto-generated method stub
		Actividad resultado = null;
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("select * from actividad "
							+ "where id = ?");
			sentencia.setInt(1, id);
			ResultSet r = sentencia.executeQuery();
			if(r.next()) {
				resultado = new Actividad(r.getInt(1), r.getString(2), 
						r.getString(4), r.getFloat(3)); 
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}


	public boolean estaInscirto(String usuario, Actividad a) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("select * "
							+ "from cliente c join participa p "
							+ "on c.id = p.cliente_id "
							+ "where c.usuario = ? "
							+ "and p.actividad_id = ?");
			sentencia.setString(1, usuario);
			sentencia.setInt(2, a.getId());
			ResultSet r = sentencia.executeQuery();
			if(r.next()) {
				resultado=true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}


	public boolean inscribirEnActividad(String usuario, Actividad a) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("insert into participa "
							+ "values (?,(select id "
							+ "from cliente where usuario = ?))");
			sentencia.setInt(1, a.getId());
			sentencia.setString(2, usuario);
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


	public boolean borrarActividadCliente(String usuario2, Actividad a) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("delete from participa "
							+ "where actividad_id = ? and "
							+ "cliente_id = (select id from cliente "
							+ "where usuario = ?)");
			sentencia.setInt(1, a.getId());
			sentencia.setString(2, usuario2);
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


	public void mostrarRecibosCliente(String usuario2) {
		// TODO Auto-generated method stub
		try {
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
			
			PreparedStatement sentencia = 
					conexion.prepareStatement("select * from recibo r join cliente c "
							+ "on r.cliente_id = c.id "
							+ "where c.usuario = ?");
			sentencia.setString(1, usuario2);
			ResultSet r = sentencia.executeQuery();
			while(r.next()) {
				
				java.util.Date fechaPago=null;
				if(r.getDate(3) == null) {
					fechaPago = formato.parse("31-12-9999");
				}
				else {
					fechaPago = new java.util.Date(r.getDate(3).getTime());
				}
				
				Recibo recibo = new Recibo(new Cliente(r.getInt(6), 
													new Usuario(r.getString(7), null), 
													r.getString(8), 
													r.getString(9), 
													r.getString(10), 
													r.getString(11), 
													r.getBoolean(12)), 
						new java.util.Date(r.getDate(2).getTime()), 
						fechaPago,
						r.getFloat(4), 
						r.getBoolean(5));
				recibo.mostrar();
			}
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mostrarRecibosCliente(int cliente) {
		// TODO Auto-generated method stub
		try {
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
			
			PreparedStatement sentencia = 
					conexion.prepareStatement("select * from recibo r join cliente c "
							+ "on r.cliente_id = c.id "
							+ "where c.id = ? and r.pagado = false");
			sentencia.setInt(1, cliente);
			ResultSet r = sentencia.executeQuery();
			while(r.next()) {
				
				java.util.Date fechaPago=null;
				if(r.getDate(3) == null) {
					fechaPago = formato.parse("31-12-9999");
				}
				else {
					fechaPago = new java.util.Date(r.getDate(3).getTime());
				}
				
				Recibo recibo = new Recibo(new Cliente(r.getInt(6), 
													new Usuario(r.getString(7), null), 
													r.getString(8), 
													r.getString(9), 
													r.getString(10), 
													r.getString(11), 
													r.getBoolean(12)), 
						new java.util.Date(r.getDate(2).getTime()), 
						fechaPago,
						r.getFloat(4), 
						r.getBoolean(5));
				recibo.mostrar();
			}
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean generarRecibos(int mes, int anio) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		
	try {
		CallableStatement rutina  = 
				conexion.prepareCall("{?= call generar_recibos(?, ?)}");
		rutina.registerOutParameter(1, java.sql.Types.INTEGER);
		rutina.setInt(2, mes);
		rutina.setInt(3, anio);
		
		rutina.executeUpdate();
		
		int r = rutina.getInt(1);
		if(r==1 || r==0) {
			resultado = true;
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		
		return resultado;
	}


	public void mostrarRecibos(int mes, int anio) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("select * from recibo r "
							+ "join cliente c "
							+ "on r.cliente_id = c.id "
							+ "where fecha_emision = ?");
			
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
			java.util.Date fecha = formato.parse("01-"+mes+"-"+anio);
			sentencia.setDate(1, new java.sql.Date(fecha.getTime()));
			
			ResultSet r = sentencia.executeQuery();
			while(r.next()) {
				java.util.Date fechaPago=null;
				if(r.getDate(3) == null) {
					fechaPago = formato.parse("31-12-9999");
				}
				else {
					fechaPago = new java.util.Date(r.getDate(3).getTime());
				}
				
				Recibo recibo = new Recibo(new Cliente(r.getInt(6), 
						new Usuario(r.getString(7), null), 
						r.getString(8), 
						r.getString(9), 
						r.getString(10), 
						r.getString(11), 
						r.getBoolean(12)), 
						new java.util.Date(r.getDate(2).getTime()), 
						fechaPago,
						r.getFloat(4), 
						r.getBoolean(5));
				recibo.mostrar();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public boolean pagarRecibo(Cliente c, java.util.Date fecha) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("update recibo "
							+ "set fecha_pago = ?,"
							+ "pagado = ? "
							+ "where cliente_id = ? and "
							+ "fecha_emision = ?");
			sentencia.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			sentencia.setBoolean(2, true);
			sentencia.setInt(3, c.getId());
			sentencia.setDate(4, new java.sql.Date(fecha.getTime()));
			
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


	public void mostrarRecibos(int anio) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("select sum(cuantia), c.id, c.dni "
							+ "from recibo r join cliente c "
							+ "on r.cliente_id = c.id "
							+ "where year(fecha_emision) = ? and "
							+ "pagado = true "
							+ "group by c.id" 
							 );
			sentencia.setInt(1, anio);
			ResultSet r = sentencia.executeQuery();
			float total = 0;
			while(r.next()) {
				System.out.println("Id Cliente:" + r.getInt(2)+
						"\nDNI:"+r.getString(3) + 
						"\tTotal:"+r.getFloat(1));
				total += r.getFloat(1);
			}
			System.out.println("Total Año:"+total);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
