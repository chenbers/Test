package com.inthinc.pro.dao.hessian.debug;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class DebugUtil
{
    public static <K, T> void dumpMap(Map<K, T> map)
    {
        System.out.println("--------------------");
        for (K key : map.keySet())
        {
            T object = map.get(key);
//            System.out.println("Key: " + key + " Value Type:" + ((object == null) ? "null" : object.getClass().getName()));
            if (object != null)
            {
                System.out.println("Key: " + key + " [" + object.toString() + "]");
//                dumpObj(object);
            }
            else System.out.println("Key: " + key + " Value: null");

        }
        System.out.println("--------------------");
    }
    public static <T> void dumpObj(T object) 
    {
//        System.out.println("Value Type:" + object.getClass().getName());

        BeanInfo beanInfo = null;
        try
        {
            beanInfo = Introspector.getBeanInfo(object.getClass());
        }
        catch (IntrospectionException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PropertyDescriptor propertyDescriptors[] = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++)
        {

            String prop = propertyDescriptors[i].getName();
            if (prop.equals("class"))
                continue;
            Method getMethod = propertyDescriptors[i].getReadMethod();
            if (getMethod == null)
                continue;
            Object value = null;
            try
            {
                value = getMethod.invoke(object);
            }
            catch (IllegalArgumentException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

//            System.out.println(prop + ":\t" + ((value == null) ? "<null>" : value.toString()));

        }
    }

}
