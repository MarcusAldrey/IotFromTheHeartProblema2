package model;

import java.io.Serializable;

public class Paciente implements Comparable<Object>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private int frequencia;
	private int sistole;
	private int diastole;
	private String emMovimento;
	
	
	
	public Paciente(String nome, int frequencia, int sistole, int diastole, String emMovimento) {
		super();
		this.nome = nome;
		this.frequencia = frequencia;
		this.sistole = sistole;
		this.diastole = diastole;
		this.emMovimento = emMovimento;
	}

	@Override
	public String toString(){
		return this.nome;
	}
	
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(this.getFrequencia() > ((Paciente) o).getFrequencia())
			return 1;
		else if(this.getFrequencia() < ((Paciente) o).getFrequencia())
			return -1;
		return 0;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the frequencia
	 */
	public int getFrequencia() {
		return frequencia;
	}

	/**
	 * @param frequencia the frequencia to set
	 */
	public void setFrequencia(int frequencia) {
		this.frequencia = frequencia;
	}

	/**
	 * @return the sistole
	 */
	public int getSistole() {
		return sistole;
	}

	/**
	 * @param sistole the sistole to set
	 */
	public void setSistole(int sistole) {
		this.sistole = sistole;
	}

	/**
	 * @return the diastole
	 */
	public int getDiastole() {
		return diastole;
	}

	/**
	 * @param diastole the diastole to set
	 */
	public void setDiastole(int diastole) {
		this.diastole = diastole;
	}

	/**
	 * @return the emMovimento
	 */
	public String isEmMovimento() {
		return emMovimento;
	}

	/**
	 * @param emMovimento the emMovimento to set
	 */
	public void setEmMovimento(String emMovimento) {
		this.emMovimento = emMovimento;
	}
	
}
