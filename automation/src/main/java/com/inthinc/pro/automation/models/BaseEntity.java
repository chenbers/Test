package com.inthinc.pro.automation.models;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public abstract class BaseEntity implements Serializable {

    /**
     * Auto generated serial version
     */
    private static final long serialVersionUID = 514782827757426880L;
    private final AutomationCalendar modified = new AutomationCalendar(WebDateFormat.RALLY_DATE_FORMAT);
    private final AutomationCalendar created = new AutomationCalendar(WebDateFormat.RALLY_DATE_FORMAT);

    @JsonProperty("modified")
    public String getModifiedString(){
        return modified.toString();
    }
    
    public AutomationCalendar getModified() {
        return modified;
    }
    
    @JsonProperty("modified")
    public void setModified(String modified) {
        this.modified.setDate(modified);
    }

    @JsonProperty("created")
    public String getCreatedString(){
        return created.toString();
    }
    
    public AutomationCalendar getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        this.created.setDate(created);
    }
    
}
