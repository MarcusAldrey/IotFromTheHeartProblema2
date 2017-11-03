package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

import control.ControllerMedico;
import control.ControllerServerDeBorda;
import exceptions.MensagemInvalidaException;
import model.Paciente;

public class ThreadServerDeBorda extends Thread {

	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	public ThreadServerDeBorda(Socket socket) throws IOException {
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true) {
				String mensagem = (String) input.readObject();
				System.out.println("Recebeu " + mensagem);
				String[] mensagemDividida = mensagem.split(","); //Divide a mensagem onde tem vírgula

				/*Caso a mensagem venha de um sensor*/
				if(mensagemDividida[0].equals("connect sensor")) {
					if(mensagemDividida.length != 6) {
						System.out.println("Mensagem inválida recebida"); //Se a mensagem possuir a estrutura errada ela não será processada
						return;
					}
					String nome = mensagemDividida[1];
					int sistole = Integer.parseInt(mensagemDividida[2]);
					int diastole = Integer.parseInt(mensagemDividida[3]);
					int frequencia = Integer.parseInt(mensagemDividida[4]);
					String emMovimento = mensagemDividida[5];

					/*Caso o paciente já esteja na lista, os valores serão atualizados*/
					boolean estaNaLista = ControllerServerDeBorda.getInstance().alterarPaciente(nome, sistole, diastole, frequencia, emMovimento);

					/*Caso o paciente não esteja na lista, será adicionado*/
					if(estaNaLista == false) {
						Paciente novo = new Paciente(nome, frequencia, sistole, diastole, emMovimento);
						ControllerServerDeBorda.getInstance().adicionarPaciente(novo);
					}
				}

				/*Caso a mensagem venha de um médico*/
				else if(mensagemDividida[0].equals("connect medico")) {
					/* Caso a mensagem seja de um médico, será verificado se ele quer todos os pacientes ou algum especifico */
					if(mensagemDividida[1].equals("info")) {
						/* Caso queira todos, recebera uma String com o nome de todos os pacientes separados por vírgula */
						if(mensagemDividida[2].equals("todos")) {
							String todosOsPacientes = "";
							List<Paciente> pacientes = ControllerServerDeBorda.getInstance().getPacientes();
							Iterator<Paciente> iterator = pacientes.iterator();
							while(iterator.hasNext()) {
								Paciente paciente = (Paciente) iterator.next();
								String nomePacienteAtual = paciente.getNome();
								todosOsPacientes = todosOsPacientes.concat(nomePacienteAtual+",");					
							}
							System.out.println(todosOsPacientes);
							output.writeObject(todosOsPacientes);
						}
						/* Caso queira algum especifico, receberá uma String com os dados do paciente separado por vírgula */
						else {
							List<Paciente> pacientes = ControllerServerDeBorda.getInstance().getPacientes();
							Iterator<Paciente> iterator = pacientes.iterator();
							boolean pacienteEncontrado = false;
							while(iterator.hasNext()) {
								Paciente paciente = (Paciente) iterator.next();
								if(paciente.getNome().equals(mensagemDividida[2])) {
									output.writeObject(paciente.getSistole()+","+paciente.getDiastole()+","+paciente.getFrequencia()+","+paciente.isEmMovimento());
									pacienteEncontrado = true;
									break;
								}
							}
							/* Se toda lista for percorrida e não for encontrado paciente, é enviado uma mensagem de erro ao medico */
							if(pacienteEncontrado == false)
								output.writeObject("paciente nao encontrado");
						}
					}
				}
				/*Caso a mensagem seja de desconexão, o sensor é removido da lista*/
				else if(mensagemDividida[0].equals("disconnect sensor")) {
					String nome = mensagemDividida[1];
					ControllerServerDeBorda.getInstance().removerPaciente(nome);
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}


	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	
}
