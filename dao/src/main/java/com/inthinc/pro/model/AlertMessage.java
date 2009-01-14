package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class AlertMessage extends BaseEntity
{

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

}
