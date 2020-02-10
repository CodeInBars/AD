package Ivan;

import java.util.Scanner;

public class Principal {
	
	static Scanner sc = new Scanner(System.in);
	static Modelo chat = new Modelo();
	static String userName;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(chat.getCol()!=null) {
			
			int opcion = 0;
			
			do {
				
				System.out.println("0-Salir");
				System.out.println("1-Registrarse");
				System.out.println("2-Iniciar sesión");
				opcion = sc.nextInt(); sc.nextLine();
				
				switch(opcion) {
				case 0:
					System.out.println("Finalizado");
					break;
				case 1:
					System.out.println("Introduce nombre de usuario: ");
					userName = sc.nextLine();
					System.out.println("Introduce tu nombre: ");
					String nombre = sc.nextLine();
					System.out.println("Introduce contraseña: ");
					String pass = sc.nextLine();
					//Comprobar que los campos no estén vacíos o sólo contengan espacios en blanco
					if(!userName.isBlank() && !pass.isBlank() && !nombre.isBlank()) {
						//Registrar usuario si el nombre no existe
						if(chat.registrarUsuario(userName, nombre, pass)) {
							System.out.println("Usuario registrado");
						}
						else {
							System.out.println("Error, no se ha registrado el usuario");
						}
					}
					else {
						System.out.println("Campos vacíos");
					}
					break;
				case 2:
					System.out.println("Introduce nombre de usuario: ");
					userName = sc.nextLine();
					System.out.println("Introduce contraseña: ");
					pass = sc.nextLine();
					
					if(chat.iniciarSesion(userName, pass)) {
						System.out.println("Sesión iniciada");
						menuUsuario();
					}
					else {
						System.out.println("Datos incorrectos");
					}
					
					break;
				}
				
			}while(opcion!=0);
			
			chat.cerrar();
			
		}
		else {
			System.out.println("ERROR");
		}

	}

	private static void menuUsuario() {
		// TODO Auto-generated method stub
		int option = 0;
		
		do {
			
			System.out.println("0-Salir");
			System.out.println("1-Enviar mensaje");
			System.out.println("2-Ver mensajes recibidos");
			System.out.println("3-Ver mensajes enviados");
			System.out.println("4-Borrar mensaje enviado");
			System.out.println("5-Modificar contraseña");
			System.out.println("6-Modificar nombre");
			option = sc.nextInt(); sc.nextLine();
			
			switch(option) {
			case 0:
				System.out.println("Volviendo a menú principal");
				break;
			case 1:
				chat.mostrarUsuarios(userName);
				System.out.println("Introduce destinatario: ");
				String destinatario = sc.nextLine();
				System.out.println("Introduce mensaje: ");
				String mensaje = sc.nextLine();
				if(!destinatario.isBlank() && !mensaje.isBlank()) {
					//Comprobar que existe el usuario de destino
					if(chat.existeNick(destinatario)) {
						if(chat.enviarMensaje(userName, destinatario, mensaje)) {
							System.out.println("Mensaje enviado");
						}
						else {
							System.out.println("Error, no se ha enviado el mensaje");
						}
					}
					else {
						System.out.println("Destinatario no encontrado");
					}
					
				}
				else {
					System.out.println("ERROR, campos vacíos");
				}
				
				break;
			case 2:
				chat.mensajesRecibidos(userName);
				break;
			case 3:
				chat.mensajesEnviados(userName);
				break;
			case 4:
				chat.mensajesEnviados(userName);
				System.out.println("Introduce ID del mensaje: ");
				int id = sc.nextInt(); sc.nextLine();
				//Comprobar que ese mensaje pertenece al usuario
				if(chat.comprobarMensaje(id).equals(userName)) {
					//Si es suyo se borra
					if(chat.borrarMensaje(id)) {
						System.out.println("Mensaje borrado");
					}
					else {
						System.out.println("Error al borrar mensaje");
					}
				}
				else {
					System.out.println("No se ha encontrado ese mensaje");
				}
				
				break;
			case 5:
				System.out.println("Introduce nueva contraseña: ");
				String nuevaPass = sc.nextLine();
				System.out.println("Vuelve a introducirla: ");
				String nuevaClave = sc.nextLine();
				if(nuevaPass.equals(nuevaClave)) {
					if(chat.modificarClave(userName, nuevaPass)) {
						System.out.println("Contraseña modificada");
					}
					else {
						System.out.println("Error, no se ha modificado la contraseña");
					}
				}
				else {
					System.out.println("Las contraseñas no coinciden");
				}
				break;
			case 6:
				System.out.println("Introduce nuevo nombre: ");
				String nombre = sc.nextLine();
				if(!nombre.isBlank()) {
					if(chat.modificarNombre(userName, nombre)) {
						System.out.println("Nombre modificado");
					}
					else {
						System.out.println("Error, no se han modificado tus datos");
					}
				}
				else {
					System.out.println("Error, campo vacío");
				}
			}
			
		}while(option!=0);
	}

}
