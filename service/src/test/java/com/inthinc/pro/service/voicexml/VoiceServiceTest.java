package com.inthinc.pro.service.voicexml;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.security.providers.TestingAuthenticationToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;
import com.inthinc.pro.service.VoiceService;

public class VoiceServiceTest {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final Integer USER_ID = 1;
    private static final Integer GROUP_ID = 2;
    private static final Integer ACCOUNT_ID = 3;
    
    private static HttpServletRequest context;
    
    @BeforeClass
    public static void setProUser() {
        Person person = new Person();
        person.setAcctID(ACCOUNT_ID);
        
        User user = new User(USER_ID, 1, null, Status.ACTIVE, "test", "testpassword", GROUP_ID);
        user.setPerson(person);

        ProUser proUser = new ProUser(user, new GrantedAuthority[] {new GrantedAuthorityImpl(ROLE_ADMIN)});

        TestingAuthenticationToken testToken = new TestingAuthenticationToken(
                proUser, proUser.getPassword(), proUser.getAuthorities());
        
        SecurityContext securityContext = new SecurityContextImpl();
        testToken.setAuthenticated(true);
        securityContext.setAuthentication(testToken);
        SecurityContextHolder.setContext(securityContext);
        
        context = new MockHttpServletRequest();
        ((MockHttpServletRequest)context).setServerPort(8080);
        ((MockHttpServletRequest)context).setPathInfo("/service/api/vxml");
        
        ((MockHttpServletRequest)context).setRequestURI("/service/api/vxml");

    }
    
    @Test
    public void voiceServiceGet(){

        VoiceService voiceService = new VoiceServiceImpl();
        ((VoiceServiceImpl)voiceService).setVoxeoAudioURL("http://webhosting.voxeo.net/57256/www/audio/");
        Integer msgID = 1;
        String msg="hello";
        Integer ack= 1;
        //set up a get request to it
        try{
            Response response = voiceService.get(context, msgID, msg, ack);
            
           String entity = (String) response.getEntity();
           //read as xml and check parameters are included
           Document document = readXMLFromEntity(entity);
           Element docElement = document.getDocumentElement();
           assertTrue(docElement.getNodeName().equals("vxml"));
           
           NodeList nodes = docElement.getElementsByTagName("var");
           
           assertTrue(nodes.getLength() == 5);
           
           Map<String, String> nodeMap = new HashMap<String, String>();
           for(int i=0;i<nodes.getLength();i++){
               Node node = nodes.item(i);
               NamedNodeMap map =node.getAttributes();
               String name = map.getNamedItem("name").getNodeValue();
               String expr = map.getNamedItem("expr").getNodeValue();
               nodeMap.put(name, expr);
           }
           assertTrue(nodeMap.get("voxeoAudio").equals("'http://webhosting.voxeo.net/57256/www/audio/'"));
           assertTrue(nodeMap.get("ackQuery").equals("'http://localhost:8080/voiceack/1'"));
           assertTrue(nodeMap.get("messageText").equals("'hello'"));
           assertTrue(nodeMap.get("welcomeAudio").equals("voxeoAudio + 'iwi-welcome-new.wav'"));
           assertTrue(nodeMap.get("pressOneAudio").equals("voxeoAudio + 'iwi-acknowledge.wav'"));
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
}
