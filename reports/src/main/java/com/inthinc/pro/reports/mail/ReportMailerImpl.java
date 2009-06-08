package com.inthinc.pro.reports.mail;

import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class ReportMailerImpl implements ReportMailer
{
    private JavaMailSender javaMailSender;
    
    private static final Logger logger = Logger.getLogger(ReportMailerImpl.class);
    
    //TODO get these constants tied to a resource bundle
    private static final String DEFAULT_SUBJECT = "tiwiPRO Report";
    private static final String DEFAULT_MESSAGE = "View the attatchment(s) to see the report";
    private String from;
    
    @Override
    public void emailReport(List<String> toAddress, List<ReportAttatchment> attachments)
    {
        emailReport(toAddress, from, attachments, DEFAULT_MESSAGE, DEFAULT_SUBJECT);
        
    }
    
    @Override
    public void emailReport(List<String> toAddress, String fromAddress, List<ReportAttatchment> attachments)
    {
       emailReport(toAddress, fromAddress, attachments, DEFAULT_MESSAGE, DEFAULT_SUBJECT);
    }
    
    @Override
    public void emailReport(List<String> toAddress, List<ReportAttatchment> attachments, String message, String subject)
    {
        emailReport(toAddress, from, attachments, message == null? DEFAULT_MESSAGE: message,  subject == null ? DEFAULT_SUBJECT : subject);
    }
    
    @Override
    public void emailReport(List<String> toAddress, String fromAddress, List<ReportAttatchment> attachments, String message, String subject)
    {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage(); 
        try
        {
            if(message == null)
            {
                message = DEFAULT_MESSAGE;
            }
            
            if(subject == null)
            {
                subject = DEFAULT_SUBJECT;
            }
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
            
            mimeMessageHelper.setTo(toAddress.toArray(new String[toAddress.size()]));
            mimeMessageHelper.setFrom(fromAddress);
            for(ReportAttatchment reportAttatchment:attachments)
            {
                mimeMessageHelper.addAttachment(reportAttatchment.getFileName(),reportAttatchment.getAttatchmentAsFile());
            }
            
            mimeMessageHelper.setText(message);
            mimeMessageHelper.setSubject(subject);
            
            
            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException e)
        {
           logger.error(e);
        }
        catch (IOException e)
        {
           logger.error(e);
        }
        
    }

    public void setJavaMailSender(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }

    public JavaMailSender getJavaMailSender()
    {
        return javaMailSender;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getFrom()
    {
        return from;
    }
}
