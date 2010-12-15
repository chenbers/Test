package com.inthinc.pro.service.validation.aspects;

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

import com.inthinc.pro.service.params.IFTAReportsParamsBean;

/**
 * This class advices the IFTA Reports services.
 * It captures the incoming parameters and validates them using JSR-303.
 * 
 * @author dcueva
 */
//@Aspect
@Component
public class IFTAReportsParamsValidationAdvice {
	
	Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	Logger logger = Logger.getLogger(IFTAReportsParamsValidationAdvice.class);
	
	@SuppressWarnings("unused")
	//@Pointcut("target(com.inthinc.pro.service.reports.IFTAService)")
	private void isIFTAService() {};
	
	//@Around("isIFTAService() && args(params)") 
	public Object validate(ProceedingJoinPoint pjp, IFTAReportsParamsBean params) throws Throwable {
		
		logger.debug("Validating parameters " + params);

		raiseExceptionIfConstraintViolated(validator.validate(params));
		
		// call actual method
		return pjp.proceed(new Object[] {params});
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
