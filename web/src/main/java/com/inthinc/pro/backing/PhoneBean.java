package com.inthinc.pro.backing;

import com.inthinc.pro.model.Account;

public class PhoneBean extends BaseBean {
    private String phoneNumber01;
    private String phoneNumber02;

    public String getPhoneNumber01() {
        Account acct = this.getAccountDAO().findByID(this.getProUser().getUser().getPerson().getAcctID());
        
        // this is required, so load something
        phoneNumber01 = "1888888TIWI";
        
        if ( acct.getProps() != null &&
             acct.getProps().getSupportPhone1() != null &&
             acct.getProps().getSupportPhone1().trim().length() != 0 ) {
            phoneNumber01 = acct.getProps().getSupportPhone1();
        }
        return phoneNumber01;
    }

    public String getPhoneNumber02() {
        Account acct = this.getAccountDAO().findByID(this.getProUser().getUser().getPerson().getAcctID());
        
        // this is not required, load blank then see if another number is supplied
        phoneNumber02 = "";
        
        if ( acct.getProps() != null &&
             acct.getProps().getSupportPhone2() != null &&
             acct.getProps().getSupportPhone2().trim().length() != 0 ) {
               phoneNumber02 = acct.getProps().getSupportPhone2();
           }        
        return phoneNumber02;
    }
    
}
