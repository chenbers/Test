package com.inthinc.pro.service.exceptions;

/**
 * Exception to represent an HTTP error code 403.
 * Not available from the RestEasy framework.
 * 
 * @author dcueva
 *
 */
public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = -1831393566623899329L;

	public ForbiddenException(String message) {
		super(message);
	}
	
}
