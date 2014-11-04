package Android;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tcc2.TCC2_AndroidApp.Macroambiente;


/**
 * 
 */

/**
 * @author Nilton Mena Garcez
 *
 */
public class teste {

	//Pattern pattern = Pattern.compile("\"(.*?)\"");
	

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Android.Macroambiente macroObjeto = null;
		String listAmbientes = "";
		URL url = new URL("https://api.mongolab.com/api/1/databases/tcc2_data/collections/Macroambiente?apiKey=PpJuOpEhdLdCEELSCAr_26upkPcsYMJF");

		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\Nilton Garcez\\testes.txt"));
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
        and = and.trim();
               
        macroObjeto = new Android.Macroambiente(ident, desc, Integer.parseInt(and));
        
        System.out.println(macroObjeto.getIdentificacao());
        System.out.println(macroObjeto.getDescricao());
        System.out.println(macroObjeto.getAndares());

        
	}

}
