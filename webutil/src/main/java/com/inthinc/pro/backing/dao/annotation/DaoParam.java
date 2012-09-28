package com.inthinc.pro.backing.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.inthinc.pro.backing.dao.validator.ValidatorType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface DaoParam
{
    public abstract java.lang.String name() default "";    
    public abstract java.lang.String inputDesc() default "";
    public abstract java.lang.Class type() default java.lang.Object.class;
    public abstract boolean isAccountID() default false;
    public abstract ValidatorType  validator() default ValidatorType.DEFAULT;
}
