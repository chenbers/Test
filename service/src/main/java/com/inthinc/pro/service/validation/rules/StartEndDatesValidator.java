/**
 * 
 */
package com.inthinc.pro.service.validation.rules;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.inthinc.pro.service.params.HasStartEndDates;
import com.inthinc.pro.service.validation.annotations.ValidStartEndDates;

/**
 * Constraint validator for Start and End Dates.
 * 
 * @author dcueva
 */
public class StartEndDatesValidator implements ConstraintValidator<ValidStartEndDates, HasStartEndDates> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(ValidStartEndDates bean) {
		// No need to get info from the annotation here
	}

	/**
	 * Start and End dates are valid if:
	 * <ul>
	 * <li>Both of them are null</li> 
	 * <li>Start is before finish</li>
	 * </ul>
	 * 
	 * Note, the case where only one is null is NOT taken in account because it is not allowed by the WS framework.
	 */
	@Override
	public boolean isValid(HasStartEndDates bean, ConstraintValidatorContext context) {

		if (bean.getStartDate() == null || bean.getEndDate() == null) return true; 
		if (bean.getEndDate().before(bean.getStartDate())) return false;
		return true;
	}

}
