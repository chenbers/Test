package com.inthinc.pro.service.security.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.service.adapters.GroupDAOAdapter;

/**
 * Group authorization advice for DAO adapter.
 */
@Aspect
@Component
public class GroupAuthorizationAdvice {

    @Autowired
    private BaseAuthorizationAdvice baseAuthorizationAdvice;

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match all methods in the {@link GroupDAOAdapter} class.
     */
    @Pointcut("target(com.inthinc.pro.service.adapters.GroupDAOAdapter)")
    public void inGroupDAOAdapter() {}

    /**
     * Advice definition.
     * <p/>
     * Advice which checks if the user has access to the {@link Group} resource.
     */
    @Before(value = "inGroupDAOAdapter() && com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.receivesIntegerAs1stArgumentJoinPoint() && !com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.findByJoinPoint()")
    public void doAccessCheck(JoinPoint jp) {
        Group group = ((GroupDAOAdapter) jp.getTarget()).findByID((Integer) jp.getArgs()[0]);
        doAccessCheck(group);
    }

    /**
     * Access to Group entities is done by the BaseAuthenticationAspect.
     */
    public void doAccessCheck(Group entity) {
        baseAuthorizationAdvice.doAccessCheck(entity);
    }

}
