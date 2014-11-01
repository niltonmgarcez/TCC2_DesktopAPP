/**
 * 
 */
package tcc2.TCC2_AndroidApp;

import java.util.ArrayList;

/**
 * @author Nilton Mena Garcez
 *
 */
public class Ambiente {
	
	String tipo;
	int andar;
	String identificacao;
	float posX;
	float posY;
	String resumo;
	String descricao;
	String listAmbiente;
	/**
	 * @param tipo
	 * @param andar
	 * @param identificacao
	 * @param posX
	 * @param posY
	 * @param resumo
	 * @param descricao
	 * @param listAmbiente
	 */
	public Ambiente(String tipo, int andar, String identificacao, float posX,
			float posY, String resumo, String descricao, String listAmbiente) {
		super();
		this.tipo = tipo;
		this.andar = andar;
		this.identificacao = identificacao;
		this.posX = posX;
		this.posY = posY;
		this.resumo = resumo;
		this.descricao = descricao;
		this.listAmbiente = listAmbiente;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getAndar() {
		return andar;
	}
	public void setAndar(int andar) {
		this.andar = andar;
	}
	public String getIdentificacao() {
		return identificacao;
	}
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	public float getPosX() {
		return posX;
	}
	public void setPosX(float posX) {
		this.posX = posX;
	}
	public float getPosY() {
		return posY;
	}
	public void setPosY(float posY) {
		this.posY = posY;
	}
	public String getResumo() {
		return resumo;
	}
	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getListAmbiente() {
		return listAmbiente;
	}
	public void setListAmbiente(String listAmbiente) {
		this.listAmbiente = listAmbiente;
	}
}
