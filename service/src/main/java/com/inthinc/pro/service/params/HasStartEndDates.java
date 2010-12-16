/**
 * 
 */
package com.inthinc.pro.service.params;

import java.util.Date;

/**
 * Interface defining a contract for returning start and and dates from a bean.
 * The methods insure the dates can be correctly validated and changed by the default values. 
 * 
 * @author dcueva
 *
 */
public interface HasStartEndDates {

	/**
	 * Returns the start date.
	 * If the date is null, returns a default value.
	 * 
	 * @return The start date.
	 */
	public Date getStartDate();

	/**
	 * Returns the end date.
	 * If the date is null, returns a default value.
	 * 
	 * @return The end date.
	 */
	public Date getEndDate();
	
}
