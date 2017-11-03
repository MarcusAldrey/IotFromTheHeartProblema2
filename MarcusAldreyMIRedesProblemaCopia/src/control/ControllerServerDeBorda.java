package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import model.Medico;
import model.Paciente;

public class ControllerServerDeBorda {
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private int porta;
	private String ip;
	private String ipNuvem;
	private int portaNuvem;
	private int x;
	private int y;
	private static ControllerServerDeBorda instance;
	private List<Paciente> pacientes;

	private ControllerServerDeBorda() {
		pacientes = new ArrayList<Paciente>();
	}
	
	public void cadastrarNaNuvem(String ipNuvem, int portaNuvem, String IP, int porta, int x, int y) throws UnknownHostException, IOException {
		criarConexao(ipNuvem, portaNuvem);
		output.writeObject("connect server,"+IP+","+porta+","+x+","+y);
	}

	public static ControllerServerDeBorda getInstance(){
		if(instance == null)
			instance = new ControllerServerDeBorda();
		return instance;		 
	}

	public void enviarMensagem(String mensagem) throws IOException {
		output.writeObject(mensagem);
		output.flush();
	}

	public void adicionarPaciente(Paciente novo) {
		pacientes.add(novo);
		ordenarListaPacientes();
		System.out.println("Paciente " + novo.getNome() + " adicionado à lista");
	}

	public boolean alterarPaciente(String nome, int sistole, int diastole, int frequencia, String emMovimento) {
		for(Paciente paciente : pacientes) {
			if(paciente.getNome().equals(nome)) {
				paciente.setSistole(sistole);
				paciente.setDiastole(diastole);
				paciente.setFrequencia(frequencia);
				paciente.setEmMovimento(emMovimento);
				ordenarListaPacientes();
				return true;
			}
		}
		return false;
	}

	public void removerPaciente(String nome) {
		for(Paciente paciente : pacientes) {
			if(paciente.getNome().equals(nome))
				pacientes.remove(paciente);
		}		
	}

	public void criarConexao(String IP, int porta) throws IOException {
		socket = new Socket(IP,porta);
		output = new ObjectOutputStream(socket.getOutputStream());
		input = new ObjectInputStream(socket.getInputStream());
	}

	public Object receberMensagem() throws ClassNotFoundException, IOException{
		return input.readObject();
	}

	/**
	 * @return the pacientes
	 */
	public List<Paciente> getPacientes() {
		return pacientes;
	}

	/**
	 * @param pacientes the pacientes to set
	 */
	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	private void ordenarListaPacientes(){
		Collections.sort(pacientes);
	}

}
