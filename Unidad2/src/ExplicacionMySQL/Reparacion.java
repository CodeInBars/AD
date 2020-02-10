package ExplicacionMySQL;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reparacion {
	private int codigo;
	private TipoRep tipo;
	private Coche matricula;
	private Date fecha;
	public Reparacion() {
		super();
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public TipoRep getTipo() {
		return tipo;
	}
	public void setTipo(TipoRep tipo) {
		this.tipo = tipo;
	}
	public Coche getMatricula() {
		return matricula;
	}
	public void setMatricula(Coche matricula) {
		this.matricula = matricula;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public void mostrar() {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("-------------------------------------");
		System.out.println("Código:"+codigo+"\tFecha:"+formatoFecha.format(fecha));
		tipo.mostrar();
		System.out.println("Matrícula:"+matricula.getMatricula());
		System.out.println("-------------------------------------");
	}
}
