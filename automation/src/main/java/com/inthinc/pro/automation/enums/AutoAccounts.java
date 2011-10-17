package com.inthinc.pro.automation.enums;

import java.util.HashSet;
import java.util.Set;


public enum AutoAccounts {
    prime(AccountCapabilities.HOSEnabled, 
            AccountCapabilities.WaySmartEnabled,
            AccountCapabilities.LoginExpireNever,
            AccountCapabilities.PasswordExpireNever,
            AccountCapabilities.PasswordMinStrengthNone,
            AccountCapabilities.PasswordRequireInitialChangeNone), 
    QA, 
            
            
    ;

    private Set<AccountCapabilities> capabilities;

    private AutoAccounts(AccountCapabilities... capabilities) {
        this.capabilities = new HashSet<AccountCapabilities>();
        for (AccountCapabilities ac : capabilities) {
            this.capabilities.add(ac);
        }
    }

    public Set<AccountCapabilities> getCapabilities() {
        return capabilities;
    }
}
