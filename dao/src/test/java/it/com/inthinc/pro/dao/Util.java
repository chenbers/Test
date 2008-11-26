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
import java.util.List;
import java.util.Map;

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
    public static <T> void compareObjects(T obj1, T obj2, String ignoreList[])
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
            String key = propertyDescriptors[i].getName();
            if (ignoreFields.contains(key))
                continue;
    
            Method getMethod = propertyDescriptors[i].getReadMethod();
            Object value1;
            Object value2;
            try
            {
                value1 = getMethod.invoke(obj1);
                value2 = getMethod.invoke(obj2);
    
                if (List.class.isInstance(value1))
                {
                    List<?> value1List = (List<?>) value1;
                    List<?>  value2List = (List<?>) value2;
                    for (int v = 0; v < value1List.size(); v++)
                    {
                        compareObjects(value1List.get(v), value2List.get(v));
                    }
                        
                }
                else
                {
                    if (value1 == null && value2 == null)
                    {
                        continue;
                    }
                    else if (value1 == null)
                    {
                        assertNotNull(value1.getClass().getSimpleName() + " Field: " + key + " ", value1 );
                    }
                    else if (value2 == null)
                    {
                        assertNotNull(value1.getClass().getSimpleName() + " Field: " + key + " ", value2 );
                    }
                    assertEquals(value1.getClass().getSimpleName() + " Field: " + key + " ", value1, value2);
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

}
