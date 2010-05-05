package com.inthinc.pro.backing;

import com.inthinc.pro.model.Account;

public class PhoneBean extends BaseBean {
    private String phoneNumber01;
    private String phoneNumber02;

    public String getPhoneNumber01() {
        Account acct = this.getAccountDAO().findByID(this.getProUser().getUser().getPerson().getAcctID());
        return acct.getProps().getSupportPhone1();
    }

    public String getPhoneNumber02() {
        Account acct = this.getAccountDAO().findByID(this.getProUser().getUser().getPerson().getAcctID());        
        return acct.getProps().getSupportPhone2();
    }
    
}
