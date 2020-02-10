package gimnasio;

public class Actividad {
	private int id;
	private String nombre, activa;
	private float coste_mensual;
	
	public Actividad() {
		
	}

	public Actividad(int id, String nombre, String activa, float coste_mensual) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.activa = activa;
		this.coste_mensual = coste_mensual;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getActiva() {
		return activa;
	}

	public void setActiva(String activa) {
		this.activa = activa;
	}

	public float getCoste_mensual() {
		return coste_mensual;
	}

	public void setCoste_mensual(float coste_mensual) {
		this.coste_mensual = coste_mensual;
	}
	
	public void mostrar() {
		System.out.println("Id:"+ id +
		"\tNombre:" + nombre + 
		"\tActiva:"+activa + 
		"\tCoste mensual:"+ coste_mensual);

	}
}	
