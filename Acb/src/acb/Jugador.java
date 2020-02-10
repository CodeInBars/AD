package acb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Jugador implements Serializable{
	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigo;
	
	@ManyToOne
	@JoinColumn(name="equipo", referencedColumnName = "nombre")
	private Equipo equipo;
	@Column(nullable = false)
	private int dorsal;
	@Column(nullable = false)
	private String nombre;
	
	@Column(columnDefinition = "enum('P', 'B', 'A', 'E') not null")
	private String tipo;
	
	@OneToMany(cascade = CascadeType.MERGE,mappedBy = "jugador")
	private List<Accion> acciones = new ArrayList<Accion>();
	
	public Jugador() {
		super();
	}
	public Jugador(int codigo, Equipo equipo, int dorsal, String nombre, String tipo) {
		super();
		this.codigo = codigo;
		this.equipo = equipo;
		this.dorsal = dorsal;
		this.nombre = nombre;
		this.tipo = tipo;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Equipo getEquipo() {
		return equipo;
	}
	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}
	public int getDorsal() {
		return dorsal;
	}
	public void setDorsal(int dorsal) {
		this.dorsal = dorsal;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	public List<Accion> getAcciones() {
		return acciones;
	}
	public void setAcciones(ArrayList<Accion> acciones) {
		this.acciones = acciones;
	}
	public void mostrar() {
		System.out.println("Código:" + codigo + 
				"\tEquipo:"+equipo.getNombre() + 
				"\tDorsal" + dorsal + 
				"\tNombre" + nombre + 
				"\tTipo" + tipo);
	}
}
