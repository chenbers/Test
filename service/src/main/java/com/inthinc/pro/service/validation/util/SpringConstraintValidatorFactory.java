/**
 * 
 */
package com.inthinc.pro.service.validation.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Instantiate ConstraintValidator instances using Spring </br>
 * instead of the default Hibernate's {@link org.hibernate.validator.engine.ConstraintValidatorFactoryImpl ConstraintValidatorFactoryImpl} </br>
 * 
 * In Spring 3 there is a class for this purpose 
 * <a href="http://static.springsource.org/spring/docs/3.0.x/javadoc-api/org/springframework/validation/beanvalidation/SpringConstraintValidatorFactory.html">SpringConstraintValidatorFactory</a>
 * 
 * @author dcueva
 *
 */
@Component
public class SpringConstraintValidatorFactory implements
		ConstraintValidatorFactory {

	@Autowired
	ApplicationContext applicationContext;
	
	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidatorFactory#getInstance(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> clazz) {
		return (T) applicationContext.getAutowireCapableBeanFactory().createBean(clazz);
	}

}
