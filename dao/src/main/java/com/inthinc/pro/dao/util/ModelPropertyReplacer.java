package com.inthinc.pro.dao.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.BeanInitializationException;

import com.inthinc.pro.model.ReferenceEntity;

public class ModelPropertyReplacer {
    /**
     * Deeply compares all properties of the item to the given replace item, replacing the property of the item if they aren't equal and the replacement item is not null. 
     * 
     * @param item
     *            The item to replace properties in.
     * @param replacefrom
     *            The item to replace properties from.
     * @throws BeanInitializationException
     * @throws FatalBeanException
     */
    public static void compareAndReplace(Object item, Object replacefrom)
    {
        final HashSet<Object> compared = new HashSet<Object>();
        compared.add(item);
        compareAndReplace(item, replacefrom, compared);
    }
    /**
     * Deeply compares all properties of the item to the given replace item, replacing the property of the item if they aren't equal and the replacement item is not null. 
     * 
     * @param item
     *            The item to properties in.
     * @param replacefrom
     *            The item to replace properties from.
     * @param compared
     *            A map of objects we've already compared, to handle circular references.
     * @throws BeanInitializationException
     * @throws FatalBeanException
     */
    private static void compareAndReplace(Object item, Object replaceFrom, Set<Object> compared)
    {
        try
        {
        	if (replaceFrom == null) return;
            for (PropertyDescriptor itemPropertyDescriptor : BeanUtils.getPropertyDescriptors(item.getClass()))
            {
                final Method itemWrite = itemPropertyDescriptor.getWriteMethod();
                if(itemWrite == null) continue;
                
                final Method itemRead = itemPropertyDescriptor.getReadMethod();
                final PropertyDescriptor replaceDescriptor = BeanUtils.getPropertyDescriptor(replaceFrom.getClass(), itemPropertyDescriptor.getName());
                if (replaceDescriptor != null)
                {
                    final Method replaceRead = replaceDescriptor.getReadMethod();
                    if ((itemRead != null) && (replaceRead != null))
                    {
                        final Object replaceProperty = replaceRead.invoke(replaceFrom, new Object[0]);
                        if (replaceProperty != null)
                        {
	                        Object itemProperty = itemRead.invoke(item, new Object[0]);
	                        if ((itemProperty == null) || !itemProperty.equals(replaceProperty))
	                        {
	                            if (compared.contains(itemProperty))
	                                continue;

                                final Class<?> propertyClass = itemPropertyDescriptor.getPropertyType();
                                if (propertyClass != null)
                                {
                                    // recursive compare
                                	if(notASimpleClass(item.getClass(), propertyClass)){
                                        compared.add(itemProperty);
                                        compareAndReplace(itemProperty, replaceProperty, compared);
                                    }
                                	else{
	                                    // replace the item property with replace property
	                                	itemWrite.invoke(item, replaceProperty);
                                	}
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Throwable ex)
        {
            throw new FatalBeanException("Could not compare and init properties", ex);
        }
    }
    private static boolean notASimpleClass(Class<?> itemClass, Class<?> propertyClass){
    	return !BeanUtils.isSimpleProperty(propertyClass) && !propertyClass.isEnum() && !Collection.class.isAssignableFrom(propertyClass) && !Map.class.isAssignableFrom(propertyClass)
        && !propertyClass.isArray() && !Date.class.isAssignableFrom(propertyClass) && !TimeZone.class.isAssignableFrom(propertyClass) 
        && !ReferenceEntity.class.isAssignableFrom(propertyClass)
        && !(SimpleType.valueOf(itemClass) != null && SimpleType.valueOf(itemClass).getSimpleTypes().contains(propertyClass))
        && !Locale.class.isAssignableFrom(propertyClass);
    }

}
