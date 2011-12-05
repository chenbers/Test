package com.inthinc.pro.scheduler.dispatch;

import org.apache.log4j.Logger;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailDispatcher
{
    private MailSender mailSender;
    private String from;
    
    private static final Logger logger = Logger.getLogger(MailDispatcher.class);
    
    public static final String LIST_DELIM = ";";
    
    
    public MailDispatcher()
    {
    }
    
    public void setMailSender(MailSender mailSender) 
    {
        this.mailSender = mailSender;
    }
    
    public boolean send(String emailAddress, String subjectText, String messageText)
    {
        logger.info("sendEmail: [" + emailAddress + "] [" + subjectText + "] [" + messageText + "]");
        boolean ok = true;
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAddress);
        message.setFrom(getFrom());
        message.setSubject(subjectText);
        message.setText(messageText);
       
        if (emailAddress==null || emailAddress.trim().isEmpty())
        {
            logger.error("sendEmail FAILED. Address is empty: [" + emailAddress + "] [" + subjectText + "] [" + messageText + "]");
            ok = false;
        }
        else
        {
            try
            {
                mailSender.send(message);
            }
            catch (Exception e)
            {
                logger.error("sendEmail FAILED: [" + emailAddress + "] [" + subjectText + "] [" + messageText + "]", e);
                ok = false;
            }
        }
        logger.debug("sendEmail - Complete");
        return ok;
    }

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
}

