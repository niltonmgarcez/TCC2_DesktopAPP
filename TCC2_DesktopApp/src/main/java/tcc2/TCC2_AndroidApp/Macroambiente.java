/**
 * 
 */
package tcc2.TCC2_AndroidApp;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * @author Nilton Mena Garcez
 *
 */
public class Macroambiente {

	String textUri = "mongodb://niltongarcez:trabalho2@ds047440.mongolab.com:47440/tcc2_data";
	MongoClientURI uri = new MongoClientURI(textUri);
	MongoClient mongoClient = new MongoClient(uri);
	DB db = mongoClient.getDB("tcc2_data");
	DBCollection predio = db.getCollection("Macroambiente");
	BasicDBObject documento = new BasicDBObject();
	
	String identificacao;
	String descricao;
	ArrayList<Ambiente> listaAmbientes = new ArrayList<Ambiente>();
	int andares;
	Ambientes ambientes = new Ambientes();
	/**
	 * @param identificacao
	 * @param descricao
	 * @param listaAmbientes
	 * @param andares
	 * @throws UnknownHostException 
	 */
	public Macroambiente() throws UnknownHostException {
		DBCursor cursor = predio.find();
		DBObject objeto = cursor.next();
		identificacao = objeto.get("nome").toString();
		descricao = objeto.get("descricao").toString();
		listaAmbientes = ambientes.getListaAmbientes();
		andares = Integer.parseInt(objeto.get("andares").toString());		
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
	public Ambiente getAmbiente(String Ambiente)
	{
		return ambientes.getAmbiente(Ambiente);
	}
	
	public static void main(String[] args) throws UnknownHostException {
		
		Macroambiente mamb = new Macroambiente();
		System.out.println(mamb.getIdentificacao());
		System.out.println(mamb.getDescricao());
		for (int i = 0; i < mamb.getListaAmbientes().size(); i++)
		{
			System.out.println(mamb.getListaAmbientes().get(i).getIdentificacao().toString());
		}
		System.out.println(mamb.getAndares());

	}
}
