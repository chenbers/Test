package com.inthinc.pro.service.security.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.inthinc.pro.service.adapters.AccountDAOAdapter;
import com.inthinc.pro.service.adapters.BaseDAOAdapter;

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
     * This pointcut will match all methods in the {@link AccountDAOAdapter} class.
     */
    @Pointcut("target(com.inthinc.pro.service.adapters.AccountDAOAdapter)")
    public void inAccountDAOAdapter() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the delete(Integer) method in any class in the adapters package.
     * <p/>
     * Note: AspectJ (in Spring 2.5, at least) does not check for inherited methods. So any execution pointcut will only match the methods actually declared in a class.<br/>
     * Since AccaountDAOAdapter does not override delete() from {@link BaseDAOAdapter}, trying an execution pointcut on AccountDAOAdapter.delete() does not work.
     * <p/>
     * Instead, two pointcuts are defined: one that matches the delete method on any class in the adapters package and another that matches methods only in the AccountDAOAdapter.<br/>
     * By combining the two on an advice, only the desired method is intercepted.
     */
    @Pointcut("execution(* com.inthinc.pro.service.adapters.*.delete(java.lang.Integer))")
    public void deleteJoinPoint() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the getAll method in any class in the adapters package.
     * <p/>
     * Please check the documentation on {@link AccountAuthorizationAdvice#deleteJoinPoint()} for caveats when defining pointcuts in Spring AspectJ.
     */
    @Pointcut("execution(* com.inthinc.pro.service.adapters.*.getAll())")
    public void getAllJoinPoint() {}

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has access to delete the entity. Only Inthinc users are allowed to delete accounts.
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
     * Before advice which checks if the user has access to get a list of accounts. Only Inthinc users are allowed to list all accounts.
     */
    @Before(value = "inAccountDAOAdapter() && getAllJoinPoint()")
    public void doGetAllAccessCheck() {
        if (!principal.isInthincUser()) {
            throw new AccessDeniedException("Access denied. User does not have the required privileges to list all accounts.");
        }
    }
}
