package com.inthinc.pro.model;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.dao.util.DateUtil;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlertMessage 
{

    /**
     * 
     */
    private static final long serialVersionUID = -3349406814384682640L;

    @ID
    @Column(name = "msgID")
    private Integer messageID;

    @Column(name = "deliveryMethodID")
    private AlertMessageDeliveryType alertMessageDeliveryType;
    
    @Column(name = "alertTypeID")
    private AlertMessageType alertMessageType;
    
    private RedFlagLevel level;     
    
    private String address;         // delivery address (e.g. the email address dave@mydomain.com)
    private String message;         // the text of the message (e.g "dave was speeding at 9...")
    
    private Long noteID;
    
    private Integer personID;
    
    private Integer alertID; //Alert that caused this message to occurr.
    
    private Integer zoneID;
    
    private Boolean acknowledge;
    
    private AlertEscalationStatus status;
    
    private Integer escalationOrdinal;
    private Integer escalationTryCount;
    
    public AlertMessage(Integer messageID, AlertMessageDeliveryType alertMessageDeliveryType, AlertMessageType alertMessageType, String address, String message)
    {
        super();
        this.messageID = messageID;
        this.alertMessageDeliveryType = alertMessageDeliveryType;
        this.alertMessageType = alertMessageType;
        this.address = address;
        this.message = message;
    }
    public AlertMessage()
    {
        
    }
    public AlertMessage(Integer messageID, AlertMessageDeliveryType alertMessageDeliveryType, AlertMessageType alertMessageType, RedFlagLevel level, String address, String message, Long noteID,
            Integer personID, Integer alertID, Integer zoneID, Boolean acknowledge, AlertEscalationStatus status) {
        super();
        this.messageID = messageID;
        this.alertMessageDeliveryType = alertMessageDeliveryType;
        this.alertMessageType = alertMessageType;
        this.level = level;
        this.address = address;
        this.message = message;
        this.noteID = noteID;
        this.personID = personID;
        this.alertID = alertID;
        this.zoneID = zoneID;
        this.acknowledge = acknowledge;
        this.status = status;
    }
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
    public Integer getMessageID()
    {
        return messageID;
    }
    public void setMessageID(Integer messageID)
    {
        this.messageID = messageID;
    }
    public AlertMessageDeliveryType getAlertMessageDeliveryType()
    {
        return alertMessageDeliveryType;
    }
    public void setAlertMessageDeliveryType(AlertMessageDeliveryType alertMessageDeliveryType)
    {
        this.alertMessageDeliveryType = alertMessageDeliveryType;
    }
    public AlertMessageType getAlertMessageType()
    {
        return alertMessageType;
    }
    public void setAlertMessageType(AlertMessageType alertMessageType)
    {
        this.alertMessageType = alertMessageType;
    }
    public RedFlagLevel getLevel()
    {
        return level;
    }
    public void setLevel(RedFlagLevel level)
    {
        this.level = level;
    }
    public void setNoteID(Long noteID)
    {
        this.noteID = noteID;
    }
    public Long getNoteID()
    {
        return noteID;
    }
    public void setAlertID(Integer alertID)
    {
        this.alertID = alertID;
    }
    public Integer getAlertID()
    {
        return alertID;
    }
    public void setZoneID(Integer zoneID)
    {
        this.zoneID = zoneID;
    }
    public Integer getZoneID()
    {
        return zoneID;
    }
    public Integer getPersonID()
    {
        return personID;
    }
    public void setPersonID(Integer personID)
    {
        this.personID = personID;
    }
    public Boolean getAcknowledge() {
        return acknowledge;
    }
    public void setAcknowledge(Boolean acknowledge) {
        this.acknowledge = acknowledge;
    }
    public AlertEscalationStatus getStatus() {
        return status;
    }
    public void setStatus(AlertEscalationStatus status) {
        this.status = status;
    }
    
    public Integer getEscalationOrdinal() {
        return escalationOrdinal;
    }
    public void setEscalationOrdinal(Integer escalationOrdinal) {
        this.escalationOrdinal = escalationOrdinal;
    }
    public Integer getEscalationTryCount() {
        return escalationTryCount;
    }
    public void setEscalationTryCount(Integer escalationTryCount) {
        this.escalationTryCount = escalationTryCount;
    }

}
