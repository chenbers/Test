package com.inthinc.pro.automation.models;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class AccountAttributes extends BaseEntity {
        
    private static final long serialVersionUID = 1L;
    
//    private String  wmsURL;
//    private String  wmsQuery;
//    private String  wmsLayers;
//    private String  wmsLayerQueryParam;
    private String  phoneAlertsActive;    
    private String  noReplyEmail;
    private String  supportContact1;
    private String  supportContact2;
    private String  supportContact3;
    private String  supportContact4;
    private String  supportContact5;
    private String  waySmart;
    private String  passwordExpire;
    private String  passwordStrength;
    private String  loginExpire;
    private String  passwordChange;
    
    private String supportContacts[];
    
    public String getPhoneAlertsActive() {
        return phoneAlertsActive;
    }
    public void setPhoneAlertsActive(String phoneAlertsActive) {
        this.phoneAlertsActive = phoneAlertsActive;
    }    
    public String getNoReplyEmail() {
        return noReplyEmail;
    }
    public void setNoReplyEmail(String noReplyEmail) {
        this.noReplyEmail = noReplyEmail;
    }
    public String getSupportContact1() {
        return supportContact1;
    }
    public void setSupportContact1(String supportContact1) {
        this.supportContact1 = supportContact1;
    }
    public String getSupportContact2() {
        return supportContact2;
    }
    public void setSupportContact2(String supportContact2) {
        this.supportContact2 = supportContact2;
    }
    
    public String getSupportContact3() {
        return supportContact3;
    }
    public void setSupportContact3(String supportContact3) {
        this.supportContact3 = supportContact3;
    }
    public String getSupportContact4() {
        return supportContact4;
    }
    public void setSupportContact4(String supportContact4) {
        this.supportContact4 = supportContact4;
    }
    public String getSupportContact5() {
        return supportContact5;
    }
    public void setSupportContact5(String supportContact5) {
        this.supportContact5 = supportContact5;
    }

    public String[] getSupportContacts() {
        
        supportContacts = new String[5];
        
        supportContacts[0] = (supportContact1 == null ? "" : supportContact1);
        supportContacts[1] = (supportContact2 == null ? "" : supportContact2);
        supportContacts[2] = (supportContact3 == null ? "" : supportContact3);
        supportContacts[3] = (supportContact4 == null ? "" : supportContact4);
        supportContacts[4] = (supportContact5 == null ? "" : supportContact5);
        return supportContacts;
    }


    public void setSupportContacts(String[] supportContacts) {
        this.supportContacts = supportContacts;
        
        setSupportContact1(supportContacts.length>0?supportContacts[0]:null);
        setSupportContact2(supportContacts.length>1?supportContacts[1]:null);
        setSupportContact3(supportContacts.length>2?supportContacts[2]:null);
        setSupportContact4(supportContacts.length>3?supportContacts[3]:null);
        setSupportContact5(supportContacts.length>4?supportContacts[4]:null);
    }

    public String getWaySmart() {
        if (waySmart == null)
            return "false";
        return waySmart;
    }
    public void setWaySmart(String waySmart) {
        this.waySmart = waySmart;
    }
    public String getPasswordExpire() {
        return passwordExpire;
    }
    public void setPasswordExpire(String passwordExpire) {
        this.passwordExpire = passwordExpire;
    }
    public String getPasswordStrength() {
        return passwordStrength;
    }
    public void setPasswordStrength(String passwordStrength) {
        this.passwordStrength = passwordStrength;
    }
    public String getLoginExpire() {
        return loginExpire;
    }
    public void setLoginExpire(String loginExpire) {
        this.loginExpire = loginExpire;
    }
    public void setPasswordChange(String passwordChange) {
        this.passwordChange = passwordChange;
    }
    /**
     * Returns the account's "Initial Password Change" setting
     * @return none, warn, or require
     */
    public String getPasswordChange() {
        return passwordChange;
    }
}
