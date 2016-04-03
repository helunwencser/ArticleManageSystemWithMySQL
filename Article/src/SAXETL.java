import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Author: Lunwen He
 * Date: 04/02/2016
 * */

/**
 * This class extracts, transfers, and loads data from dblp.xml
 * into MySQL database. It uses SAX as the parser since it's an
 * event based parser and doesn't need to load all dblp.xml into
 * memory. In consideration of the volume of data set, we choose
 * SAX as parser instead of DOM.
 * Reference: 
 * https://www.javacodegeeks.com/2013/05/parsing-xml-using-dom-sax-and-stax-parser-in-java.html#sax
 * */

public class SAXETL {
	public static void main(String[] args) {
	    SAXParserFactory parserFactor = SAXParserFactory.newInstance();
	    SAXParser parser;
		try {
			parser = parserFactor.newSAXParser();
		    SAXHandler handler = new SAXHandler();
		    parser.parse(new FileInputStream(new File("article.xml")), handler);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
}

/**
 * The Handler for SAX Events.
 */
class SAXHandler extends DefaultHandler {
  
  private Article article = null;
  private String content = null;
  
  @Override
  /* Triggered when the start tag <article> is found. */
  public void startElement(
		  String uri, 
		  String localName, 
		  String qName, 
		  Attributes attributes
		  ) throws SAXException {

      //Create a new Article object when the start tag of <article> is found
	  if(qName.equals("article")) {
	        article = new Article();
	        article.setMdate(attributes.getValue("mdate"));
	        article.setKey(attributes.getValue("key"));		  
	  }
  }

  @Override
  /* Triggered when an end tag is found. */
  public void endElement(
		  String uri, 
		  String localName, 
		  String qName
		  ) throws SAXException {
	  
   switch(qName){
     case "article":
       System.out.println(article.toString());       
       break;
     case "title":
       article.setTitle(content);
       break;
     case "author":
       article.addAuthors(content);    
       break;
     case "pages":
       article.setPages(content);
       break;
     case "year":
    	 article.setYear(content);
    	 break;
     case "volume":
    	 article.setVolume(content);
    	 break;
     case "journal":
    	 article.setJournal(content);
    	 break;
     case "number":
    	 article.setNumber(content);
    	 break;
     case "url":
    	 article.setUrl(content);
    	 break;
     case "ee":
    	 article.setEe(content);
    	 break;
     default:
    	 break;
   }
 }

  @Override
  public void characters(char[] ch, int start, int length) 
          throws SAXException {
    content = String.copyValueOf(ch, start, length).trim();
  }
}