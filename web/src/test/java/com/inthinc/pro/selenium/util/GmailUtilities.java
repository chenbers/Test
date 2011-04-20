package com.inthinc.pro.selenium.util;

/*
 * Class to find information found in an e-mail
 * 
 * p. w. wehan
 */

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;

import com.sun.mail.pop3.POP3SSLStore;

public class GmailUtilities {
    private Session session = null;
    private Store store = null;
    private Folder folder;
 
    // connection info
    private String username;
    private String password;
    
    // stuff to look for
    private String fromAddrToLookFor = null;
    private String subjectToLookFor = null;
    private String attachmentToLookFor = null;
    
    // did I find them?
    private boolean foundAddress = false;
    private boolean foundSubject = false;
    private boolean foundAttach = false;
    
    public boolean isAddressFound(String addressToLookFor) {
        this.fromAddrToLookFor = addressToLookFor;
        searchEMail();
        return foundAddress;
    }

    public boolean isSubjectFound(String subjectToLookFor) {
        this.subjectToLookFor = subjectToLookFor;
        searchEMail();
        return foundSubject;
    }

    public boolean isAttachFound(String attachmentToLookFor) {
        this.attachmentToLookFor = attachmentToLookFor;
        searchEMail();
        return foundAttach;
    }

    private void searchEMail() {
        
        try {
            connect();
            openFolder("Inbox");
            
            int totalMessages = getMessageCount();
            int newMessages = getNewMessageCount();

            printAllMessages(totalMessages);
            
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
    }
    
    public GmailUtilities(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    private void connect() throws Exception {
        
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
    
    private void openFolder(String folderName) throws Exception {
        
        // Open the Folder
        folder = store.getDefaultFolder();
        
        folder = folder.getFolder(folderName);
        
        if (folder == null) {
            throw new Exception("Invalid folder");
        }
        
        // try to open read/write and if that fails try read-only
        try {
            
            folder.open(Folder.READ_WRITE);
            
        } catch (MessagingException ex) {
            
            folder.open(Folder.READ_ONLY);
            
        }
    }
    
    private void closeFolder() throws Exception {
        folder.close(false);
    }
    
    private int getMessageCount() throws Exception {
        return folder.getMessageCount();
    }
    
    private int getNewMessageCount() throws Exception {
        return folder.getNewMessageCount();
    }
    
    private void disconnect() throws Exception {
        store.close();
    }
    
    private void printMessage(int messageNo) throws Exception {
//        System.out.println("Getting message number: " + messageNo);
        
        Message m = null;
        
        try {
            m = folder.getMessage(messageNo);
            dumpPart(m);
        } catch (IndexOutOfBoundsException iex) {
            System.out.println("Message number out of range");
        }
    }
    
    private void printAllMessageEnvelopes() throws Exception {
        
        // Attributes & Flags for all messages ..
        Message[] msgs = folder.getMessages(555,557);
        
        // Use a suitable FetchProfile
        FetchProfile fp = new FetchProfile();
        fp.add(FetchProfile.Item.ENVELOPE);        
        folder.fetch(msgs, fp);
        
        for (int i = 0; i < msgs.length; i++) {
//            System.out.println("--------------------------");
//            System.out.println("MESSAGE #" + (i + 1) + ":");
            dumpEnvelope(msgs[i]);
            
        }
        
    }
    
    private void printAllMessages(int totalMessages) throws Exception {
     
        // Attributes & Flags for all messages ..
        Message[] msgs = folder.getMessages(totalMessages-5,totalMessages);
        
        // Use a suitable FetchProfile
        FetchProfile fp = new FetchProfile();
        fp.add(FetchProfile.Item.ENVELOPE);        
        folder.fetch(msgs, fp);
        
        for (int i = 0; i < msgs.length; i++) {
//            System.out.println("--------------------------");
//            System.out.println("MESSAGE #" + (i + 1) + ":");
            dumpPart(msgs[i]);
        }
        
    
    }
    
    private void dumpPart(Part p) throws Exception {
        if (p instanceof Message) {
            dumpEnvelope((Message)p);
            
            // if correct type, check for attachment....
            if ( p.getContent() instanceof Multipart) {
                Multipart mp = (Multipart)p.getContent();

                for (int i=0, n=mp.getCount(); i<n; i++) {
                    Part part = mp.getBodyPart(i);

                    String disposition = part.getDisposition();

                    if ((disposition != null) && 
                            (disposition.equals(Part.ATTACHMENT)) ) {
                        if ( part.getFileName().equalsIgnoreCase(attachmentToLookFor)) {
//                            System.out.println("==============================> Found attachment: " + part.getFileName());
                            this.foundAttach = true;
                        }
                    }
                }
            }
        }
        
        String ct = p.getContentType();
        try {
            pr("CONTENT-TYPE: " + (new ContentType(ct)).toString());
        } catch (ParseException pex) {
            pr("BAD CONTENT-TYPE: " + ct);
        }
        
        /*
         * Using isMimeType to determine the content type avoids
         * fetching the actual content data until we need it.
         */
        if (p.isMimeType("text/plain")) {
            pr("This is plain text");
            pr("---------------------------");
//            System.out.println((String)p.getContent());        
        } else {
            
            // just a separator
            pr("---------------------------");
            
        }
        
        
    }
    
    private void dumpEnvelope(Message m) throws Exception {        
        pr(" ");
        Address[] a;
        // FROM
        if ((a = m.getFrom()) != null) {
            for (int j = 0; j < a.length; j++) {
                pr("FROM: " + a[j].toString());
                if ( a[j].toString().equalsIgnoreCase(fromAddrToLookFor) ) {
//                    System.out.println("==============================> Found from match: " + a[j].toString()); 
                    this.foundAddress = true;
                }
            }
        }
        
        // TO
        if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
            for (int j = 0; j < a.length; j++) {
                pr("TO: " + a[j].toString());                
            }
        }
        
        // SUBJECT
//        pr("SUBJECT: " + m.getSubject());
        if (m.getSubject().equalsIgnoreCase(subjectToLookFor)) {
//            System.out.println("==============================> Found subject match: " + m.getSubject());   
            this.foundSubject = true;
        }
        
        // DATE
        Date d = m.getSentDate();
        pr("SendDate: " +
                (d != null ? d.toString() : "UNKNOWN"));
        

    }
    
    static String indentStr = "                                               ";
    static int level = 0;
    
    /**
     * Print a, possibly indented, string.
     */
    private static void pr(String s) {
        
//        System.out.print(indentStr.substring(0, level * 2));
//        System.out.println(s);
    }
}
