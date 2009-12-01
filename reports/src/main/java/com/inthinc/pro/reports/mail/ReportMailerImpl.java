package com.inthinc.pro.reports.mail;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class ReportMailerImpl implements ReportMailer {
    private JavaMailSender javaMailSender;

    private static final Logger logger = Logger.getLogger(ReportMailerImpl.class);

    // TODO get these constants tied to a resource bundle
    private static final String DEFAULT_SUBJECT = "tiwiPRO Report";
    private static final String DEFAULT_MESSAGE = "View the attachment(s) to see the report";
    private String from;

    @Override
    public void emailReport(List<String> toAddress, List<ReportAttatchment> attachments) {
        emailReport(toAddress, from, attachments, DEFAULT_MESSAGE, DEFAULT_SUBJECT);

    }

    @Override
    public void emailReport(List<String> toAddress, String fromAddress, List<ReportAttatchment> attachments) {
        emailReport(toAddress, fromAddress, attachments, DEFAULT_MESSAGE, DEFAULT_SUBJECT);
    }

    @Override
    public void emailReport(List<String> toAddress, List<ReportAttatchment> attachments, String message, String subject) {
        emailReport(toAddress, from, attachments, message == null ? DEFAULT_MESSAGE : message, subject == null ? DEFAULT_SUBJECT : subject);
    }

    @Override
    public void emailReport(List<String> toAddress, String fromAddress, List<ReportAttatchment> attachments, String message, String subject) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            if (message == null) {
                message = DEFAULT_MESSAGE;
            }

            if (subject == null) {
                subject = DEFAULT_SUBJECT;
            }
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            String[] toAddressArray = toAddress.toArray(new String[toAddress.size()]);
            mimeMessageHelper.setTo(toAddressArray);
            mimeMessageHelper.setFrom(fromAddress);
            for (ReportAttatchment reportAttatchment : attachments) {
                mimeMessageHelper.addAttachment(reportAttatchment.getFileName(), reportAttatchment.getAttatchmentAsFile());
            }

            mimeMessageHelper.setText(message);
            mimeMessageHelper.setSubject(subject);

            if (logger.isDebugEnabled()) {
                logger.debug("ATTEMPTING TO SEND EMAIL");
                if (JavaMailSenderImpl.class.isInstance(javaMailSender)) {
                    JavaMailSenderImpl sender = (JavaMailSenderImpl) javaMailSender;
                    logger.debug("Host: " + sender.getHost() + ":" + sender.getPort());
                }
                logger.debug("to addresses: " + Arrays.toString(toAddressArray));
                logger.debug("from address:" + fromAddress);
                logger.debug("subject:" + subject);
                logger.debug("message:" + message);
            }

            javaMailSender.send(mimeMessage);
            
            if(logger.isDebugEnabled()) 
                logger.debug("EMAIL SENT SUCCESSFULLY");
        } catch (MessagingException e) {
            if(logger.isDebugEnabled()) 
                logger.debug("EXCEPTION SENDING EMAIL");
            logger.error(e);
        } catch (IOException e) {
            if(logger.isDebugEnabled()) 
                logger.debug("EXCEPTION SENDING EMAIL");
            logger.error(e);
        }

    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }
}
