import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import javax.xml.*;
import java.util.regex.*;
import java.nio.file.*;
import org.apache.commons.lang3.StringEscapeUtils;

public class XMLParser extends Thread{
	public static void main(String[] Args){

	}
	public void test(){
		
	}
	public static void delFiles(){
		try{
			Set<String> QSet = new TreeSet<String>();
		    Pattern p2 = Pattern.compile("\\*3\\*\\*\\* COMPANY REPORTS \\*\\*\\*3\\*");
		    Matcher m;
			String path = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/WholeData_BArticleParsed_Escaped2/";
			String bpath = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/";
			String outPath = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/WholeData_BArticleParsed_Escaped/";
			String line; int fcount = 0;
			for(File file : new File(path).listFiles()){
				fcount++;
				line = new String(Files.readAllBytes(Paths.get(path+file.getName())));
				m = p2.matcher(line);
				if(m.find()){
					QSet.add(file.getName());
				}
				System.out.println(file.getName());
			}
			//File f;
			PrintWriter out = new PrintWriter(new FileOutputStream(bpath+"delList2.txt"));
			for(String sr : QSet){
				out.println(sr);
			}
			out.close();
		}catch(Exception e){e.printStackTrace();}
	}
	public static void PnE(){
		try{
			//String path = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/WholeData_BArticleParsed/";
			String path = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/WholeData_BArticleParsed_Escaped/";
			String bpath = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/";
			String outPath = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/WholeData_BArticleParsed_Escaped2/";
			String line;
		    Set<String> QSet = new TreeSet<String>();
		    Pattern p = Pattern.compile("<!--[^(--)]+-->");
		    Pattern p2 = Pattern.compile("<[^<>]+>");
		    Matcher m;
		    int fcount = 0;
		    PrintWriter out;
			for(File file : new File(path).listFiles()){
				fcount++;
				line = new String(Files.readAllBytes(Paths.get(path+file.getName())));
				/*
				line = StringEscapeUtils.unescapeHtml4(line);
				line = escp(line);
				line = escp2(line);
				*/
				m = p.matcher(line);
				while(m.find()){
					QSet.add(m.group());//System.out.println(m.group());
					line = line.replace(m.group(), "");
				}
				m = p2.matcher(line);
				while(m.find()){
					QSet.add(m.group());//System.out.println(m.group());
					line = line.replace(m.group(), "");
				}
				
				
				out = new PrintWriter(new FileOutputStream(outPath+file.getName()));
				out.println(line);
				out.close();
				
				System.out.println(fcount);
			}
			for(String sr : QSet){
				System.out.println(sr);
			}
		}catch(Exception e){e.printStackTrace();}
	}
	public static String escp2(String entry){
		entry = entry.replace("''", "\"");
		entry = entry.replace("--", "-");
		return entry;
	}
	public static String escp(String entry){
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
		entry = entry.replace("&#189;","½");
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
		entry = entry.replace("	"," ");
		entry = entry.replace(" "," ");
		entry = entry.replace("","");
		entry = entry.replace("","");
		entry = entry.replace("","");
		entry = entry.replace("","");
		entry = entry.replace("","");
		entry = entry.replace("","");
		entry = entry.replace("","");
		entry = entry.replace("","");
		entry = entry.replace("","");
		entry = entry.replace(" "," ");
		entry = entry.replace("·",".");
		entry = entry.replace("»",">");
		entry = entry.replace("À","A");
		entry = entry.replace("Á","A");
		entry = entry.replace("Â","A");
		entry = entry.replace("Ã","A");
		entry = entry.replace("Ä","A");
		entry = entry.replace("Å","A");
		entry = entry.replace("Æ","Ae");
		entry = entry.replace("Ç","C");
		entry = entry.replace("È","E");
		entry = entry.replace("É","E");
		entry = entry.replace("Ë","E");
		entry = entry.replace("Í","I");
		entry = entry.replace("Î","I");
		entry = entry.replace("Ñ","N");
		entry = entry.replace("Ò","O");
		entry = entry.replace("Ö","O");
		entry = entry.replace("Ü","U");
		entry = entry.replace("à","a");
		entry = entry.replace("á","a");
		entry = entry.replace("â","a");
		entry = entry.replace("ã","a");
		entry = entry.replace("ä","a");
		entry = entry.replace("å","a");
		entry = entry.replace("ç","c");
		entry = entry.replace("è","e");
		entry = entry.replace("é","e");
		entry = entry.replace("ê","e");
		entry = entry.replace("ë","e");
		entry = entry.replace("í","I");
		entry = entry.replace("î","I");
		entry = entry.replace("ï","I");
		entry = entry.replace("ð","th");
		entry = entry.replace("ñ","n");
		entry = entry.replace("ò","o");
		entry = entry.replace("ó","o");
		entry = entry.replace("ô","o");
		entry = entry.replace("õ","o");
		entry = entry.replace("ö","o");
		entry = entry.replace("ø","oe");
		entry = entry.replace("ù","u");
		entry = entry.replace("ú","u");
		entry = entry.replace("û","u");
		entry = entry.replace("ü","u");
		entry = entry.replace("ý","y");
		entry = entry.replace("þ","p");
		entry = entry.replace("ć","c");
		entry = entry.replace(" "," ");
		entry = entry.replace(" "," ");
		entry = entry.replace("​"," ");
		entry = entry.replace("–","-");
		entry = entry.replace("—","-");
		entry = entry.replace("‘","'");
		entry = entry.replace("’","'");
		entry = entry.replace("“","\"");
		entry = entry.replace("”","\"");
		entry = entry.replace("‡","*");
		entry = entry.replace("•","*");
		entry = entry.replace("‹","<");
		entry = entry.replace("›",">");
		entry = entry.replace("⁄","/");
		entry = entry.replace("€","$");
		entry = entry.replace("™","");
		entry = entry.replace("●","*");
		entry = entry.replace("♣","*");
		entry = entry.replace("♥","*");
		entry = entry.replace("　"," ");
		entry = entry.replace("","");
		entry = entry.replace("＜","<");
		entry = entry.replace("�","");

		return entry;

	}
}
