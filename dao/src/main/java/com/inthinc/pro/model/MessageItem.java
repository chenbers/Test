package com.inthinc.pro.model;

import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.annotations.Column;

@XmlRootElement
public class MessageItem {
    private Boolean selected;
    @Column(name = "time")
    private Date sendDate;
    @Column(name = "driverID")
    private Integer fromDriverID;
    @Column(name = "vehicleID")
    private Integer fromVehicleID;
    private String to;
    @Column(name = "driverName")
    private String from;
    @Column(name = "textMsg")
    private String message;  
    private TimeZone timeZone;
    private String result;
    private String entity;
    
    public Boolean isSelected() {
        return selected!=null?selected:false;
    }
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
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
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
    public void setFrom(String from) {
        this.from = from;
    }
    public String getFrom() {
        return from;
    }
    public void setFromDriverID(Integer fromDriverID) {
        this.fromDriverID = fromDriverID;
    }
    public Integer getFromDriverID() {
        return fromDriverID;
    }
    public void setFromVehicleID(Integer fromVehicleID) {
        this.fromVehicleID = fromVehicleID;
    }
    public Integer getFromVehicleID() {
        return fromVehicleID;
    }
}

