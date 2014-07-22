package com.inthinc.pro.util;

import org.joda.time.chrono.AssembledChronology;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

/**
 * Utilities for beans.
 */
public class BeanUtil {

    public static <T> List<T> makeListValuesNotNull(List<T> objList){
       for (Object obj: objList){
           makeNullValuesNotNull(obj);
       }
       return objList;
    }

    /**
     * Attempts to make all object fields of the given object
     * non null. Fails silently if it cannot. Can return partial results.
     * @param obj given object
     *
     */
    public static  <T> T makeNullValuesNotNull(T obj) {
        if (obj == null)
            throw new InvalidParameterException("Object cannot be null.");

        Class<?> clazz = obj.getClass();
        Field[] fieldArr = clazz.getDeclaredFields();
        for (Field field: fieldArr){
            try {
                field.setAccessible(true);
                if (field.get(obj) == null) {
                    makeNotNull(obj, field);
                }
            }catch(IllegalAccessException iae){
                // failed for this field, continue
            }
        }

        return obj;
    }

    /**
     * Determines the type of a field and makes it a non-null value.
     *
     * @param obj object
     * @param field field
     */
    private static void makeNotNull(Object obj, Field field) throws IllegalAccessException {
        Class clazz = field.getType();

        if (clazz.equals(String.class))
            field.set(obj,"");
        else if (clazz.equals(Date.class))
            field.set(obj, new Date());
        else if (clazz.equals(Number.class))
            field.set(obj, BigDecimal.valueOf(0));
        else if (clazz.equals(Integer.class))
            field.set(obj, Integer.valueOf(0));
        else if (clazz.equals(Long.class))
            field.set(obj, Long.valueOf(0));
        else if (clazz.equals(Double.class))
            field.set(obj, Double.valueOf(0));
        else if (clazz.equals(Float.class))
            field.set(obj, Float.valueOf(0));
    }
}
