package com.inthinc.pro.backing.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


@Ignore
public class HelpConfigPropertiesTest {
    
    
    @Test
    public void testPrettyIDsExist()
    {
        HelpConfigProperties helpConfigProperties = new HelpConfigProperties();
        helpConfigProperties.setLocation("helpConfig.properties");
        helpConfigProperties.init();
        
        assertNotNull("default should not be null", helpConfigProperties.getDefault());
        
        List<String> prettyIDList = getPrettyIDList();
        assertTrue("prettyIDList is empty", (prettyIDList.size() > 0));
        for (String id : prettyIDList) {
            assertNotNull(id + " is not defined", helpConfigProperties.get(id));
        }
    }

    // TODO: need to figure out how to get this xml file when running test from Hudson 
    private List<String> getPrettyIDList() {
        List<String> prettyIDList = new ArrayList<String>();
        try
        {
            File file = new File("src/main/webapp/WEB-INF/pretty-config.xml");
            System.out.println("current dir is " + file.getAbsoluteFile() + " exists: " + file.exists());
            
//            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("src/main/webapp/WEB-INF/pretty-config.xml");
//            if (stream == null) {
//                throw new Exception("cannot open pretty-config.xml");
//            }
            Document doc = parse(file);
            
            NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
//                System.out.println("node " + node.getNodeName());
                if (node.getNodeName().equalsIgnoreCase("url-mapping")) {
                    NamedNodeMap attribs = node.getAttributes();
                
                    System.out.println(attribs.getNamedItem("id").getNodeValue());
                    String idItem = attribs.getNamedItem("id").getNodeValue();
                    prettyIDList.add(idItem);
                    
                }
            }
                
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return prettyIDList;
    }

    
//    public static Document parse(InputStream is) throws Exception
//    {
//        Document doc = createDocumentBuilder().parse(is);
//        return doc;
//    }
    
    public static Document parse(File file) throws Exception
    {
        return createDocumentBuilder().parse(file);
    }

    public static DocumentBuilder createDocumentBuilder() throws Exception
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        dbf.setIgnoringComments(true);

        try
        {
            return dbf.newDocumentBuilder();
        }
        catch (ParserConfigurationException e)
        {
            throw new Exception("Failed to create a document builder factory", e);
        }
    }




}
