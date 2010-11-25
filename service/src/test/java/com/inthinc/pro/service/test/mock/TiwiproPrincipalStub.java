/**
 * 
 */
package com.inthinc.pro.service.test.mock;

import org.springframework.stereotype.Component;

import com.inthinc.pro.service.security.TiwiproPrincipal;

/**
 * TODO Temporary place holder until the final spec of the class is defined.
 */
@Component
public class TiwiproPrincipalStub extends TiwiproPrincipal {

    private boolean inthincUser;
    private int accountID;

    /**
     * @return
     */
    public boolean isInthincUser() {
        return inthincUser;
    }

    public void setInthincUser(boolean inthincUser) {
        this.inthincUser = inthincUser;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountId) {
        this.accountID = accountId;
    }
}
