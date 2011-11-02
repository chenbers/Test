package com.inthinc.pro.selenium.util;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.URLName;

import com.sun.mail.pop3.POP3SSLStore;

public class JGMail {
    
    private String username;
    private String password;
    private Session session;
    private POP3SSLStore store;
    private String folder = "All";

    public JGMail(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    
    private void connect() throws MessagingException {
        
        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        
        Properties pop3Props = new Properties();
        
        pop3Props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
        pop3Props.setProperty("mail.pop3.socketFactory.fallback", "false");
        pop3Props.setProperty("mail.pop3.port",  "995");
        pop3Props.setProperty("mail.pop3.socketFactory.port", "995");
        
        URLName url = new URLName("pop3", "pop.gmail.com", 995, "",
                username, password);
        
        session = Session.getInstance(pop3Props, null);
        store = new POP3SSLStore(session, url);
        store.connect();
    }
    
    private void disconnect() throws MessagingException {
        store.close();
    }

}
