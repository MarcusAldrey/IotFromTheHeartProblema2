package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadCliente implements Runnable {

	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	public ThreadCliente(Socket socket) throws IOException{
		// TODO Auto-generated constructor stub
		this.setSocket(socket);
		this.output = new ObjectOutputStream(socket.getOutputStream());
		this.input = new ObjectInputStream(socket.getInputStream());
		
	}
	
	public void enviarMensagem(Object object) throws IOException{
		output.writeObject(object);
	}
	
	public Object receberMensagem() throws ClassNotFoundException, IOException{
		return input.readObject();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
