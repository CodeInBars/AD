package acb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Partido implements Serializable{
	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigo;
	
	@ManyToOne
	@JoinColumn(name="local", referencedColumnName = "nombre")
	private Equipo local;
	
	@ManyToOne
	@JoinColumn(name="visitante", referencedColumnName = "nombre")
	private Equipo visitante;
	
	@OneToMany(cascade = CascadeType.MERGE,mappedBy = "partido")
	private List<Accion> acciones = new ArrayList<Accion>();
	
	public Partido() {
		super();
	}
	public Partido(int codigo, Equipo local, Equipo visitante) {
		super();
		this.codigo = codigo;
		this.local = local;
		this.visitante = visitante;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Equipo getLocal() {
		return local;
	}
	public void setLocal(Equipo local) {
		this.local = local;
	}
	public Equipo getVisitante() {
		return visitante;
	}
	public void setVisitante(Equipo visitante) {
		this.visitante = visitante;
	}
	
	public List<Accion> getAcciones() {
		return acciones;
	}
	public void setAcciones(List<Accion> acciones) {
		this.acciones = acciones;
	}
	public void mostrar() {
		System.out.println("Codigo:"+codigo + 
				"\tLocal:"+local.getNombre()+
				"\tVisitante:"+visitante.getNombre());
		
	}
	
}
