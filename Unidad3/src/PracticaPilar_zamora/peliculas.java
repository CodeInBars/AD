package PracticaPilar_zamora;

public class peliculas {
	String id;
	String titulo;
	int duracion;
	public peliculas() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public void mostrar(){
		System.out.println("id:"+ id + 
				"\tTitulo:"+ titulo+
				"\tDuracion:"+ duracion);
		            
		
	}
	public void setCodigo(Object obtenerCodigoPelicula) {
		// TODO Auto-generated method stub
		
	}
	

}
