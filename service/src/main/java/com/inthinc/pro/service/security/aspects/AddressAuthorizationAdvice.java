package com.inthinc.pro.service.security.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.Address;
import com.inthinc.pro.service.adapters.AddressDAOAdapter;

/**
 * Base authorization advice for DAO adapters which will only grant access to objects belonging to the same account as the currently logged user.
 */
@Aspect
@Component
public class AddressAuthorizationAdvice {

    @Autowired
    private BaseAuthorizationAdvice baseAuthorizationAdvice;

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match all methods in the {@link AddressDAOAdapter} class.
     */
    @Pointcut("target(com.inthinc.pro.service.adapters.AddressDAOAdapter)")
    public void inAddressDAOAdapter() {}

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has access to delete the entity. Only Inthinc users are allowed to delete accounts.
     */
    @Before(value = "inAddressDAOAdapter() && (com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.deleteJoinPoint()) && args(addressId)", argNames = "addressId")
    public void doDeleteAccessCheck(JoinPoint pjp, Integer addressId) {
        AddressDAOAdapter adapter = (AddressDAOAdapter) pjp.getTarget();
        Address address = adapter.findByID(addressId);
        baseAuthorizationAdvice.doAccessCheck(address);
    }

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has access to get a list of accounts. Only Inthinc users are allowed to list all accounts.
     */
    @Before(value = "inAddressDAOAdapter() && com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.getAllJoinPoint()")
    public void doGetAllAccessCheck() {
    // Not yet implemented. Do nothing.
    }
}
