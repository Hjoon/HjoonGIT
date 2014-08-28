import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.*;
import java.util.*;
import java.io.*;

public class NEPCollector {

  public static void main(String[] args) throws Exception {

    String serializedClassifier = "/home/hjoon/Downloads/stanford-ner-2014-06-16/classifiers/english.all.3class.distsim.crf.ser.gz";

    AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
    Pattern p = Pattern.compile("<ORGANIZATION>[^<]+</ORGANIZATION>");
    Matcher m;
    String nameline;
    String line;
    HashSet<String> nameset = new HashSet<String>();
    String path = "/media/hjoon/D20E87220E86FEAB/Research/NYTimes/WholeData_BArticleParsed_Escaped/";
    for(File file : new File(path).listFiles()){
    	line = new String(Files.readAllBytes(Paths.get(path+file.getName())));//1803869
    	String result = classifier.classifyWithInlineXML(line).replace("</ORGANIZATION> and <ORGANIZATION>", " and ");
        m = p.matcher(result);
        while(m.find()){
        	nameline = m.group();
        	nameline = nameline.substring(14, nameline.length()-15);
        	nameset.add(nameline);
        }
    }
    
    PrintWriter out = new PrintWriter(new FileOutputStream("/home/hjoon/Documents/NYTimes/NameSet2"));
    for(String name : nameset){
    	out.println(name);
    }
    out.close();
  }

}