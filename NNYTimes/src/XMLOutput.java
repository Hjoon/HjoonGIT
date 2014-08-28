import java.io.*;
import java.util.*;

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

public class XMLOutput {
	private static String outPath = "D:/Research/NYTimes/XMLParsed/";
	private static String path = "D:/Research/NYTimes/Whole data/";	
	private static String bpath = "D:/Research/NYTimes/";
	public static void main(String[] Args){
		
	}
	public static void CreateXML(String filename){
		try{
			String fpath = path+filename;
			String resultpath = outPath+filename;
			File file = new File(fpath);
			String title, Line, ttitle, ccont;
			StringTokenizer token;
			String Mon, Dat, Yr;
			Calendar cal = Calendar.getInstance();
			DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBld = docFac.newDocumentBuilder();
			Document doc = docBld.parse(file);
			doc.getDocumentElement().normalize();
			NodeList aList = doc.getElementsByTagName("Article");
			Node article; NodeList aNList; Element eElement;Element auxDate;Node attrs;
			for(int i = 0; i<aList.getLength(); i++){
				article = aList.item(i);
				if(!article.getNodeName().equals("Article"))continue;
				aNList = article.getChildNodes();
				eElement = (Element) article;
				ttitle = eElement.getElementsByTagName("Title").item(0).getTextContent();
				ttitle = ttitle.replace("<strong>", "").replace("</strong>", "").replace(" - New York Times", "").replace(" - New York ...", "").replace(" - New ...", "").replace("&#39;", "'");
				Line = eElement.getElementsByTagName("Content").item(0).getTextContent();
				token = new StringTokenizer(Line, "\n");
				title = token.nextToken();
				int index = title.indexOf("Published:");
				try{
				title = title.substring(index+11);
				Mon = title.substring(0,title.indexOf(" ")).substring(0,3);
				Dat = title.substring(title.indexOf(" ")).substring(1,3);
				Yr = title.substring(title.indexOf(" ")).substring(5,9);
				cal.set(Integer.parseInt(Yr), ReturnMonth(Mon), Integer.parseInt(Dat));
				ccont="";
				while(token.hasMoreTokens()){
					ccont+=token.nextToken()+"\n";
				}
				}catch(Exception ee){
					title = eElement.getElementsByTagName("Date").item(0).getTextContent();
					Yr = title.substring(0,4);Dat = title.substring(8);
					cal.set(Integer.parseInt(Yr), Integer.parseInt(title.substring(5,7))-1, Integer.parseInt(Dat));
					ccont=Line;
				}
				for(int j = 0; j<aNList.getLength(); j++){
					attrs = aNList.item(j);
					if(attrs.getNodeType() == Node.ELEMENT_NODE){
						if(attrs.getNodeName().equals("Id")){}
						else if(attrs.getNodeName().equals("Title")){
							attrs.setTextContent(ttitle);
						}
						else if(attrs.getNodeName().equals("Date")){
							attrs.setTextContent(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE));
						}
						else if(attrs.getNodeName().equals("Section")){}
						else if(attrs.getNodeName().equals("Content")){
							ccont = StringEscapeUtils.unescapeHtml4(ccont);
							ccont = escp(ccont);
							ccont = escp2(ccont);
							ccont = ccont.replaceAll("\n[\\s]*", "\n");
							attrs.setTextContent(ccont);
							//System.out.println(ccont);
							//System.out.println();
						}
						else{article.removeChild(attrs);}
						
					}
				}
				auxDate = doc.createElement("AuxDate");
				auxDate.appendChild(doc.createTextNode(cal.getTimeInMillis()/86400000+""));
				article.appendChild(auxDate);
			}
			
			TransformerFactory tFac = TransformerFactory.newInstance();
			Transformer tform = tFac.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(resultpath));
			tform.transform(source, result);
			System.out.println(filename+" "+"Done!");
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
	private static int ReturnMonth(String name){
		ArrayList<String> Mon = new ArrayList<String>();
		Mon.add("Jan");Mon.add("Feb");Mon.add("Mar");Mon.add("Apr");Mon.add("May");Mon.add("Jun");
		Mon.add("Jul");Mon.add("Aug");Mon.add("Sep");Mon.add("Oct");Mon.add("Nov");Mon.add("Dec");
		return Mon.indexOf(name);
	}
	private static String escp2(String entry){
		entry = entry.replace("''", "\"");
		entry = entry.replace("--", "-");
		return entry;
	}
	private static String escp(String entry){
		entry = entry.replace("&#0032;"," ");
		entry = entry.replace("&#0038;","&");
		entry = entry.replace("&#0149;","*");
		entry = entry.replace("&#0151;","-");
		entry = entry.replace("&#0160;"," ");
		entry = entry.replace("&#0163;","$");
		entry = entry.replace("&#0182;","*");
		entry = entry.replace("&#0195;","A");
		entry = entry.replace("&#0201;","E");
		entry = entry.replace("&#0225;","a");
		entry = entry.replace("&#0226;","a");
		entry = entry.replace("&#0228;","a");
		entry = entry.replace("&#0231;","c");
		entry = entry.replace("&#0233;","e");
		entry = entry.replace("&#0234;","e");
		entry = entry.replace("&#0235;","e");
		entry = entry.replace("&#0237;","I");
		entry = entry.replace("&#0239;","I");
		entry = entry.replace("&#0241;","n");
		entry = entry.replace("&#0243;","o");
		entry = entry.replace("&#0244;","o");
		entry = entry.replace("&#0246;","o");
		entry = entry.replace("&#0250;","u");
		entry = entry.replace("&#126;","~");
		entry = entry.replace("&#167;","*");
		entry = entry.replace("&#189;","쩍");
		entry = entry.replace("&#224;","a");
		entry = entry.replace("&#225;","a");
		entry = entry.replace("&#227;","a");
		entry = entry.replace("&#228;","a");
		entry = entry.replace("&#231;","c");
		entry = entry.replace("&#232;","e");
		entry = entry.replace("&#233;","e");
		entry = entry.replace("&#235;","e");
		entry = entry.replace("&#237;","I");
		entry = entry.replace("&#241;","n");
		entry = entry.replace("&#243;","o");
		entry = entry.replace("&#246;","o");
		entry = entry.replace("&#250;","u");
		entry = entry.replace("&#38;","&");
		entry = entry.replace("&#8212;","-");
		entry = entry.replace("&nbsp;"," ");
		entry = entry.replace("&amp;","&");
		entry = entry.replace("&lt;","<");
		entry = entry.replace("&gt;",">");
		entry = entry.replace("	"," ");
		entry = entry.replace(" "," ");
		entry = entry.replace("혩","");
		entry = entry.replace("혪","");
		entry = entry.replace("혫","");
		entry = entry.replace("혬","");
		entry = entry.replace("혮","");
		entry = entry.replace("혯","");
		entry = entry.replace("혰","");
		entry = entry.replace("혲","");
		entry = entry.replace("혴","");
		entry = entry.replace("혻"," ");
		entry = entry.replace("쨌",".");
		entry = entry.replace("쨩",">");
		entry = entry.replace("�","A");
		entry = entry.replace("횁","A");
		entry = entry.replace("횂","A");
		entry = entry.replace("횄","A");
		entry = entry.replace("횆","A");
		entry = entry.replace("횇","A");
		entry = entry.replace("횈","Ae");
		entry = entry.replace("횉","C");
		entry = entry.replace("횊","E");
		entry = entry.replace("횋","E");
		entry = entry.replace("횏","E");
		entry = entry.replace("횒","I");
		entry = entry.replace("횓","I");
		entry = entry.replace("횗","N");
		entry = entry.replace("횘","O");
		entry = entry.replace("횜","O");
		entry = entry.replace("횥","U");
		entry = entry.replace("횪","a");
		entry = entry.replace("찼","a");
		entry = entry.replace("창","a");
		entry = entry.replace("찾","a");
		entry = entry.replace("채","a");
		entry = entry.replace("책","a");
		entry = entry.replace("챌","c");
		entry = entry.replace("챔","e");
		entry = entry.replace("챕","e");
		entry = entry.replace("챗","e");
		entry = entry.replace("챘","e");
		entry = entry.replace("챠","I");
		entry = entry.replace("챤","I");
		entry = entry.replace("챦","I");
		entry = entry.replace("챨","th");
		entry = entry.replace("챰","n");
		entry = entry.replace("챵","o");
		entry = entry.replace("처","o");
		entry = entry.replace("척","o");
		entry = entry.replace("천","o");
		entry = entry.replace("철","o");
		entry = entry.replace("첩","oe");
		entry = entry.replace("첫","u");
		entry = entry.replace("첬","u");
		entry = entry.replace("청","u");
		entry = entry.replace("체","u");
		entry = entry.replace("첵","y");
		entry = entry.replace("첸","p");
		entry = entry.replace("훶","c");
		entry = entry.replace("��"," ");
		entry = entry.replace("��"," ");
		entry = entry.replace("��"," ");
		entry = entry.replace("��","-");
		entry = entry.replace("��","-");
		entry = entry.replace("��","'");
		entry = entry.replace("��","'");
		entry = entry.replace("��","\"");
		entry = entry.replace("��","\"");
		entry = entry.replace("��","*");
		entry = entry.replace("��","*");
		entry = entry.replace("��","<");
		entry = entry.replace("��",">");
		entry = entry.replace("�걚","/");
		entry = entry.replace("�궗","$");
		entry = entry.replace("�꽓","");
		entry = entry.replace("�뿈","*");
		entry = entry.replace("�솭","*");
		entry = entry.replace("�솯","*");
		entry = entry.replace("��"," ");
		entry = entry.replace("�겦","");
		entry = entry.replace("竊�","<");
		entry = entry.replace("占�","");

		return entry;

	}
}
