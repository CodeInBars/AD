package biblioteca;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table
public class Socio  implements Serializable{
	@Column(nullable = false)
	@Id
	//Indicamos que la clave es automunérica
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, unique = true)
	private String nif;
	@Column(nullable = false)
	private String nombre;
	@OneToMany(mappedBy = "id.socio")
	private List<Prestamo> prestamos = new ArrayList<Prestamo>();
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date fechaSancion;
	
	public Socio() {
		super();
	}
	public Socio(int id, String nif, String nombre,List<Prestamo> prestamos) {
		super();
		this.id = id;
		this.nif = nif;
		this.nombre = nombre;
		this.prestamos = prestamos;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<Prestamo> getPrestamos() {
		return prestamos;
	}
	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}
	
	public void mostrar(boolean mostrarP) {
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		String f = "";
		if(fechaSancion!=null) {
			f=formato.format(fechaSancion);
		}
		System.out.println("ID:"+id + 
				"\tNIF:"+nif+
				"\tNombre:"+nombre+
				"\tFecha Sanción:" + f);
		if(mostrarP) {
			System.out.println("Préstamos del libro");
			for(Prestamo p:prestamos) {
				p.mostrar();
			}
		}
	}
	public Date getFechaSancion() {
		return fechaSancion;
	}
	public void setFechaSancion(Date fechaSancion) {
		this.fechaSancion = fechaSancion;
	}
}	
