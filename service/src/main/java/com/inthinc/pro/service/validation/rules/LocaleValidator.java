/**
 * 
 */
package com.inthinc.pro.service.validation.rules;

import java.util.Locale;
import java.util.MissingResourceException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.inthinc.pro.service.validation.annotations.ValidLocale;

/**
 * Constraint validator for Locales.
 * Implements the validation rules for Locales 
 * 
 * @author dcueva
 */
public class LocaleValidator implements ConstraintValidator<ValidLocale, Locale> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(ValidLocale localeAnnotation) {
		// No need to get info from the annotation here
		// In the future the annotation could take a list of accepted locales
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(Locale locale, ConstraintValidatorContext context) {
		
		// Following JSR303, a null value is valid here.
		// If the value must not be null, it must be annotated with @NotNull
		if (locale == null) return true;

		// If we can get the ISO3 language, then it is a valid locale
		try {
			locale.getISO3Language();
		} catch (MissingResourceException e) {
			return false;
		}
		return true;
	}

}
