package com.inthinc.pro.backing.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Ignore
public class HelpConfigPropertiesTest {
    
    public static String webInfPath = null;
    @BeforeClass
    public static void runOnceBeforeAllTests() throws Exception
    {
        webInfPath = System.getProperty("webinf.dir");
        
        if (webInfPath == null) {
            webInfPath = "src/main/webapp/WEB-INF/"; 
        }
//System.out.println("webInfPath: " + webInfPath);        
    }
    
    @Test
    public void testPrettyIDsExist()
    {
        // make sure all of the page definitions in the pretty config have a matching help link associated
        
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

    private List<String> getPrettyIDList() {
        List<String> prettyIDList = new ArrayList<String>();
        try
        {
            File file = new File(webInfPath + "pretty-config.xml");
//            System.out.println("prettyconfig path is " + file.getAbsoluteFile() + " exists: " + file.exists());
            Document doc = parse(file);
            
            NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
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
