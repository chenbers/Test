package com.inthinc.pro.service.security.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.service.adapters.GroupDAOAdapter;
import com.inthinc.pro.service.adapters.PersonDAOAdapter;
import com.inthinc.pro.service.adapters.UserDAOAdapter;

/**
 * Authorization advice for UserDAOAdapter.
 */
@Aspect
@Component
public class UserAuthorizationAdvice {

    @Autowired
    private GroupAuthorizationAdvice groupAuthorizationAdvice;

    @Autowired
    private PersonAuthorizationAdvice personAuthorizationAdvice;

    @Autowired
    private GroupDAOAdapter groupDAOAdapter;

    @Autowired
    private PersonDAOAdapter personDAOAdapter;

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match all methods in the {@link UserDAOAdapter} class.
     */
    @Pointcut("target(com.inthinc.pro.service.adapters.UserDAOAdapter)")
    public void inUserDAOAdapter() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the login(String, String) method.
     */
    @Pointcut("execution(* com.inthinc.pro.service.adapters.UserDAOAdapter.login(java.lang.String, java.lang.String))")
    public void loginJoinPoint() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match any joinpoints (methods) which receives a runtime instance of User as the first parameter.
     */
    @Pointcut("args(com.inthinc.pro.model.User,..)")
    public void receivesUserObjectAsFirstArgument() {}

    /**
     * Advice definition.
     * <p/>
     * Advice which checks if the user has access to the {@link User} resource. Access check to User entities are performed through Group and Person.
     */
    @Before(value = "inUserDAOAdapter() && com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.receivesIntegerAs1stArgumentJoinPoint() && !com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.findByJoinPoint()")
    public void doAccessCheck(JoinPoint jp) {
        User user = ((UserDAOAdapter) jp.getTarget()).findByID((Integer) jp.getArgs()[0]);
        doAccessCheck(user);
    }

    /**
     * Advice definition.
     * <p/>
     * Advice which checks if the user has access to the {@link User} instance. Access check to User entities are performed through Group and Person.
     */
    @Before(value = "loginJoinPoint()")
    public void doLoginAccessCheck(JoinPoint jp) {
        User user = ((UserDAOAdapter) jp.getTarget()).findByUserName((String) jp.getArgs()[0]);
        doAccessCheck(user);
    }

    /**
     * Advice definition.
     * <p/>
     * AfterReturning advice which checks if the user has access to the returned {@link User} instance. Access check to User entities are performed through Group and Person.
     */
    @AfterReturning(value = "com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.findByJoinPoint() && inUserDAOAdapter()", returning = "retVal", argNames = "retVal")
    public void doFindByAccessCheck(User retVal) {
        doAccessCheck(retVal);
    }

    /**
     * Advice definition.
     * <p/>
     * Access check to {@link User} entities are performed through Group and Person.
     */
    @Before(value = "inUserDAOAdapter() && receivesUserObjectAsFirstArgument() && args(entity)", argNames = "entity")
    public void doAccessCheck(User entity) {
        /*
         * TODO Use the DAOs directly. If using the adapter, they will be advised as well, making unnecessary additional calls to the validation framework. Optionally, just use the
         * adapters to do findById and the access rules will automatically be applied. First approach is best though as there are no guarantees that the adapters are being advised.
         */
        Group group = groupDAOAdapter.findByID(entity.getGroupID());
        Person person = personDAOAdapter.findByID(entity.getPersonID());

        groupAuthorizationAdvice.doAccessCheck(group);
        personAuthorizationAdvice.doAccessCheck(person);
    }
}
