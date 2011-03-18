package com.inthinc.pro.service.voicexml;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.inthinc.pro.service.VoiceAckService;
import com.inthinc.pro.service.test.mock.AlertMessageDAOStub;

public class VoiceAckServiceTest {
    
    private VoiceAckService voiceAckService;
    
    @Before
    public void setup(){
        voiceAckService = new VoiceAckServiceImpl();
        ((VoiceAckServiceImpl)voiceAckService).setVoxeoAudioURL("http://webhosting.voxeo.net/57256/www/audio/");
        ((VoiceAckServiceImpl)voiceAckService).setAlertMessageDAO(new AlertMessageDAOStub());
    }
    
    @Test
    public void voiceServiceGet(){

        Integer msgID = 1;
        //set up a get request to it
        try{
            Response response = voiceAckService.get(msgID);
            
           assertTrue(Response.Status.OK.getStatusCode() == response.getStatus());
           
           String entity = (String) response.getEntity();
           //read as xml and check parameters are included
           Document document = readXMLFromEntity(entity);
           Element docElement = document.getDocumentElement();
           assertTrue(docElement.getNodeName().equals("vxml"));
           
           NodeList nodes = docElement.getElementsByTagName("var");
           
           assertTrue(nodes.getLength() == 2);
           
           Map<String, String> nodeMap = new HashMap<String, String>();
           for(int i=0;i<nodes.getLength();i++){
               Node node = nodes.item(i);
               NamedNodeMap map =node.getAttributes();
               String name = map.getNamedItem("name").getNodeValue();
               String expr = map.getNamedItem("expr").getNodeValue();
               nodeMap.put(name, expr);
           }
           assertTrue(nodeMap.get("voxeoAudio").equals("'http://webhosting.voxeo.net/57256/www/audio/'"));
           assertTrue(nodeMap.get("thankYouAudio").equals("voxeoAudio + 'iwi-goodbye.wav'"));
        }
        catch(IOException ioe){
            
        }
    }

    private Document readXMLFromEntity(String entity){
        
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = createDocumentBuilder();
            InputSource is = new InputSource(new StringReader(entity));
            is.setEncoding("UTF-8");
            Document document = documentBuilder.parse(is);
            
            return document;
        } 
        catch (SAXException e) {

            e.printStackTrace();
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }
    private  DocumentBuilder createDocumentBuilder() throws Exception
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
    @Test
    public void voiceServiceGetNullMsgID(){

        Integer msgID = null;
        //set up a get request to it
        try{
            Response response = voiceAckService.get(msgID);
            
            assertTrue(Response.Status.NOT_FOUND.getStatusCode() == response.getStatus());
        }
        catch(IOException ioe){
            
        }
    }

}
