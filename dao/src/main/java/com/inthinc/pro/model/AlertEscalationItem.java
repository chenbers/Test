package com.inthinc.pro.model;

import java.io.Serializable;

public class AlertEscalationItem implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public AlertEscalationItem() {
        super();
    }
    private Integer personID;
    private Integer escalationOrder; //-1 = last resort email
    private Integer contactType;
    
    public AlertEscalationItem(Integer personID, Integer escalationOrder) {
        this.personID = personID;
        this.escalationOrder = escalationOrder;
        this.contactType = escalationOrder == -1? 0:1;
    }
    public Integer getPersonID() {
        return personID;
    }
    public void setPersonID(Integer personID) {
        this.personID = personID;
    }
    public Integer getEscalationOrder() {
        return escalationOrder;
    }
    public Integer getContactType() {
        return contactType;
    }
   public void setEscalationOrder(Integer escalationOrder) {
        this.escalationOrder = escalationOrder;
        contactType = escalationOrder == -1? 0:1;
    }
    public String toString() {
        return "AlertEscalationItem [personID="+getPersonID()+", escalationOrder="+getEscalationOrder()+", contactType="+contactType+"]";
    }
}
