import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.apache.commons.lang3.StringEscapeUtils;

public class XMLFilterSingle {
	private static String outPath = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/XMLParsed3/";
	private static String path = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/XMLParsed2/";	
	private static String bpath = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/";
	static HashSet<String> fset = new HashSet<String>();
	static HashMap<String, Integer> pfxmap = new HashMap<String, Integer>();

	public static void main(String[] Args){
		for(File file : new File(path).listFiles()){
			FilterXML(file.getName());	
		}
		for(String sr : pfxmap.keySet()){
			System.out.println(sr+"*"+pfxmap.get(sr));
		}
	}
	public static void FilterXML(String filename){
		try{
			String fpath = path+filename;
			String resultpath = outPath+filename;
			String filterpath = bpath+"TitleRemList";
			String line;
			BufferedReader in = new BufferedReader(new FileReader(filterpath));
			while((line = in.readLine())!=null){
				fset.add(line);
			}
			in.close();
			File file = new File(fpath);
			String title, Line, ccont, section, aId;
			StringTokenizer token;
			HashSet<String> secset = new HashSet<String>();
			
			DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBld = docFac.newDocumentBuilder();
			Document doc = docBld.parse(file);
			doc.getDocumentElement().normalize();
			NodeList aList = doc.getElementsByTagName("Article");
			Pattern p = Pattern.compile("^[ a-zA-Z]+(;|:)");
			Matcher m;String match;
			Node article, nArticle; NodeList aNList; Element eElement;Element auxDate;Node attrs;

			for(int i = 0; i<aList.getLength(); i++){
				article = aList.item(i);
				eElement = (Element) article;
				//ccont = eElement.getElementsByTagName("Content").item(0).getTextContent();
				title = eElement.getElementsByTagName("Title").item(0).getTextContent();
				//section = eElement.getElementsByTagName("Section").item(0).getTextContent();
				//aId = eElement.getElementsByTagName("Id").item(0).getTextContent();
				m = p.matcher(title);
				if(m.find()){
					match = m.group();
					pfxmap.put(match, pfxmap.containsKey(match)?pfxmap.get(match)+1:1);
				}
			}
			
			if(true)return;
			TransformerFactory tFac = TransformerFactory.newInstance();
			Transformer tform = tFac.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(resultpath));
			tform.transform(source, result);
			//System.out.println(filename+" "+"Done!");
			
		}catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (TransformerException tfe) {
			tfe.printStackTrace();
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}catch (SAXException sae) {
			sae.printStackTrace();
		}catch(Exception e){e.printStackTrace();}
	}
	public static void dedup(String filename){
		try{
			String fpath = path+filename;
			String resultpath = outPath+filename;
			File file = new File(fpath);
			String title, Line, ccont, section, aId;
			StringTokenizer token;
			
			HashSet<String> secset = new HashSet<String>();
			
			DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBld = docFac.newDocumentBuilder();
			Document doc = docBld.parse(file);
			doc.getDocumentElement().normalize();
			
			NodeList aList = doc.getElementsByTagName("Article");
			Node article, nArticle; NodeList aNList; Element eElement;Element auxDate;Node attrs;
			for(int i = 0; i<aList.getLength(); i++){
				article = aList.item(i);
				eElement = (Element) article;
				ccont = eElement.getElementsByTagName("Content").item(0).getTextContent();
				if(ccont.length()<150)continue;
				title = ccont.substring(0,150);
				if(secset.contains(title)){
					article.getParentNode().removeChild(article);i--;
				}else{
					secset.add(title);
				}
				//aId = eElement.getElementsByTagName("Id").item(0).getTextContent();
				
			}

			
			TransformerFactory tFac = TransformerFactory.newInstance();
			Transformer tform = tFac.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(resultpath));
			tform.transform(source, result);
			//System.out.println(filename+" "+"Done!");
		}catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (TransformerException tfe) {
			tfe.printStackTrace();
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}catch (SAXException sae) {
			sae.printStackTrace();
		}catch(Exception e){e.printStackTrace();}
	}
	public static void trimmer(String filename){
		String fpath = path+filename;
		String resultpath = outPath+filename;
		File file = new File(fpath);
		String line;
		try{
			BufferedReader in = new BufferedReader(new FileReader(file));
			PrintWriter out = new PrintWriter(new FileOutputStream(resultpath));
			while((line = in.readLine())!=null){
				if(line.trim().equals(""))continue;
				out.println(line);
			}
			in.close();
			out.close();
		}catch(Exception e){e.printStackTrace();}
	}
	private static void pFixes(){
		/*		
				if(section.equals("Health")||section.equals("Sports")||section.equals("Science")||section.equals("Arts")||section.equals("Theater")||section.equals("NYRegion")||section.equals("Television")){article.getParentNode().removeChild(article);i--;}
				else if(ccont.replace("\n", "").trim().equals("")){article.getParentNode().removeChild(article);i--;}
				else if(ccont.contains("*3*** COMPANY REPORTS ***3*")){article.getParentNode().removeChild(article);i--;}
				else if((title.length()>14)&&title.substring(0,14).equals("Corrections - ")){article.getParentNode().removeChild(article);i--;}
				else if(title.equals("Key Rates")||title.equals("KEY RATES")){article.getParentNode().removeChild(article);i--;}
				else if (title.equals("Executive Changes")||title.equals("EXECUTIVE CHANGES")||title.contains("Paid Notice:")){article.getParentNode().removeChild(article);i--;}
				else if (title.contains(" REVIEW - ")||title.contains(" Review - ")){article.getParentNode().removeChild(article);i--;}
				else if(section.equals("US")||section.equals("World")){article.getParentNode().removeChild(article);i--;}
				else if(ccont.contains("To the Editor:")){article.getParentNode().removeChild(article);i--;}
				else if(ccont.contains("DEAR DIARY:")){article.getParentNode().removeChild(article);i--;}
				else if(title.equals("Q and A")){article.getParentNode().removeChild(article);i--;}
				else if(title.equals("Auctions")){article.getParentNode().removeChild(article);i--;}
				else if(title.equals("PROFITS SCOREBOARD")){article.getParentNode().removeChild(article);i--;}
				else if(title.equals("ANSWERS TO QUIZ")){article.getParentNode().removeChild(article);i--;}
				else if(title.equals("House of Music Benefit")){article.getParentNode().removeChild(article);i--;}
				else if(title.equals("WEEKENDER GUIDE")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>4&&title.substring(0,4).equals("ART;")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>5&&title.substring(0,5).equals("FILM;")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>6&&title.substring(0,6).equals("ESSAY;")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>7&&title.substring(0,7).equals("HEALTH;")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>7&&title.substring(0,7).equals("Review/")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>8&&title.substring(0,8).equals("THEATER;")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>8&&title.substring(0,8).equals("THE LAW;")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>8&&title.substring(0,8).equals("THE LAW:")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>8&&title.substring(0,8).equals("Message:")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>9&&title.substring(0,9).equals("WINE TALK")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>10&&title.substring(0,10).equals("EDUCATION;")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>11&&title.substring(0,11).equals("TELEVISION;")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>11&&title.substring(0,11).equals("HOME VIDEO;")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>15&&title.substring(0,15).equals("CONSUMER RATES;")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>15&&title.substring(0,15).equals("CONSUMER RATES;")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>16&&title.substring(0,16).equals("BUSINESS PEOPLE;")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>16&&title.substring(0,16).equals("WASHINGTON TALK:")){article.getParentNode().removeChild(article);i--;}
				else if(title.length()>17&&title.substring(0,17).equals("Dining Out Guide:")){article.getParentNode().removeChild(article);i--;}
		 */
	}
}