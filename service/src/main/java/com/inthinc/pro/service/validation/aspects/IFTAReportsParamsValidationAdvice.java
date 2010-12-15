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
import org.springframework.stereotype.Component;

import com.inthinc.pro.service.validation.annotations.ValidParams;
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
	@Pointcut("@annotation(validParamsAnnotation)")
	private void validateParams(ValidParams validParamsAnnotation) {};	
	
	@SuppressWarnings("unused")
	@Pointcut("args(groupID, startDate, endDate)")
	private void withDates(Integer groupID, Date startDate, Date endDate) {}
	
	@SuppressWarnings("unused")
	@Pointcut("args(groupID)")
	private void withoutDates(Integer groupID) {}

	
	
//	@Around("isIFTAService() && receivesIFTAParams(params) && validateParams(validParamsAnnotation)") 
//	public Object validate(ProceedingJoinPoint pjp, IFTAReportsParamsBean params, ValidParams validParamsAnnotation) throws Throwable {
//		
//		logger.debug("Validating parameters " + params);
//
//		raiseExceptionIfConstraintViolated(validator.validate(params));
//		
//		// call actual method
//		return pjp.proceed(new Object[] {params});
//	}

	
	@Around("isIFTAService() && withDates(groupID, startDate, endDate) && validateParams(validParamsAnnotation)") 
	public Object validateWithDates(ProceedingJoinPoint pjp, Integer groupID, Date startDate, Date endDate, ValidParams validParamsAnnotation) throws Throwable {
		
		logger.debug("Validating parameters");
	
		return pjp.proceed(new Object[] {groupID, startDate, endDate});
	}	

	@Around("isIFTAService() && withoutDates(groupID) && validateParams(validParamsAnnotation)") 
	public Object validateWithoutDates(ProceedingJoinPoint pjp, Integer groupID, ValidParams validParamsAnnotation) throws Throwable {
		
		logger.debug("Validating parameters");
	
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
