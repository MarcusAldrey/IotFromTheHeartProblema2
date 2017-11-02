package util;

import java.io.IOException;
import java.net.Socket;

public class Sensor {
	
	private Socket cliente;
	
	public Sensor(String IP, int porta) throws IOException{
		System.out.println("Sensor iniciou");
		this.cliente = new Socket(IP,porta);
		System.out.println("conectou viado");
		ThreadCliente a = new ThreadCliente(cliente);
		Thread b = new Thread(a);
		b.start();
	}
}
