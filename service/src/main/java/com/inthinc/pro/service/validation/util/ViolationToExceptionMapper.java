package com.inthinc.pro.service.validation.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;
import org.springframework.stereotype.Component;

import com.inthinc.pro.service.exceptions.ForbiddenException;
import com.inthinc.pro.service.params.IFTAReportsParamsBean;
import com.inthinc.pro.service.validation.rules.AbstractServiceValidator;


/**
 * Maps JSR-303 constrain violations to Rest Easy Exceptions.
 * @author dcueva
 */

@Component
public class ViolationToExceptionMapper {

	public static final String DELIMITER = "#";
	private static final String MULTIPLE_VIOLATIONS = "\nMultiple constraint violations:\n";

	private Map<String, Class<? extends RuntimeException>> map = new HashMap<String, Class<? extends RuntimeException>>();
	
	public ViolationToExceptionMapper(){
		map.put(Response.Status.BAD_REQUEST.name(), BadRequestException.class);
		map.put(Response.Status.FORBIDDEN.name(), ForbiddenException.class);
		map.put(Response.Status.NOT_FOUND.name(), NotFoundException.class);
	}
	
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
	 * Handle single violation depending on the annotation type.
	 * This method will throw an exception depending on the constraint violation.</br></br>
	 * 
	 * @param violation The constraint violation.
	 */
	private void handleSingleViolation(ConstraintViolation<IFTAReportsParamsBean> violation) {

			// Get the exception corresponding to the violation
			Class<? extends RuntimeException> exceptionClass = map.get(extractStatusTypeName(violation.getMessage()));

			// Default exception: BadRequestException
			if (exceptionClass == null) exceptionClass = BadRequestException.class;
			
			// Throw the exception
			RuntimeException exception;
			try {
				exception = exceptionClass.getDeclaredConstructor(String.class).newInstance(extractUserMessage(violation.getMessage()));
			} catch (Exception e) {
				throw new BadRequestException(e);
			}
			throw exception;
	}

	/**
	 * Handle multiple violations by concatenating messages and raising a 400. 
	 * @param constraintViolations The constraint violations
	 */
	private void handleMultipleViolations(
			Set<ConstraintViolation<IFTAReportsParamsBean>> constraintViolations) {

			StringBuffer fullMessage = new StringBuffer(MULTIPLE_VIOLATIONS); 
			Iterator<ConstraintViolation<IFTAReportsParamsBean>>  it = constraintViolations.iterator();
			while (it.hasNext()) fullMessage.append("* " + extractUserMessage(it.next().getMessage()) + "\n");
			throw new BadRequestException(fullMessage.toString());
	}


	/**
	 * Returns the name of the {@link Response.Status} from the violation message.</br>
	 * The convention is detailed in {@link AbstractServiceValidator}
	 * 
	 * @param violation The Constraint violation
	 * @return name of the {@link Response.Status} from the violation message.
	 */
	private Object extractStatusTypeName(String violationMessage) {
		String[] parts = violationMessage.split(DELIMITER);
		return parts[0];
	}	
	
	/**
	 * Returns the user-readable portion of the violation message.</br>
	 * The convention is detailed in {@link AbstractServiceValidator}
	 * 
	 * @param violation The Constraint violation
	 * @return user-readable portion of the violation message.
	 */
	private String extractUserMessage(String violationMessage) {
		String[] parts = violationMessage.split(DELIMITER);
		return parts[1];
	}
	
}
