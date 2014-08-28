import java.io.*;
import java.util.*;
import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class RawDataParser {
	public static String outPath = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/WholeData_BArticleParsed/";
	public static String path = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/Whole data/";	
	public static String bpath = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/";
	public static void main(String[] Args){
		Parse();
	}
	public static int ReturnMonth(String name){
		ArrayList<String> Mon = new ArrayList<String>();
		Mon.add("Jan");Mon.add("Feb");Mon.add("Mar");Mon.add("Apr");Mon.add("May");Mon.add("Jun");
		Mon.add("Jul");Mon.add("Aug");Mon.add("Sep");Mon.add("Oct");Mon.add("Nov");Mon.add("Dec");
		return Mon.indexOf(name);
	}
	public static void Parse(){
		try{
			int year;
			Calendar cal = Calendar.getInstance();
			StringTokenizer token;
			PrintWriter out;
			HashSet<Character> charSet = new HashSet<Character>();			
			//out = new PrintWriter(System.out);
			//out = new PrintWriter(new FileOutputStream(bpath+"times.txt"));
			for(File file : new File(path).listFiles()){
				year = Integer.parseInt(file.getName().substring(4,8));
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(file);
				String Line, title;
				String line;
				String Date, Mon, Dat, Yr;
				int aId;
				doc.getDocumentElement().normalize();
				//System.out.println("Root element : " +doc.getDocumentElement().getNodeName());
				NodeList nList = doc.getElementsByTagName("Article");
				
				for(int temp = 0; temp<nList.getLength(); temp++){
					Node nNode = nList.item(temp);
					//System.out.println("\nCurrent Element :" + nNode.getNodeName());
					if(nNode.getNodeType() == Node.ELEMENT_NODE){
						Element eElement = (Element) nNode;
						
						//if(!eElement.getElementsByTagName("Section").item(0).getTextContent().equals("Business"))continue;
						title = eElement.getElementsByTagName("Title").item(0).getTextContent();
						title = title.replace("<strong>", "").replace("</strong>", "").replace(" - New York Times", "").replace(" - New York ...", "").replace(" - New ...", "");
						//out.println(title+"\t"+eElement.getElementsByTagName("Id").item(0).getTextContent());
						Line = eElement.getElementsByTagName("Content").item(0).getTextContent();
						if(Line.trim().equals(""))continue;
						token = new StringTokenizer(Line, "\n");
						title = token.nextToken();
						int index = title.indexOf("Published:");
						try{
						title = title.substring(index+11);
						Mon = title.substring(0,title.indexOf(" ")).substring(0,3);
						Dat = title.substring(title.indexOf(" ")).substring(1,3);
						Yr = title.substring(title.indexOf(" ")).substring(5,9);
						cal.set(Integer.parseInt(Yr), ReturnMonth(Mon), Integer.parseInt(Dat));
						//out.println((cal.getTimeInMillis()/86400000)+"\t"+Dat+"\t"+eElement.getElementsByTagName("Id").item(0).getTextContent());
						//}catch(Exception ee){System.out.println(Line+"\t"+eElement.getElementsByTagName("Id").item(0).getTextContent());}
						}catch(Exception ee){
							title = eElement.getElementsByTagName("Date").item(0).getTextContent();
							Yr = title.substring(0,4);Dat = title.substring(8);
							cal.set(Integer.parseInt(Yr), Integer.parseInt(title.substring(5,7))-1, Integer.parseInt(Dat));
						}
						
						//out.println((cal.getTimeInMillis()/86400000)+"\t"+Dat+"\t"+eElement.getElementsByTagName("Id").item(0).getTextContent());
						aId = Integer.parseInt(eElement.getElementsByTagName("Id").item(0).getTextContent());
						//out = new PrintWriter(new FileOutputStream(outPath+aId));
						//out = new PrintWriter(System.out);

					}
				}			
			}
			/*
			out = new PrintWriter(new FileOutputStream(bpath+"CharSet"));
			for(char c : charSet){
				out.println(c);
			}
			*/
			//out.close();
			
		}catch(Exception e){e.printStackTrace();}	
	}
}
