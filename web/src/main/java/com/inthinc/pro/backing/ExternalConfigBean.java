package com.inthinc.pro.backing;

import com.inthinc.pro.model.Account;

public class ExternalConfigBean extends BaseBean {

    private String phoneNumber01;
    private String phoneNumber02; 
    
    public void init() {  
        // set inthinc default
        phoneNumber01 = "1888888TIWI";
        phoneNumber02 = "18888888494";
        
        if ( this.isLoggedIn() ) {
            setPhoneData();
        }
    }
    
    public String getPhoneNumber01() {
        if ( this.isLoggedIn() ) {
            setPhoneData();
        }
        return phoneNumber01;
    }
    public void setPhoneNumber01(String phoneNumber01) {        
        this.phoneNumber01 = phoneNumber01;
    }
    public String getPhoneNumber02() {
        if ( this.isLoggedIn() ) {
            setPhoneData();
        }
        return phoneNumber02;
    }
    public void setPhoneNumber02(String phoneNumber02) {
        this.phoneNumber02 = phoneNumber02;
    }
    public void setPhoneData() {
        Account acct = getAccountDAO().findByID(this.getProUser().getUser().getPerson().getAcctID());
        setPhoneNumber01(acct.getProps().getSupportPhone1());
        setPhoneNumber02(acct.getProps().getSupportPhone2());
    }
}
