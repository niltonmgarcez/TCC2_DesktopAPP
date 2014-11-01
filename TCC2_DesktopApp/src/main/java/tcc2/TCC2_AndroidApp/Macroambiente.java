/**
 * 
 */
package tcc2.TCC2_AndroidApp;

import java.util.ArrayList;

/**
 * @author Nilton Mena Garcez
 *
 */
public class Macroambiente {

	String identificacao;
	String descricao;
	ArrayList<Ambiente> listaAmbientes = new ArrayList<Ambiente>();
	int andares;
	/**
	 * @param identificacao
	 * @param descricao
	 * @param listaAmbientes
	 * @param andares
	 */
	public Macroambiente(String identificacao, String descricao,
			ArrayList<Ambiente> listaAmbientes, int andares) {
		super();
		this.identificacao = identificacao;
		this.descricao = descricao;
		this.listaAmbientes = listaAmbientes;
		this.andares = andares;
	}
	public String getIdentificacao() {
		return identificacao;
	}
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public ArrayList<Ambiente> getListaAmbientes() {
		return listaAmbientes;
	}
	public void setListaAmbientes(ArrayList<Ambiente> listaAmbientes) {
		this.listaAmbientes = listaAmbientes;
	}
	public int getAndares() {
		return andares;
	}
	public void setAndares(int andares) {
		this.andares = andares;
	}
	
	
}
