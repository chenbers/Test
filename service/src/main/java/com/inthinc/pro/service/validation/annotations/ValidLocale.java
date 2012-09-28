package com.inthinc.pro.service.validation.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.inthinc.pro.service.validation.rules.LocaleValidator;

/**
 * Constraint annotation following JSR-303.</br>
 * Verifies if a locale is valid by comparing trying to obtain its ISO 639-2 language code.</br>
 * Language codes can be found at <a href="http://www.loc.gov/standards/iso639-2/englangn.html">http://www.loc.gov/standards/iso639-2/englangn.html.</a> 
 * 
 * @author dcueva
 *
 */

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = LocaleValidator.class)
@Documented
public @interface ValidLocale {

    String message() default "{com.inthinc.pro.service.validation.annotations.ValidLocale.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
	
}
