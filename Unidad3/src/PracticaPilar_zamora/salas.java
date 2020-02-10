package PracticaPilar_zamora;

import java.util.ArrayList;

public class salas {

int numero;
private ArrayList<proyeccion> proyecciones = new ArrayList<proyeccion>();
public salas() {
	super();
}
public salas(String id, int numero) {
	super();
	
	this.numero = numero;
}
public void mostrar() {
	System.out.println("Numero:"+numero);
}
public int getNumero() {
	return numero;
}
public void setNumero(int numero) {
	this.numero = numero;
}
public ArrayList<proyeccion> getProyecciones() {
	return proyecciones;
}
public void setProyecciones(ArrayList<proyeccion> proyecciones) {
	this.proyecciones = proyecciones;
}
public static boolean existeSalas() {
	// TODO Auto-generated method stub
	return false;
}














}
