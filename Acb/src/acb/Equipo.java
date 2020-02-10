package acb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Equipo implements Serializable{
	@Column(nullable = false)
	@Id
	private String nombre;
	@Column(nullable = false)
	private String localidad;
	
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "equipo")
	List<Jugador> jugadores = new ArrayList<Jugador>();
	
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "local")
	List<Partido> partidosL = new ArrayList<Partido>();
	
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "visitante")
	List<Partido> partidosV = new ArrayList<Partido>();
	
	public Equipo() {
		super();
	}
	public Equipo(String nombre, String localidad) {
		super();
		this.nombre = nombre;
		this.localidad = localidad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	public List<Jugador> getJugadores() {
		return jugadores;
	}
	public void setJugadores(ArrayList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}
	public List<Partido> getPartidosL() {
		return partidosL;
	}
	public void setPartidosL(ArrayList<Partido> partidosL) {
		this.partidosL = partidosL;
	}
	public List<Partido> getPartidosV() {
		return partidosV;
	}
	public void setPartidosV(List<Partido> partidosV) {
		this.partidosV = partidosV;
	}
	public void mostrar() {
		System.out.println("Nombre:"+nombre + "Localidad:"+localidad);
	}
}
