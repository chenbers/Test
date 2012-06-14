package com.inthinc.pro.service.validation.aspects;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.UriInfo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.service.params.GroupParam;
import com.inthinc.pro.service.validation.util.SpringConstraintValidatorFactory;
import com.inthinc.pro.service.validation.util.ViolationToExceptionMapper;

@Aspect
@Component
public class GroupPropertiesAdvice {
    /**
     * Helper class to map violations to exceptions.
     */
    @Autowired
    private ViolationToExceptionMapper violationToExceptionMapper;
    
    /**
     * JSR303 validator. Reference implementation: Hibernate validator, thread-safe.
     */
    Validator validator;
    /**
     * Constructor. 
     * @param springConstraintValidatorFactory Custom factory to get Spring-wired constraint validators.
     */
    @Autowired
    public GroupPropertiesAdvice(SpringConstraintValidatorFactory springConstraintValidatorFactory)
    {
        Configuration<?> configuration = Validation.byDefaultProvider().configure();
        ValidatorFactory factory = configuration.constraintValidatorFactory(springConstraintValidatorFactory).buildValidatorFactory();
        validator = factory.getValidator();
    }
    /*---------------------------------------------------------------------
     * Pointcuts. 
     */
    @SuppressWarnings("unused")
    @Pointcut("execution(* com.inthinc.pro.service.impl.GroupServiceImpl.*(..))")
    private void isGroupService() {};
      // Unfortunately we cannot advice the interface package because the annotation @ValidParams is not inherited
    
    @SuppressWarnings("unused")
    @Pointcut("@annotation(com.inthinc.pro.service.validation.annotations.ValidParams)")
    private void validateParams() {};   

    @SuppressWarnings("unused")
    @Pointcut("args(group, uriInfo)")
    private void withGroupAndUriInfo(Group group, UriInfo uriInfo) {}

    @SuppressWarnings("unused")
    @Pointcut("args(group)")
    private void withGroup(Group group) {}

    @Around("isGroupService() && validateParams() && withGroupAndUriInfo(group, uriInfo)") 
    public Object validateGroupProperties(ProceedingJoinPoint pjp, Group group, UriInfo uriInfo) throws Throwable {
        GroupParam groupParam = new GroupParam(group);
        violationToExceptionMapper.raiseGroupExceptionIfConstraintViolated(validator.validate(groupParam));
        return pjp.proceed(new Object[] {group, uriInfo});
    }   
    @Around("isGroupService() && validateParams() && withGroup(group)") 
    public Object validateGroupProperties(ProceedingJoinPoint pjp, Group group) throws Throwable {
        GroupParam groupParam = new GroupParam(group);
        violationToExceptionMapper.raiseGroupExceptionIfConstraintViolated(validator.validate(groupParam));
        return pjp.proceed(new Object[] {group});
    }   

}
