package util;

import java.util.Scanner;

import control.ControllerServerDeBorda;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDeBorda {

	private ServerSocket server;
	
	public ServerDeBorda() throws IOException, ClassNotFoundException{
		System.out.print("iniciando servidor de borda... \nInsira o n�mero da porta: ");
		Scanner scanner = new Scanner(System.in);
		int porta = scanner.nextInt();
		scanner.nextLine();
		System.out.print("Insira o IP desta m�quina: ");
		String IP = scanner.nextLine();
		System.out.print("Insira o IP da nuvem: ");
		String IPNuvem = scanner.nextLine();
		System.out.print("Insira a porta da nuvem: ");
		int portaNuvem = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Insira a localiza��o do servidor:");
		System.out.print("x=");
		int x = scanner.nextInt();
		System.out.print("y=");
		int y = scanner.nextInt();
		this.server = new ServerSocket(porta);
		scanner.close();
		ControllerServerDeBorda.getInstance().cadastrarNaNuvem(IPNuvem,portaNuvem, IP, porta, x, y);
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
