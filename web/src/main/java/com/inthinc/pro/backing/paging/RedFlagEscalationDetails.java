package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.RedFlagAndZoneAlertsDAO;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagOrZoneAlert;

public class RedFlagEscalationDetails {
    private AlertMessageDAO alertMessageDAO;
    private AlertMessage message;
    private RedFlagOrZoneAlert alert;
    private List<String> personList;
    private List<PhoneEscalationDetails> phoneList;
    private String escalationEmail;
    
    public RedFlagEscalationDetails(AlertMessageDAO alertMessageDAO, RedFlagAndZoneAlertsDAO redFlagsAndZoneAlertsDAO, PersonDAO personDAO, RedFlag redFlag) {
        
        this.alertMessageDAO = alertMessageDAO;
        
        if (redFlag.getMsgID() != null) {
            message = alertMessageDAO.findByID(redFlag.getMsgID());
            if (message.getAlertID() != null) {
                alert = redFlagsAndZoneAlertsDAO.findByID(message.getAlertID());
                initPersonList(alert.getNotifyPersonIDs(), personDAO);
                initPhoneList(alert.getVoiceEscalationPersonIDs(), personDAO);
                initEscalationEmail(alert.getEmailEscalationPersonID(), personDAO);
                
            }
        }
    }
    
    public void cancelPendingAction() {
        if (message != null && message.getMessageID() != null) {
            System.out.println("cancel pending msgID: " + message.getMessageID());
            alertMessageDAO.cancelPendingMessage(message.getMessageID());
        }
    }

    private void initPhoneList(List<Integer> voiceEscalationPersonIDs, PersonDAO personDAO) {
        phoneList = new ArrayList<PhoneEscalationDetails>();
        phoneList.add(new PhoneEscalationDetails("5555551111", PhoneEscalationStatus.FAILED, 0,0));
        phoneList.add(new PhoneEscalationDetails("5555552222", PhoneEscalationStatus.IN_PROGRESS, 2, 4));
        phoneList.add(new PhoneEscalationDetails("5555553333", PhoneEscalationStatus.NOT_ATTEMPTED, 0,0));
/*        
        if (voiceEscalationPersonIDs == null)
            return;
        int cnt = 0;
        int escalationOrdinal = (message.getEscalationOrdinal() == null) ? 0 : message.getEscalationOrdinal().intValue(); 
        for (Integer personID : voiceEscalationPersonIDs) {
            Person person = personDAO.findByID(personID);
            PhoneEscalationStatus status = PhoneEscalationStatus.NOT_ATTEMPTED;
            if (cnt == escalationOrdinal)
                status = (message.getStatus().equals(AlertEscalationStatus.ESCALATED_ACKNOWLEDGED) ? PhoneEscalationStatus.SUCCESS : PhoneEscalationStatus.IN_PROGRESS);
            else if (cnt < escalationOrdinal)
                status = PhoneEscalationStatus.FAILED;
            phoneList.add(new PhoneEscalationDetails(person.getPriPhone(), status, message.getEscalationTryCount(), alert.getMaxEscalationTries()));
            cnt++;
        }
        if (phoneList.size() == 0)
            phoneList = null;
*/        
    }
    private void initEscalationEmail(Integer emailEscalationPersonID, PersonDAO personDAO) {
        if (emailEscalationPersonID == null)
            return;
        Person person = personDAO.findByID(emailEscalationPersonID);
        if (person == null)
            return;
        escalationEmail = person.getPriEmail();
        
    }
    private void initPersonList(List<Integer> notifyPersonIDs, PersonDAO personDAO) {
        if (notifyPersonIDs == null)
            return;
        personList = new ArrayList<String>();
        for (Integer personID : notifyPersonIDs) {
            Person person = personDAO.findByID(personID);
            personList.add(person.getFullName());
        }
        if (personList.size() == 0)
            personList = null;
    }
    
    public AlertMessage getMessage() {
        return message;
    }
    public void setMessage(AlertMessage message) {
        this.message = message;
    }
    public RedFlagOrZoneAlert getAlert() {
        return alert;
    }
    public void setAlert(RedFlagOrZoneAlert alert) {
        this.alert = alert;
    }
    
    public String getTest() {
        return "This is a test.";
    }
        
    public List<String> getPersonList() {
        return personList;
    }
    public void setPersonList(List<String> personList) {
        this.personList = personList;
    }
    public List<PhoneEscalationDetails> getPhoneList() {
        return phoneList;
    }
    public void setPhoneList(List<PhoneEscalationDetails> phoneList) {
        this.phoneList = phoneList;
    }
    public String getEscalationEmail() {
        return escalationEmail;
    }
    public void setEscalationEmail(String escalationEmail) {
        this.escalationEmail = escalationEmail;
    }
    public List<String> getEmailTo() {
        if (alert.getEmailTo() == null || alert.getEmailTo().size() == 0)
            return null;
        
        return alert.getEmailTo();
    }
    public AlertMessageDAO getAlertMessageDAO() {
        return alertMessageDAO;
    }

    public void setAlertMessageDAO(AlertMessageDAO alertMessageDAO) {
        this.alertMessageDAO = alertMessageDAO;
    }
    
}
