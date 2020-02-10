package gimnasio;

public class Cliente {
	private int id;
	private Usuario usuario;
	private String dni,apellidos,nombre,telefono;
	private boolean baja;
	public Cliente() {
		
	}
	public Cliente(int id, Usuario usuario, String dni, String apellidos, String nombre, String telefono, boolean baja) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.dni = dni;
		this.apellidos = apellidos;
		this.nombre = nombre;
		this.telefono = telefono;
		this.baja = baja;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public boolean isBaja() {
		return baja;
	}
	public void setBaja(boolean baja) {
		this.baja = baja;
	}

	public void mostrar() {
		System.out.println("Cliente Id:" + id + "\tUsuario:" + usuario.getUsuario() + 
				"\tDni:" + dni + 
				"\tNombre:" + nombre + " "+apellidos + 
				"\tTelefono:" + telefono + 
				"\tBaja:" + baja);
	}
	
	
}
