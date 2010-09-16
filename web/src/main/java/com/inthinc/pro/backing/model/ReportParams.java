package com.inthinc.pro.backing.model;

import java.util.Locale;

import com.inthinc.pro.backing.ui.DateRange;

public class ReportParams implements Cloneable {
    
    DateRange dateRange;
    Integer groupID;
    Integer driverID;
    Boolean valid;
    Locale locale;
    
    public ReportParams(Locale locale)
    {
        dateRange = new DateRange(locale);
        this.locale = locale;
    }
    
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Integer getGroupID() {
        return groupID;
    }
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }
    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
    
    public Boolean getValid() {
        return (getDateRange() != null && getDateRange().getBadDates() == null);
    }
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getErrorMsg() {
        return (getDateRange() == null ? null : getDateRange().getBadDates());
    }
    
    public DateRange getDateRange() {
        return dateRange;
    }
    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public ReportParams clone() 
    {
        try {
            return (ReportParams)super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        
    }

}
