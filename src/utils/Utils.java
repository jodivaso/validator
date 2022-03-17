package utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Utils {
	
	
	public static int countSubstringAppearances(String cadena, String subcadena){
	int lastIndex = 0;
	int count = 0;

	while(lastIndex != -1){

	    lastIndex = cadena.indexOf(subcadena,lastIndex);

	    if(lastIndex != -1){
	        count ++;
	        lastIndex += subcadena.length();
	    }
	}
	return count;
	}
	

	public static int countTag (Document doc, String etiqueta)
	{
		Element messages = doc.getDocumentElement();
		NodeList nodos = messages.getElementsByTagName(etiqueta);		
		return nodos.getLength();				
	}
	
	
	public static int countAttribute (Document doc, String atributo)
	{
		Element messages = doc.getDocumentElement();
		NodeList nodos = messages.getElementsByTagName("*");
		int total = 0;
		for (int i = 0; i<nodos.getLength();i++)
		{			
			if (nodos.item(i).getAttributes().getNamedItem(atributo)!=null)
			{
				total++;
			}
		}
		return total;				
	}
	
	public static String readFileContents (String fichero)
	{
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader (new FileInputStream(new File(fichero)),"UTF-8"));
			String linea;
			while ((linea = br.readLine()) != null)
			{
				sb = sb.append(linea+"\r\n");
			}		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			close(br);
		}
		return sb.toString().toLowerCase();
		
	}
	
	
	public static byte[] readFile (String fichero)
	{
		File f = new File (fichero);

		byte resultado[]=new byte[(int) f.length()];

		try (RandomAccessFile fis = new RandomAccessFile(f,"r");){
			fis.readFully(resultado);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultado;
		
	}
	
	public static void close(Closeable o) {
		try {
			if (o != null)
				o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void deleteDir(File file) {
	    File[] contents = file.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	            deleteDir(f);
	        }
	    }
	    file.delete();
	}
	
	
	public static void deleteDirOnExit(File file) {
	    File[] contents = file.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	            f.deleteOnExit();
	        }
	    }
	    file.deleteOnExit();
	}
	
	public static String nombreFichero(String f){
		return f.substring(f.indexOf('/')+1);
	}
	
	public static String fileName(String f,String padre){
		
		if(f.contains(padre)) {
			String aux=f.substring(f.lastIndexOf(padre)+padre.length()+1);
			aux=aux.replace('\\', '/');
			
			return aux;
	} else {
		if (f.contains("/")) {
		return f.substring(f.indexOf('/')+1);}
		else {
			String aux=f.substring(f.indexOf('\\')+1);
			aux=aux.replace('\\', '/');
			
			return aux;}
		
	}
	}
	
	public static String showListFiles (List<String> l){
		String aux="</p><ul  class=plistafich>";
		for (int i=0; i<l.size()-1; i++){
			aux=aux+nombreFichero(l.get(i))+", ";
		}
		aux=aux+nombreFichero(l.get(l.size()-1))+"</ul><p class=plista>";
		return aux;
	}
	
	
	public static String showListFiles (List<String> l,String padre){
		String aux="</p><ul  class=plistafich>";
		for (int i=0; i<l.size()-1; i++){
			aux=aux+fileName(l.get(i),padre)+"<br>";
		}
		aux=aux+fileName(l.get(l.size()-1),padre)+"</ul><p class=plista>";
		return aux;
	}
	
	
	public static String getAttributeContent (String f, String atributo) 
	{
		if (f.contains(atributo)) 
		{
			int indexAtributo = f.indexOf(atributo+"=\"");
			int lengthAtributo = atributo.length()+2; //Añado dos para añadir el ="
			String aux = f.substring(indexAtributo + lengthAtributo);
			String resultado = aux.substring(0, aux.indexOf("\""));
			return resultado;
		}
		else return null;
	}
	
	
	
	public static List<String> getAttributeContentList (String cadena, String atributo) 
	{
		List<String> resultado = new ArrayList<String>();
		
		int lastIndex = 0;
		String valorAtributo;
		
		while(lastIndex != -1){

		    lastIndex = cadena.indexOf(atributo,lastIndex);

		    if(lastIndex != -1){
		    	//int indexAtributo = cadena.indexOf(atributo+"=\"");
				int lengthAtributo = atributo.length()+2; //Añado dos para añadir el ="
				String aux = cadena.substring(lastIndex + lengthAtributo);
				valorAtributo = aux.substring(0, aux.indexOf("\""));
				resultado.add(valorAtributo);
		        lastIndex += valorAtributo.length() + lengthAtributo;
		    }
		}
		return resultado;
	}
	

	public static int numberAbsoluteLinks (String contenido, String cuasi) 
	{
		int resultado = 0;
		List<String> resultadoHREF = getAttributeContentList(contenido, "href");
		for (String a : resultadoHREF) 
		{
			if (a.contains("belenus.unirioja.es/~" + cuasi)) 
			{
				resultado++;
			}
		}
		
		List<String> resultadoSRC = getAttributeContentList(contenido, "src");
		for (String a : resultadoSRC) 
		{
			if (a.contains("belenus.unirioja.es/~" + cuasi)) 
			{
				resultado++;
			}
		}
		return resultado;		
	}
	
	
	public static String getHTMLEncoding(String textoFichero) 
	{		
		return getAttributeContent(textoFichero, "charset");
	}
	
	public static HashMap<String, Integer> readConfigureFile() 
	{
		HashMap<String, Integer> configuration = new HashMap<String, Integer>();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("configure.txt"))))
		{
			
			String line;		
			while ((line = br.readLine()) !=null)
			{
				line = line.replaceAll("\\s+",""); // Remove whitespaces
				String[] parts = line.split("=");
				if (parts.length == 2) 
				{
					configuration.put(parts[0], Integer.valueOf(parts[1]));
				}				
			}
		}
		catch(IOException e){e.printStackTrace();}
		
		return configuration;
		
		
	}
	
	
	
}
