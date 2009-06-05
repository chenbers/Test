package com.inthinc.pro.model;

import java.util.List;
import java.util.Locale;

public class AlertMessageBuilder
{
    private Integer alertMessageID;
    private Locale  locale;
    private String  address;
    private AlertMessageType alertMessageType;
    private List<String> paramterList;
    
    public void setAlertMessageID(Integer alertMessageID)
    {
        this.alertMessageID = alertMessageID;
    }
    public Integer getAlertMessageID()
    {
        return alertMessageID;
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

}
