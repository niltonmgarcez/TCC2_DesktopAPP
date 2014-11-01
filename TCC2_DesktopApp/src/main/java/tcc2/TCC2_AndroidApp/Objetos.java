/**
 * 
 */
package tcc2.TCC2_AndroidApp;

import java.awt.Color;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class Objetos {
	
	String textUri = "mongodb://niltongarcez:trabalho2@ds047440.mongolab.com:47440/tcc2_data";
	MongoClientURI uri = new MongoClientURI(textUri);
	MongoClient mongoClient = new MongoClient(uri);
	DB db = mongoClient.getDB("tcc2_data");
	DBCollection objetos = db.getCollection("Objetos");
	BasicDBObject documento = new BasicDBObject();
	ArrayList<Objeto> listaObjetos = new ArrayList<Objeto>();

	Pattern pattern = Pattern.compile("\"(.*?)\"");

	/**
	 * 
	 */
	public Objetos() throws UnknownHostException {
		DBCursor cursor = objetos.find();
		while(cursor.hasNext())
		{
			DBObject objeto = cursor.next();
			String identificacaoAmbiente = objeto.get("identificacao do ambiente").toString();
			String identificacao = objeto.get("identificacao do objeto").toString();
			float posX = Float.parseFloat(objeto.get("posicao em X").toString());
			float posY = Float.parseFloat(objeto.get("posicao em Y").toString());
			String descricao = objeto.get("descricao").toString();
			int criticidade = Integer.parseInt(objeto.get("criticidade").toString());
			
			listaObjetos.add(new Objeto(identificacaoAmbiente, identificacao, posX, posY, descricao, criticidade));
		}
	}
	
	public ArrayList<Objeto> getListaAmbientes()
	{
		return listaObjetos;
	}
	
	public Objeto getObjeto(String identificacao)
	{
		Objeto ambiente = null;
		for (int i = 0; i < listaObjetos.size(); i++)
		{
			if (listaObjetos.get(i).getIdentificacao().equals(identificacao))
			{
				ambiente = listaObjetos.get(i);
			}
		}		
		return ambiente;		
	}
		
	public static void main(String[] args) throws UnknownHostException {
		
		Objetos obj = new Objetos();
		Objeto o = obj.getObjeto("Conjunto de Cadeiras");
		System.out.println(o.getDescricao());
	}
}
