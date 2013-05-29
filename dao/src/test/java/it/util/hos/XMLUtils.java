package it.util.hos;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class XMLUtils {

    public static Document parse(InputStream is) throws Exception {
        Document doc = createDocumentBuilder().parse(is);
        return doc;
    }

    public static DocumentBuilder createDocumentBuilder() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        dbf.setIgnoringComments(true);

        try {
            return dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new Exception("Failed to create a document builder factory", e);
        }
    }

}
