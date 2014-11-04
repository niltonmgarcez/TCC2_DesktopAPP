/**
 * 
 */
package Android;

/**
 * @author Nilton Mena Garcez
 *
 */
public class Beacon {

	float posX;
	float posY;
	int minor;
	int major;
	String identificacao;
	String ambiente;
	
	public Beacon(float X, float Y, int minorRec, int majorRec, String ident, String amb)
	{
		posX = X;
		posY = Y;
		int minor = minorRec;
		int major = majorRec;
		identificacao = ident;
		ambiente = amb;
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

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}
}
