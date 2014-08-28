import java.io.*;
import java.net.URL;
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
import edu.stanford.nlp.dcoref.*;
import edu.stanford.nlp.pipeline.TokensRegexNERAnnotator;
import edu.stanford.nlp.pipeline.RegexNERAnnotator;
import edu.stanford.nlp.dcoref.CorefChain.CorefMention;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ie.*;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.*;


public class Test {

	public static void main(String[] Args){
		try{
			Properties props = new Properties();
		    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner");
		    props.put("regexner.mapping", "D:/Research/NYTimes/regexner.txt");
		    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		    Annotation annotation;
		    String line = "Bank of America and the Justice Department have a reached a record $16.65 billion settlement, capping the most sweeping federal investigation into the sale of troubled mortgages by a Wall Street bank since the 2008 financial crisis. The landmark settlement, announced by Attorney General Eric H. Holder Jr. in Washington on Thursday morning, requires Bank of America to pay a $9.65 billion cash penalty and provide about $7 billion in relief to homeowners and blighted neighborhoods. \"The size and scope of this multibillion-dollar agreement go far beyond the \'cost of doing business,\'\" Mr. Holder said in a prepared statement. \"This outcome does not preclude any criminal charges against the bank of its employees. Nor was it inevitable over these last few weeks that this case would be resolved out of court.\"The accord ends a painful chapter for Bank of America, based in Charlotte N.C., which bought Countrywide Financial as the United States housing market was collapsing in 2008, ultimately causing widespread losses across the financial system. Including Thursday\'s deal, which is the largest government settlement by a company in American history, Bank of America\'s legal bill related to mortgage issues is approaching $70 billion. As part of the most recent settlement, the bank has agreed to write down the balances of mortgages of struggling homeowners and also pay to demolish foreclosed properties contributing to blight in certain cities. \"We believe this settlement, which resolves significant remaining mortgage-related exposures, is in the best interests of our shareholders, and allows us to continue to focus on the future,\" Bank of America\'s chief executive, Brian T. Moynihan, said in a statement. While no bank executives will face charges as part of the civil settlement, federal prosecutors in Los Angeles are preparing a lawsuit against Angelo Mozilo, Countrywide\'s co-founder, who had come to symbolize the risky mortgages that required homeowners to show little proof they had the ability to pay the loans back.  So-called \"no-doc\" and \"low doc\" loans helped spur Countrywide\'s growth, which by 2006 became the nation\'s largest mortgage lender. But such exotic loans - and the financial damage they inflicted on millions of homeowners and investors - have haunted Bank of America for the last six years, as it has tried to dig out from under a mountain of lawsuits and investigations related to Countrywide. The $16.65 billion settlement is the culmination of a reckoning that has been years in the making. Across the country, multiple United States attorney offices have mounted investigations into Countrywide and Merrill Lynch, the investment firm that Bank of America bought in the financial crisis. Bank of America\'s lawyers argued that the bank should not be overly punished for the suspected misdeeds of Countrywide, and particularly Merrill, which it felt pressured by regulators to acquire in the depths of the financial crisis. But that argument failed to win over prosecutors, who at one point during the negotiations last month threatened to file a lawsuit against the bank if it did not significantly raise its cash offer. For the Justice Department, which has come under fire for an uneven response to the financial crisis, the case is intended as a signature moment and a warning shot to all of Wall Street. The deal eclipses the sums that JPMorgan Chase and Citigroup paid recently to settle similar cases. And the settlement is sure to be the largest of those still to come, as the Justice Department is now expected to shift its attention to banks like Goldman Sachs and Wells Fargo.";
		    String tword, word, ne, rne;
		    List<CoreMap> sentences;
		    
	        annotation = new Annotation(line); 
		    pipeline.annotate(annotation);
		    tword="";
		    sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		    for(CoreMap sentence: sentences) {
		    	for (CoreLabel ctoken: sentence.get(TokensAnnotation.class)) {
		    		word = ctoken.get(TextAnnotation.class);
			          ne = ctoken.get(NamedEntityTagAnnotation.class);
			          //rne = ctoken.get();
//			          if(!(ne.equals("O")||ne.equals("DATE")))
			        	  System.out.println(word+"\t"+ne);
			     }
	        }
		    /*
		    Map<Integer, CorefChain> graph = annotation.get(CorefChainAnnotation.class);
		    List<CorefMention> crm;
			for(Integer i : graph.keySet()){
				if(graph.get(i).getMentionMap().size()>1){
					//System.out.println(graph.get(i));
					crm = graph.get(i).getMentionsInTextualOrder();
					for(int j = 0; j<crm.size(); j++){
						System.out.print(crm.get(j)+" // ");
					}
					System.out.println();
				}
				
			}
			*/
		}catch(Exception e){e.printStackTrace();}
	}
}
