/**
 * 
 */
package Android;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;
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
		Ambiente ambienteCollection = null;
		URL url = new URL("https://api.mongolab.com/api/1/databases/tcc2_data/collections/Ambientes?apiKey=PpJuOpEhdLdCEELSCAr_26upkPcsYMJF");
				
		BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\nilton_garcez\\texto.txt"));

		String ambiente = in.readLine();
		String[] macro = ambiente.split("} , ");
		
		for (int i = 0; i< macro.length; i++)
		{
			if (!(i%2 == 0)) 
			{
				System.out.println(macro[i]);
				Pattern p = Pattern.compile("\\[.*\"]");			
		        Matcher m = p.matcher(macro[i]);
		        if(m.find())
		        {
		        	listAmbientes = (String) m.group().subSequence(1, m.group().length()-1);
		        	listAmbientes = listAmbientes.substring(1,listAmbientes.length());
		        	listAmbientes = listAmbientes.replace("\"", "");
		        } 
		        System.out.println(listAmbientes);
		        macro[i] = macro[i].replace(" , \"ambientes interligados\" : [" + listAmbientes + "]", "");
		                
		        String[] ambienteObjeto = macro[i].split(" , ");
		        System.out.println(macro.length);
		 
		        	String data[] = ambienteObjeto[2].split(":");
		            String tipo = data[1].replace("\"", "");
		            tipo = tipo.substring(1,tipo.length());		            
		            System.out.println(tipo);
		            
		            String data1[] = ambienteObjeto[3].split(":");
		            String andar = data1[1].replace("\"", "");
		            andar = andar.substring(1,andar.length());
		            System.out.println(andar);		 
		            
		            String data2[] = ambienteObjeto[4].split(":");
		            String identificacao = data2[1].replace("\"", "");
		            identificacao = identificacao.substring(1,identificacao.length());
		            System.out.println(identificacao);
		            
		            String data3[] = ambienteObjeto[5].split(":");
		            String posX = data3[1].replace("\"", "");
		            posX = posX.substring(1,posX.length());
		            System.out.println(posX);
		            
		            String data4[] = ambienteObjeto[6].split(":");
		            String posY = data4[1].replace("\"", "");
		            posY = posY.substring(1,posY.length());
		            System.out.println(posY);
		            
		            String data5[] = ambienteObjeto[7].split(":");
		            String resumo = data5[1].replace("\"", "");
		            resumo = resumo.substring(1,resumo.length());
		            System.out.println(resumo);
		            
		            String data6[] = ambienteObjeto[8].split(":");
		            String descricao = data6[1].substring(2, data6[1].lastIndexOf("\""));
		            System.out.println(descricao);
		        
		        ambienteCollection = new Ambiente(tipo, Integer.parseInt(andar), identificacao, Float.parseFloat(posX), Float.parseFloat(posY), resumo, descricao, listAmbientes);
		        listaAmbientes.add(ambienteCollection);
			}			
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
		
	public static void main(String[] args) throws IOException {
				
		Ambientes amb = new Ambientes();
		System.out.println(amb.getListaAmbientes().get(2).getIdentificacao());
	}
}
