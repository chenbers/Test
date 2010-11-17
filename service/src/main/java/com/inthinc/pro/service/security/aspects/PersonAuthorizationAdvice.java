package com.inthinc.pro.service.security.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.Person;
import com.inthinc.pro.service.adapters.BaseDAOAdapter;
import com.inthinc.pro.service.adapters.PersonDAOAdapter;
import com.inthinc.pro.service.security.TiwiproPrincipal;

/**
 * Authorization advice for PersonDAOAdapter.
 */
@Aspect
@Component
public class PersonAuthorizationAdvice {

    @Autowired
    private TiwiproPrincipal principal;

    @Autowired
    private BaseAuthorizationAdvice baseAuthorizationAdvice;

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match all methods in the {@link PersonDAOAdapter} class.
     */
    @Pointcut("target(com.inthinc.pro.service.adapters.PersonDAOAdapter)")
    public void inPersonDAOAdapter() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the create(java.lang.Integer, {@link Person}) method on {@link PersonDAOAdapter}.
     */
    @Pointcut("execution(* com.inthinc.pro.service.adapters.PersonDAOAdapter.create(java.lang.Integer, com.inthinc.pro.model.Person))")
    public void createWithAccountJoinPoint() {}

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has permissions to create a {@link Person} entity with another AccountId. Access is granted only to Inthinc users.
     */
    @Before(value = "createWithAccountJoinPoint() && args(id,entity)", argNames = "id,entity")
    public void doAccessCheck(Integer id, Person entity) {
        if (!principal.isInthincUser()) {
            throw new AccessDeniedException("Principal does not have the proper permission to create the resource.");
        }
    }

    /**
     * Advice definition.
     * <p/>
     * Access to Person entities also require that the address passes validation. Aspects are additive. So we only require to define the check for Address, since the check on
     * person is done by the BaseAuthenticationAspect.
     */
    @Before(value = "com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.receivesHasAccountIdObjectAsFirstArgument() && args(entity)", argNames = "entity")
    public void doAccessCheck(Person entity) {
        baseAuthorizationAdvice.doAccessCheck(entity.getAddress());
    }

    /**
     * Advice definition.
     * <p/>
     * Access to Person entities also require that the address passes validation.
     */
    @Before(value = "inPersonDAOAdapter() && com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.deleteJoinPoint() && args(entityId)", argNames = "entityId")
    public void doDeleteAccessCheck(JoinPoint jp, Integer entityId) {
        BaseDAOAdapter<?> adapter = (BaseDAOAdapter<?>) jp.getTarget();
        Person entity = (Person) adapter.findByID(entityId);

        baseAuthorizationAdvice.doAccessCheck(entity);
        baseAuthorizationAdvice.doAccessCheck(entity.getAddress());
    }

    /**
     * Advice definition.
     * <p/>
     * Access to Person entities also require that the address passes validation. Aspects are additive. So we only require to define the check for Address, since the check on
     * person is done by the BaseAuthenticationAspect.
     */
    @AfterReturning(value = "inPersonDAOAdapter() && com.inthinc.pro.service.security.aspects.BaseAuthorizationAdvice.findByJoinPoint()", returning = "retVal", argNames = "retVal")
    public void doFindByAccessCheck(Person retVal) {
        baseAuthorizationAdvice.doAccessCheck(retVal.getAddress());
    }
}
