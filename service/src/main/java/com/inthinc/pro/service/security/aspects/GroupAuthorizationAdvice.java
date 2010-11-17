package com.inthinc.pro.service.security.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.Group;

/**
 * Authorization advice for GroupDAOAdapter..
 */
@Aspect
@Component
public class GroupAuthorizationAdvice {

    /**
     * @param group
     */
    public void doAccessCheck(Group group) {
        // TODO Auto-generated method stub
        
    }

    
}
