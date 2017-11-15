package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import control.ControllerCloud;

public class ThreadCloud extends Thread{
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	public ThreadCloud(Socket socket) throws IOException {
		// TODO Auto-generated constructor stub
		this.setSocket(socket);
		this.input = new ObjectInputStream(socket.getInputStream());
		this.output = new ObjectOutputStream(socket.getOutputStream());
	}

	public void enviarMensagem(Object object) throws IOException{
		output.writeObject(object);
	}

	public Object receberMensagem() throws ClassNotFoundException, IOException{
		return input.readObject();
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ObjectInputStream getInput() {
		return input;
	}

	public void setInput(ObjectInputStream input) {
		this.input = input;
	}

	public ObjectOutputStream getOutput() {
		return output;
	}

	public void setOutput(ObjectOutputStream output) {
		this.output = output;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true) {
				String mensagem = (String) input.readObject();
				System.out.println("Recebeu " + mensagem);
				String[] mensagemDividida = mensagem.split(","); //Divide a mensagem onde tem v�rgula
				/*Caso a mensagem venha de um servidor de borda*/

				if(mensagemDividida[0].equals("connect server")) {

					if(mensagemDividida[1].equals("info") && mensagemDividida[2].equals("todos")) {
						for(int i = 0; i < mensagemDividida.length; i++) {
							
						}
					}

					else {

						String IP = mensagemDividida[1];
						int porta = Integer.parseInt(mensagemDividida[2]);
						int x = Integer.parseInt(mensagemDividida[3]);
						int y = Integer.parseInt(mensagemDividida[4]);
						String infoServerDeBorda = IP + "," + porta + "," + x + "," + y+ ",";

						/*adiciona o servidor de borda � lista de servidores da cloud*/
						ControllerCloud.getInstance().adicionarServerDeBorda(infoServerDeBorda);
					}
				}
				else if(mensagemDividida[0].equals("connect sensor")) {

					if(mensagemDividida.length != 4) {
						System.out.println("Mensagem inv�lida recebida"); //Se a mensagem possuir a estrutura errada ela n�o ser� processada
						return;
					}

					if(mensagemDividida[1].equals("closest server")) {
						int x = Integer.parseInt(mensagemDividida[2]);
						int y = Integer.parseInt(mensagemDividida[3]);
						String serverMaisProximo = ControllerCloud.getInstance().getServerMaisProximo(x, y);
						output.writeObject(serverMaisProximo);
						System.out.println("server mais proximo esta em: " + serverMaisProximo);
					}
				}
			}
		}   catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
