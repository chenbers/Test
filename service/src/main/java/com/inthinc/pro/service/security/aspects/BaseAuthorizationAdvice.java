package com.inthinc.pro.service.security.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.HasAccountId;
import com.inthinc.pro.service.adapters.BaseDAOAdapter;
import com.inthinc.pro.service.security.TiwiproPrincipal;

/**
 * Base authorization advice for DAO adapters which will only grant access to objects belonging to the same account as the currently logged user.
 */
@Aspect
@Component
public class BaseAuthorizationAdvice {

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match any joinpoints (methods) within the adapters package.
     */
    @Pointcut("within(com.inthinc.pro.service.adapters..*)")
    public void inAdaptersLayer() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match any joinpoints (methods) which receives a runtime instance of HasAccountId as the first parameter.
     */
    @Pointcut("args(com.inthinc.pro.model.HasAccountId,..)")
    public void receivesHasAccountIdObjectAsFirstArgument() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the findByID(java.lang.Integer) method on any class in the adapters package.
     */
    @Pointcut("execution(* com.inthinc.pro.service.adapters.*.findByID(java.lang.Integer))")
    public void findByIdJoinPoint() {}
    
    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the delete(Integer) method in any class in the adapters package.
     * <p/>
     * Note: AspectJ (in Spring 2.5, at least) does not check for inherited methods. So any execution pointcut will only match the methods actually declared in a class.<br/>
     * This join point must be combined with a join point for the specific class which does not override delete() from {@link BaseDAOAdapter} for it to be applied.
     */
    @Pointcut("execution(* com.inthinc.pro.service.adapters.*.delete(java.lang.Integer))")
    public void deleteJoinPoint() {}

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match the getAll method in any class in the adapters package.
     * <p/>
     * Please check the documentation on {@link BaseAuthorizationAdvice#deleteJoinPoint()} for caveats when defining pointcuts in Spring AspectJ.
     */
    @Pointcut("execution(* com.inthinc.pro.service.adapters.*.getAll())")
    public void getAllJoinPoint() {}

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has permissions to access a {@link HasAccountId} entity. Access is granted if the entity has the same account id as the currently
     * logged user.
     * <p/>
     * Note the argNames attribute in the @Before aspect. This is required since there's no guarantee the code will be compiled with debug information or with the AspectJ compiler.
     */
    // TODO Use correct TiwiproPrincipal object once it is created.
    @Before(value = "inAdaptersLayer() && receivesHasAccountIdObjectAsFirstArgument() && args(entity)", argNames = "entity")
    public void doAccessCheck(HasAccountId entity) {
        if (entity != null) {
            if (!principal.isInthincUser() && !principal.getAccountID().equals(entity.getAccountID())) {
                // TODO Come up with good exception message.
                throw new AccessDeniedException("Principal does not have the proper permission to access the resource.");
            }
        }
    }

    /**
     * Advice definition.
     * <p/>
     * AfterReturning advice which checks if the user has access to the returned {@link HasAccountId} instance of the findById() method. Access is granted if the entity has the
     * same account id as the currently logged user.
     * <p/>
     * Note that AspectJ will only invoke this advice if the returning object is of type {@link HasAccountId}.
     */
    @AfterReturning(value = "findByIdJoinPoint()", returning = "retVal", argNames = "retVal")
    public void doFindByIdAccessCheck(HasAccountId retVal) {
        doAccessCheck(retVal);
    }

    @Autowired
    private TiwiproPrincipal principal;

    public TiwiproPrincipal getPrincipal() {
        return principal;
    }

    public void setPrincipal(TiwiproPrincipal principal) {
        this.principal = principal;
    }

}
