package gimnasio;

public class Usuario {
		private String usuario,tipo;

		public Usuario(String usuario, String tipo) {
			super();
			this.usuario = usuario;
			this.tipo = tipo;
		}

		public Usuario() {
			
		}

		public String getUsuario() {
			return usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		
		
}
