package util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Cloud {
	private ServerSocket server;
	
	public Cloud() throws IOException, ClassNotFoundException{
		System.out.println("Insira o número da porta: ");
		Scanner scanner = new Scanner(System.in);
		int porta = scanner.nextInt();
		scanner.close();
		System.out.println("iniciando cloud...");
		this.server = new ServerSocket(porta);
		while(true){
			System.out.println("Aguardando cliente se conectar...");
			Socket socket = this.server.accept();
			ThreadCloud a = new ThreadCloud(socket);
			a.start();
			System.out.println("Novo cliente adicionado");
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		new Cloud();
	}

}
