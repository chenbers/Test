package com.inthinc.pro.backing;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.ReportScheduleDAO;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.reports.ReportGroup;
import com.inthinc.pro.util.MessageUtil;

@KeepAlive
public class UnsubscribeBean {

    private static final Logger logger = Logger.getLogger(UnsubscribeBean.class);

    private String emailAddress;
    private Integer reportScheduleID;

    private ReportScheduleDAO reportScheduleDAO;
    private PersonDAO personDAO;
    private String encryptPassword;

    private String messageType;
//    private String messageName;

    private Boolean showLoginLink = Boolean.TRUE;

    private Person person;

    private String decryptedEmailAddress;
    private ReportSchedule reportSchedule;

    public void prepareToUnsubscribeToReportSchedule() {
        if (emailAddress == null || reportScheduleID == null) {
            throw new InvalidParameterException("Email Address or reportScheduleID are null.");
        }
        setupMessageType();
        setupReportSchedule();
        setupDecryptedEmailAddress();
    }
    public String cancelAction(){
        return "pretty:unsubscribeLogin";

    }

    public String unsubscribeAction() {

        if (reportSchedule != null) {
            ReportGroup reportGroup = ReportGroup.valueOf(reportSchedule.getReportID());
            if (reportGroup.getEntityType() == EntityType.ENTITY_INDIVIDUAL_DRIVER) {

                Person person = personDAO.findByEmail(decryptedEmailAddress);
                if (person == null) {
                    logger.debug("Cannot find person record with emailAddress = " + decryptedEmailAddress);
                    return "";
                }
                if (person.getDriverID() == null) {
                    logger.debug("Cannot find driverID for person with emailAddress = " + decryptedEmailAddress);
                    return "";
                }
                List<Integer> idList = reportSchedule.getDriverIDList();
                if (idList == null) {
                    logger.debug("Cannot find driverIDList for reportID = " + reportScheduleID);
                    return "";
                }
                List<Integer> modIdList = new ArrayList<Integer>();
                for (Integer id : idList) {
                    if (id.equals(person.getDriverID()) || id.equals(ReportScheduleBean.ALL_DRIVERS_ID))
                        continue;

                    modIdList.add(id);
                }
                reportSchedule.setDriverIDList(modIdList);
                setShowLoginLink(Boolean.FALSE);
            } else {
                reportSchedule.getEmailTo().remove(decryptedEmailAddress);
            }
            Integer result = reportScheduleDAO.update(reportSchedule);
            logger.debug("ReportSchedule Update Called, Result: " + result);
        } else {
            logger.debug("ReportScheduler does not exist for ID = " + reportScheduleID);
            return "";
        }
        return "pretty:unsubscribeSuccessful";
    }

    public void unsubscribeSuccessful() {

        if (reportScheduleID == null) {
            throw new InvalidParameterException("ReportScheduleID is null.");
        }
        setupMessageType();
        setupReportSchedule();
        logger.debug("Unsubscribe successful.");
    }

    private void setupMessageType() {
        Locale locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        if (!LocaleBean.supportedLocale(locale))
            locale = Locale.ENGLISH;
        messageType = MessageUtil.getMessageString("unsubscribe_report", locale);
    }

    private void setupReportSchedule() {
        reportSchedule = reportScheduleDAO.findByID(reportScheduleID);
        if (reportSchedule == null) {
            logger.debug("ReportScheduler does not exist for ID = " + reportScheduleID);
        }
    }

    private void setupDecryptedEmailAddress() {
        StandardPBEStringEncryptor textEncryptor = new StandardPBEStringEncryptor();
        textEncryptor.setPassword(encryptPassword);
        textEncryptor.setStringOutputType("hexadecimal");
        decryptedEmailAddress = textEncryptor.decrypt(emailAddress);
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

    public String getMessageName() {
        if (reportSchedule != null)
            return reportSchedule.getName();
        else
            return null;
    }

//    public void setMessageType(String messageType) {
//        this.messageType = messageType;
//    }

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getDecryptedEmailAddress() {
        return decryptedEmailAddress;
    }

    public void setDecryptedEmailAddress(String decryptedEmailAddress) {
        this.decryptedEmailAddress = decryptedEmailAddress;
    }

}
