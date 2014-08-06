package com.inthinc.pro.backing.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DAODescription {
    String daoID() default "";
    String daoMethod() default "";
    Class<? extends com.inthinc.pro.backing.dao.argmapper.AbstractArgumentMapper> daoParamMapper() default com.inthinc.pro.backing.dao.argmapper.BaseArgumentMapper.class;
    String returnValueName() default "value";
    boolean useMapper() default true;
}
