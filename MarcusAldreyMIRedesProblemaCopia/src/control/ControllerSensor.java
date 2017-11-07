package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import exceptions.NenhumServerDeBordaEncontraException;

public class ControllerSensor {

	private static ControllerSensor instance;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	public static ControllerSensor getInstance(){
		if(instance == null)
			instance = new ControllerSensor();
		return instance;		 
	}

	public void enviarMensagem(String mensagem) throws IOException {
		output.writeObject(mensagem);
		output.flush();
	}

	public void criarConexao(String IP, int porta) throws IOException {
		socket = new Socket(IP,porta);
		output = new ObjectOutputStream(socket.getOutputStream());
		input = new ObjectInputStream(socket.getInputStream());
	}

	public String infoServerMaisProximo(String IP, int porta, int x, int y) throws IOException, NenhumServerDeBordaEncontraException, ClassNotFoundException {
		criarConexao(IP,porta);
		output.writeObject("connect sensor,closest server,"+x+","+y);
		while(true) {
			String mensagem = (String) input.readObject();
			System.out.println(mensagem);
			if(mensagem.equals("server de borda nao encontrado"))
				throw new NenhumServerDeBordaEncontraException();
			return mensagem;
		}
	}
}
