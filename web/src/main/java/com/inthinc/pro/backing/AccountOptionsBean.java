package com.inthinc.pro.backing;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.util.MessageUtil;

public class AccountOptionsBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    
    String phoneAlerts;
    Boolean enablePhoneAlerts;
    
    public AccountOptionsBean() {
        super();
    }
    
    public void init() {}
    
    public Boolean getEnablePhoneAlerts() {               
        Account acct = this.getAccountDAO().findByID(this.getProUser().getUser().getPerson().getAcctID());
        String phoneAlertsActive = acct.getProps().getPhoneAlertsActive();
          
        // Default to on, any site specific info?
        setEnablePhoneAlerts(true);
        if ( (phoneAlertsActive != null) && (phoneAlertsActive.trim().length() != 0)) {
            setEnablePhoneAlerts((phoneAlertsActive.equalsIgnoreCase(MessageUtil.getMessageString("editAccount_yes")))?true:false);
        }       
        
        return enablePhoneAlerts;
    }

    public void setEnablePhoneAlerts(Boolean enablePhoneAlerts) {
        this.enablePhoneAlerts = enablePhoneAlerts;
    }

    public String getPhoneAlerts() {
        return phoneAlerts;
    }

    public void setPhoneAlerts(String phoneAlerts) {
        this.phoneAlerts = phoneAlerts;
        setEnablePhoneAlerts(new Boolean(phoneAlerts));
    }

}
