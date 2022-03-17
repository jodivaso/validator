package utils;

import java.util.Collections;
import java.util.*;

public class Accumulator {
	private int table;
	private int ul;
	private int ol;
	private int video;
	private int form;
	
	private int width;
	private int height;
	private int b;
	private int frame;
	private int border;
	private int HTMLsinCharset;
	private int HTMLsinCSS;
	private int HTMLcon2CSS;
	
	private List<String> listaWidth;
	private List<String> listaHeight;
	private List<String> listaB;
	private List<String> listaFrame;
	private List<String> listaBorder;
	
	private List<String> listaHTMLsinCharset;
	private List<String> listaHTMLsinCSS;
	private List<String> listaHTMLcon2CSS; 
			
	private String satisfied, notSatisfied;
	
	private List<String> listaEnlacesAbsolutos;
	private int enlacesAbsolutos;
	private HashMap<String, Integer> configuration;
	
	public Accumulator(HashMap<String, Integer> configuration) {
		super();
		this.table = 0;
		this.ul = 0;
		this.ol = 0;
		this.video = 0;
		this.form = 0;
		this.width = 0;
		this.height = 0;
		this.b = 0;
		this.border=0;
		this.frame=0;
		this.HTMLsinCSS=0;
		this.HTMLcon2CSS=0;		
		this.listaWidth = Collections.synchronizedList(new ArrayList<String>());
		this.listaHeight = Collections.synchronizedList(new ArrayList<String>());
		this.listaB = Collections.synchronizedList(new ArrayList<String>());
		this.listaFrame= Collections.synchronizedList(new ArrayList<String>());
		this.listaBorder= Collections.synchronizedList(new ArrayList<String>());
		this.listaHTMLsinCharset = Collections.synchronizedList(new ArrayList<String>());
		this.listaHTMLsinCSS = Collections.synchronizedList(new ArrayList<String>());
		this.listaHTMLcon2CSS = Collections.synchronizedList(new ArrayList<String>());
		this.satisfied="";
		this.notSatisfied="";
		this.listaEnlacesAbsolutos = Collections.synchronizedList(new ArrayList<String>());
		this.enlacesAbsolutos = 0;
		this.configuration = configuration;		
	}
	
	public List<String> getListaWidth() {
		return listaWidth;
	}

	public synchronized void añadirListaWidth(String fichero) {
		this.listaWidth.add(fichero);
	}

	public List<String> getListaHeight() {
		return listaHeight;
	}

	public synchronized void añadirListaHeight(String fichero) {
		this.listaHeight.add(fichero);
	}

	public List<String> getListaB() {
		return listaB;
	}

	public synchronized void añadirListaB(String fichero) {
		this.listaB.add(fichero);
	}
	
	public List<String> getListaFrame() {
		return listaFrame;
	}

	public synchronized void añadirListaFrame(String fichero) {
		this.listaFrame.add(fichero);
	}
	
	public List<String> getListaBorder() {
		return listaBorder;
	}

	public synchronized void añadirListaBorder(String fichero) {
		this.listaBorder.add(fichero);
	}
	
	public List<String> getListaCharset() {
		return listaHTMLsinCharset;
	}

	public synchronized void addListHTMLwithoutCharset(String fichero) {
		this.listaHTMLsinCharset.add(fichero);
	}

	public int getTable() {
		return table;
	}
	
	public synchronized void incrementTable(int table) {
		this.table += table;
	}
	public int getUl() {
		return ul;
	}
	public synchronized void incrementUl(int ul) {
		this.ul += ul;
	}
	public int getOl() {
		return ol;
	}
	public synchronized void incrementOl(int ol) {
		this.ol += ol;
	}
	public int getVideo() {
		return video;
	}
	public synchronized void incrementVideo(int video) {
		this.video +=video;
	}
	public int getForm() {
		return form;
	}
	public synchronized void incrementForm(int form) {
		this.form += form;
	}
	public int getWidth() {
		return width;
	}
	public synchronized void incrementWidth(int width) {
		this.width += width ;
	}
	public int getHeight() {
		return height;
	}
	public synchronized void incrementHeight(int height) {
		this.height+= height;
	}
	public int getB() {
		return b;
	}
	public synchronized void incrementB(int b) {
		this.b += b;
	}
	
