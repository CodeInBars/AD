package Ismael;
import java.util.ArrayList;
import java.util.Date;

public class Cajas {

	private String codigo;
	private Date fecha = new Date();
	private ArrayList<Regalos> regalos = new ArrayList<Regalos>();
	private float precio;
	
	public Cajas() {
		
	}

	public Cajas(String codigo, Date fecha, ArrayList<Regalos> regalos, float precio) {
		
		this.codigo = codigo;
		this.fecha = fecha;
		this.regalos = regalos;
		this.precio = precio;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ArrayList<Regalos> getRegalos() {
		return regalos;
	}

	public void setRegalos(ArrayList<Regalos> regalos) {
		this.regalos = regalos;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	
}
