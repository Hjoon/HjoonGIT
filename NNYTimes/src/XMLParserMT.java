import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javax.xml.*;

import java.util.regex.*;
import java.nio.file.*;

import org.apache.commons.lang3.StringEscapeUtils;

public class XMLParserMT{
	public static void main(String[] Args){
		int ThNum = 4;
		//String path = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/Whole data/";
		String path = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/XMLParsed2/";
		String bpath = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/";
		String outPath = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/XMLParsed3/";
		
		File[] files = new File(path).listFiles();
		//File[][] flists = new File[ThNum][files.length/ThNum+1];
		ArrayList<ArrayList<File>> flists = new ArrayList<ArrayList<File>>();
		
		ArrayList<String> cfiles = new ArrayList<String>();
		
		//for(File file : new File(outPath).listFiles()){cfiles.add(file.getName());} //Incompleter
		for(int i = 0; i<ThNum; i++){
			flists.add(new ArrayList<File>());
		}
		for(int i = 0, j = 0; i<files.length; i++){
			if(!cfiles.contains(files[i].getName())){
				flists.get(j%ThNum).add(files[i]);j++;
			}
		}
		MThread[] THs = new MThread[ThNum];
		for(int i = 0; i<ThNum; i++){
			THs[i] = new MThread(flists.get(i));
			THs[i].start();
		}	
	}
}
class MThread extends Thread{
	private ArrayList<File> flist;
	public MThread(ArrayList<File> flist){
		this.flist = flist;
	}
	String line;
	BufferedReader in;
	int nl;
	public void run(){
		for(int i = 0; i<flist.size(); i++){
			try{
				System.out.println(this.getName()+"\t"+flist.get(i).getName());
				XMLFilter.FilterXML(flist.get(i).getName());
			}catch(Exception e){e.printStackTrace();}
		}
	}
}