	public int getFrame() {
		return frame;
	}
	public synchronized void incrementFrame(int frame) {
		this.frame+= frame;
	}
	
	public int getBorder() {
		return border;
	}
	public synchronized void incrementBorder(int border) {
		this.border+= border;
	}	
	
	
	public List<String> getListHTMLwithoutCharset() {
		return listaHTMLsinCharset;
	}


	
	public synchronized void incrementHTMLwithoutCharset(int b) {
		this.HTMLsinCharset += b;
	}

	
	public List<String> getListHTMLwithoutCSS() {
		return listaHTMLsinCSS;
	}

	public synchronized void addlistHTMLwithoutCSS(String fichero) {
		this.listaHTMLsinCSS.add(fichero);
	}
	
	public int getHTMLwith2CSS() {
		return HTMLcon2CSS;
	}
	public synchronized void incrementHTMLwith2CSS(int b) {
		this.HTMLcon2CSS += b;
	}
	
	public int getHTMLwithoutCSS() {
		return HTMLsinCSS;
	}
	public synchronized void incrementHTMLwithoutCSS(int b) {
		this.HTMLsinCSS += b;
	}
	
	public List<String> getListHTMLwith2CSS() {
		return listaHTMLcon2CSS;
	}

	public synchronized void addlistHTMLwith2CSS(String fichero) {
		this.listaHTMLcon2CSS.add(fichero);
	}
	
	
	public List<String> getListAbsoluteLinks() {
		return listaEnlacesAbsolutos;
	}

	public synchronized void addListAbsoluteLinks(String fichero) {
		this.listaEnlacesAbsolutos.add(fichero);
	}
	
	
	public int getAbsoluteLinks() {
		return enlacesAbsolutos;
	}
	
	public synchronized void incrementAbsoluteLinks(int EnlacesLocal) {
		this.enlacesAbsolutos += EnlacesLocal;
	}
	

	@Override
	public String toString() {
		return "More info: \r\n table=" + table + "\r\n ul=" + ul + "\r\n ol=" + ol + "\r\n video=" + video + "\r\n form=" + form
				+ "\r\n width=" + width + "\r\n height=" + height + "\r\n b=" + b  
				+ "\r\n HTMLsinCSS=" + HTMLsinCSS + "\r\n HTMLcon2CSS=" + HTMLcon2CSS
				+ "\r\n listaWidth=" + listaWidth + "\r\n listaHeight=" + listaHeight + "\r\n listaB=" + listaB
				+ "\r\n listaHTMLsinCSS=" + listaHTMLsinCSS + "\r\n listaHTMLcon2CSS=" + listaHTMLcon2CSS + "";
	}
	
