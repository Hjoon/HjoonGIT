import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ie.*;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.*;

public class NameCollecter {

	public static void main(String[] args) throws IOException {
	    try{
			PrintWriter out;
		    String path = "D:/Research/NYTimes/XMLParsed3/";
		    String bpath = "D:/Research/NYTimes/";
		    String line;
		    HashSet<String> nameset = new HashSet<String>();
		    Properties props = new Properties();
		    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		    Annotation annotation;
		    
		    List<CoreMap> sentences;
		    String word, ne, tword;
		    
		    DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBld = docFac.newDocumentBuilder();
			StringTokenizer token;
			Document doc;
			NodeList aList;
			HashMap<String, String> linkset = new HashMap<String, String>();
			Node article, nArticle; NodeList aNList; Element eElement;Element auxDate;Node attrs;
			String ccont, cont, seg, aId;
			int ac = 0;
			String link;
			for(File file : new File(path).listFiles()){
				doc = docBld.parse(file);
				doc.getDocumentElement().normalize();
				aList = doc.getElementsByTagName("Article");
				for(int i = 0; i<aList.getLength(); i++){
					System.out.println(i);
					article = aList.item(i);
					eElement = (Element) article;
					line = eElement.getElementsByTagName("Content").item(0).getTextContent();
					aId = eElement.getElementsByTagName("Id").item(0).getTextContent();
					//line = new String(Files.readAllBytes(Paths.get(path+file.getName())));
			        annotation = new Annotation(line); 
				    pipeline.annotate(annotation);
				    tword="";
				    sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
				    for(CoreMap sentence: sentences) {
				    	for (CoreLabel ctoken: sentence.get(TokensAnnotation.class)) {
				    		word = ctoken.get(TextAnnotation.class);
					          ne = ctoken.get(NamedEntityTagAnnotation.class);
					          
					          if(!tword.equals("")){
					        	  if(ne.equals("ORGANIZATION")){
					        		  tword+=(" "+word);
					        	  }
					        	  else if(word.equals("and")){
					        		  tword+=(" "+word);
					        	  }
					        	  else{
					        		  if(tword.substring(tword.length()-3).equals("and"))tword = tword.substring(0,tword.length()-3);
					        		  if(!tword.trim().equals(""))nameset.add(tword.trim());
					        		  tword="";
					        	  }
					          }else{
					        	  if(ne.equals("ORGANIZATION")){
					        		  tword+=(" "+word);
					        	  }
					          }
					     }
			        }
				}
				break;
			}
	    }
		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}catch (SAXException sae) {
			sae.printStackTrace();
		}catch (Exception e){e.printStackTrace();}
	}
}

