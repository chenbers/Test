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
    
    @Column(name = "data")
    private String fromPortalMsg;
    @Column(name = "senderName")
    private String fromPortalFrom;
    @Column(name = "created")
    private Date fromPortalSent;
    
    @Column(name = "textId")
    private Integer dmrOffset;
    private Integer type;
    
    public String getFromPortalTo() {
        return from;
    }
    
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
    public String getFromPortalMsg() {
        return fromPortalMsg;
    }
    public void setFromPortalMsg(String fromPortalMsg) {
        this.fromPortalMsg = fromPortalMsg;
    }
    public String getFromPortalFrom() {
        return fromPortalFrom;
    }
    public void setFromPortalFrom(String fromPortalFrom) {
        this.fromPortalFrom = fromPortalFrom;
    }
    public Date getFromPortalSent() {
        return fromPortalSent;
    }
    public void setFromPortalSent(Date fromPortalSent) {
        this.fromPortalSent = fromPortalSent;
    }

    public void setDmrOffset(Integer dmrOffset) {
        this.dmrOffset = dmrOffset;
    }

    public Integer getDmrOffset() {
        return dmrOffset;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}

