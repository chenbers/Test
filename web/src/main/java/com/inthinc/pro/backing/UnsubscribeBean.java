package com.inthinc.pro.backing;

import java.security.InvalidParameterException;
import java.util.Locale;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import com.inthinc.pro.dao.ReportScheduleDAO;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.util.MessageUtil;

public class UnsubscribeBean {
    
    private static final Logger logger = Logger.getLogger(UnsubscribeBean.class);

    private String emailAddress;
    private Integer reportScheduleID;
    
    private ReportScheduleDAO reportScheduleDAO;
    private String encryptPassword;
    
    private String messageType;
    private String messageName;
    
    public void unsubscribeToReportSchedule(){
        if(emailAddress == null || reportScheduleID == null){
            throw new InvalidParameterException("Email Address or reportScheduleID are null.");
        }
        
        Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        if (!LocaleBean.supportedLocale(locale))
            locale = Locale.ENGLISH;
        
        StandardPBEStringEncryptor textEncryptor = new StandardPBEStringEncryptor();
        textEncryptor.setPassword(encryptPassword);
        textEncryptor.setStringOutputType("hexadecimal");
        
        String decryptedEmailAddress = textEncryptor.decrypt(emailAddress);
        
        ReportSchedule reportSchedule = reportScheduleDAO.findByID(reportScheduleID);
        if(reportSchedule != null){
            reportSchedule.getEmailTo().remove(decryptedEmailAddress);
            Integer result = reportScheduleDAO.update(reportSchedule);
            logger.debug("ReportSchedule Update Called, Result: " + result);
            messageType = MessageUtil.getMessageString("unsubscribe_report", locale);
            messageName = reportSchedule.getName();
        }else{
            logger.debug("ReportScheduler does not exist for ID = " + reportScheduleID);
        }
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setReportScheduleID(Integer reportScheduleID) {
        this.reportScheduleID = reportScheduleID;
    }

    public Integer getReportScheduleID() {
        return reportScheduleID;
    }

    public void setReportScheduleDAO(ReportScheduleDAO reportScheduleDAO) {
        this.reportScheduleDAO = reportScheduleDAO;
    }

    public ReportScheduleDAO getReportScheduleDAO() {
        return reportScheduleDAO;
    }

    
    public String getEncryptPassword() {
        return encryptPassword;
    }

    
    public void setEncryptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }


    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageType() {
        return messageType;
    }
    
    
}
