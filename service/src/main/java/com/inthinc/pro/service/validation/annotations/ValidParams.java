package com.inthinc.pro.service.validation.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marker annotation to indicate that the params of a method must be validated. </br>
 * 
 * Must be applied to the methods of the implementation class, not to the interface. 
 * @see {@link java.lang.annotation.Inherited} </br>
 * 
 * @author dcueva
 */
@Target( { METHOD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
@Inherited
public @interface ValidParams {

}
