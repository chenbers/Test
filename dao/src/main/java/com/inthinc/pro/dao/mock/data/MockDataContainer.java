package com.inthinc.pro.dao.mock.data;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.BaseEnum;

public class MockDataContainer
{
    private static final Logger logger = Logger.getLogger(MockDataContainer.class);


    // all the data
    Map<Class, List<Object>> dataMap = new HashMap<Class, List<Object>>();

/*
    private int timeOffset;
    private int todaysDate = DateUtil.getTodaysDate();

*/
    public MockDataContainer()
    {
        new MockData().initializeStaticData(this);

    }

    // simple lookup using a single key/value
    public Map<String, Object> lookup(Class clas, String primaryKey, Object searchValue)
    {
        Object obj = retrieveObject(clas, primaryKey, searchValue);
        if (obj != null)
        {
            return createMapFromObject(obj);
        }
        return null;
    }
    public <T> T lookupObject(Class clas, String primaryKey, Object searchValue)
    {
       return (T)retrieveObject(clas, primaryKey, searchValue);
        
    }

    // get full list of a class
    public <T> List<T> lookupObjectList(Class clas, T object)
    {
        return (List<T>)dataMap.get(clas);
    }
    public List<Map<String, Object>> lookupList(Class clas)
    {
        List<Object> objList = dataMap.get(clas);
        if (objList != null && objList.size() > 0)
        {
            List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
            for (Object obj : objList)
            {
                returnList.add(createMapFromObject(obj));
            }
            
            return returnList;
        }
        return null;
    }

    // lookup using multiple key/value pairs ANDed together
    public List<Map<String, Object>> lookupList(Class clas, SearchCriteria searchCriteria)
    {
        List<Object> objList = retrieveObjectList(clas, searchCriteria);
        if (objList != null && objList.size() > 0)
        {
            List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
            for (Object obj : objList)
            {
                returnList.add(createMapFromObject(obj));
            }
            
            return returnList;
        }
        return null;
    }


    public Map<String, Object> lookup(Class clas, SearchCriteria searchCriteria)
    {
        Object obj = retrieveObject(clas, searchCriteria);
        if (obj != null)
        {
            return createMapFromObject(obj);
        }
        return null;
    }

    
    public static <T> Map<String, Object> createMapFromObject(T object)
    {
        Map<String, Object> objMap = new HashMap<String, Object>();

        Class cls = object.getClass();
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
            try
            {
                //getDeclaredFields will not resolve inherited fields. It will throw
                //a NoSuchFieldException.
                if (Modifier.isTransient(cls.getDeclaredField(key).getModifiers()))
                    continue;
            }
            catch (NoSuchFieldException e)
            {
                //if the declared field doesn't exist, we don't care. Move on.
            }

            Method getMethod = propertyDescriptors[i].getReadMethod();
            Object value;
            try
            {
                value = getMethod.invoke(object);
                
                if (value == null)
                    continue;
                
                // backend represents booleans as integers
                if (value instanceof Boolean)
                {
                    value = new Integer((Boolean)value ? 1 : 0);
                }
                // backend represents enums as integers
                else if (value.getClass().isEnum())
                {
                    if (value instanceof BaseEnum)
                    {
                        value = new Integer(((BaseEnum)value).getCode());
                    }
                    else
                    {
                        // we can only handle enums derived from our BaseEnum
                        continue;
                    }
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
    
    public <T> void storeObject(T obj)
    {
        List<Object> objList = dataMap.get(obj.getClass());
        if (objList == null)
        {
            objList = new ArrayList<Object>();

            dataMap.put(obj.getClass(), objList);
        }
        objList.add(obj);

    }

    public <T> T retrieveObject(Class<T> clas, String key, Object value)
    {
        List<Object> objList = dataMap.get(clas);
        if (objList == null)
        {
            return null;
        }

        for (Object obj : objList)
        {
            Object fieldValue = getFieldValue(obj, key);
            if (fieldValue.equals(value))
            {
                return (T) obj;
            }
        }

        return null;
    }

    public <T> T retrieveObject(Class<T> clas, SearchCriteria searchCriteria)
    {
        
        Map<String, Object> searchMap = searchCriteria.getCriteriaMap();
        List<Object> objList = dataMap.get(clas);
        if (objList == null)
        {
            return null;
        }
        for (Object obj : objList)
        {
            boolean isMatch = true;
            for (Map.Entry<String,Object> searchItem : searchMap.entrySet())
            {
                Object fieldValue = getFieldValue(obj, searchItem.getKey().toString());
                
                if (searchItem.getValue() instanceof ValueRange)
                {
                    ValueRange valueRange = (ValueRange)searchItem.getValue();
                    if (valueRange.low.compareTo(fieldValue) > 0 ||
                            valueRange.high.compareTo(fieldValue) < 0)
                    {
                        isMatch = false;
                        break;
                    }
                            
                }
                else if (!fieldValue.equals(searchItem.getValue()))
                {
                    isMatch = false;
                    break;
                }
            }
            
            if (isMatch)
            {
                return (T)obj;
            }
        }


        return null;
    }
    public <T> List<T> retrieveObjectList(Class<T> clas, SearchCriteria searchCriteria)
    {
        List<T> returnObjList = new ArrayList<T>();
        
        Map<String, Object> searchMap = searchCriteria.getCriteriaMap();
        List<Object> objList = dataMap.get(clas);
        if (objList == null)
        {
            return null;
        }
        for (Object obj : objList)
        {
            boolean isMatch = true;
            for (Map.Entry<String,Object> searchItem : searchMap.entrySet())
            {
                Object fieldValue = getFieldValue(obj, searchItem.getKey().toString());
                
                if (searchItem.getValue() instanceof ValueRange)
                {
                    ValueRange valueRange = (ValueRange)searchItem.getValue();
                    if (valueRange.low.compareTo(fieldValue) > 0 ||
                            valueRange.high.compareTo(fieldValue) < 0)
                    {
                        isMatch = false;
                        break;
                    }
                            
                }
                else if (!fieldValue.equals(searchItem.getValue()))
                {
                    isMatch = false;
                    break;
                }
            }
            
            if (isMatch)
            {
                returnObjList.add((T)obj);
            }
        }


        return returnObjList;
    }

    public <T> Object getFieldValue(T object, String field)
    {
        Object value = null;

        Class cls = object.getClass();
        BeanInfo beanInfo;
        try
        {
            beanInfo = Introspector.getBeanInfo(object.getClass());
        }
        catch (IntrospectionException e)
        {
            e.printStackTrace();
            return value;
        }

        PropertyDescriptor propertyDescriptors[] = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++)
        {
            String key = propertyDescriptors[i].getName();
            if (!key.equals(field))
                continue;

            Method getMethod = propertyDescriptors[i].getReadMethod();
            try
            {
                value = getMethod.invoke(object);
            }
            catch (IllegalAccessException e)
            {
                System.out.println("IllegalAccessException occured while trying to invoke the method "
                        + getMethod.getName());
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                System.out.println("InvocationTargetExcpetion occured while trying to invoke the method "
                        + getMethod.getName());
                e.printStackTrace();
            }

            break;
        }

        return value;
    }

}
