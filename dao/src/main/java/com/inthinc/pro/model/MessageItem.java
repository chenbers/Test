package com.inthinc.pro.model;

import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MessageItem {
    private Boolean selected;
    private Date sendDate;
    private String toFrom;
    private String message;
    private TimeZone timeZone;
    private String result;
    private String entity;
    
    public Boolean getSelected() {
        return selected;
    }
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    public Date getSendDate() {
        return sendDate;
    }
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
    public String getToFrom() {
        return toFrom;
    }
    public void setToFrom(String toFrom) {
        this.toFrom = toFrom;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public TimeZone getTimeZone() {
        return timeZone;
    }
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getEntity() {
        return entity;
    }
    public void setEntity(String entity) {
        this.entity = entity;
    }
}

