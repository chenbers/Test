package com.inthinc.pro.service.security.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.inthinc.pro.service.adapters.AccountDAOAdapter;
import com.inthinc.pro.service.security.TiwiproPrincipal;

/**
 * Authorization advice for AccountDAOAdapter.
 */
@Aspect
@Component
public class AccountAuthorizationAdvice {

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
    @Before(value = "inAccountDAOAdapter() && (com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.deleteJoinPoint()) && args(accountId)", argNames = "accountId")
    public void doDeleteAccessCheck(Integer accountId) {
        if (!principal.isInthincUser()) {
            throw new AccessDeniedException("Access denied. User does not have the required privileges to delete account id " + accountId);
        }
    }

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has access to get a list of accounts. Only Inthinc users are allowed to list all accounts.
     */
    @Before(value = "inAccountDAOAdapter() && com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.getAllJoinPoint()")
    public void doGetAllAccessCheck() {
        if (!principal.isInthincUser()) {
            throw new AccessDeniedException("Access denied. User does not have the required privileges to list all accounts.");
        }
    }
}
