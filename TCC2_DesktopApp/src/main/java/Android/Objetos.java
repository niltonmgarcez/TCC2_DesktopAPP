/**
 * 
 */
package Android;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
	
	ArrayList<Objeto> listaObjetos = new ArrayList<Objeto>();

	Pattern pattern = Pattern.compile("\"(.*?)\"");

	/**
	 * @throws IOException 
	 * 
	 */
	public Objetos() throws IOException {
		Objeto objetoCollection = null;
		URL url = new URL("https://api.mongolab.com/api/1/databases/tcc2_data/collections/Beacons?apiKey=PpJuOpEhdLdCEELSCAr_26upkPcsYMJF");
				
		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\nilton_garcez\\texto.txt"));

		String beacon = in.readLine();
		String[] macro = beacon.split("} , ");
		
		for (int i = 0; i< macro.length; i++)
		{
			if (!(i%2 == 0)) 
			{
				System.out.println(macro[i]);
				Pattern p = Pattern.compile("\\[.*\"]");			
		        Matcher m = p.matcher(macro[i]);
		                
		        String[] beaconObjeto = macro[i].split(" , ");
		        System.out.println(macro.length);
		 
		        	String data[] = beaconObjeto[1].split(":");
		            String amb = data[1].replace("\"", "");
		            amb = amb.substring(1,amb.length());		            
		            System.out.println(amb);
		            
		            String data1[] = beaconObjeto[2].split(":");
		            String objetoUnit = data1[1].replace("\"", "");
		            objetoUnit = objetoUnit.substring(1,objetoUnit.length());
		            System.out.println(objetoUnit);		 
		            
		            String data2[] = beaconObjeto[3].split(":");
		            String X = data2[1].replace("\"", "");
		            X = X.substring(1,X.length());
		            System.out.println(X);
		            
		            String data3[] = beaconObjeto[4].split(":");
		            String Y = data3[1].replace("\"", "");
		            Y = Y.substring(1,Y.length());
		            System.out.println(Y);
		            
		            String data4[] = beaconObjeto[5].split(":");
		            String desc = data4[1].replace("\"", "");
		            desc = desc.substring(1,desc.length());
		            System.out.println(desc);
		            
		            String data5[] = beaconObjeto[6].split(":");
		            String criticidade = data5[1].substring(2, data5[1].lastIndexOf("\""));
		            System.out.println(criticidade);
		                     
		        
		        objetoCollection = new Objeto(amb, objetoUnit, Float.parseFloat(X), Float.parseFloat(Y), desc, Integer.parseInt(criticidade));
		        listaObjetos.add(objetoCollection);
			}			
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
		
	public static void main(String[] args) throws IOException {
		
		Objetos obj = new Objetos();
		Objeto o = obj.getObjeto("Conjunto de Cadeiras");
		System.out.println(o.getDescricao());
	}
}
