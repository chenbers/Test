package com.inthinc.pro.service.security.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.service.adapters.DriverDAOAdapter;

/**
 * Authorization advice for DriverDAOAdapter.
 */
@Aspect
@Component
public class DriverAuthorizationAdvice implements EntityAuthorization<Driver> {

    @Autowired
    private GroupAuthorizationAdvice groupAuthorizationAdvice;

    @Autowired
    private PersonAuthorizationAdvice personAuthorizationAdvice;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private PersonDAO personDAO;

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match all methods in the {@link DriverDAOAdapter} class.
     */
    @Pointcut("target(com.inthinc.pro.service.adapters.DriverDAOAdapter)")
    public void inDriverDAOAdapter() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match any joinpoints (methods) which receives a runtime instance of Driver as the first parameter.
     */
    @Pointcut("args(com.inthinc.pro.model.Driver,..)")
    public void receivesDriverObjectAsFirstArgument() {}

    /**
     * Advice definition.
     * <p/>
     * AfterReturning advice which checks if the user has access to the returned {@link Driver} instance. Access check to Driver entities are performed through Group and Person.
     */
    @SuppressWarnings("unused")
    @AfterReturning(value = "com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.findByJoinPoint() && inDriverDAOAdapter()", returning = "retVal", argNames = "retVal")
    private void doFindByAccessCheck(Driver retVal) {
        doAccessCheck(retVal);
    }

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has access to the resource. Access check to Driver entities are performed through Group and Person.
     */
    @SuppressWarnings("unused")
    @Before(value = "inDriverDAOAdapter() && com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.receivesIntegerAs1stArgumentJoinPoint() && !com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.findByJoinPoint()")
    private void doAccessCheck(JoinPoint jp) {
        Driver driver = ((DriverDAOAdapter) jp.getTarget()).findByID((Integer) jp.getArgs()[0]);
        doAccessCheck(driver);
    }

    /**
     * Advice definition.
     * <p/>
     * Access check to Driver entities are performed through Group and Person.
     * 
     * @param entity
     *            The driver to be checked.
     */
    @SuppressWarnings("unused")
    @Before(value = "inDriverDAOAdapter() && receivesDriverObjectAsFirstArgument() && args(entity)", argNames = "entity")
    private void doDriverAccessCheck(Driver entity) {
        doAccessCheck(entity);
    }

    /**
     * @see com.inthinc.pro.service.security.aspects.EntityAuthorization#doAccessCheck(java.lang.Object)
     */
    public void doAccessCheck(Driver entity) {
        if (entity != null) {
            Group group = groupDAO.findByID(entity.getGroupID());
            Person person = personDAO.findByID(entity.getPersonID());

            groupAuthorizationAdvice.doAccessCheck(group);
            personAuthorizationAdvice.doAccessCheck(person);
        }
    }
}
