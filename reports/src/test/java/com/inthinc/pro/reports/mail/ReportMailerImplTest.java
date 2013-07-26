package com.inthinc.pro.reports.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.Test;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class ReportMailerImplTest {
    
    private static final String TEST_TO_ADDRESSES[] = {"TEST_TO@INTHINC.COM"};
    private static final String TEST_FROM_ADDRESS = "TEST_FROM@INTHINC.COM";
    private static final String TEST_MESSAGE = "TEST_MESSAGE";
    private static final String TEST_SUBJECT = "TEST_SUBJECT";
    private static final String TEST_ATTACHMENT = "TEST_ATTACHMENT";

    @Test
    public void testEmailReport() throws IOException {
        
        ReportMailerImpl reportMailerImpl = new ReportMailerImpl();
        MockJavaMailSender javaMailSender = new MockJavaMailSender();
        reportMailerImpl.setJavaMailSender(javaMailSender);
        
        ReportAttatchment reportAttatchment = new ReportAttatchment(TEST_ATTACHMENT, TEST_ATTACHMENT.getBytes());
        List<ReportAttatchment> attachments = new ArrayList<ReportAttatchment>();
        attachments.add(reportAttatchment);

        reportMailerImpl.emailReport(Arrays.asList(TEST_TO_ADDRESSES), TEST_FROM_ADDRESS, attachments, TEST_MESSAGE, TEST_SUBJECT);
        
        
        MimeMessage sentMessage = javaMailSender.getSentMessage();
        
        
        try {
            Address[] from = sentMessage.getFrom();
            assertEquals("Expected one FROM Address", 1, from.length);
            assertTrue("From Address: ", from[0].toString().contains(TEST_FROM_ADDRESS));
            
            Address[] to = sentMessage.getAllRecipients();
            assertEquals("Expected one TO Address", 1, to.length);
            assertTrue("To Address: ", to[0].toString().contains(TEST_TO_ADDRESSES[0]));
            
            assertEquals("Subject: " , TEST_SUBJECT, sentMessage.getSubject());
            
            Object content = sentMessage.getContent();  
            assertTrue("Expected multipart", content instanceof MimeMultipart);  
            MimeMultipart mp = (MimeMultipart)content;  
            int count = mp.getCount();  
            assertEquals("Expected 2 parts", 2, count);  

            BodyPart bp = mp.getBodyPart(1);  
            Object attachmentContent = bp.getContent();  
            assertTrue("Expected an inputStream", attachmentContent instanceof InputStream);  
            InputStream attachmentStream = ((InputStream)attachmentContent);
            int len = attachmentStream.available();
            byte[] attachment = new byte[len];
            attachmentStream.read(attachment, 0, len);
            attachmentStream.close();
            
            assertEquals("Attachment Length", reportAttatchment.getAttatchmentData().length, len);
            for (int j = 0; j < len; j++) {
                assertEquals("Attachment Byte " + j, reportAttatchment.getAttatchmentData()[j],attachment[j]);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            fail("Unexpected exception");
        }
        

    }
    
    
    class MockJavaMailSender implements JavaMailSender {
        
        private MimeMessage sentMessage;
        
        @Override
        public void send(SimpleMailMessage simpleMessage) throws MailException {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void send(SimpleMailMessage[] simpleMessages) throws MailException {
            // TODO Auto-generated method stub
            
        }

        @Override
        public MimeMessage createMimeMessage() {
            return new MimeMessage(Session.getInstance(getProperties()));
        }

        private Properties getProperties() {
            
            Properties properties = new Properties();
            properties.setProperty("mail.store.protocol", "");
            properties.setProperty("mail.transport.protocol", "");
            properties.setProperty("mail.host", "");
            properties.setProperty("mail.user", "");
            properties.setProperty("mail.from", "");
            return properties;
            
        }

        @Override
        public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void send(MimeMessage mimeMessage) throws MailException {
            sentMessage = mimeMessage;
        }

        public MimeMessage getSentMessage() {
            return sentMessage;
        }

        public void setSentMessage(MimeMessage sentMessage) {
            this.sentMessage = sentMessage;
        }

        @Override
        public void send(MimeMessage[] mimeMessages) throws MailException {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void send(MimeMessagePreparator[] mimeMessagePreparators) throws MailException {
            // TODO Auto-generated method stub
            
        }
        
    }

}
