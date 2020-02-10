package ExplicacionJaxB;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
//Anotación para indicar que este es el elemento raíz
@XmlRootElement
//Anotación para indicar el orden en el que 
//aparecen los elementos
@XmlType(propOrder = {"nombre","piezas"})
public class Taller {
	private String nombre;
	private ArrayList<Pieza> piezas = new ArrayList<Pieza>();
	
	public Taller() {
		
	}
	//Anotación para indicar el elemento simple nombre
	//El atributo name permite cambiar el nombre en el 
	//xml. Es opcional, si coincide con el nombre del 
	//atributo de la clase no es necesario ponerlo
	@XmlElement(name="nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	//Anotaciones para hacer un elemento que contiene una lista
	//de otro elemento
	//Elemento lista
	@XmlElementWrapper
	//Elmento contenido en la lista. Es necesario poner el
	//name ya que si no, pone el nombre del elemento lista
	@XmlElement(name="pieza")
	public ArrayList<Pieza> getPiezas() {
		return piezas;
	}

	public void setPiezas(ArrayList<Pieza> piezas) {
		this.piezas = piezas;
	}
	
	public void mostrar() {
		System.out.println("Nombre:" + nombre);
		System.out.println("Piezas:");
		for(Pieza p:piezas) {
			p.mostrar();
		}
	}
}
