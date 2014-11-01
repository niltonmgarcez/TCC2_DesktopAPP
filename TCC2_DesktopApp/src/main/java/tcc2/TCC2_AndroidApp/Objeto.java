/**
 * 
 */
package tcc2.TCC2_AndroidApp;

/**
 * @author Nilton Mena Garcez
 *
 */
public class Objeto {

	String identificacaoAmbiente;
	String identificacao;
	float posX;
	float posY;
	String descricao;
	int criticidade;
	/**
	 * @param identificacaoAmbiente
	 * @param identificacao
	 * @param posX
	 * @param posY
	 * @param descricao
	 * @param criticidade
	 */
	public Objeto(String identificacaoAmbiente, String identificacao,
			float posX, float posY, String descricao, int criticidade) {
		super();
		this.identificacaoAmbiente = identificacaoAmbiente;
		this.identificacao = identificacao;
		this.posX = posX;
		this.posY = posY;
		this.descricao = descricao;
		this.criticidade = criticidade;
	}
	public String getIdentificacaoAmbiente() {
		return identificacaoAmbiente;
	}
	public void setIdentificacaoAmbiente(String identificacaoAmbiente) {
		this.identificacaoAmbiente = identificacaoAmbiente;
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getCriticidade() {
		return criticidade;
	}
	public void setCriticidade(int criticidade) {
		this.criticidade = criticidade;
	}
	
	
}
