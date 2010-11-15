/**
 * 
 */
package com.inthinc.pro.service.security.aspects;

import org.springframework.stereotype.Component;

/**
 * TODO Temporary place holder until the final spec of the class is defined.
 */
@Component
public class TiwiproPrincipalStub {

    private boolean inthincUser;
    
    /**
     * @return
     */
    public boolean isInthincUser() {
        return inthincUser;
    }

    /**
     * @return
     */
    public Integer getAccountID() {
        // TODO Temporary stub for testing.
        return 10;
    }

    public void setInthincUser(boolean inthincUser) {
        this.inthincUser = inthincUser;
    }
}
