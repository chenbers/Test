/**
 * 
 */
package com.inthinc.pro.service.validation.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.inthinc.pro.service.validation.rules.StartEndDatesValidator;

/**
 * Constraint annotation following JSR-303.</br>
 * Annotates a the bean implementing {@link com.inthinc.pro.service.params.HasStartEndDates HasStartEndDates}
 * 
 * @author dcueva
 *
 */
@Target( { TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = StartEndDatesValidator.class)
@Documented
public @interface ValidStartEndDates {

    String message() default "{com.inthinc.pro.service.validation.annotations.StartEndDatesValidator.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};	
	
}
