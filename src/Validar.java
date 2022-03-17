import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import utils.Accumulator;
import utils.HashSHA256;
import utils.Utils;

public class Validar extends Thread {
	private boolean html;
	private String file;
	private CyclicBarrier barrier;
	private List<String> erroneousHTML;
	private List<String> erroneousCSS;
	private Accumulator accumulator;
	private String cuasi;
	private String folderPath;
	private List<String> noExistsUniversityServer;
	private List<String> noEqualUniversityServer;
	private boolean sourceZip; 


	public Validar(boolean h,String fichero, CyclicBarrier barrera, List<String> e1, List<String> e2, 
			Accumulator acumulador, String cuasi, String rutaCarpeta, List<String> e3, List<String> e4, boolean origenComprimido) {
		this.html=h;
		this.file=fichero;
		this.barrier=barrera;
		this.erroneousHTML=e1;
		this.erroneousCSS=e2;
		this.accumulator = acumulador;
		this.cuasi = cuasi;
		this.noExistsUniversityServer = e3;
		this.noEqualUniversityServer = e4;
		this.sourceZip = origenComprimido;
		this.folderPath=rutaCarpeta;
	}

	public void run() {
		try {
			if(html){
				html();
				count();
			}else{
				css2();
			}
			checkUniversityServer();
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void html() {
		InputStream isFile = null;
		OutputStream os = null;

		InputStream is = null;
		OutputStream osFile = null;
		File f = null;
		try {
			URL u = new URL("https://validator.w3.org/nu/?out=xml");
			URLConnection uc = u.openConnection();
			uc.setDoOutput(true);
			uc.setRequestProperty("Content-Type", "text/html; charset=UTF-8");

			isFile = new FileInputStream(new File(file));
			os = uc.getOutputStream();
			byte buff1[] = new byte[1024 * 32];
			int leidos1 = isFile.read(buff1);
			while (leidos1 != -1) {
				os.write(buff1, 0, leidos1);
				leidos1 = isFile.read(buff1);
			}
			os.flush();
			Utils.close(os);

			String fichxml = file.substring(0, file.lastIndexOf("."));

			is = uc.getInputStream();
			File fi=new File(fichxml + "Errores.xml");
			osFile = new FileOutputStream(fi);
			byte buff[] = new byte[1024];
			int leidos2 = is.read(buff);
			while (leidos2 != -1) {
				osFile.write(buff, 0, leidos2);
				leidos2 = is.read(buff);
			}
			osFile.flush();
			Utils.close(isFile);
			Utils.close(osFile);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			f = new File(fichxml + "Errores.xml");
			Document doc = db.parse(f);

			Element messages = doc.getDocumentElement();
			NodeList errors = messages.getElementsByTagName("error");
			if (errors.getLength() > 0) {
				erroneousHTML.add(file);
			}
			fi.deleteOnExit();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Utils.close(isFile);
			Utils.close(os);

			Utils.close(is);
			Utils.close(osFile);
			//if (f!=null) 
				//f.delete();
		}
	}

	public void css() {
		BufferedReader br = null;

		InputStream is = null;
		OutputStream osFile = null;
		File f = null;
		try {
			String param = "text=";

			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String lineaFich = br.readLine();
			while (lineaFich != null) {
				param = param + URLEncoder.encode(lineaFich+"\r\n", "UTF-8");
				lineaFich = br.readLine();
			}
			Utils.close(br);
			
			URL u = new URL("http://jigsaw.w3.org/css-validator/validator?output=soap12&" + param);

			String fichxml = file.substring(0, file.lastIndexOf("."));

			is = u.openStream();
			File fi=new File(fichxml + "Errores.xml");
			osFile = new FileOutputStream(fi);
			byte buff[] = new byte[1024];
			int leidos2 = is.read(buff);
			while (leidos2 != -1) {
				osFile.write(buff, 0, leidos2);
				leidos2 = is.read(buff);
			}
			osFile.flush();
			Utils.close(osFile);
			Utils.close(is);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			f = new File(fichxml + "Errores.xml");
			Document doc = db.parse(f);

			Element envelope = doc.getDocumentElement();
			NodeList hijoErrors = envelope.getElementsByTagName("m:errorcount");
			Element errorcount = (Element) hijoErrors.item(0);
			String numErroresString = errorcount.getTextContent();
			int numErrores = Integer.parseInt(numErroresString);
			
			if (numErrores > 0) {
				erroneousCSS.add(file);
			}
			fi.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IO Error when uploading the file: " + file);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Utils.close(br);

			Utils.close(is);
			Utils.close(osFile);
		//	if (f!=null) 
			//	f.delete();
		}
	}
	
	

	public void css2() {
		BufferedReader br = null;		
		InputStream is = null;
		OutputStream osFile = null;
		File f = null;
		try {
			String param = "";

			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String lineaFich = br.readLine();
			while (lineaFich != null) {
				param = param + lineaFich + "\r\n";//+ URLEncoder.encode(lineaFich+"\r\n", "UTF-8");
				lineaFich = br.readLine();
			}
			Utils.close(br);
			
			URL u = new URL("http://jigsaw.w3.org/css-validator/validator");
			String fichxml = file.substring(0, file.lastIndexOf("."));

			HttpURLConnection url = (HttpURLConnection) u.openConnection();
			url.addRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------1207390276808");
			url.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(url.getOutputStream());
			String post = "-----------------------------1207390276808\r\n" + 
					"Content-Disposition: form-data; name=\"text\"\r\n" + 
					"\r\n" + 
					param +
					"\r\n" + 
					"-----------------------------1207390276808\r\n" + 
					"Content-Disposition: form-data; name=\"profile\"\r\n" + 
					"\r\n" + 
					"css3svg\r\n" + 
					"-----------------------------1207390276808\r\n" + 
					"Content-Disposition: form-data; name=\"usermedium\"\r\n" + 
					"\r\n" + 
					"all\r\n" + 
					"-----------------------------1207390276808\r\n" + 
					"Content-Disposition: form-data; name=\"type\"\r\n" + 
					"\r\n" + 
					"none\r\n" + 
					"-----------------------------1207390276808\r\n" + 
					"Content-Disposition: form-data; name=\"warning\"\r\n" + 
					"\r\n" + 
					"1\r\n" + 
					"-----------------------------1207390276808\r\n" + 
					"Content-Disposition: form-data; name=\"vextwarning\"\r\n" + 
					"\r\n" + 
					"false\r\n" + 
					"-----------------------------1207390276808\r\n" + 
					"Content-Disposition: form-data; name=\"lang\"\r\n" + 
					"\r\n" + 
					"es\r\n" + 
					"-----------------------------1207390276808\r\n" + 
					"Content-Disposition: form-data; name=\"output\"\r\n" + 
					"\r\n" + 
					"soap12\r\n" + 
					"-----------------------------1207390276808--\r\n" + 
					"";
			out.write(post);
			out.flush();
			is = url.getInputStream();
			osFile = new FileOutputStream(new File(fichxml + "Errores.xml"));
			byte buff[] = new byte[1024];
			int leidos2 = is.read(buff);
			while (leidos2 != -1) {
				osFile.write(buff, 0, leidos2);
				leidos2 = is.read(buff);
			}
			osFile.flush();
			Utils.close(osFile);
			Utils.close(is);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			f = new File(fichxml + "Errores.xml");
			Document doc = db.parse(f);

			Element envelope = doc.getDocumentElement();
			NodeList hijoErrors = envelope.getElementsByTagName("m:errorcount");
			Element errorcount = (Element) hijoErrors.item(0);
			String numErroresString = errorcount.getTextContent();
			int numErrores = Integer.parseInt(numErroresString);			
			if (numErrores > 0) {
				erroneousCSS.add(file);
			}
			f.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IO Error when uploading the file: " + file);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Utils.close(br);

			Utils.close(is);
			Utils.close(osFile);
		//	if (f!=null) 
			//	f.delete();
		}
	}
	
	
	public void count()
	{
		String contenido = Utils.readFileContents(file);
		int numTables = Utils.countSubstringAppearances(contenido, "<table");
		int numUl = Utils.countSubstringAppearances(contenido,"<ul");
		int numOl = Utils.countSubstringAppearances(contenido,"<ol");
		int numVideo = Utils.countSubstringAppearances(contenido,"<video");
		int numForm = Utils.countSubstringAppearances(contenido,"<form");
		int numB = Utils.countSubstringAppearances(contenido,"<b>");
		int numBorder = Utils.countSubstringAppearances(contenido,"border=");
		int numFrame = Utils.countSubstringAppearances(contenido,"<frame");
		int numCharset = Utils.countSubstringAppearances(contenido,"charset=\"");

		int numWidth = Utils.countSubstringAppearances(contenido,"width=\"");
		int numHeight = Utils.countSubstringAppearances(contenido, "height=\"");

		int numCSS = Utils.countSubstringAppearances(contenido, "rel=\"stylesheet");
		
	
		int numEnlacesAbsolutos = Utils.numberAbsoluteLinks(contenido, cuasi);
		
		accumulator.incrementTable(numTables);
		accumulator.incrementUl(numUl);
		accumulator.incrementOl(numOl);
		accumulator.incrementVideo(numVideo);
		accumulator.incrementForm(numForm);
		accumulator.incrementB(numB);
		accumulator.incrementBorder(numBorder);
		accumulator.incrementHeight(numHeight);
		accumulator.incrementWidth(numWidth);
		accumulator.incrementFrame(numFrame);
		accumulator.incrementAbsoluteLinks(numEnlacesAbsolutos);
		
		if (numB > 0) 
		{
			accumulator.añadirListaB(file);
		}
		if (numWidth > 0) 
		{
			accumulator.añadirListaWidth(file);
		}
		if (numHeight > 0) 
		{
			accumulator.añadirListaHeight(file);
		}
		
		if (numFrame > 0) 
		{
			accumulator.añadirListaFrame(file);
		}
		
		if (numBorder > 0) 
		{
			accumulator.añadirListaBorder(file);
		}
		
		
		if (numCharset == 0)
		{
			accumulator.addListHTMLwithoutCharset(file);
			accumulator.incrementHTMLwithoutCharset(1);
		}
		
		
		if (numCSS == 0)
		{
			accumulator.addlistHTMLwithoutCSS(file);
			accumulator.incrementHTMLwithoutCSS(1);
		}
		
		if (numCSS >= 2)
		{
			accumulator.addlistHTMLwith2CSS(file);
			accumulator.incrementHTMLwith2CSS(1);
		}
		
		if (numEnlacesAbsolutos > 0) 
		{
			accumulator.incrementAbsoluteLinks(numEnlacesAbsolutos);
			accumulator.addListAbsoluteLinks(file);
		}
	}
	
	
	
	public void checkUniversityServer() 
	{		
		String url_belenus = "";
		if (sourceZip)
		{			
			String rutaSinCarpetaComprimida = file.substring(file.indexOf("/") + 1);
			url_belenus = "https://laika.unirioja.es/~" + cuasi + "/trabajoSI/"+rutaSinCarpetaComprimida;
		} 
		
		else url_belenus = "https://laika.unirioja.es/~" + cuasi + "/trabajoSI/"+Utils.fileName(file,folderPath);
				int codigo_respuesta = 0;
		HttpsURLConnection con = null;
		try {
			  URL url = new URL(url_belenus);		
			  con = (HttpsURLConnection)url.openConnection();
			  codigo_respuesta = con.getResponseCode();
			  if (codigo_respuesta == 200) 
				{
					InputStream in = con.getInputStream();
					File fichero_descargado = new File(file+"_Belenus");
					FileOutputStream fos = new FileOutputStream(fichero_descargado);
					byte buff[] = new byte[1024*32];
					int leidos = in.read(buff);
					while (leidos != -1) 
					{
						fos.write(buff, 0, leidos);
						leidos = in.read(buff);
					}
					in.close();
					fos.flush();
					fos.close();
					if (!HashSHA256.getHash(fichero_descargado).equals(HashSHA256.getHash(new File(file)))) 
					{
						noEqualUniversityServer.add(file);
						
					}					
					fichero_descargado.deleteOnExit();
				}
			  else {
				  noExistsUniversityServer.add(file);
		  
				  }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
			
			
		}
	}


