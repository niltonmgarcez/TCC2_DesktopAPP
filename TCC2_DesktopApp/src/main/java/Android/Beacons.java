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
public class Beacons {
	
	ArrayList<Beacon> listaBeacons = new ArrayList<Beacon>();
	Pattern pattern = Pattern.compile("\"(.*?)\"");

	/**
	 * @throws IOException 
	 * 
	 */
	public Beacons() throws IOException {
		Beacon beaconCollection = null;
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
		            String beaconUnit = data1[1].replace("\"", "");
		            beaconUnit = beaconUnit.substring(1,beaconUnit.length());
		            System.out.println(beaconUnit);		 
		            
		            String data2[] = beaconObjeto[3].split(":");
		            String X = data2[1].replace("\"", "");
		            X = X.substring(1,X.length());
		            System.out.println(X);
		            
		            String data3[] = beaconObjeto[4].split(":");
		            String Y = data3[1].replace("\"", "");
		            Y = Y.substring(1,Y.length());
		            System.out.println(Y);
		            
		            String data4[] = beaconObjeto[5].split(":");
		            String major = data4[1].replace("\"", "");
		            major = major.substring(1,major.length());
		            System.out.println(major);
		            
		            String data5[] = beaconObjeto[6].split(":");
		            String minor = data5[1].substring(2, data5[1].lastIndexOf("\""));
		            System.out.println(minor);
		            
		            
		        
		        beaconCollection = new Beacon(Float.parseFloat(X), Float.parseFloat(Y), Integer.parseInt(minor), Integer.parseInt(major), beaconUnit, amb);
		        listaBeacons.add(beaconCollection);
			}			
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
		
	public static void main(String[] args) throws IOException {
		
		Beacons bea = new Beacons();
		Beacon o = bea.getBeacon("Beacon 01");
		System.out.println(o.getPosX());
	}
}
