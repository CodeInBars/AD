package hospital;

public class Consultas {
	private int id;
	private Pacientes p;
	private Medicos m;
	private String fecha;
	private String diagnostico;
	public Consultas() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Consultas(int id, Pacientes p, Medicos m, String fecha, String diagnostico) {
		super();
		this.id = id;
		this.p = p;
		this.m = m;
		this.fecha = fecha;
		this.diagnostico = diagnostico;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Pacientes getP() {
		return p;
	}
	public void setP(Pacientes p) {
		this.p = p;
	}
	public Medicos getM() {
		return m;
	}
	public void setM(Medicos m) {
		this.m = m;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getDiagnostico() {
		return diagnostico;
	}
	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}
	@Override
	public String toString() {
		return "Id: " + id + "\tPaciente: " + p + "\tMedico: " + m + "\tFecha: " + fecha + "\tDiagnostico: " + diagnostico;
	}
	
	
}
