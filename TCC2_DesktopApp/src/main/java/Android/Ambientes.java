/**
 * 
 */
package Android;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class Ambientes {
	
	ArrayList<Ambiente> listaAmbientes = new ArrayList<Ambiente>();

	Pattern pattern = Pattern.compile("\"(.*?)\"");

	/**
	 * @throws IOException 
	 * 
	 */
	public Ambientes() throws IOException {
		String listAmbientes = "";
		URL url = new URL("https://api.mongolab.com/api/1/databases/tcc2_data/collections/Ambientes?apiKey=PpJuOpEhdLdCEELSCAr_26upkPcsYMJF");

		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\Nilton Garcez\\testes.txt"));

		String ambiente = in.readLine();
		System.out.println(ambiente);
		
		String[] macro = ambiente.split("} , ");
        //macro[1] = "\"predio_ID" + macro[1] ;
        //macro[1] = macro[1].replace("} ]", "");
		for (int i = 0; i< macro.length;i++)
		{
			System.out.println(macro[i]);			
		}
		
		
		/**
		String[] macro = in.readLine().split("} , \"predio_ID");
        macro[1] = "\"predio_ID" + macro[1] ;
        macro[1] = macro[1].replace("} ]", "");
        
        Pattern p = Pattern.compile("\\[.*]");
        Matcher m = p.matcher(macro[1]);
        if(m.find())
        listAmbientes = (String) m.group().subSequence(1, m.group().length()-1); 
        System.out.println(listAmbientes);
        
        macro[1] = macro[1].replace("\"listaAmbientes\" : [" + listAmbientes + "] , ", "");
        String[] macroambiente = macro[1].split(" , ");
        
        String data[] = macroambiente[1].split(":");
        String ident = data[1].replace("\"", "");
        ident = ident.substring(1,ident.length());
       
        String data1[] = macroambiente[2].split(":");
        String desc = data1[1].replace("\"", "");
        desc = desc.substring(1,desc.length());
                
        String data2[] = macroambiente[3].split(":");
        String and = data2[1].replace("\"", "");
        and = and.trim();**/
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
		
	public static void main(String[] args) throws IOException {
				
		Ambientes amb = new Ambientes();
		//Ambiente a = amb.getAmbiente("Hall de Entrada");
		//System.out.println(a.getDescricao());
	}
}
