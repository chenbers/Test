/**
 * 
 */
package com.inthinc.pro.service.validation.rules;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.inthinc.pro.service.params.HasStartEndDates;
import com.inthinc.pro.service.validation.annotations.ValidStartEndDates;

/**
 * Constraint validator for Start and End Dates.
 * 
 * @author dcueva
 */
@Component
public class StartEndDatesValidator extends AbstractServiceValidator implements ConstraintValidator<ValidStartEndDates, HasStartEndDates> {
	
    private final String SIMPLE_DATE_FORMAT = "yyyyMMdd";
	
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

		if (bean.getEndDate().before(bean.getStartDate())) return violationTemplate(context, Response.Status.BAD_REQUEST, getPrintableDates(bean)); 
		return true;
	}

	private List<String> getPrintableDates(HasStartEndDates bean) {
		List<String> dates = new ArrayList<String>();
		SimpleDateFormat formatter = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
		dates.add(formatter.format(bean.getStartDate()));
		dates.add(formatter.format(bean.getEndDate()));
		return dates;
	}

}
