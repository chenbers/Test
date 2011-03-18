package com.inthinc.pro.model;

import java.util.List;
import java.util.Locale;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlertMessageBuilder implements Comparable<AlertMessageBuilder>
{
    private Integer alertID;
    private Integer messageID;
    private Locale  locale;
    private String  address;
    private AlertMessageType alertMessageType;
    private List<String> paramterList;
    private Boolean acknowledge;
    
    public void setAlertID(Integer alertID)
    {
        this.alertID = alertID;
    }
    public Integer getAlertID()
    {
        return alertID;
    }
    public void setMessageID(Integer messageID)
    {
        this.messageID = messageID;
    }
    public Integer getMessageID()
    {
        return messageID;
    }
    public void setAlertMessageType(AlertMessageType alertMessageType)
    {
        this.alertMessageType = alertMessageType;
    }
    public AlertMessageType getAlertMessageType()
    {
        return alertMessageType;
    }
    public void setParamterList(List<String> paramterList)
    {
        this.paramterList = paramterList;
    }
    public List<String> getParamterList()
    {
        return paramterList;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public String getAddress()
    {
        return address;
    }
    public void setLocale(Locale locale)
    {
        this.locale = locale;
    }
    public Locale getLocale()
    {
        return locale;
    }
    public Boolean getAcknowledge() {
        return acknowledge;
    }
    public void setAcknowledge(Boolean acknowledge) {
        this.acknowledge = acknowledge;
    }
    @Override
    public int compareTo(AlertMessageBuilder other) {
        //compare by address
        
        return this.getAddress().compareToIgnoreCase(other.getAddress());
    }

}
