package com.inthinc.pro.service.validation.aspects;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.inthinc.pro.service.params.IFTAReportsParamsBean;

/**
 * This class advices the services capturing the incoming parameters and validating them using JSR-303.
 * 
 * @author dcueva
 *
 */
//@Aspect
@Component
public class IFTAReportsParamsValidationAdvice {
	
	Logger logger = Logger.getLogger(IFTAReportsParamsValidationAdvice.class);
	
	@SuppressWarnings("unused")
	//@Pointcut("target(com.inthinc.pro.service.reports.IFTAService)")
	private void isIFTAService() {};
	
	
	//@Around("isIFTAService() && args(params)") 
	public void validate(IFTAReportsParamsBean params) {
		logger.info("Advising");
		Locale a = params.getLocale();
		logger.info(a.getISO3Language());
		logger.info(Locale.ENGLISH.equals(a));
		
	}
	
}
