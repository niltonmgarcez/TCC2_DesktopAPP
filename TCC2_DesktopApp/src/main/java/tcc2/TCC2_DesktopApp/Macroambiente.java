/**
 * 
 */
package tcc2.TCC2_DesktopApp;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;

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
	DBCollection ambientes = db.getCollection("Ambientes");
	DBCollection objetos = db.getCollection("Objetos");
	DBCollection beacons = db.getCollection("Beacons");
	
	BasicDBObject predioDetalhes = new BasicDBObject();
	BasicDBObject ambienteDetalhes = new BasicDBObject();

	
	public Macroambiente() throws UnknownHostException{
		
	}
	
	public int getID()
	{
		DBObject obj = predio.findOne();			
		return Integer.parseInt(obj.get("predio_ID").toString());
	}
	
	public String getNome()
	{
		DBObject obj = predio.findOne();			
		return obj.get("nome").toString();	
	}
	
	public String getDescricao()
	{		
		DBObject obj = predio.findOne();			
		return obj.get("descricao").toString();
	}
	
	public String getAmbiente(String ambiente)
	{
		ambienteDetalhes.put("nome do ambiente", ambiente);
		DBCursor resultado = ambientes.find(ambienteDetalhes);
		DBObject detalhes = resultado.next();
	    String data = detalhes.toString();
		return data;		
	}
	
	public String getAmbientes()
	{
			
	}

}
