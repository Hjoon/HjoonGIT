import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.*;

import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class TGZReader {
	public static void main(String[] Args){
		try{
			BufferedReader in;
			TreeMap<String, Integer> tagList = new TreeMap<String, Integer>();
			String line, nLine, tag;
			PrintWriter out;
			StringTokenizer token;
			boolean isBusiness = false;
			boolean isStat = false;
			for(int y = 1987; y<2008; y++){
				String path = String.format("D:/Research/NYTimes/data/%d/", y);
				String opath = String.format("D:/Research/NYTimes/Bdata/%d/", y);
				for(File file : new File(path).listFiles()){
					in = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file))));
					out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(opath+file.getName().replace("tgz", "xml")),StandardCharsets.UTF_8), true);
					out.print("<NYTimes>");
					nLine = "";
					while((line = in.readLine())!=null){
						if(line.startsWith("<nitf")){
							nLine = "";continue;
						}else if (line.startsWith("</nitf>")){
							if(isBusiness&&!isStat){
								out.print("<Article>");
								out.print(nLine.replaceAll(">\\s+<", "><").trim());
								out.print("</Article>");
							}
							isBusiness = false;
							isStat = false;	
						}
						nLine += (line + "\n");
						//System.out.println(line);
						if(line.matches(".+Statis.+</classifier>")){
							isStat=true;
							if(!line.endsWith(">Statistics</classifier>"))System.out.println(line);
						}
						if(line.endsWith(" name=\"online_sections\"/>")){
							if(line.contains("Business")){
								isBusiness=true;
								line = line.substring(0,line.indexOf("\" name")).substring(line.indexOf("\"")+1);
								token = new StringTokenizer(line, ";");
								while(token.hasMoreTokens()){
									tag = token.nextToken().trim();
									if(tag.equals("Paid Death Notices")||tag.equals("Opinion")||tag.equals("Obituaries")||tag.equals("Corrections"))isBusiness=false;
								}
							}
						}					
					}
					out.print("</NYTimes>");
					in.close();
					out.close();
				}
			}
			
		}catch(Exception e){e.printStackTrace();}
	}
}
