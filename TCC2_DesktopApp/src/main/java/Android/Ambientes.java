/**
 * 
 */
package Android;

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
	
	ArrayList<Ambiente> listaAmbientes = new ArrayList<Ambiente>();

	Pattern pattern = Pattern.compile("\"(.*?)\"");

	/**
	 * 
	 */
	public Ambientes() {
		
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
