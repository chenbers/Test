package com.inthinc.pro.automation.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationStringUtil;
import com.inthinc.pro.automation.utils.ObjectConverter;

public abstract class BaseEntity implements Serializable {

    /**
     * Auto generated serial version
     */
    private static final long serialVersionUID = 514782827757426880L;
    
    @JsonIgnore
    private final AutomationCalendar modified = new AutomationCalendar(WebDateFormat.RALLY_DATE_FORMAT);
    
    @JsonIgnore
    private final AutomationCalendar created = new AutomationCalendar(WebDateFormat.RALLY_DATE_FORMAT);
    
    private final Map<String, Object> unknowns = new HashMap<String, Object>();

    
    public AutomationCalendar getModified() {
        return modified;
    }
    
    @JsonProperty("modified")
    public void setModified(String modified) {
        this.modified.setDate(modified);
    }

    
    public AutomationCalendar getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        this.created.setDate(created);
    }
    
    @JsonAnySetter
    public void setUnknown(String key, Object value){
        unknowns.put(key, value);
    }
    
    @JsonAnyGetter
    public Map<String, Object> getUnknown(){
        return unknowns;
    }
    
    public String toJsonString(){
        JSONObject jsonA = ObjectConverter.convertToJSONObject(this, "object");
        return AutomationStringUtil.toString(jsonA);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> T autoCopy(){
        return (T) ObjectConverter.convertJSONToObject(toJsonString(), "object", this.getClass());
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj instanceof BaseEntity){
            return toJsonString().equals(((BaseEntity)obj).toJsonString());
        }
        return false;
    }
    
}
