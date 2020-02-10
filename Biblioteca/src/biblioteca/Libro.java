package biblioteca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//Marcamos la clase como tabla
//El atributo name es opcional, solamente lo rellenamos cuando
//cambiar el nombre en la bd. Si llaman igual no se rellena
@Entity
@Table(name="libro")
public class Libro  implements Serializable{
	//marcamos los atributos como columna de la tabla libro
	//Se pueden definir opciones como si el campo admite nulos
	//o es clave alternativa
	@Column(name="isbn",nullable = false)
	//Marcamos la columna como clave primaria
	@Id
	private String isbn;
	@Column(nullable = false)
	private int numEjemplares;
	@Column(nullable = false)
	private String titulo;
	//Creamos la relación 1:M con préstamos
	//Se debe especificar qué operaciones se hacen en casacada, si
	//no se especifica no se hacen operaciones en cascada
	//En mapped by debemos indicar el atributo de la clase préstamo
	//sobre el que se crea la clave externa
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id.libro")
	
	private List<Prestamo> prestamos = new ArrayList<Prestamo>();
	public Libro() {
		super();
	}
	public Libro(String isbn, int numEjemplares, String titulo, List<Prestamo> prestamos) {
		super();
		this.isbn = isbn;
		this.numEjemplares = numEjemplares;
		this.titulo = titulo;
		this.prestamos = prestamos;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getNumEjemplares() {
		return numEjemplares;
	}
	public void setNumEjemplares(int numEjemplares) {
		this.numEjemplares = numEjemplares;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public List<Prestamo> getPrestamos() {
		return prestamos;
	}
	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}
	public void mostrar(boolean mostrarP) {
		System.out.println("Isbn:"+isbn + 
				"\tNumEjemplares:"+numEjemplares+
				"\tTitulo:"+titulo);
		if(mostrarP) {
			System.out.println("Préstamos del libro");
			for(Prestamo p:prestamos) {
				p.mostrar();
			}
		}
		
	}
}
