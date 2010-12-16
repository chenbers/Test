package com.inthinc.pro.service.validation.aspects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.service.params.IFTAReportsParamsBean;
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
	
    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    private ViolationToExceptionMapper violationToExceptionMapper;
	
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
	@Pointcut("args(groupID, startDate, endDate, locale, measurementType)")
	private void withDates(Integer groupID, Date startDate, Date endDate, Locale locale, MeasurementType measurementType) {}
	
	@SuppressWarnings("unused")
	@Pointcut("args(groupID, locale, measurementType)")
	private void withoutDates(Integer groupID, Locale locale, MeasurementType measurementType) {}

	
	@Around("isIFTAService() && validateParams() && withDates(groupID, startDate, endDate, locale, measurementType)") 
	public Object validateWithDates(ProceedingJoinPoint pjp, Integer groupID, Date startDate, Date endDate,
			Locale locale, MeasurementType measurementType) throws Throwable {
		
		logger.debug("Validating parameters with dates");
		IFTAReportsParamsBean params = getBean(groupID, startDate, endDate, locale, measurementType);
		violationToExceptionMapper.raiseExceptionIfConstraintViolated(validator.validate(params));
		return pjp.proceed(new Object[] {params.getGroupIDList().get(0), params.getStartDate(), params.getEndDate(), params.getLocale(), params.getMeasurementType()});
	}	

	@Around("isIFTAService() && validateParams() && withoutDates(groupID, locale, measurementType)") 
	public Object validateWithoutDates(ProceedingJoinPoint pjp, Integer groupID,
		Locale locale, MeasurementType measurementType) throws Throwable {
		
		logger.debug("Validating parameters without dates");
		IFTAReportsParamsBean params = getBean(groupID, locale, measurementType);
		violationToExceptionMapper.raiseExceptionIfConstraintViolated(validator.validate(params));
		return pjp.proceed(new Object[] {params.getGroupIDList().get(0), params.getLocale(), params.getMeasurementType()});
	}	
	
	/**
	 * Obtains an empty instance of the paramters bean from the application context.
	 * This bean must have the principal alreade autowired.
	 * 
	 * @return parameters bean
	 */
	private IFTAReportsParamsBean getBeanInstanceFromSpring() {
		return (IFTAReportsParamsBean) applicationContext.getBean("IFTAReportsParamsBean");
	}

	/**
	 * Overloaded convenience method.
	 * @see #fillBean(IFTAReportsParamsBean, List, Date, Date, Locale, MeasurementType)
	 */
	private IFTAReportsParamsBean getBean(Integer groupID, Locale locale, MeasurementType measurementType) {
		return getBean(groupID, null, null, locale, measurementType);	
	}
	
	/**
	 * Overloaded convenience method.
	 * @see #fillBean(IFTAReportsParamsBean, List, Date, Date, Locale, MeasurementType)
	 */
	private IFTAReportsParamsBean getBean(Integer groupID, Date startDate, Date endDate, Locale locale,
			MeasurementType measurementType) {
		List<Integer> groupIDList = new ArrayList<Integer>();
		groupIDList.add(groupID);
		return getBean(groupIDList, startDate, endDate, locale, measurementType);
	}
	
	/**
	 * Simply fill the bean with the passed parameters.
	 * @return Bean filled with the incoming parameters
	 */
	private IFTAReportsParamsBean getBean(List<Integer> groupIDList, Date startDate, Date endDate, 
			Locale locale, MeasurementType measurementType) {
		
		IFTAReportsParamsBean paramsBean = getBeanInstanceFromSpring();
		paramsBean.setGroupIDList(groupIDList);
		paramsBean.setStartDate(startDate);
		paramsBean.setEndDate(endDate);
		paramsBean.setLocale(locale);
		paramsBean.setMeasurementType(measurementType);
		return paramsBean;
	}	
	
}
