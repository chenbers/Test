package com.inthinc.pro.service.validation.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marker interface to indicate a method's params must be validated. 
 * 
 * @author dcueva
 */
@Target( { METHOD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface ValidParams {

}
