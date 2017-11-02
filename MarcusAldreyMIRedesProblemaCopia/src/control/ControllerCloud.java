package control;
import java.util.ArrayList;
import java.util.List;

import model.Paciente;;

public class ControllerCloud {
	List<Paciente> pacientesEmRisco = new ArrayList<Paciente>();
	List<String> infoServers = new ArrayList<String>();    
	
	public String getServerMaisProximo(int x, int y) {
		double distanciaDoMaisProximo = 0;
		String IPdoMaisProximo = "none";
		for(String infos : infoServers) {
			String[] informacoes = infos.split(",");
			String IPAtual = informacoes[0];
			int x0 = Integer.parseInt(informacoes[1]);
			int y0 = Integer.parseInt(informacoes[2]);
			double distanciaAtual = Math.sqrt(Math.pow(x-x0, 2)+Math.pow(y-y0, 2));
			if(distanciaAtual < distanciaDoMaisProximo) {
				IPdoMaisProximo = IPAtual;
				distanciaDoMaisProximo = distanciaAtual;
			}			
		}
		return IPdoMaisProximo;
	}

}
