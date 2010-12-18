/**
 * 
 */
package com.inthinc.pro.service.validation.rules;

import javax.validation.ConstraintValidatorContext;
import javax.ws.rs.core.Response.Status;

import com.inthinc.pro.service.validation.util.ViolationToExceptionMapper;

/**
 * Abstract class to encapsulate reusable validator-specific code.
 * 
 * @author dcueva
 */
public abstract class AbstractServiceValidator {

	/**
	 * Creates a Constraint Violation with a special message template and returns FALSE.</br></br>
	 * 
	 * The template uses the following format: </br>
	 * {@link Response.Status@name}#{default template} invalidValue</br>
	 * For example:</br>
	 * BAD_REQUEST#{com.inthinc.pro.service.validation.annotations.ValidLocale.message} xxy</br></br>
	 * The part before # will be stripped and used by the {@link ViolationToExceptionMapper} to select the appropriate exception to throw.</br>
	 * </br>
	 * Notes:</br>
	 * <ol>
	 * <li> A more robust implementation could be done using a custom <a href="http://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#d0e2273">Message Interpolator</a></li>
	 * <li> Returning meta-data embedded in the message is not the most elegant solution. However JSR303 does not seem to support this need. I investigated the use of {@link javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder#addNode Node Name}. In the future we can probably investigate other techniques. </li>
	 * </ol>
	 *  
	 * 
	 * @param context The ConstraintValidatorContext from Hibernate Validator
	 * @param statusType HTTP Status Type to be associated with this violation
	 * @param invalidValue The invalid value. It is appended at the end of the message using {@link invalidValue#toString()}
	 * @param baseTemplate the template used as basis
	 * @return Always false
	 */
	protected boolean violationTemplate(ConstraintValidatorContext context, Status statusType, Object invalidValue, String baseTemplate) { 
		String newTemplate =  statusType.name() + ViolationToExceptionMapper.DELIMITER + baseTemplate + " " + invalidValue.toString();
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(newTemplate).addConstraintViolation();
	
		return false;
	}
	

	/**
	 * @see #violationTemplate(ConstraintValidatorContext, Status, Object, String)
	 */
	protected boolean violationTemplate(ConstraintValidatorContext context, Status statusType, Object invalidValue) {
		return violationTemplate(context, statusType, invalidValue, context.getDefaultConstraintMessageTemplate());
	}	
	
}
