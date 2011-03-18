package com.inthinc.pro.backing.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.inthinc.pro.backing.dao.model.CrudType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MethodDescription
{
    
    String description();
    CrudType crudType();
    Class<?> modelClass() default java.util.Map.class;
    Class<? extends com.inthinc.pro.backing.dao.mapper.BaseUtilMapper> mapperClass() default com.inthinc.pro.backing.dao.mapper.DaoUtilMapper.class;
    String populateMethod() default "";

}
