package com.inthinc.pro.backing;

public class AccountOptionsBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    // TODO: change this to populate from the back end, not tiwiPro.properties
    String phoneAlerts;
    Boolean enablePhoneAlerts;

    public Boolean getEnablePhoneAlerts() {
        if (enablePhoneAlerts == null)
            return Boolean.FALSE;
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
