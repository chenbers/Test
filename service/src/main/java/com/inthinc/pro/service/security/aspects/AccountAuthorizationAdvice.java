package com.inthinc.pro.service.security.aspects;

import org.apache.commons.lang.NotImplementedException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.service.adapters.AccountDAOAdapter;
import com.inthinc.pro.service.security.TiwiproPrincipal;

/**
 * Authorization advice for AccountDAOAdapter.
 */
@Aspect
@Component
public class AccountAuthorizationAdvice implements EntityAuthorization<Account> {

    @Autowired
    private TiwiproPrincipal principal;

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match all methods in the {@link AccountDAOAdapter} class.
     */
    @Pointcut("target(com.inthinc.pro.service.adapters.AccountDAOAdapter)")
    public void inAccountDAOAdapter() {}

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has access to delete the entity. Only Inthinc users are allowed to delete accounts.
     */
    @SuppressWarnings("unused")
    @Before(value = "inAccountDAOAdapter() && (com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.deleteJoinPoint()) && args(accountId)", argNames = "accountId")
    private void doDeleteAccessCheck(Integer accountId) {
        if (!principal.isInthincUser()) {
            throw new AccessDeniedException("Access denied. User does not have the required privileges to delete account id " + accountId);
        }
    }

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has access to get a list of accounts. Only Inthinc users are allowed to list all accounts.
     */
    @SuppressWarnings("unused")
    @Before(value = "inAccountDAOAdapter() && com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.getAllJoinPoint()")
    private void doGetAllAccessCheck() {
        if (!principal.isInthincUser()) {
            throw new AccessDeniedException("Access denied. User does not have the required privileges to list all accounts.");
        }
    }

    /**
     * @see com.inthinc.pro.service.security.aspects.EntityAuthorization#doAccessCheck(java.lang.Object)
     */
    @Override
    public void doAccessCheck(Account entity) {
        throw new NotImplementedException("Method not implemented.");
    }
}
