package acb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tipoaccion")
public class TipoAccion implements Serializable{
	@Column(nullable = false)
	@Id
	private String tipo;
	
	@Column(name="descrip", nullable = false)
	private String descripcion;
	
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "tipo")
	private List<Accion> acciones = new ArrayList<Accion>();
	
	public TipoAccion() {
		super();
	}
	public TipoAccion(String tipo, String descripcion) {
		super();
		this.tipo = tipo;
		this.descripcion = descripcion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void mostrar() {
		System.out.println("Tipo Acción:"+tipo+" " + descripcion );
	}
	
	public List<Accion> getAcciones() {
		return acciones;
	}
	public void setAcciones(ArrayList<Accion> acciones) {
		this.acciones = acciones;
	}
	
}
