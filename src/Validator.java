import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;

import com.github.junrar.Archive;
import com.github.junrar.impl.FileVolumeManager;
import com.github.junrar.rarfile.FileHeader;

import jMeld.InitJMeld;
import navigator.Navigator;
import utils.Accumulator;
import utils.Utils;

public class Validator extends JApplet implements ActionListener, KeyListener{
	boolean activado=false;
	JTextField cuasiTF=new JTextField("your_username");
	String cuasi;
	JButton b;
	JFileChooser fc = new JFileChooser();
	JTextPane  tf1, tf2, tf3;
	Label l1,l2,l3;
	String s1="", s2="";
	
	String s3="";

	List<String> archivos;
	int numAnalizados = 0;
	List<String> carpetasZip=new ArrayList<String>();
	
	String rutaCarpeta="";
	int w;
	int h;
	Panel p=new Panel();
	Label luser,l,l4;
	JScrollPane tf11, tf22, tf33;
	JSeparator j=new JSeparator(JSeparator.HORIZONTAL);
	boolean origenComprimido=false;
	File[] files;
	
	HashMap<String, Integer> configuration = new HashMap<String,Integer>();
	
	
	public Validator()
	{
		//Default values of the configuration
		configuration.put("validateHTML", 1);
		configuration.put("validateCSS", 1);
		configuration.put("numHTML", 8);
		configuration.put("numCSS", 2);
		configuration.put("HTMLwithoutCSS", 0);
		configuration.put("HTMLwith2CSS", 2);
		configuration.put("HTMLnoCharset", 0);
		configuration.put("numAspect", 0);
		configuration.put("AbsoluteLinks", 0);
		configuration.put("universityServer", 1);
		configuration.put("check_user_name", 1);
	}
	
	public Validator(HashMap c)
	{		
		configuration = c;
	}
	
	
	public void startAnalysis () {
		 
		 archivos=new ArrayList<String>();
		 //carpetasZip=new ArrayList<String>();
		 rutaCarpeta=""; 
		 s1="";
		 s2="";
		 s3="";
		 tf1.setText(s1);
		 tf2.setText(s2);
		 tf3.setText(s3);
		 numAnalizados=0;
		 
	 }
	
	public void setWidth(int w) {
		this.w=w;
	}
	
	public void setHeight(int h) {
		this.h=h;
	}
	
