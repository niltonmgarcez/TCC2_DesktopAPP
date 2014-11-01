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
public class Ambientes {
	
	String textUri = "mongodb://niltongarcez:trabalho2@ds047440.mongolab.com:47440/tcc2_data";
	MongoClientURI uri = new MongoClientURI(textUri);
	MongoClient mongoClient = new MongoClient(uri);
	DB db = mongoClient.getDB("tcc2_data");
	DBCollection ambientes = db.getCollection("Ambientes");
	BasicDBObject documento = new BasicDBObject();
	ArrayList<Ambiente> listaAmbientes = new ArrayList<Ambiente>();

	Pattern pattern = Pattern.compile("\"(.*?)\"");

	/**
	 * 
	 */
	public Ambientes() throws UnknownHostException {
		DBCursor cursor = ambientes.find();
		while(cursor.hasNext())
		{
			DBObject objeto = cursor.next();
			String tipo = objeto.get("tipo de ambiente").toString();			
			int andar = Integer.parseInt(objeto.get("andar").toString());
			String identificacao = objeto.get("identificacao do ambiente").toString();
			float posX = Float.parseFloat(objeto.get("posicao em X").toString());
			float posY = Float.parseFloat(objeto.get("posicao em Y").toString());
			String resumo = objeto.get("resumo").toString();
			String descricao = objeto.get("descricao").toString();
			
			String ambientes_inter = objeto.get("ambientes interligados").toString();
			Matcher matcher = pattern.matcher(ambientes_inter);
		    String ambientesInterligados = "";
			while (matcher.find()) {  
		        String text = matcher.group(1);		        
		        ambientesInterligados = ambientesInterligados + ";" + text;
			}							
			listaAmbientes.add(new Ambiente(tipo, andar, identificacao, posX, posY, resumo, descricao, ambientesInterligados));
		}
	}
	
	public ArrayList<Ambiente> getListaAmbientes()
	{
		return listaAmbientes;
	}
	
	public Ambiente getAmbiente(String identificacao)
	{
		Ambiente ambiente = null;
		for (int i = 0; i < listaAmbientes.size(); i++)
		{
			if (listaAmbientes.get(i).getIdentificacao().equals(identificacao))
			{
				ambiente = listaAmbientes.get(i);
			}
		}		
		return ambiente;		
	}
		
	public static void main(String[] args) throws UnknownHostException {
				
		Ambientes amb = new Ambientes();
		Ambiente a = amb.getAmbiente("Hall de Entrada");
		System.out.println(a.getDescricao());
	}
}
