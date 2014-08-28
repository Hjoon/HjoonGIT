import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ie.*;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.*;

public class PoSCount {

	public static void main(String[] args) throws IOException {
	    PrintWriter out;
        out = new PrintWriter(System.out);

	    String path = "/home/hjoon/Documents/NYTimes/";
	    String line;
	    HashSet<String> nameset = new HashSet<String>();
    
	    Annotation annotation;
	    Properties props = new Properties();
	    //props.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner");
	    props.put("annotators", "tokenize, ,split, ner");
	    //props.put("regexner.mapping", "org/foo/resources/jg-regexner.txt");
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	    
	    List<CoreMap> sentences;
	    String word, ne, pos = "";
	    boolean isSent;
	    BufferedReader in = new BufferedReader(new FileReader(path+"CompanyNames2"));
	    out = new PrintWriter(new FileOutputStream(path+"NameSetProcesed"));
	    while((line = in.readLine())!=null){
	    	annotation = new Annotation(line); 
		    pipeline.annotate(annotation);
		    sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		    for(CoreMap sentence: sentences) {
		    	isSent=false;
		    	for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
		    		word = token.get(TextAnnotation.class);
			          ne = token.get(NamedEntityTagAnnotation.class);
			          pos = token.get(PartOfSpeechAnnotation.class);
			          if((pos.equals("VB")||pos.equals("VBP")||pos.equals("VBZ")||pos.equals("VBD")||pos.equals("VBN"))){
			        	  isSent = true;
			        	  break;
			          }
			     }
		    	if(isSent){
		    		out.println(sentence+"\t"+pos);
		    	}else{
		    		out.println(sentence);
		    	}
	        }
	    }
	    in.close();
	    out.close();
	}
}

