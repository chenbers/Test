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

import com.inthinc.pro.service.validation.rules.GroupPropertiesValidator;

/**
 * Constraint annotation following JSR-303.</br>
 * Verifies if a a set of group IDs exist.
 * 
 * @author dcueva
 *
 */

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = GroupPropertiesValidator.class)
@Documented

public @interface ValidGroupProperties {
    String message() default "{com.inthinc.pro.service.validation.annotations.ValidGroupIDs.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
