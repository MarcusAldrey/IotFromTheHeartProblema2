package util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Cloud {
	private ServerSocket server;
	
	public Cloud() throws IOException, ClassNotFoundException{
		System.out.println("iniciando servidor");
		this.server = new ServerSocket(12345);
		while(true){
			System.out.println("Aguardando cliente se conectar...");
			Socket socket = this.server.accept();
			ThreadServerDeBorda a = new ThreadServerDeBorda(socket);
			a.start();
			System.out.println("Novo cliente adicionado");
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		new ServerDeBorda();
	}

}
