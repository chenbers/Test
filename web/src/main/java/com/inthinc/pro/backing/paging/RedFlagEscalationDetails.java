package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.AlertMessageDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.RedFlagAlertDAO;
import com.inthinc.pro.model.AlertEscalationStatus;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.util.MessageUtil;

public class RedFlagEscalationDetails {
    private AlertMessageDAO alertMessageDAO;
    private List<AlertMessage> messageList;
    private RedFlagAlert alert;
    private List<String> personList;
    private List<PhoneEscalationDetails> phoneList;
    private String escalationEmail;
    
    public RedFlagEscalationDetails(AlertMessageDAO alertMessageDAO, RedFlagAlertDAO redFlagsAlertsDAO, PersonDAO personDAO, RedFlag redFlag, AlertMessage message) {
        
        this.alertMessageDAO = alertMessageDAO;
        messageList = new ArrayList<AlertMessage>();
        if (message != null) {
            if (message.getAlertID() != null) {
                alert = redFlagsAlertsDAO.findByID(message.getAlertID());
                initPersonList(alert.getNotifyPersonIDs(), personDAO);
                initPhoneList(alert.getVoiceEscalationPersonIDs(), personDAO);
                initEscalationEmail(alert.getEmailEscalationPersonID(), personDAO);
            }
            messageList.add(message);
        }
    }

    public void addMessage(AlertMessage message) {
        messageList.add(message);
    }
    
    public void cancelPendingAction() {
        for (AlertMessage message : messageList)
            if (message != null && message.getMessageID() != null &&
               (message.getStatus() == AlertEscalationStatus.ESCALATED_AWAITING_ACKNOWLEDGE || message.getStatus() == AlertEscalationStatus.NEW)) {
                System.out.println("cancel pending msgID: " + message.getMessageID());
                if (alertMessageDAO.cancelPendingMessage(message.getMessageID()))
                   message.setStatus(AlertEscalationStatus.CANCELED); 
                
            }
    }
    public AlertEscalationStatus getStatus() {
        for (AlertMessage message : messageList)
            if (message != null && message.getMessageID() != null && 
                (message.getStatus() == AlertEscalationStatus.ESCALATED_AWAITING_ACKNOWLEDGE || message.getStatus() == AlertEscalationStatus.NEW)) {
                return AlertEscalationStatus.ESCALATED_AWAITING_ACKNOWLEDGE;
            }
        return AlertEscalationStatus.NONE;
        
    }

    private void initPhoneList(List<Integer> voiceEscalationPersonIDs, PersonDAO personDAO) {
        phoneList = new ArrayList<PhoneEscalationDetails>();
// TODO: remove when real backend is ready
        
//        phoneList.add(new PhoneEscalationDetails("5555551111", PhoneEscalationStatus.FAILED, 0,0));
//        phoneList.add(new PhoneEscalationDetails("5555552222", PhoneEscalationStatus.IN_PROGRESS, 2, 4));
//        phoneList.add(new PhoneEscalationDetails("5555553333", PhoneEscalationStatus.NOT_ATTEMPTED, 0,0));
        if (voiceEscalationPersonIDs == null) {
            phoneList = null;
            return;
        }
        
        // only one of the messages should be the phone delivery type with escalation details
        for (AlertMessage message : messageList) {
            int cnt = 0;
            int escalationOrdinal = (message.getEscalationOrdinal() == null) ? 0 : message.getEscalationOrdinal().intValue(); 
            for (Integer personID : voiceEscalationPersonIDs) {
                Person person = personDAO.findByID(personID);
                PhoneEscalationStatus status = PhoneEscalationStatus.NOT_ATTEMPTED;
                if (cnt == escalationOrdinal)
                    status = (message.getStatus().equals(AlertEscalationStatus.ESCALATED_ACKNOWLEDGED) ? PhoneEscalationStatus.SUCCESS : ((message.getStatus().equals(AlertEscalationStatus.CANCELED) ? PhoneEscalationStatus.CANCELED : PhoneEscalationStatus.IN_PROGRESS)));
                else if (cnt < escalationOrdinal)
                    status = PhoneEscalationStatus.FAILED;
                phoneList.add(new PhoneEscalationDetails(person.getPriPhone(), status, message.getEscalationTryCount(), alert.getMaxEscalationTries()));
                cnt++;
            }
        }
        if (phoneList.size() == 0)
            phoneList = null;
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
    
    public RedFlagAlert getAlert() {
        return alert;
    }
    public void setAlert(RedFlagAlert alert) {
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
//    public List<String> getEmailTo() {
//        if (alert.getEmailTo() == null || alert.getEmailTo().size() == 0)
//            return null;
//        
//        return alert.getEmailTo();
//    }
    public AlertMessageDAO getAlertMessageDAO() {
        return alertMessageDAO;
    }

    public void setAlertMessageDAO(AlertMessageDAO alertMessageDAO) {
        this.alertMessageDAO = alertMessageDAO;
    }
    
    public List<AlertMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<AlertMessage> messageList) {
        this.messageList = messageList;
    }

    public String getAlertType() {
        if (alert == null || alert.getTypes() == null || alert.getTypes().size() == 0)
            return "";
        
        StringBuffer buffer = new StringBuffer();
        for (AlertMessageType type : alert.getTypes()) {
            if (buffer.length() > 0)
                buffer.append(",");
            
            buffer.append(MessageUtil.getMessageString(type.toString()));
        }
        
        return buffer.toString();
    }
}
