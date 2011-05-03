package com.inthinc.pro.reports.hos.model;

import java.util.Date;
import java.util.TimeZone;

import com.inthinc.hos.model.HOSStatus;

public class RemarkLog {
    private HOSStatus   status;
    private Date        logTimeDate;
    private TimeZone    logTimeZone;
    private Boolean     edited;
    private String      location;
    private String      originalLocation;
    private Boolean     deleted;
    private Number      startOdometer;
    private String      statusDescription;
    private Boolean     locationEdited;
    private String      editor;
    
    public String getEditor() {
        return editor;
    }
    public void setEditor(String editor) {
        this.editor = editor;
    }
    public Boolean getLocationEdited() {
        return locationEdited;
    }
    public void setLocationEdited(Boolean locationEdited) {
        this.locationEdited = locationEdited;
    }
    public String getStatusDescription() {
        return statusDescription;
    }
    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
    public HOSStatus getStatus() {
        return status;
    }
    public void setStatus(HOSStatus status) {
        this.status = status;
    }
    public Date getLogTimeDate() {
        return logTimeDate;
    }
    public void setLogTimeDate(Date logTimeDate) {
        this.logTimeDate = logTimeDate;
    }
    public TimeZone getLogTimeZone() {
        return logTimeZone;
    }
    public void setLogTimeZone(TimeZone logTimeZone) {
        this.logTimeZone = logTimeZone;
    }
    public Boolean getEdited() {
        return edited;
    }
    public void setEdited(Boolean edited) {
        this.edited = edited;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getOriginalLocation() {
        return originalLocation;
    }
    public void setOriginalLocation(String originalLocation) {
        this.originalLocation = originalLocation;
    }
    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    public Number getStartOdometer() {
        return startOdometer;
    }
    public void setStartOdometer(Number startOdometer) {
        this.startOdometer = startOdometer;
    }
    
    
}
