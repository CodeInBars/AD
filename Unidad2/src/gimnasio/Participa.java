package gimnasio;

public class Participa {
	private Actividad actividad_id;
	private Cliente cliente_id;
	public Participa() {
		
	}
	public Participa(Actividad actividad_id, Cliente cliente_id) {
		super();
		this.actividad_id = actividad_id;
		this.cliente_id = cliente_id;
	}
	public Actividad getActividad_id() {
		return actividad_id;
	}
	public void setActividad_id(Actividad actividad_id) {
		this.actividad_id = actividad_id;
	}
	public Cliente getCliente_id() {
		return cliente_id;
	}
	public void setCliente_id(Cliente cliente_id) {
		this.cliente_id = cliente_id;
	}
	
	
}
