package Vicente;

import java.util.ArrayList;
import java.util.Date;

public class Medicamentos {

	private String codigo;
	private Date fecha=new Date();
	private ArrayList<Producto>medicamento=new ArrayList<>();
	
	public Medicamentos() {
		
	}

	public Medicamentos(String codigo, Date fecha, ArrayList<Producto> medicamento) {
		this.codigo = codigo;
		this.fecha = fecha;
		this.medicamento = medicamento;
	}

	protected String getCodigo() {
		return codigo;
	}

	protected void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	protected Date getFecha() {
		return fecha;
	}

	protected void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	protected ArrayList<Producto> getMedicamento() {
		return medicamento;
	}

	protected void setMedicamento(ArrayList<Producto> medicamento) {
		this.medicamento = medicamento;
	}

	
	
	
}
