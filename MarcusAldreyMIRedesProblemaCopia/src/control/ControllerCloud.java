package control;
import java.util.ArrayList;
import java.util.List;

import model.Paciente;;

public class ControllerCloud {
	private List<String> pacientesEmRisco = new ArrayList<String>();
	private List<String> infoServers = new ArrayList<String>();   
	private static ControllerCloud instance;
	
	public static ControllerCloud getInstance(){
		if(instance == null)
			instance = new ControllerCloud();
		return instance;		 
	}
	
	public void adicionarServerDeBorda(String infosServer) {
		infoServers.add(infosServer);
	}
	
	public String getServerMaisProximo(int x, int y) {
		double distanciaDoMaisProximo = Double.POSITIVE_INFINITY;
		String IPdoMaisProximo = "none";
		int portaDoMaisProximo = 0;
		for(String infos : infoServers) {
			System.out.println(infos);
			String[] informations = infos.split(",");
			String IPAtual = informations[0];
			int portaAtual = Integer.parseInt(informations[1]);
			int x0 = Integer.parseInt(informations[2]);
			int y0 = Integer.parseInt(informations[3]);
			double distanciaAtual = Math.sqrt(Math.pow(x-x0, 2)+Math.pow(y-y0, 2));
			System.out.println(distanciaAtual);
			if(distanciaAtual < distanciaDoMaisProximo) {
				IPdoMaisProximo = IPAtual;
				portaDoMaisProximo = portaAtual;
				distanciaDoMaisProximo = distanciaAtual;
			}			
		}
		if(!IPdoMaisProximo.equals("none"))
			return IPdoMaisProximo + "," + portaDoMaisProximo;
		else
			return "server de borda nao encontrado";
	}
	
	public void adicionarPacienteEmRisco(Paciente paciente) {
		pacientesEmRisco.add(paciente);
	}

}
