package com.inthinc.pro.service.security.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.HasAccountId;

/**
 * Base authorization advice for DAO adapters which will only grant access to objects belonging to the same account as the currently logged user.
 */
@Aspect
@Component
public class BaseAuthorizationAdvice {
    
    @Autowired
    private TiwiproPrincipal principal;

    public TiwiproPrincipal getPrincipal() {
        return principal;
    }

    public void setPrincipal(TiwiproPrincipal principal) {
        this.principal = principal;
    }

    /**
     * Pointcut definition.
     * <p/>
     * This pointcut will match any joinpoints (methods) within the adapters package.
     */
    @Pointcut("within(com.inthinc.pro.service.adapters..*)")
    public void inAdaptersLayer() {}

    /**
     * Pointcut definition.
     * <p/>This pointcut will match any joinpoints (methods) which receives a runtime instance of HasAccountId.
     */
    @Pointcut("args(com.inthinc.pro.model.HasAccountId,..)")
    public void receivesHasAccountIdObjectAsFirstArgument() {}

    /**
     * Advice definition.
     * <p/>
     * Before advice which checks if the user has access to the entity. Access is granted if the entity has the same account id as the currently logged user.
     */
    // TODO Use correct TiwiproPrincipal object once it is created.
    @Before("inAdaptersLayer() && receivesHasAccountIdObjectAsFirstArgument() && args(entity)")
    public void doAccessCheck(HasAccountId entity) {
        if (entity != null) {
            if (!principal.isInthincUser() && !principal.getAccountID().equals(entity.getAccountID())) {
                // TODO Come up with good exception mesage.
                throw new AccessDeniedException("Principal does not have the proper permission to access the resource.");
            }
        }
    }
}
