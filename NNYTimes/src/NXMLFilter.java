import java.io.*;
import java.util.*;

import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.apache.commons.lang3.StringEscapeUtils;

class NXMLFilter{
	
	public static void main(String[] Args){
		try{
			String outPath = "D:/Research/NYTimes/Cdata/";
			DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBld = docFac.newDocumentBuilder();
			Document ndoc;
			Document doc;
			NodeList aList, pList, hList, tList, bList;
			Calendar cal = Calendar.getInstance();
			Node article, title, body = null, aId, head;
			String sTitle = "", sHead = "", sBody, sDate, sMon, pdat = "", pmon = "", pyrs = "";
			for(int y = 1987; y<2008; y++){
				String path = String.format("D:/Research/NYTimes/Bdata/%d/", y);
				for(File file : new File(path).listFiles()){
					ndoc = docBld.newDocument();
					Element root = ndoc.createElement("NYTimes");
					ndoc.appendChild(root);
					doc = docBld.parse(file);
					System.out.println(y+""+file.getName());
					doc.getDocumentElement().normalize();
					aList = doc.getElementsByTagName("Article");
					for(int i = 0; i<aList.getLength(); i++){
						Element Article = ndoc.createElement("Article");
						//ndoc.appendChild(Article);
						Element Title = ndoc.createElement("Title");
						Element AID = ndoc.createElement("Id");
						Element Date = ndoc.createElement("Date");
						Element AuxDate = ndoc.createElement("AuxDate");
						
						article = aList.item(i);
						hList = article.getChildNodes();
						for(int z = 0; z<hList.getLength(); z++){
							sHead = hList.item(z).getNodeName();
							if(sHead.equals("head")){
								head = hList.item(z);
								tList = head.getChildNodes();
								for(int q = 0; q<tList.getLength(); q++){
									sTitle = tList.item(q).getNodeName();
									if(sTitle.equals("title")){
										title = tList.item(q);
										Title.appendChild(ndoc.createTextNode(title.getTextContent()));
									}else if(sTitle.equals("meta")){
										sDate = ((Element) tList.item(q)).getAttribute("name");
										sMon = ((Element) tList.item(q)).getAttribute("content");
										if(sDate.equals("publication_day_of_month"))pdat = sMon;
										else if(sDate.equals("publication_month"))pmon = sMon;
										else if(sDate.equals("publication_year"))pyrs = sMon;
									}else if(sTitle.equals("docdata")){
										aId = tList.item(q).getFirstChild();
										AID.appendChild(ndoc.createTextNode(((Element)aId).getAttribute("id-string")));
									}
								}
							}else if(sHead.equals("body")){
								body = hList.item(z);
								bList = body.getChildNodes();
								for(int q = 0; q<bList.getLength(); q++){
									sBody = bList.item(q).getNodeName();
									if(sBody.equals("body.content")){
										body = bList.item(q);
										bList = body.getChildNodes();
										for(int w = 0; w<bList.getLength(); w++){
											sBody = ((Element) bList.item(w)).getAttribute("class");
											if(sBody.equals("full_text")){
												body = bList.item(w);
											}
										}
									}
								}
							}
						}
						
						Date.appendChild(ndoc.createTextNode(pdat+"-"+pmon+"-"+pyrs));
						cal.set(Integer.parseInt(pyrs), Integer.parseInt(pmon), Integer.parseInt(pdat));
						AuxDate.appendChild(ndoc.createTextNode(""+cal.getTimeInMillis()/86400000));
						pList = body.getChildNodes();
						String cdat = "";
						for(int z = 1; z<pList.getLength(); z++){
							if(pList.item(z).getTextContent().length()<25)continue;
							cdat += pList.item(z).getTextContent()+"\n";
						}
						Element CDat = ndoc.getDocumentElement();
						CDATASection Cdata = ndoc.createCDATASection(cdat);
						CDat.appendChild(Cdata);
						Article.appendChild(AID);
						Article.appendChild(Date);
						Article.appendChild(AuxDate);
						Article.appendChild(Title);
						Article.appendChild(Cdata);
						root.appendChild(Article);
					}
					prettyPrint(ndoc, outPath+y+file.getName());
				}
			}
		}catch(Exception e){e.printStackTrace();}
	}
	public static final void prettyPrint(Document xml, String path) throws Exception {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		Writer out = new StringWriter();
		tf.transform(new DOMSource(xml), new StreamResult(out));
		PrintWriter a = new PrintWriter(new FileOutputStream(path));
		a.print(out.toString());
		a.close();
	}
}