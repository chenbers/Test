package com.inthinc.pro.dao.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class IdExtractor {
	
    public static Integer getID(Object entity)  {
        Integer id = null;
        for (Field f : entity.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(com.inthinc.pro.dao.annotations.ID.class)) {
                try {
                    id = (Integer) f.get(entity);
                } catch (Exception e) {

                    try {
						for (PropertyDescriptor property : Introspector.getBeanInfo(entity.getClass()).getPropertyDescriptors())
						    if (property.getName().equals(f.getName())) {
						        id = (Integer) property.getReadMethod().invoke(entity, new Object[0]);
						        break;
						    }
					} catch (IllegalArgumentException e1) {
						return null;
					} catch (IntrospectionException e1) {
						return null;
					} catch (IllegalAccessException e1) {
						return null;
					} catch (InvocationTargetException e1) {
						return null;
					}
                }
                break;
            }
        }
        return id;
    }

}
