package ies;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ModeloOR {
	Connection conexion = null;
	private String url = "jdbc:postgresql://localhost:5432/IES";
	private String usuario = "postgres", clave="root";

	public ModeloOR() {
		try {
			Class.forName("org.postgresql.Driver");
			
			conexion = DriverManager.getConnection(url, usuario, clave);
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

	public void mostrarAlumnos() {
		// TODO Auto-generated method stub
		try {
			Statement consulta = conexion.createStatement();
			ResultSet  r = 
					consulta.executeQuery("select  codigo,"
							+ "nombre, (dir).tipoV, "
							+ "(dir).nombreV,"
							+ "fechaM "
							+ "from alumno "
							+ "order by codigo");
			while(r.next()) {
				Alumno a = new Alumno(r.getInt(1), 
						r.getString(2), 
						new Direccion(r.getString(3), r.getString(4), 0, 0), 
						new Date(r.getDate(5).getTime()));
				a.mostrar();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean existeAlumo(Alumno a) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("select * "
							+ "from alumno "
							+ "where codigo = ?");
			sentencia.setInt(1, a.getCodigo());
			ResultSet r = sentencia.executeQuery();
			if(r.next()) {
				resultado = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	public boolean modificarDireccion(Alumno a) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("update alumno "
							+ "set dir = (?,?,?,?) "
							+ "where codigo = ?");
			sentencia.setString(1, a.getDireccion().getTipoV());
			sentencia.setString(2, a.getDireccion().getNombreV());
			sentencia.setInt(3, a.getDireccion().getNumero());
			sentencia.setInt(4, a.getDireccion().getCp());
			sentencia.setInt(5, a.getCodigo());
			int r = sentencia.executeUpdate();
			if(r==1) {
				resultado  = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	public void mostrarNotas(Alumno a) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement sentencia= 
					conexion.prepareStatement("select * "
							+ "from nota "
							+ "where alumno = ?");
			sentencia.setInt(1, a.getCodigo());
			ResultSet r = sentencia.executeQuery();
			while(r.next()) {
				Nota n = new Nota();
				n.setAlumno(new Alumno(r.getInt(1), null, null, null));
				n.setAsig(new Asignatura(r.getString(2), null));
				//Recuperamos el array de notas
				Array notas = r.getArray(3);
				//comprobamos si hay notas ya creadas
				if(notas.getResultSet().next()) {
					String[][] tmp = (String[][]) notas.getArray();
					//Rellenamos el arraylist de notas 
					for(String[] no: tmp) {
						n.getNotas().add(no);
					}
				}
				n.mostrar();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mostrarAsig() {
		// TODO Auto-generated method stub
		try {
			Statement consulta = conexion.createStatement();
			ResultSet  r = 
					consulta.executeQuery("select * "
							+ "from asignatura "
							+ "order by nombreC");
			while(r.next()) {
				Asignatura a = 
						new Asignatura(r.getString(1), 
								r.getString(2));
				a.mostrar();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean existeAsig(Asignatura as) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("select * "
							+ "from asignatura "
							+ "where nombreC = ?");
			sentencia.setString(1, as.getNombreC());
			ResultSet r = sentencia.executeQuery();
			if(r.next()) {
				resultado = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	public boolean matricular(Alumno a, Asignatura as) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			PreparedStatement consulta = 
				conexion.prepareStatement("insert into "
						+ "nota values (?,?, array[array[]]::text[][])");
			consulta.setInt(1,a.getCodigo());
			consulta.setString(2,as.getNombreC());
			
			int ok = consulta.executeUpdate();
			if(ok==1) {
				resultado = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if(e.getSQLState().equals("23505")) {
				System.out.println("Error, ya existe la matrícula");
			}
			else {
				e.printStackTrace();
			}
		}
		return resultado;
	}

	public boolean existeMatricula(Alumno a, Asignatura as) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			PreparedStatement sentencia = 
					conexion.prepareStatement("select * "
							+ "from nota "
							+ "where alumno = ? and "
							+ "asig = ?");
			sentencia.setInt(1, a.getCodigo());
			sentencia.setString(2, as.getNombreC());
			ResultSet r = sentencia.executeQuery();
			if(r.next()) {
				resultado = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	public boolean ponerNota(Nota n) {
		// TODO Auto-generated method stub
		boolean resultado = false;
		try {
			PreparedStatement consulta = 
					conexion.prepareStatement("update nota "
							+ "set notas = "
							+ "array_cat(notas, array[array[?,?]]::text[][]) "
							+ "where alumno = ? and "
							+ "asig = ?");
			consulta.setString(1, n.getNotas().get(0)[0]);
			consulta.setString(2, n.getNotas().get(0)[1]);
			consulta.setInt(3, n.getAlumno().getCodigo());
			consulta.setString(4, n.getAsig().getNombreC());
			int ok = consulta.executeUpdate();
			if(ok==1) {
				resultado = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	public ArrayList<Asignatura> obtenerAsis() {
		// TODO Auto-generated method stub
		ArrayList<Asignatura> resultado = new ArrayList<Asignatura>();
		try {
			Statement consulta = conexion.createStatement();
			ResultSet r = consulta.executeQuery("select * "
					+ "from asignatura");
			while(r.next()) {
				Asignatura a = 
						new Asignatura(r.getString(1), 
								r.getString(2));
				resultado.add(a);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}

	public ArrayList<Alumno> obtenerAlum() {
		// TODO Auto-generated method stub
		ArrayList<Alumno> resultado = new ArrayList<Alumno>();
		try {
			Statement consulta = conexion.createStatement();
			ResultSet r = consulta.executeQuery("select codigo, nombre, "
					+ "(dir).tipoV, (dir).nombreV, (dir).numero, "
					+ "(dir).cp, fechaM "
					+ "from alumno");
			while(r.next()) {
				Alumno a = new Alumno(r.getInt(1), 
						r.getString(2), 
						new Direccion(r.getString(3), 
								r.getString(4), r.getInt(5), r.getInt(6)), 
						new Date(r.getDate(7).getTime()));
				resultado.add(a);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

	public ArrayList<Nota> obtenerNotas() {
		// TODO Auto-generated method stub
		ArrayList<Nota> resultado = new ArrayList<Nota>();
		try {
			Statement consulta = conexion.createStatement();
			ResultSet r = 
					consulta.executeQuery("select * from nota");
			while(r.next()) {
				Nota n = new Nota();
				
				n.setAlumno(
						new Alumno(r.getInt(1), null, null, null));
				
				n.setAsig(new Asignatura(r.getString(2), null));
				
				Array notas = r.getArray(3);
				String[][] aux = (String[][]) notas.getArray();
				
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				for(String[] s: aux) {
					Date f = df.parse(s[0]);
					int nota = Integer.parseInt(s[1]);
					n.getNotas2().add(new NotaExamen(f, nota));
				}
				
				
				resultado.add(n);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
			
}
