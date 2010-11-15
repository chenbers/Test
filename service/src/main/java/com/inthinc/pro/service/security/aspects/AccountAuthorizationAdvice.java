package com.inthinc.pro.service.security.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.stereotype.Component;

/**
 * Base authorization advice for DAO adapters which will only grant access to objects belonging to the same account as the currently logged user.
 */
@Aspect
@Component
public class AccountAuthorizationAdvice {

    @Autowired
    private TiwiproPrincipal principal;

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the delete(Integer) method.
     */
    @Pointcut("target(com.inthinc.pro.service.adapters.AccountDAOAdapter)")
    public void inAccountDAOAdapter() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the delete(Integer) method.
     */
    @Pointcut("execution(* com.inthinc.pro.service.adapters.*.delete(java.lang.Integer))")
    public void deleteJoinPoint() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the getAll method.
     */
    @Pointcut("execution(* com.inthinc.pro.service.adapters.*.getAll())")
    public void getAllJoinPoint() {}

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has access to the entity. Access is granted if the entity has the same account id as the currently logged user.
     */
    @Before(value = "inAccountDAOAdapter() && (deleteJoinPoint()) && args(accountId)", argNames = "accountId")
    public void doDeleteAccessCheck(Integer accountId) {
        if (!principal.isInthincUser()) {
            throw new AccessDeniedException("Access denied. User does not have the required privileges to delete account id " + accountId);
        }
    }

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has access to the entity. Access is granted if the entity has the same account id as the currently logged user.
     */
    @Before(value = "inAccountDAOAdapter() && getAllJoinPoint()")
    public void doGetAllAccessCheck() {
        if (!principal.isInthincUser()) {
            throw new AccessDeniedException("Access denied. User does not have the required privileges to list all accounts.");
        }
    }
}
