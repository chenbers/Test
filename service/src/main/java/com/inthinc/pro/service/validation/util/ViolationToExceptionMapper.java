/**
 * 
 */
package com.inthinc.pro.service.validation.util;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.jboss.resteasy.spi.BadRequestException;
import org.springframework.stereotype.Component;

import com.inthinc.pro.service.params.IFTAReportsParamsBean;
import com.inthinc.pro.service.validation.rules.LocaleValidator;
import com.inthinc.pro.service.validation.rules.StartEndDatesValidator;

/**
 * Maps JSR-303 constrain violations to Rest Easy Exceptions.
 * @author dcueva
 */
@Component
public class ViolationToExceptionMapper {

	private static final String MULTIPLE_VIOLATIONS = "Multiple constraint violations:\n";

	/**
	 * If there are constraint violations, raise exception.
	 * The appropriate exception will be chosen depending on the constraint violation.
	 * Exceptions:
	 * <ul>
	 * <li>More than one constraint violation: concatenate messages and raise 400.</li>
	 * <li>Groups</li>
	 *   <ul>
	 *   <li>Invalid Group ID:   400</li>
	 *   <li>No Group ID:   	 400</li>
	 *   <li>Negative Group ID:  400</li>
	 *   <li>Group ID not in user hierarchy:  403</li>
	 *   <li>Group ID not found: 404</li>
	 * 	 </ul>
	 * <li>Dates, always 400, this includes:</li>
	 *   <ul>
	 *   <li>End Date < Start Date</li>
	 *   <li>No Start Date</li>
	 *   <li>No End Date</li>
	 * 	 </ul>
	 * 
	 * <li>Locale: 400</li>
	 * 
	 * @param constraintViolations The set of constraint violations
	 */
	public void raiseExceptionIfConstraintViolated(
			Set<ConstraintViolation<IFTAReportsParamsBean>> constraintViolations) {

		if (constraintViolations.isEmpty()) return;
			
		if (constraintViolations.size() > 1) handleMultipleViolations(constraintViolations);
		else handleSingleViolation(constraintViolations.iterator().next());
	}	

	/**
	 * Handle single violation depending on the annotation type
	 * @param violation The violation.
	 */
	private void handleSingleViolation(
			ConstraintViolation<IFTAReportsParamsBean> violation) {

			Annotation annotation = violation.getConstraintDescriptor().getAnnotation();
			if (annotation instanceof LocaleValidator) throw new BadRequestException(violation.getMessage());
			if (annotation instanceof StartEndDatesValidator) throw new BadRequestException(violation.getMessage());
			//TODO complete list
	}

	/**
	 * Handle multiple violations by concatenating messages and raising a 400. 
	 * @param constraintViolations The constraint violations
	 */
	private void handleMultipleViolations(
			Set<ConstraintViolation<IFTAReportsParamsBean>> constraintViolations) {

			StringBuffer fullMessage = new StringBuffer(MULTIPLE_VIOLATIONS); 
			Iterator<ConstraintViolation<IFTAReportsParamsBean>>  it = constraintViolations.iterator();
			while (it.hasNext()) fullMessage.append(it.next().getMessage() + "\n");
			throw new BadRequestException(fullMessage.toString());
	}
	
}
