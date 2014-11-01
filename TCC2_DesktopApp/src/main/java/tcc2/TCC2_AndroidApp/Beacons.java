/**
 * 
 */
package tcc2.TCC2_AndroidApp;

import java.net.UnknownHostException;
import java.util.ArrayList;
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
public class Beacons {
	
	String textUri = "mongodb://niltongarcez:trabalho2@ds047440.mongolab.com:47440/tcc2_data";
	MongoClientURI uri = new MongoClientURI(textUri);
	MongoClient mongoClient = new MongoClient(uri);
	DB db = mongoClient.getDB("tcc2_data");
	DBCollection beacons = db.getCollection("Beacons");
	BasicDBObject documento = new BasicDBObject();
	ArrayList<Beacon> listaBeacons = new ArrayList<Beacon>();

	Pattern pattern = Pattern.compile("\"(.*?)\"");

	/**
	 * 
	 */
	public Beacons() throws UnknownHostException {
		DBCursor cursor = beacons.find();
		while(cursor.hasNext())
		{
			DBObject objeto = cursor.next();
			String identificacaoAmbiente = objeto.get("identificacao do ambiente").toString();
			String identificacao = objeto.get("identificacao do beacon").toString();
			float posX = Float.parseFloat(objeto.get("posicao em X").toString());
			float posY = Float.parseFloat(objeto.get("posicao em Y").toString());
			int major = Integer.parseInt(objeto.get("major").toString());
			int minor = Integer.parseInt(objeto.get("minor").toString());
			
			listaBeacons.add(new Beacon(posX, posY, minor, major, identificacao, identificacaoAmbiente));
		}
	}
	
	public ArrayList<Beacon> getListaBeacons()
	{
		return listaBeacons;
	}
	
	public Beacon getBeacon(String identificacao)
	{
		Beacon beacon = null;
		for (int i = 0; i < listaBeacons.size(); i++)
		{
			if (listaBeacons.get(i).getIdentificacao().equals(identificacao))
			{
				beacon = listaBeacons.get(i);
			}
		}		
		return beacon;		
	}
		
	public static void main(String[] args) throws UnknownHostException {
		
		Beacons bea = new Beacons();
		Beacon o = bea.getBeacon("Beacon 01");
		System.out.println(o.getAmbiente());
	}
}
