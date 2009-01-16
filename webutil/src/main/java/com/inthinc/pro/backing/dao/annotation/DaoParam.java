package com.inthinc.pro.backing.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface DaoParam
{
    public abstract java.lang.String name() default "";    
    public abstract java.lang.String inputDesc() default "";
    public abstract java.lang.Class inputConvert() default com.inthinc.pro.convert.BaseConvert.class;
    public abstract boolean isDate() default false;
}
