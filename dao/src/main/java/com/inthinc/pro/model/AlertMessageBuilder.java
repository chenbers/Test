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
    private List<String> parameterList;
    private List<String> ezParameterList;
    private Boolean acknowledge;
    private String alertName;
    
    public AlertMessageBuilder(Integer alertID, Integer messageID, Locale locale, String address, AlertMessageType alertMessageType, Boolean acknowledge,List<String> parameterList) {
        super();
        this.alertID = alertID;
        this.messageID = messageID;
        this.locale = locale;
        this.address = address;
        this.alertMessageType = alertMessageType;
        this.acknowledge = acknowledge;
        this.parameterList = parameterList;
    }
    public AlertMessageBuilder(Integer alertID, String alertName, Integer messageID, Locale locale, String address, AlertMessageType alertMessageType, Boolean acknowledge,List<String> parameterList, List<String> ezParameterList) {
        super();
        this.alertID = alertID;
        this.alertName = alertName;
        this.messageID = messageID;
        this.locale = locale;
        this.address = address;
        this.alertMessageType = alertMessageType;
        this.acknowledge = acknowledge;
        this.parameterList = parameterList;
        this.setEzParameterList(ezParameterList);
    }
    public AlertMessageBuilder() {
        super();
    }
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
    public void setParamterList(List<String> parameterList)
    {
        this.parameterList = parameterList;
    }
    public List<String> getParamterList()
    {
        return parameterList;
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
    public String getAlertName() {
        return alertName;
    }
    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }
    public boolean isEzCrm() {
        if (ezParameterList != null && !ezParameterList.isEmpty())
            return true;
        return false;
    }
    public List<String> getEzParameterList() {
        return ezParameterList;
    }
    public void setEzParameterList(List<String> ezParameterList) {
        this.ezParameterList = ezParameterList;
    }

}