	public void computeRequirements(String padre){
		
		
		if(configuration.get("HTMLwithoutCSS")!=-1 && configuration.get("HTMLwith2CSS")!=-1 && HTMLsinCSS<=configuration.get("HTMLwithoutCSS") && HTMLcon2CSS>=configuration.get("HTMLwith2CSS")){
			satisfied=satisfied+" - "+configuration.get("HTMLwithoutCSS")+" HTML files are not linked to at least one CSS, and at least "+configuration.get("HTMLwith2CSS")+" must be linked to at least two CSS.<br>";
		} else if (configuration.get("HTMLwithoutCSS")!=-1 && HTMLsinCSS<=configuration.get("HTMLwithoutCSS")){
			satisfied=satisfied+" - "+configuration.get("HTMLwithoutCSS")+" HTML files are not linked to at least one CSS.<br>";
			notSatisfied=notSatisfied+" - There are not at least "+configuration.get("HTMLwith2CSS")+" HTML files linked to two CSS files.<br>";
		} else if (configuration.get("HTMLwith2CSS")!=-1 && HTMLcon2CSS<configuration.get("HTMLwith2CSS")){
			notSatisfied=notSatisfied+" - There are not at least "+configuration.get("HTMLwith2CSS")+" HTML files linked to two CSS files.<br>";
		}// else if(HTMLsinCSS==1){
	//			noCumplidos=noCumplidos+" - The file "+Utilidades.nombreFichero(listaHTMLsinCSS.get(0),padre)+" is not linked to any CSS file.<br>";
		//} 
	else if (configuration.get("HTMLwithoutCSS")!=-1){
				notSatisfied=notSatisfied+" - The following files are not linked to any CSS file:";
				notSatisfied=notSatisfied+Utils.showListFiles(listaHTMLsinCSS,padre);
			}					
		
		if(configuration.get("HTMLnoCharset")!= -1 && HTMLsinCharset<=configuration.get("HTMLnoCharset")){
			satisfied=satisfied+" - "+configuration.get("HTMLnoCharset")+" HTML files does not contain the encoding information (charset). <br>";
		} /*else if (HTMLsinCharset==1) {
			noCumplidos=noCumplidos+" - The file "+Utilidades.nombreFichero(listaHTMLsinCharset.get(0),padre)+" does not have the charset tag. <br>";
		} */else if(configuration.get("HTMLnoCharset")!=-1){
			notSatisfied=notSatisfied+" - The following files do not specify its encoding: ";
			notSatisfied=notSatisfied+Utils.showListFiles(listaHTMLsinCharset,padre);
		}
		
		
		int numAspecto = listaWidth.size()+listaHeight.size()+listaB.size()+listaBorder.size();
		if (configuration.get("numAspect")!=-1 && numAspecto<=configuration.get("numAspect")){
			satisfied=satisfied+" - "+configuration.get("numAspect")+" HTML files contain style attributes. <br>";
			
		} else if(configuration.get("numAspect")!=-1){
			
			if(width>0){
			if (listaWidth.size()==1){
				notSatisfied=notSatisfied+" - The file "+Utils.fileName(listaWidth.get(0),padre)+" contains the width attribute, which is a style attribute. <br>";
			} else {
				notSatisfied=notSatisfied+" - The following files contain the width attribute, which is a style attribute: ";
				notSatisfied=notSatisfied+Utils.showListFiles(listaWidth,padre);
			}
			}
			if(height>0){
			if (listaHeight.size()==1){
				notSatisfied=notSatisfied+" - The file "+Utils.fileName(listaHeight.get(0),padre)+" contains the height attribute, which is a style attribute. <br>";
			} else {
				notSatisfied=notSatisfied+" - The following files contain the height attribute, which is a style attribute: ";
				notSatisfied=notSatisfied+Utils.showListFiles(listaHeight,padre);
			}
			}
			if(b>0){
			if (listaB.size()==1){
				notSatisfied=notSatisfied+" - The file "+Utils.fileName(listaB.get(0),padre)+" contains the &lt;b&gt tag; which is a style element. <br>";
			} else {
				notSatisfied=notSatisfied+" - The following files contain the &lt;b&gt tag; which is a style element: ";
				notSatisfied=notSatisfied+Utils.showListFiles(listaB,padre);
			}}
			
			if(border>0){
				if (listaBorder.size()==1){
					notSatisfied=notSatisfied+" - The file "+Utils.fileName(listaBorder.get(0),padre)+" contain the border attribute, which is a style attribute.<br>";
				} else {
					notSatisfied=notSatisfied+" - The following files contain the border attribute, which is a style attribute: ";
					notSatisfied=notSatisfied+Utils.showListFiles(listaBorder,padre);
				}}
			
		}
		
		if(configuration.get("AbsoluteLinks")!=-1 && enlacesAbsolutos<=configuration.get("AbsoluteLinks")){
			satisfied += " - The local resources are linked via relative paths.";
		}
		else if(configuration.get("AbsoluteLinks")!=-1) {
			if (listaEnlacesAbsolutos.size()==1) 
			{
				notSatisfied = notSatisfied + "- The file "+Utils.fileName(listaEnlacesAbsolutos.get(0),padre)+" contains absolute paths to your own website";
			}
			else {
				notSatisfied=notSatisfied+" - The following files contain absolute paths to your own website: ";
				notSatisfied=notSatisfied+Utils.showListFiles(listaEnlacesAbsolutos,padre);				
			}
		}
		
		


	}
	
	public String satisfiedRequirements (){
		return satisfied;
	}
	
	
	public String notSatisfiedRequirements (){
		return notSatisfied;
	}
	
	
	

	
}
