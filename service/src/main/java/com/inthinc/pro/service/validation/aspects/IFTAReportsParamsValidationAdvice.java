package com.inthinc.pro.service.validation.aspects;

import java.util.Date;
import java.util.Locale;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.service.params.IFTAReportsParamsBean;
import com.inthinc.pro.service.validation.util.IFTAReportsParamsBeanFactory;
import com.inthinc.pro.service.validation.util.SpringConstraintValidatorFactory;
import com.inthinc.pro.service.validation.util.ViolationToExceptionMapper;

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
	 * Creates the beans from the input parameters.
	 */
	@Autowired
	private IFTAReportsParamsBeanFactory iftaBeanFactory;
    
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
	public IFTAReportsParamsValidationAdvice(SpringConstraintValidatorFactory springConstraintValidatorFactory)
	{
		Configuration<?> configuration = Validation.byDefaultProvider().configure();
		ValidatorFactory factory = configuration.constraintValidatorFactory(springConstraintValidatorFactory).buildValidatorFactory();
		validator = factory.getValidator();
	}
	
	/**---------------------------------------------------------------------
	 * Pointcuts. 
	 */
	@SuppressWarnings("unused")
	@Pointcut("execution(* com.inthinc.pro.service.reports.impl.IFTAService*.*(..))")
	private void isIFTAService() {};
	  // Unfortunately we cannot advice the interface package because the annotation @ValidParams is not inherited

	@SuppressWarnings("unused")
	@Pointcut("@annotation(com.inthinc.pro.service.validation.annotations.ValidParams)")
	private void validateParams() {};	
	
	@SuppressWarnings("unused")
	@Pointcut("args(groupID, startDate, endDate, locale, measurementType)")
	private void withDates(Integer groupID, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {}
	
	@SuppressWarnings("unused")
	@Pointcut("args(groupID, locale, measurementType)")
	private void withoutDates(Integer groupID, Locale locale, MeasurementType measurementType) {}


	/**---------------------------------------------------------------------
	 * Advice 
	 */
	@Around("isIFTAService() && validateParams() && withDates(groupID, startDate, endDate, locale, measurementType)") 
	public Object validateWithDates(ProceedingJoinPoint pjp, Integer groupID, Date startDate, Date endDate,
			Locale locale, MeasurementType measurementType) throws Throwable {

		IFTAReportsParamsBean params = iftaBeanFactory.getBean(groupID, startDate, endDate, locale, measurementType);
		violationToExceptionMapper.raiseExceptionIfConstraintViolated(validator.validate(params));
		return pjp.proceed(new Object[] {params.getGroupIDList().get(0), params.getStartDate(), params.getEndDate(), params.getLocale(), params.getMeasurementType()});
	}	

	@Around("isIFTAService() && validateParams() && withoutDates(groupID, locale, measurementType)") 
	public Object validateWithoutDates(ProceedingJoinPoint pjp, Integer groupID,
		Locale locale, MeasurementType measurementType) throws Throwable {

		IFTAReportsParamsBean params = iftaBeanFactory.getBean(groupID, locale, measurementType);
		violationToExceptionMapper.raiseExceptionIfConstraintViolated(validator.validate(params));
		return pjp.proceed(new Object[] {params.getGroupIDList().get(0), params.getLocale(), params.getMeasurementType()});
	}	
	
}