	public void draw() {
		p.setBounds(0,0,w-20,h-25);
		
		int pwidth=p.getWidth();
		int pheigth=p.getHeight();
		
//		System.out.println("width:"+pwidth);
//		System.out.println("height:"+pheigth);
		int twidth=(pwidth-30)/2;
		int theigth=(pheigth-335);
		
//		System.out.println("twidth:"+twidth);
//		System.out.println("theight:"+theigth);
		
		luser.setBounds( 10,10,twidth,20 );
		cuasiTF.setBounds(twidth+20, 10, twidth, 20);
		l.setBounds( 10,40,twidth,20 );
		b.setBounds(twidth+20,40,80,25);
		j.setBounds(10,70,pwidth-20,4);
		l1.setBounds(10,75,twidth,20);
		l2.setBounds(twidth+20,75,twidth,20);
		l3.setBounds(10,p.getHeight()-225,p.getWidth()-20,20);
		tf11.setBounds(10,100,twidth,theigth);
		tf22.setBounds(twidth+20,100,twidth,theigth);
		l4.setBounds(5,p.getHeight()-40,p.getWidth()-10,20);
		tf33.setBounds(10,p.getHeight()-197,p.getWidth()-20,150);
		this.repaint();
	};
	
	
	public void init()
	  {
				
		File fichero_cuasi = new File("cuasi.txt");
		if (fichero_cuasi.exists() && fichero_cuasi.isFile())
		{
			try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fichero_cuasi)));)
			{				
				cuasi = br.readLine();
				cuasiTF.setText(cuasi);
				
				
			} 
			catch (IOException e1) {				
				e1.printStackTrace();
			}			
		}
		

		p.setLayout(null);

		
		
		
		Font f=new Font("Calibri", Font.PLAIN, 18);
		p.setFont(f);
		Font f2=new Font("Calibri", Font.PLAIN, 16);
		
		luser=new Label("Username:");
		luser.setFont(f2);
		luser.setAlignment(Label.LEFT);
		
		p.add(luser);
		cuasiTF.addKeyListener((KeyListener) this);
		
		p.add(cuasiTF);
		
		l=new Label("Select the file or folder to be analyzed or drag and drop them to the interface");
		l.setFont(f2);
		l.setAlignment(Label.LEFT);
		
		p.add(l);
	    b = new JButton( "Open" );
	    b.addActionListener((ActionListener) this); 
	    
	    b.setFont(f2);
	    p.add(b);
	    
	    p.add(j);
	    l1=new Label("Fulfilled requirements");
	    
	    p.add(l1);
	    l1.setFont(f2);
	    l2=new Label("Unfulfilled requirements");
	    l2.setAlignment(Label.RIGHT);
	    
	    l2.setFont(f2);
	    p.add(l2);
	    

	    l3=new Label("Unchecked requirements");
	    l3.setAlignment(Label.LEFT);
	    
	    l3.setFont(f2);
	    p.add(l3);
	    
	    
	    
	    tf1=new JTextPane();
	    tf1.setContentType("text/html");
	    tf11 = new JScrollPane (tf1);
	    
	    
	    tf1.setForeground(new Color(0,153,0));
	    tf1.setFont(f);
	    p.add(tf11);
	    
	    
	    tf2=new JTextPane();
	    tf2.setContentType("text/html");
	    tf2.addHyperlinkListener(e -> {
	        if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
	          String url = e.getURL().toString();
	         if (!url.startsWith("http://www.unirioja.es/NoCoincideEnBelenus/") && url.endsWith(".html")) {
	         	    
		         String fichero = url.substring(23);
		         String post = new String(Utils.readFile(fichero)); 
		         
		         String codificacion = Utils.getHTMLEncoding(post);
		        String header = "Content-Type: text/html; charset=" + codificacion;
		        String headers[] = {header};
		       
		        if (post.equals("")) 
		        {
		        	post="-----------------------------15752970731931\r\n" + 
		        			"Content-Disposition: form-data; name=\"uploaded_file\"; filename=\"" + fichero + "\"\r\n" + 
		        			"Content-Type: text/html\r\n" + 
		        			"\r\n" + 
		        			"\r\n" + 
		        			"-----------------------------15752970731931\r\n" + 
		        			"Content-Disposition: form-data; name=\"charset\"\r\n" + 
		        			"\r\n" + 
		        			"(detect automatically)\r\n" + 
		        			"-----------------------------15752970731931\r\n" + 
		        			"Content-Disposition: form-data; name=\"doctype\"\r\n" + 
		        			"\r\n" + 
		        			"Inline\r\n" + 
		        			"-----------------------------15752970731931\r\n" + 
		        			"Content-Disposition: form-data; name=\"group\"\r\n" + 
		        			"\r\n" + 
		        			"0\r\n" + 
		        			"-----------------------------15752970731931--\r\n" + 
		        			"";
		        	String headers2[]= {"Host: validator.w3.org",
		        			
		        			"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
		        			"Accept-Language: es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		        			"Accept-Encoding: gzip, deflate, br",
		        			"Referer: https://validator.w3.org/",
		        			"Content-Type: multipart/form-data; boundary=---------------------------15752970731931",
		        			"Content-Length: 512",
		        			"DNT: 1",
		        			"Connection: keep-alive",
		        			"Upgrade-Insecure-Requests: 1",
		        			"TE: Trailers"};
		        	Navigator.initNavigator("https://validator.w3.org/nu/", post, headers2, fichero);
		        }
		        else
		        Navigator.initNavigator("https://validator.w3.org/nu/", post, headers, fichero);
	         }
	         
	         if (!url.startsWith("http://www.unirioja.es/NoCoincideEnBelenus/") && url.endsWith(".css"))
	         {
		        String fichero = url.substring(23);
	        	String param = "";// "text=";
	 			String lineaFich;
				try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fichero), "UTF-8"));)
				{
					lineaFich = br.readLine();
					while (lineaFich != null) {						
		 				param = param + lineaFich+"\r\n";		 				
		 				lineaFich = br.readLine();
		 			}			
					
					
					String urlCSS = "https://jigsaw.w3.org/css-validator/validator";
					String post="-----------------------------11381276715726\r\n" + 
							"Content-Disposition: form-data; name=\"text\"\r\n" + 
							"\r\n" + 
							param + 
							"\r\n" + 
							"-----------------------------11381276715726\r\n" + 
							"Content-Disposition: form-data; name=\"profile\"\r\n" + 
							"\r\n" + 
							"css3svg\r\n" + 
							"-----------------------------11381276715726\r\n" + 
							"Content-Disposition: form-data; name=\"usermedium\"\r\n" + 
							"\r\n" + 
							"all\r\n" + 
							"-----------------------------11381276715726\r\n" + 
							"Content-Disposition: form-data; name=\"type\"\r\n" + 
							"\r\n" + 
							"none\r\n" + 
							"-----------------------------11381276715726\r\n" + 
							"Content-Disposition: form-data; name=\"warning\"\r\n" + 
							"\r\n" + 
							"1\r\n" + 
							"-----------------------------11381276715726\r\n" + 
							"Content-Disposition: form-data; name=\"vextwarning\"\r\n" + 
							"\r\n" + 
							"false\r\n" +
							"-----------------------------11381276715726\r\n" + 
							"Content-Disposition: form-data; name=\"lang\"\r\n" + 
							"\r\n" + 
							"es\r\n" + 
							"-----------------------------11381276715726--";
					
					String headers[] = {"Host: jigsaw.w3.org",
							"User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101",
							"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
							"Accept-Language: es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
							"Referer: https://jigsaw.w3.org/css-validator/",
							"Content-Type: multipart/form-data; boundary=---------------------------11381276715726",
							"Accept-Encoding: gzip, deflate, br",
							"DNT: 1",
							"Upgrade-Insecure-Requests: 1",
							"Connection: keep-alive",
							"TE: Trailers"};
					
					
			        Navigator.initNavigator(urlCSS, post, headers, fichero);
				} catch (IOException e1) {e1.printStackTrace();} 
	         }
	      
	         if (url.startsWith("http://www.unirioja.es/NoCoincideEnBelenus/")) 
	         {
			       String fichero = url.substring(43);
			       String fichero_descargado = fichero +"_Belenus";
			       (new InitJMeld()).initJMeld(fichero, fichero_descargado);
			       
			    
	         }	         	        
	         
	      }
	    });
	    
	    
	    tf22 = new JScrollPane (tf2);
	    tf2.setFont(f);
	    
	    tf2.setForeground(Color.RED);
	    p.add(tf22);
	    
	    tf3=new JTextPane();
	    tf3.setContentType("text/html");
	    tf33 = new JScrollPane (tf3);
	    tf3.setFont(f);
	    
	    p.add(tf33);

	    
	    l4=new Label("Student projects of computer systems course. Software engineering degree and degree in mathematics. University of XX XXXXX.");
	    l4.setAlignment(Label.CENTER);
	    
	    l4.setFont(new Font("CALIBRI",0,12));
	    p.add(l4);
	    
	    getContentPane().add(p);
	    setVisible (true);
	    FileDrop fileDrop = new FileDrop(this, new FileDrop.Listener() { 

	        @Override 
	        public void filesDropped(java.io.File[] f) { 
	        	startAnalysis();
	        	if (!check_username()) 
	    		{
	        		JOptionPane.showMessageDialog(new JFrame(), "You must introduce a valid username", "Error",
	    			        JOptionPane.ERROR_MESSAGE);
	    		}
	    		else {
	            activado=true;
	            files=f;
	            analize();

	        }
	        }
	    }); 
	    draw();
	  }
	
	private boolean check_username() {
		cuasi=cuasiTF.getText();
		String url_belenus = "https://belenus.unirioja.es/~" + cuasi;
		int codigo_respuesta = 0;
		
	    if (configuration.get("check_user_name")==-1) return true; //In this case, no check.
	    
		try {
			  URL url = new URL(url_belenus);

		        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		                return null;
		            }
		            public void checkClientTrusted(X509Certificate[] certs, String authType) {
		            }
		            public void checkServerTrusted(X509Certificate[] certs, String authType) {
		            }
		        } };
		        // Install the all-trusting trust manager
		        SSLContext sc = SSLContext.getInstance("SSL");				
		        sc.init(null, trustAllCerts, new java.security.SecureRandom());
		        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			  HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			  codigo_respuesta = con.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (codigo_respuesta == 200) {
			
			try (OutputStreamWriter osw = new OutputStreamWriter (new FileOutputStream("cuasi.txt"));) {
				osw.write(cuasi);
				osw.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return true; 
		}
		else return false;		
	}
	

	public void keyReleased(KeyEvent e) {
		
		
    if(e.getSource()==cuasiTF && e.getKeyCode() == KeyEvent.VK_ENTER){
    	
        if (! check_username()) {
        	
        	tf3.setText("The username is invalid");
        }
        else {
        	tf3.setText("The username is valid");
        	
        	//tf2.setText(cuasi);
               	try (OutputStreamWriter osw = new OutputStreamWriter (new FileOutputStream("cuasi.txt"));) {
				osw.write(cuasi);
				osw.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
    
	}
	}


	
	public void actionPerformed( ActionEvent ae )
    {

		startAnalysis();
		if (!check_username()) 
		{
			//tf3.setText("Para poder usar el validador debes introducir una CUASI válida.");
			JOptionPane.showMessageDialog(new JFrame(), "You must introduce a valid username.", "Error",
			        JOptionPane.ERROR_MESSAGE);
		}
		else {
			activado=true;
			
			if (ae.getSource()==b) {
      JFileChooser fc = new JFileChooser();
      //FileNameExtensionFilter filter = new FileNameExtensionFilter("Ficheros zip","zip");
      //fc.setFileFilter(filter);
      fc.setCurrentDirectory(new File("."));
      //fc.setCurrentDirectory( new File( "C:\\" ) );
      fc.setMultiSelectionEnabled(true);   
      fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      
      int returnVal = fc.showDialog(this, "Select");
      //int returnVal = fc.showSaveDialog( this ); 
      String fileName = "" ;
      if ( returnVal == JFileChooser.APPROVE_OPTION )   
      {  
  		tf1.setText("");
  		tf2.setText("");
  		tf3.setText("");
  		s1="";
  		s2="";
  		s3="";
  		files = fc.getSelectedFiles();
  		

        
        analize();
  		
        //fileName=aFile.
        
      }
      //System.out.println( fileName );
		}
		}
      
    }
	
	private boolean esFicheroComprimido(File f) {
		String s =f.getName();
		String ext=s.substring(s.lastIndexOf(".")+1);
		return (ext.equals("zip") || ext.equals("rar") );
	}
	
private void unzip(File[] l) {
	int s=l.length;
	
	
	if (rutaCarpeta.length()==0) {
		if (l.length==1 && l[0].isDirectory()) {
			rutaCarpeta=l[0].getPath();
		} else {
		rutaCarpeta=l[0].getParent();
		}
		
	}


	for(int i=0; i<s;i++) {
		File fi=l[i];
		if (fi.isFile() && ! esFicheroComprimido(fi)) {
			archivos.add(fi.getAbsolutePath());
			if((fi.getName().substring(fi.getName().lastIndexOf(".")+1).equals("html"))||
					(fi.getName().substring(fi.getName().lastIndexOf(".")+1).equals("css"))){
				numAnalizados++;
			}
		} else {
			if (esFicheroComprimido(fi)) {
				unzipFile(fi);
				
		} else {
			File[] stf=l[i].listFiles();
			unzip(stf);

		}
		}
	}
}

private void unzipRar(File fich) {
	

	String fileName = fich.getName();

    String nombreCarpeta = fileName.substring(0, fileName.lastIndexOf(".rar"));
	
	try {
	    Archive archive = new Archive(new FileVolumeManager(fich));
	    FileHeader fileHeader = archive.nextFileHeader();
	    
	    String absolutePath = fich.getAbsolutePath();
	    String archiveDirectoryFileName = absolutePath.substring(0, absolutePath.indexOf(fileName)-1)+"\\"+nombreCarpeta;
	    //File directory=new File(archiveDirectoryFileName);
	    File directory=new File(nombreCarpeta);
	    if (!directory.exists()) {
	    	directory.mkdir();
	    }
	    List<FileHeader> lista = archive.getFileHeaders();
	    for (FileHeader fh : lista) System.out.println(fh.getFileNameString());
	    
	    while (fileHeader != null) {

	      String extractedFileName = "";

	      
	    	  if (fileHeader.isUnicode()) {
	    		  extractedFileName = fileHeader.getFileNameW();
	    		  } else {
	    			  extractedFileName = fileHeader.getFileNameString();	    			  
	    		  }
	      
	      String fullExtractedFileName = nombreCarpeta+"\\"+ extractedFileName;

	      File extractedFile = new File(fullExtractedFileName);


	        File parent = extractedFile.getParentFile();
	        System.out.println("Padre: "+parent.getAbsolutePath());
			if (parent!=null && !parent.exists() && !parent.mkdirs()) {
			    throw new IllegalStateException("Couldn't create dir: " + parent);
			}					
			if (!extractedFile.isDirectory() && (fullExtractedFileName.endsWith(".css") || fullExtractedFileName.endsWith(".html"))) {
	    	  FileOutputStream fileOutputStream = new FileOutputStream(extractedFile);		
	    	  numAnalizados++;
	    	  archive.extractFile(fileHeader, fileOutputStream);
	    	  fileOutputStream.flush();
	    	  Utils.close(fileOutputStream);
	    	  //fullExtractedFileName = fullExtractedFileName.replaceAll("\\\\", "/");
	    	  archivos.add(fullExtractedFileName);
	    	  System.out.println("FULL" + fullExtractedFileName);
	    	  }
	    	  fileHeader = archive.nextFileHeader();
//      }
	    }
	    archive.close();
	    
	  } catch(Exception e) {
		 e.printStackTrace();
		 
	  }
	carpetasZip.add(nombreCarpeta);
	
}
	
	 private void unzipFile(File fich) {
		 
		 	String s =fich.getName();
			String ext=s.substring(s.lastIndexOf(".")+1);
			if (ext.equals("rar")) {
				unzipRar(fich);
			}else {
			String nombreFicheroZip = fich.getName().substring(0, fich.getName().lastIndexOf("."+ext));
		//	System.out.println(nombreFicheroZip);
			ZipInputStream zis = null;
			FileOutputStream fos = null;
			BufferedOutputStream dest = null;
			
			
			
			if (fich.exists()) {
				try {
				Charset c = Charset.forName("CP1252");
				zis = new ZipInputStream(new FileInputStream(fich),c);
				ZipEntry entrada;
				
				
				while ((entrada = zis.getNextEntry())!=null){
					
					if (!entrada.isDirectory()) {
						if((entrada.getName().substring(entrada.getName().lastIndexOf(".")+1).equals("html"))||
								(entrada.getName().substring(entrada.getName().lastIndexOf(".")+1).equals("css"))){
							numAnalizados++;
						}
							int count;																
							byte data[] = new byte[10000];
							//La ruta original poniendola dentro de una carpeta con el nombre del fichero zip
							String rutaarchivo = nombreFicheroZip + "/"+entrada.getName(); 
							File targetFile = new File(rutaarchivo);
							File parent = targetFile.getParentFile();
							if (parent!=null && !parent.exists() && !parent.mkdirs()) {
							    throw new IllegalStateException("Couldn't create dir: " + parent);
							}																
							fos = new FileOutputStream(rutaarchivo);
							dest = new BufferedOutputStream(fos, 10000);
							while ((count = zis.read(data, 0, 10000)) != -1) {
								dest.write(data, 0, count);
							}
							dest.flush();
							archivos.add(rutaarchivo);
							
							//Utilidades.cerrar(zis);
							Utils.close(dest);
							
							
						
						}
						
					}
				
				carpetasZip.add(nombreFicheroZip);
				

							
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				
				close(zis);
				close(fos);
				close(dest); 
			}
			}
			}
	 }
	
	 
	 public void analize() {
		 unzip(files);
		 analyze_files();
	 }
	 
	public  void analyze_files() {

    	if (files.length!=0 && esFicheroComprimido(files[0])) origenComprimido=true;
		
		ExecutorService pool = null;
		
		
		int numHTMLAnalizados=0;
		int numCSSAnalizados=0;
		
		
		final Accumulator acc = new Accumulator(configuration);
		int aux=0;
		

				
		List<String> erroneosHTML = Collections.synchronizedList(new ArrayList<String>());
		List<String> erroneosCSS = Collections.synchronizedList(new ArrayList<String>());

		List<String> noExistenBelenus = Collections.synchronizedList(new ArrayList<String>());
		List<String> noCoincidenBelenus = Collections.synchronizedList(new ArrayList<String>());
		try {

			
				
				
			
					final CyclicBarrier barrera = new CyclicBarrier(numAnalizados+1);
					
					pool = Executors.newCachedThreadPool();
					for(String a:archivos){
					//	System.out.println(a);
						if (a.substring(a.lastIndexOf(".") + 1).equals("html")) {
							Validar v = new Validar(true, a, barrera,erroneosHTML,erroneosCSS,acc,
										cuasi,rutaCarpeta,noExistenBelenus,noCoincidenBelenus, origenComprimido);
							numHTMLAnalizados++;
							pool.execute(v);
						} else if (a.substring(a.lastIndexOf(".") + 1).equals("css")) {
							Validar v = new Validar(false, a, barrera,erroneosHTML,erroneosCSS,acc,
										cuasi,rutaCarpeta,noExistenBelenus,noCoincidenBelenus, origenComprimido);
							numCSSAnalizados++;
							pool.execute(v);
						}
					}
					barrera.await();
					
	
				
				
	
		} catch (InterruptedException e) { // TODO Auto-generated catch
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		} finally {
			if (pool != null)
				pool.shutdown();
			//cerrar(teclado);
			
			
			 
			    
			    
			
			if(s3.equals("")){
				
				s3="<p>The following requirements have not been automatically checked. Please, remember to check them: </p>";
				s3=s3+"<p class=plista> ";
				s3=s3+" - The website must contain a navigation bar that permits to browse among the different pages of it. Each HTML page must contain a bottom to go back to the root page.<br>";
				
				s3=s3+" - The development has been performed without using any automatic web development tool.<br>";				
				s3=s3+" - The HTML files must be saved in the encoding specified in the charset tag.<br>";
				s3=s3+" - The website must be displayed correctly in the specified browsers.<br>";
				s3=s3+" - The use of tables to format and layout the contents is not allowed.<br>";
				s3=s3+" - The use of frames to format and layout the contents is not allowed.";
				
				s1="<p>Your website fulfills the following requirements: </p> <p  class=plista>";
				s2="<p>Your website does not fulfill the following requirements:</p> <p  class=plista>";
				aux=s2.length();
				
				if (configuration.get("validateHTML")!= -1 && erroneosHTML.size()==0) {
					s1=s1+" - All the HTML files satisfy the HTML5 validation.<br>";
				} else if(configuration.get("validateHTML")!= -1) {
					if (erroneosHTML.size()==1){
						s2=s2+" - The file "+URLValidateHTML(erroneosHTML.get(0))+" does not fulfill the HTML5 validation.<br>";
						
					} else {
						s2=s2+" - The following files are not valid with respect to HTML5:";
						s2=s2+showListHTMLfiles(erroneosHTML);
					}
				}
				
				
				if (configuration.get("validateCSS")!= -1 && erroneosCSS.size()==0) {
					s1=s1+" - All the CSS files satisfy the CSS3 validation.<br>";
				} else if (configuration.get("validateCSS")!= -1){
					if (erroneosCSS.size()==1){
						s2=s2+" - The file "+URLValidateCSS(erroneosCSS.get(0))+" does not fulfill the CSS3 validation.<br>";
					} else {
						s2=s2+" - The following files are not valid with respect to CSS3:";
						s2=s2+showListHTMLfiles(erroneosCSS);
					}
				}
				
				if (configuration.get("universityServer")!= -1 && noExistenBelenus.size()==0 && noCoincidenBelenus.size()==0) 
				{
					s1=s1+" - All files are uploaded to the university server.<br>";
				}
				else if (configuration.get("universityServer")!= -1){
					if (noExistenBelenus.size()==1){
						s2=s2+" - The file "+Utils.fileName(noExistenBelenus.get(0),rutaCarpeta)+" is not uploaded to the server.<br>";
					} 
					if (noCoincidenBelenus.size()==1)
					{
						s2=s2+" - The file "+URLNoEqualUniversityServer(noCoincidenBelenus.get(0))+" is uploaded to the server but it is different from your local copy.<br>";
					}
					if (noExistenBelenus.size()>1) {
						s2=s2+" - The following files are not uploaded to the server:";
						s2=s2+  Utils.showListFiles(noExistenBelenus,rutaCarpeta) ;
					}
					if (noCoincidenBelenus.size()>1) {
						s2=s2+" - The following files are uploaded to the server but are different from your local copy of them:";
						s2=s2 +  showListFilesNoEqualsUniversityServer(noCoincidenBelenus);
					}
				}
				
			if (configuration.get("numHTML")!= -1 && numHTMLAnalizados>configuration.get("numHTML")){
				s1=s1+" - The website contains at least "+configuration.get("numHTML")+" different HTML files.<br>";
			} else if (configuration.get("numHTML")!= -1){
				s2=s2+" - The website does not contain at least " + configuration.get("numHTML") + " different HTML files.<br>";
			}
			
			if (configuration.get("numCSS")!=-1 && numCSSAnalizados>configuration.get("numCSS")){
				s1=s1+" - The website contains at least "+configuration.get("numCSS")+" different CSS files.<br>";
			} else if (configuration.get("numCSS")!=-1){
				s2=s2+" - The website does not contain at least "+configuration.get("numCSS")+" different CSS files.<br>";
			}
			
			
			acc.computeRequirements(rutaCarpeta);
			s1=s1+acc.satisfiedRequirements();
			s2=s2+acc.notSatisfiedRequirements();
			
			if (s2.length()==aux) s2="All the checked requirements are correct.";
			
			
			
			String estiloGen ="<style>"
					+ "body {font-family: Calibri,Candara,Segoe,Segoe UI,Optima,Arial,sans-serif;"
					+     "  font-size: 12px;   }"
					+ "p {margin-top: 0px;}"
					+ ".plista {margin-left: 12px;}"
					+ ".plistafich {margin-left: 40px;"
					+ "             margin-top: 0px;"
					+ "             margin-bottom: 0px;}";
		    String estiloTf1="body { color: black;}</style>";
		    String estiloTf2="body { color: black;}"
		    		+        "a {color: blue;}"
		    		+        "</style>";
		    String estiloTf3="</style>";
		    

		    s1="<html><head>"+estiloGen+estiloTf1+"</head><body>"+s1+"</p></body></html>";
		    s2="<html><head>"+estiloGen+estiloTf2+"</head><body>"+s2+"</p></body></html>";
		    s3="<html><head>"+estiloGen+estiloTf3+"</head><body>"+s3+"</p></body></html>";
		    
			tf1.setText(s1);
			tf2.setText(s2);
			tf2.setEditable(false);
			
			//System.out.println(s);
		}

		   						
			tf3.setText(s3);
			
			
		
			
			List<Integer> datos = new ArrayList<Integer>();
			datos.add(numAnalizados);
			datos.add(numHTMLAnalizados);
			datos.add(numCSSAnalizados);
			datos.add(erroneosHTML.size());
			datos.add(erroneosCSS.size());
			datos.add(acc.getHTMLwith2CSS());
			datos.add(acc.getHTMLwithoutCSS());
			
			datos.add(acc.getListHTMLwithoutCharset().size());
			datos.add(acc.getAbsoluteLinks());
			datos.add(acc.getListAbsoluteLinks().size());
			datos.add(noExistenBelenus.size());
			datos.add(noCoincidenBelenus.size());
			
			datos.add(acc.getB());
			datos.add(acc.getListaB().size());
			datos.add(acc.getBorder());
			datos.add(acc.getListaBorder().size());
			datos.add(acc.getHeight());
			datos.add(acc.getListaHeight().size());
			datos.add(acc.getWidth());
			datos.add(acc.getListaWidth().size());
			datos.add(acc.getFrame());
			datos.add(acc.getListaFrame().size());
			datos.add(acc.getForm());
			datos.add(acc.getTable());
			datos.add(acc.getUl());
			datos.add(acc.getOl());
			datos.add(acc.getVideo());
			//crearLogYMandarCorreo(datos);
		}
	}
	
	
	

	private String URLValidateHTML(String fich) {
		return ("<a href=\"http://www.unirioja.es/" + fich +"\">"+Utils.fileName(fich,rutaCarpeta)+"</a>");
		
	}
	
	
	private String URLValidateCSS(String fich) {
		return ("<a href=\"http://www.unirioja.es/" + fich +"\">"+Utils.fileName(fich,rutaCarpeta)+"</a>");		
	}
	
	private String URLNoEqualUniversityServer(String fich) {
		return ("<a href=\"http://www.unirioja.es/NoCoincideEnBelenus/" + fich +"\">"+Utils.fileName(fich,rutaCarpeta)+"</a>");
		
	}
	
	private String showListHTMLfiles(List<String> l) {
		String aux="</p><ul class=plistafich>";
		for (int i=0; i<l.size()-1; i++){
			aux=aux+URLValidateHTML(l.get(i))+"<br>";
		}
		aux=aux+URLValidateHTML(l.get(l.size()-1))+"</ul><p class=plista>";
		return aux;
	}
	
	private String showListFilesNoEqualsUniversityServer(List<String> l) {
		String aux="</p><ul class=plistafich>";
		for (int i=0; i<l.size()-1; i++){
			aux=aux+URLNoEqualUniversityServer(l.get(i))+"<br>";
		}
		aux=aux+URLNoEqualUniversityServer(l.get(l.size()-1))+"</ul><p class=plista>";
		return aux;
	}
	

	public static void close(Closeable o) {
		try {
			if (o != null)
				o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String args[]){ 

		JFrame mywindow = new JFrame("");
		mywindow.add(new Validator()); 
		mywindow.setSize(1015,630); //call necessary methods to make the window visible 
		mywindow.setVisible(true);
		}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void createStatistics(List<Integer> datos) 
	{
		//The module for sending email with information of the students attemps is deactivated in this version.
		/*
	    SimpleDateFormat sourceDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
	    String fecha = sourceDateFormat.format(new Date());
		
		String nombreFicheroLog = cuasi + "_" + fecha + ".txt";		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(nombreFicheroLog));)
		{
			for (int i = 0; i<datos.size() - 1; i++) 
			{
				
				osw.write(datos.get(i) + " ");
			}
			datos.get(datos.size() - 1);
			
			osw.flush();
			//Send the email
			String  d_email = "hidden@hidden.com",
		            d_password = "hidden",
		            d_host = "smtp.hidden.com",
		            d_port  = "465",
		            m_to = "hidden@hidden.com",
		            m_subject = "Logs",
		            m_text = "";
			EnviarEmail enviaremail = new EnviarEmail(d_email, d_password, d_host, d_port, 
					m_to, m_subject, m_text, nombreFicheroLog);
			enviaremail.enviarEmail();
			

		finally
		{
			File f = new File(nombreFicheroLog);
			f.delete();
		}*/
	}
	 
	
	public void deleteFolder() {
		if (carpetasZip != null) {
		for(String carpetazip: carpetasZip) {
			File carpeta = new File(carpetazip);
				Utils.deleteDir(carpeta);
		}

	}
	}
	

}
