package it.com.inthinc.pro.dao;


import static org.junit.Assert.*;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.inthinc.pro.model.BaseEnum;

public class Util
{
    public static int randomInt(int min, int max)
    {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }
    
    public static <T> void compareObjects(T obj1, T obj2)
    {
        compareObjects(obj1, obj2, new String[] {});
    }
    public static <T> void compareObjects(T obj1, T obj2, String... ignoreList)
    {
        List<String> ignoreFields = Arrays.asList(ignoreList);
        Class<?> cls = obj1.getClass();
        BeanInfo beanInfo = null;
        try
        {
            beanInfo = Introspector.getBeanInfo(obj1.getClass());
        }
        catch (IntrospectionException e)
        {
            e.printStackTrace();
            fail("IntrospectionExecption");
        }
    
        PropertyDescriptor propertyDescriptors[] = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++)
        {
            if (propertyDescriptors[i].isHidden())
                continue;
            
            String key = propertyDescriptors[i].getName();
            if (ignoreFields.contains(key) || key.equals("class"))
                continue;
    
            Method getMethod = propertyDescriptors[i].getReadMethod();
            Object value1;
            Object value2;
            try
            {
                value1 = getMethod.invoke(obj1);
                value2 = getMethod.invoke(obj2);

                if (value1 == null && value2 == null)
                {
                    continue;
                }
                else if (value1 == null)
                {
                    assertNotNull(value2.getClass().getSimpleName() + " Field: " + key + " expected value is null, but returned value is not", value1 );
                }
                else if (value2 == null)
                {
                    assertNotNull(value1.getClass().getSimpleName() + " Field: " + key + " expected value is not null, but returned value is", value2 );
                }
    
                if (List.class.isInstance(value1))
                {
                    List<?> value1List = (List<?>) value1;
                    List<?>  value2List = (List<?>) value2;
                    assertEquals("Field: " + key + " list sizes differ", value1List.size(), value2List.size());
                    for (int v = 0; v < value1List.size(); v++)
                    {
                        compareObjects(value1List.get(v), value2List.get(v));
                    }
                }
                else if (Date.class.isInstance(value1))
                {
                    long diff = ((Date)value1).getTime() - ((Date)value2).getTime();
                    
                    if (diff > 6000 || diff < -6000)
                    {
                        assertEquals(value1.getClass().getSimpleName() + " Field: " + key + " ", ((Date)value1).getTime(), ((Date)value2).getTime());
                    }
                }
                else
                {
                    if (!isStandardProperty(value1) && !isComparableProperty(value1))
                    {
                        compareObjects(value1, value2, ignoreList);
                    }
                    else
                    {    
                        assertEquals(value1.getClass().getSimpleName() + " Field: " + key + " ", value1, value2);
                    }
                }
            }
            catch (IllegalAccessException e)
            {
                System.out.println("IllegalAccessException occured while trying to invoke the method " + getMethod.getName());
                e.printStackTrace();
                fail("IntrospectionExecption");
            }
            catch (InvocationTargetException e)
            {
                System.out.println("InvocationTargetException occured while trying to invoke the method " + getMethod.getName());
                e.printStackTrace();
                fail("InvocationTargetException");
            }
        }
        
    }

    private static boolean isComparableProperty(Object value1)
    {
        if (BaseEnum.class.isAssignableFrom(value1.getClass()))
            return true;
        if (Enum.class.isAssignableFrom(value1.getClass()))
            return true;
        if (TimeZone.class.isAssignableFrom(value1.getClass()))
            return true;
        
        return false;
    }

    public static Date genDate(Integer year, Integer month, Integer day)
    {
        GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        cal.set(year, month, day, 0, 0, 1);
        cal.setTimeInMillis((cal.getTimeInMillis()/1000l) * 1000l);
        
        
        return cal.getTime();
        
    }
    private static boolean isStandardProperty(Object o)
    {
        if (Number.class.isInstance(o))
            return true;
        if (Character.class.isInstance(o))
            return true;
        if (String.class.isInstance(o))
            return true;
        if (Boolean.class.isInstance(o))
            return true;
        return false;
    }



}
