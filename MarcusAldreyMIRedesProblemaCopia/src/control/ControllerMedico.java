package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import exceptions.MedicoNaoEncontradoException;
import exceptions.MensagemInvalidaException;
import exceptions.PacienteNaoEncontradoException;
import model.Paciente;

public class ControllerMedico {

	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private static ControllerMedico instance;
	private List<Paciente> pacientes;

	private ControllerMedico() {

	}

	/**
	 * Cria um socket com o servidor
	 * @param IP
	 * @param porta
	 * @throws IOException
	 */
	public void criarConexao(String IP, int porta) throws IOException {
		socket = new Socket(IP,porta);
		output = new ObjectOutputStream(socket.getOutputStream());
		input = new ObjectInputStream(socket.getInputStream());
	}

	public static ControllerMedico getInstance() {
		if(instance == null)
			instance = new ControllerMedico();
		return instance;
	}

	public Paciente receberInfosDePacientedoServidorDeBorda(String nome) throws IOException, ClassNotFoundException, PacienteNaoEncontradoException, MensagemInvalidaException {
		output.writeObject("connect medico,info,"+nome);
		while(true) {
			String mensagem = (String) input.readObject();
			System.out.println(mensagem);
			if(mensagem.equals("paciente nao encontrado")) {
				throw new PacienteNaoEncontradoException();
			}
			String[] mensagemSeparada = mensagem.split(",");
			if(mensagemSeparada.length != 4)
				throw new MensagemInvalidaException();
			int sistole = Integer.parseInt(mensagemSeparada[0]);
			int diastole = Integer.parseInt(mensagemSeparada[1]);
			int frequencia = Integer.parseInt(mensagemSeparada[2]);
			String emMovimento = mensagemSeparada[3];
			return new Paciente(nome, frequencia, sistole, diastole, emMovimento);
		}
	}
	
	public void conectarAoServidorDeBorda(String idPaciente) throws IOException, MensagemInvalidaException, PacienteNaoEncontradoException, ClassNotFoundException {
		output.writeObject("connect medico,location,"+idPaciente);
		while(true) {
			String mensagem = (String) input.readObject();
			System.out.println(mensagem);
			if(mensagem.equals("paciente nao encontrado")) {
				throw new PacienteNaoEncontradoException();
			}
			String[] mensagemSeparada = mensagem.split(",");
			if(mensagemSeparada.length != 2)
				throw new MensagemInvalidaException();
			String Ip = mensagemSeparada[0];
			int porta  = Integer.parseInt(mensagemSeparada[1]);
			criarConexao(Ip, porta);
		}
	}

	/**
	 * @return the pacientes
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public String[] getPacientes() throws IOException, ClassNotFoundException {
		output.writeObject("connect medico,info,todos");
		while(true) {
			String mensagem[] = ((String) input.readObject()).split(",");
			return mensagem;
		}
	}

	/**
	 * @param pacientes the pacientes to set
	 */
	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

}
