package gimnasio;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Recibo {
	private Cliente cliente_id;
	private Date fecha_emision, fecha_pago;
	private float cuantia;
	private boolean pagado;
	
	public Recibo() {
		
	}

	public Recibo(Cliente cliente_id, Date fecha_emision, Date fecha_pago, float cuantia, boolean pagado) {
		super();
		this.cliente_id = cliente_id;
		this.fecha_emision = fecha_emision;
		this.fecha_pago = fecha_pago;
		this.cuantia = cuantia;
		this.pagado = pagado;
	}

	public Cliente getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Cliente cliente_id) {
		this.cliente_id = cliente_id;
	}

	public Date getFecha_emision() {
		return fecha_emision;
	}

	public void setFecha_emision(Date fecha_emision) {
		this.fecha_emision = fecha_emision;
	}

	public Date getFecha_pago() {
		return fecha_pago;
	}

	public void setFecha_pago(Date fecha_pago) {
		this.fecha_pago = fecha_pago;
	}

	public float getCuantia() {
		return cuantia;
	}

	public void setCuantia(float cuantia) {
		this.cuantia = cuantia;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}
	
	
	public void mostrar() {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy;");
		System.out.println("Cliente:"+  cliente_id.getNombre() + 
				" " + cliente_id.getApellidos() +
		"\tFecha Emision:" + formatoFecha.format(fecha_emision)+ 
		"\tFecha Pago:"+formatoFecha.format(fecha_pago) + 
		"\tCuantia:"+  cuantia +
		"\tPagado"+ pagado);
	}
	
}
