package com.inthinc.pro.scheduler.dispatch;

import org.apache.log4j.Logger;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailDispatcher
{
    private MailSender mailSender;
    private static final Logger logger = Logger.getLogger(MailDispatcher.class);
    
    public static final String LIST_DELIM = ";";
    public MailDispatcher()
    {
    }
    
    public void setMailSender(MailSender mailSender) 
    {
        this.mailSender = mailSender;
    }
    
    public void send(String emailAddress, String subjectText, String messageText)
    {
        logger.debug("sendEmail: [" + emailAddress + "] [" + subjectText + "] [" + messageText + "]");
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAddress);
        message.setFrom("DriveAware Support <support@tiwipro.com>");
        message.setSubject(subjectText);
        message.setText(messageText);
        try
        {
            mailSender.send(message);
        }
        catch (Exception e)
        {
            logger.error("sendEmail FAILED: [" + emailAddress + "] [" + subjectText + "] [" + messageText + "]", e);
        }
        logger.debug("sendEmail - Complete");
    }
}

