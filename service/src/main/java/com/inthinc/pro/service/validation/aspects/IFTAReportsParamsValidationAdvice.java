package com.inthinc.pro.service.validation.aspects;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.service.params.IFTAReportsParamsBean;

/**
 * This class advices the IFTA Reports services.
 * It captures the incoming parameters and validates them using JSR-303.
 * 
 * @author dcueva
 */
@Aspect
@Component
public class IFTAReportsParamsValidationAdvice {
	
	/**
	 * Autowired prototype instance of an empty bean.
	 * It includes the tiwipro principal to obtain default parameters.
	 */
	@Autowired
	IFTAReportsParamsBean paramsBean;
	
	/**
	 * JSR303 validator. Reference implementation: Hibernate validator.
	 */
	Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	
	Logger logger = Logger.getLogger(IFTAReportsParamsValidationAdvice.class);

	
	/**
	 * Unfortunately we cannot advice the interface package because the annotation @ValidParams is not inherited
	 * This is a limitation of the JDK.
	 */
	@SuppressWarnings("unused")
	@Pointcut("execution(* com.inthinc.pro.service.reports.impl.IFTAService*.*(..))")
	private void isIFTAService() {};

	@SuppressWarnings("unused")
	@Pointcut("@annotation(com.inthinc.pro.service.validation.annotations.ValidParams)")
	private void validateParams() {};	
	
	@SuppressWarnings("unused")
	@Pointcut("args(groupID, startDate, endDate)")
	private void withDates(Integer groupID, Date startDate, Date endDate) {}
	
	@SuppressWarnings("unused")
	@Pointcut("args(groupID)")
	private void withoutDates(Integer groupID) {}

	
	@Around("isIFTAService() && validateParams() && withDates(groupID, startDate, endDate)") 
	public Object validateWithDates(ProceedingJoinPoint pjp, Integer groupID, Date startDate, Date endDate) throws Throwable {
		
		logger.debug("Validating parameters with dates");
	
		return pjp.proceed(new Object[] {groupID, startDate, endDate});
	}	

	@Around("isIFTAService() && validateParams() && withoutDates(groupID)") 
	public Object validateWithoutDates(ProceedingJoinPoint pjp, Integer groupID) throws Throwable {
		
		logger.debug("Validating parameters without dates");
	
		return pjp.proceed(new Object[] {groupID});
	}	
	
	
	/**
	 * If there are constraint violations, raise exception.
	 * The appropriate exception will be chosen depending on the constraint violation.
	 * 
	 * @param constraintViolations The set of constraint violations
	 */
	private void raiseExceptionIfConstraintViolated(
			Set<ConstraintViolation<IFTAReportsParamsBean>> constraintViolations) {
		// TODO Auto-generated method stub
		if (!constraintViolations.isEmpty()){
			
		}
	}

	
}
