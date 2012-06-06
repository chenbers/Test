package com.inthinc.pro.dao.mock.data;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.inthinc.pro.model.BaseEnum;

public class TempConversionUtil
{

    public static <T> Map<String, Object> createMapFromObject(T object)
    {
        return createMapFromObject(object, false);
    }

    public static <T> Map<String, Object> createMapFromObject(T object, boolean includeTransients)
    {
        Map<String, Object> objMap = new HashMap<String, Object>();
    
        Class<?> cls = object.getClass();
        BeanInfo beanInfo;
        try
        {
            beanInfo = Introspector.getBeanInfo(object.getClass());
        }
        catch (IntrospectionException e)
        {
            e.printStackTrace();
            return objMap;
        }
    
        PropertyDescriptor propertyDescriptors[] = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++)
        {
            String key = propertyDescriptors[i].getName();
            if (key.equals("class"))
                continue;
    
            // if the field represented by key is transient, skip it
            if (!includeTransients)
            {
                try
                {
                    // getDeclaredFields will not resolve inherited fields. It will throw
                    // a NoSuchFieldException.
                    if (Modifier.isTransient(cls.getDeclaredField(key).getModifiers()))
                        continue;
                }
                catch (NoSuchFieldException e)
                {
                    // if the declared field doesn't exist, we don't care. Move on.
                }
            }
    
            Method getMethod = propertyDescriptors[i].getReadMethod();
            if (getMethod == null)
            	continue;
            Object value;
            try
            {
                value = getMethod.invoke(object);
    
                if (value == null)
                    continue;
    
                // backend represents booleans as integers
                if (value instanceof Boolean)
                {
                    value = new Integer((Boolean) value ? 1 : 0);
                }
                // backend represents enums as integers
                else if (value.getClass().isEnum())
                {
                    if (value instanceof BaseEnum)
                    {
                        value = new Integer(((BaseEnum) value).getCode());
                    }
                    else
                    {
                        // we can only handle enums derived from our BaseEnum
                        continue;
                    }
                }
                else if (List.class.isInstance(value))
                {
                    List<Object> returnList = new ArrayList<Object>();
                    for (Object o : (List<?>) value)
                    {
                        if ((o instanceof Boolean) || (o instanceof Number) || (o instanceof String)
                                || (o instanceof Character) || (o instanceof Enum))
                            returnList.add(o);
                        else
                            returnList.add(createMapFromObject(o, includeTransients));
                    }
                    value = returnList;
                }
                objMap.put(key, value);
            }
            catch (IllegalAccessException e)
            {
                System.out.println("IllegalAccessException occured while trying to invoke the method " + getMethod.getName());
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                System.out.println("InvocationTargetExcpetion occured while trying to invoke the method " + getMethod.getName());
                e.printStackTrace();
            }
        }
    
        return objMap;
    }

}
