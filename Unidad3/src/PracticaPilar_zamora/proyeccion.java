package PracticaPilar_zamora;

import java.util.ArrayList;

import tiendaInformatica.Componente;

public class proyeccion {
int idPelicula;
String hora;
public proyeccion(int idPelicula, String hora) {
	super();
	this.idPelicula = idPelicula;
	this.hora = hora;
}
public proyeccion() {
	super();
}
public int getIdPelicula() {
	return idPelicula;
}
public void setIdPelicula(int idPelicula) {
	this.idPelicula = idPelicula;
}
public String getHora() {
	return hora;
}
public void setHora(String hora) {
	this.hora = hora;
}
public void mostrar() {
	System.out.println("idPelicula:"+ idPelicula + 
			"\tHora:"+ hora);
	
	            
}



}
