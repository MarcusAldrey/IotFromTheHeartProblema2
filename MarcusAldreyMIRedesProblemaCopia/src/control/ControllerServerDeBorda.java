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
import java.util.Timer;
import java.util.TimerTask;

import model.Medico;
import model.Paciente;

public class ControllerServerDeBorda {
	private Socket socketCloud;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Timer timer;
	private static ControllerServerDeBorda instance;
	private List<Paciente> pacientes;
	String IPServerDeBorda;

	private ControllerServerDeBorda() {
		pacientes = new ArrayList<Paciente>();
		timer = new Timer();
		timer.schedule(new AtualizaServidor(), 2000,5000);
	}

	public void cadastrarNaNuvem(String ipNuvem, int portaNuvem, String IP, int porta, int x, int y) throws UnknownHostException, IOException {
		criarConexao(ipNuvem, portaNuvem);
		this.IPServerDeBorda = IP;
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
		socketCloud = new Socket(IP,porta);
		output = new ObjectOutputStream(socketCloud.getOutputStream());
		input = new ObjectInputStream(socketCloud.getInputStream());
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

	private class AtualizaServidor extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			/* A cada cinco segundos, a lista de pacientes críticos é atualizada */
			System.out.println("A lista está com " + pacientes.size() +" pacientes");
			String todosOsPacientes = "";
			Iterator<Paciente> iterator = pacientes.iterator();
			while(iterator.hasNext()) {
				Paciente paciente = (Paciente) iterator.next();
				//Verifica se o paciente está com frequência maior que 100 e salva na lista
				if(paciente.getFrequencia() > 100 && !Boolean.parseBoolean(paciente.isEmMovimento())) {
					String nomePacienteAtual = paciente.getNome();
					todosOsPacientes = todosOsPacientes.concat(nomePacienteAtual+",");
				}
			}
			//Caso a lista de pacientes críticos NÃO ESTEJA vazia, ela é enviada para a nuvem 
			if(!todosOsPacientes.equals("")) {
				System.out.println("connect server,info,todos,"+IPServerDeBorda+","+ todosOsPacientes);
				try {
					enviarMensagem("connect server,info,todos,"+IPServerDeBorda+","+ todosOsPacientes);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
