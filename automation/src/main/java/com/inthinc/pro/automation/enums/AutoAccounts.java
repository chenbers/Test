package com.inthinc.pro.automation.enums;

import java.util.HashSet;
import java.util.Set;


public enum AutoAccounts {
    prime(AccountCapability.HOSEnabled, 
            AccountCapability.WaySmartEnabled,
            AccountCapability.LoginExpireNever,
            AccountCapability.PasswordExpireNever,
            AccountCapability.PasswordMinStrengthNone,
            AccountCapability.PasswordRequireInitialChangeNone), 
    QA, 
            
            
    ;

    private Set<AccountCapability> capabilities;

    private AutoAccounts(AccountCapability... capabilities) {
        this.capabilities = new HashSet<AccountCapability>();
        for (AccountCapability ac : capabilities) {
            this.capabilities.add(ac);
        }
    }

    public Set<AccountCapability> getCapabilities() {
        return capabilities;
    }
}
