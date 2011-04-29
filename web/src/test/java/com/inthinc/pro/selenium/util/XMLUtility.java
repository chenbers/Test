package com.inthinc.pro.selenium.util;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;

public class XMLUtility {
    
    public static ArrayList<Element> parseFusionXML(String xml) {
        ArrayList<Element> tmp = new ArrayList<Element>();
        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource( new StringReader( xml ) );
            Document document = builder.parse( is );
            
            DocumentTraversal traversal = (DocumentTraversal) document; 
            NodeIterator iterator = traversal.createNodeIterator(
                    document.getDocumentElement(), NodeFilter.SHOW_ELEMENT, null, true);

            for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode()) {
                Element el = (Element) n;
//                System.out.println("Element: " + el.getNodeName() + " attribute - value: " + el.getAttribute("value"));
                tmp.add(el);
            }
        } catch (Exception e) {
            System.out.println("Problems decomposing fusion xml...  " + e.getMessage());
        }
        
        return tmp;
    }
}
