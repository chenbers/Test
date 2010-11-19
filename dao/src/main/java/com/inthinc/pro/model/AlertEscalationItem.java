package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;

public class AlertEscalationItem {
    
    @Column(name = "alertID")
    private Integer redFlagAlertID;
    private Integer personID;
    private Integer ordinal;
    private AlertMessageDeliveryType alertMessageDeliveryType;
    
    public Integer getId() {
        return redFlagAlertID;
    }
    public void setId(Integer redFlagAlertID) {
        this.redFlagAlertID = redFlagAlertID;
    }
    public Integer getPersonID() {
        return personID;
    }
    public void setPersonID(Integer personID) {
        this.personID = personID;
    }
    public AlertMessageDeliveryType getAlertMessageDeliveryType() {
        return alertMessageDeliveryType;
    }
    public void setAlertMessageDeliveryType(AlertMessageDeliveryType alertMessageDeliveryType) {
        this.alertMessageDeliveryType = alertMessageDeliveryType;
    }
    public Integer getOrdinal() {
        return ordinal;
    }
    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }
}
