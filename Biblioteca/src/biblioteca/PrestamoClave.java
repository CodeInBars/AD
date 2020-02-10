package biblioteca;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class PrestamoClave implements Serializable{
	//Definimos la clave externa
	@ManyToOne
	//Indicamos el nombre del campo ya que si no se pone un nombre
	//que concatena el texto id
	//Indicamos el atributo de la clase Libro que contiene la 
	// clave primaria de un libro
	@JoinColumn(name="libro", referencedColumnName = "isbn")
	private Libro libro;
	@ManyToOne
	@JoinColumn(name="socio", referencedColumnName = "id")
	
	private Socio socio;
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fechaP;
	public PrestamoClave() {
		super();
	}
	public PrestamoClave(Libro libro, Socio socio, Date fechaP) {
		super();
		this.libro = libro;
		this.socio = socio;
		this.fechaP = fechaP;
	}
	public Libro getLibro() {
		return libro;
	}
	public void setLibro(Libro libro) {
		this.libro = libro;
	}
	public Socio getSocio() {
		return socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}
	public Date getFechaP() {
		return fechaP;
	}
	public void setFechaP(Date fechaP) {
		this.fechaP = fechaP;
	}
	
	
}
