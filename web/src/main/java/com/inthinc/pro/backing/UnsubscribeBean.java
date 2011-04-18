package com.inthinc.pro.backing;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.ReportScheduleDAO;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.util.MessageUtil;

public class UnsubscribeBean {
    
    private static final Logger logger = Logger.getLogger(UnsubscribeBean.class);

    private String emailAddress;
    private Integer reportScheduleID;
    
    private ReportScheduleDAO reportScheduleDAO;
    private PersonDAO personDAO;
    private String encryptPassword;
    
    private String messageType;
    private String messageName;
    
    private Boolean showLoginLink = Boolean.TRUE;
    

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
            ReportGroup reportGroup = ReportGroup.valueOf(reportSchedule.getReportID());
            if (reportGroup.getEntityType() == EntityType.ENTITY_INDIVIDUAL_DRIVER) {
                
                Person person = personDAO.findByEmail(decryptedEmailAddress);
                if (person == null) {
                    logger.debug("Cannot find person record with emailAddress = " + decryptedEmailAddress);
                    return;
                }
                if (person.getDriverID() == null) {
                    logger.debug("Cannot find driverID for person with emailAddress = " + decryptedEmailAddress);
                    return;
                }
                List<Integer> idList = reportSchedule.getGroupIDList();
                if (idList == null) {
                    logger.debug("Cannot find driverIDList for reportID = " + reportScheduleID);
                    return;
                }
                List<Integer> modIdList = new ArrayList<Integer>();
                for (Integer id : idList) {
                    if (id.equals(person.getDriverID()) || id.equals(ReportScheduleBean.ALL_DRIVERS_ID))
                        continue;
                    
                    modIdList.add(id);
                }
                reportSchedule.setGroupIDList(modIdList);
                setShowLoginLink(Boolean.FALSE);
            }
            else {
                reportSchedule.getEmailTo().remove(decryptedEmailAddress);
            }
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
    
    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public Boolean getShowLoginLink() {
        return showLoginLink;
    }

    public void setShowLoginLink(Boolean showLoginLink) {
        this.showLoginLink = showLoginLink;
    }

    
}
