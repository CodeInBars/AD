package hospital;

public class Pacientes {
	private String dni;
	private String nombre;
	private String fechaNac;
	public Pacientes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Pacientes(String dni, String nombre, String fechaNac) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.fechaNac = fechaNac;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}
	@Override
	public String toString() {
		return "Dni: " + dni + "\tNombre: " + nombre + "\tFechaNac: " + fechaNac;
	}
	
	
}
