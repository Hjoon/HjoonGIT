import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringEscapeUtils;

public class TableArticleDetecter {
	
	public static void main(String[] Args){
		try{
			String path = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/WholeData_BArticleParsed/";
			String bpath = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/";
			String outPath = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/WholeData_BArticleParsed_Escaped/";
			String line;
		    Set<Character> QSet = new TreeSet<Character>();
		    Matcher m;
		    int fcount = 0;
		    PrintWriter out;
			for(File file : new File(path).listFiles()){
				fcount++;
				if(file.getName().equals("3768410"))continue;
				line = new String(Files.readAllBytes(Paths.get(path+file.getName())));
				line = StringEscapeUtils.unescapeHtml4(line);
				out = new PrintWriter(new FileOutputStream(outPath+file.getName()));
				out.println(line);
				out.close();
				System.out.println(fcount);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
