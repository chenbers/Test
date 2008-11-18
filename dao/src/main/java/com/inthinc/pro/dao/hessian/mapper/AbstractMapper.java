package com.inthinc.pro.dao.hessian.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.dao.hessian.exceptions.MappingException;
import com.inthinc.pro.model.BaseEnum;

public abstract class AbstractMapper implements Mapper
{
    private static final Logger logger = Logger.getLogger(AbstractMapper.class);
    // private Class<T> modelClass;
    private Map<String, Method> convertToFieldMap = new HashMap<String, Method>();
    private Map<String, Method> convertToColumnMap = new HashMap<String, Method>();

    @SuppressWarnings("unchecked")
    public AbstractMapper()
    {
        // this.modelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        populateConverterMaps();
    }

    private void populateConverterMaps()
    {
        for (Method method : this.getClass().getDeclaredMethods())
        {
            if (method.isAnnotationPresent(ConvertColumnToField.class))
            {
                convertToFieldMap.put(method.getAnnotation(ConvertColumnToField.class).columnName(), method);
            }
            else if (method.isAnnotationPresent(ConvertFieldToColumn.class))
            {
                convertToColumnMap.put(method.getAnnotation(ConvertFieldToColumn.class).fieldName(), method);
            }
        }

    }

    // public T convertToModelObject(Map<String, Object> map)
    // {
    //
    // T modelObject;
    // try
    // {
    // modelObject = modelClass.newInstance();
    // }
    // catch (InstantiationException e)
    // {
    // throw new MappingException(e);
    // }
    // catch (IllegalAccessException e)
    // {
    // throw new MappingException(e);
    // }
    //
    // return convertToModelObject(map, modelObject);
    // }

    public <E> E convertToModelObject(Map<String, Object> map, Class<E> type)
    {
        E modelObject;
        try
        {
            modelObject = type.newInstance();
        }
        catch (InstantiationException e)
        {
            throw new MappingException(e);
        }
        catch (IllegalAccessException e)
        {
            throw new MappingException(e);
        }
        return convertToModelObject(map, modelObject);
    }

    private <E> E convertToModelObject(Map<String, Object> map, E modelObject)
    {
        List<Field> fieldList = getAllFields(modelObject.getClass());

        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            String columnName = entry.getKey();
            Object value = entry.getValue();
            String key = null;

            for (Field field : fieldList)
            {
                if (field.isAnnotationPresent(Column.class))
                {
                    Column column = field.getAnnotation(Column.class);
                    if (column.name().equals(columnName))
                    {
                        key = column.name();
                        break;
                    }
                }
            }
            if (key == null)
                key = columnName;

            // Check to see if the key/value pair in the map is associated with a custom converter.
            // If so, invoke the converter. If not, do normal mapping from map key/value to field in modelObject
            if (convertToFieldMap.containsKey(columnName))
            {
                Method method = convertToFieldMap.get(columnName);
                try
                {
                    method.invoke(this, modelObject, value);
                }
                catch (IllegalAccessException e)
                {
                    throw new MappingException(e);
                }
                catch (InvocationTargetException e)
                {
                    throw new MappingException(e);
                }
                continue;
            }

            // If a converter was not found, get the Field object for later use
            Field field = null;
            try
            {
                field = getField(key, fieldList);

                // Check if the value is a Map or a List and handle it or just set the value in the object to be returned
                if (Map.class.isInstance(value))
                {
                    setProperty(modelObject, key, convertToModelObject((Map<String, Object>) value, field.getType()));
                }
                else if (List.class.isInstance(value))
                {
                    setProperty(modelObject, key, convertToModelObject((List<Map<String, Object>>) value, field.getAnnotation(Column.class).type()));
                }
                else
                {
                    setProperty(modelObject, key, value);
                }
            }
            catch (NoSuchFieldException e)
            {
                // If the field doesn't exist, continue
                if (logger.isDebugEnabled())
                {
                    logger.debug("The field \"" + key + "\" does not exist for class: " + value.getClass().getName());
                }
            }
        }

        return modelObject;
    }

    // public List<T> convertToModelObject(List<Map<String, Object>> list)
    // {
    // return convertToModelObject(list, modelClass);
    // }

    public <E> List<E> convertToModelObject(List<Map<String, Object>> list, Class<E> type)
    {
        List<E> returnList = new ArrayList<E>();
        if (list != null)
        {
            for (Map<String, Object> map : list)
            {
                returnList.add(convertToModelObject(map, type));
            }
        }
        return returnList;
    }

    @Override
    public Map<String, Object> convertToMap(Object modelObject)
    {
        Map<Object, Map<String, Object>> handled = new HashMap<Object, Map<String, Object>>();
        return convertToMap(modelObject, handled);
    }

    protected Map<String, Object> convertToMap(Object modelObject, Map<Object, Map<String, Object>> handled)
    {
        if (modelObject == null)
            return null;

        Map<String, Object> map = new HashMap<String, Object>();
        if (handled.get(modelObject) != null)
            return handled.get(modelObject);
        else
            handled.put(modelObject, map);

        Class<?> clazz = modelObject.getClass();
        while (clazz != null)
        {
            for (Field field : clazz.getDeclaredFields())
            {
                field.setAccessible(true);

                Column column = null;
                String name = null;
                if (field.isAnnotationPresent(Column.class))
                    column = field.getAnnotation(Column.class);
                if (column != null)
                {
                    // If the field has been annotated with the @Column(updateable=false), then skip
                    if (!column.updateable())
                        continue;
                    if (!column.name().isEmpty())
                        name = column.name();
                    else
                        name = field.getName();
                }
                else
                {
                    name = field.getName();
                }

                Object value = null;
                try
                {
                    value = field.get(modelObject);
                }
                catch (IllegalAccessException e)
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Attempt to access the property \"" + field.getName() + "\" on object of type \"" + modelObject.getClass().getName()
                                + "\" caused an exception", e);
                    }
                }

                if (value == null)
                    continue;

                // Start checking the value for special cases. If a case doesn't exist, just put the field name and value in the map
                if (Class.class.isInstance(value))
                    continue;
                else if (convertToColumnMap.containsKey(field.getName()))
                {
                    Method method = convertToColumnMap.get(field.getName());
                    try
                    {
                        method.invoke(this, modelObject, map);
                    }
                    catch (IllegalAccessException e)
                    {
                        throw new MappingException(e);
                    }
                    catch (InvocationTargetException e)
                    {
                        throw new MappingException(e);
                    }

                }

                // if the property is a Map, convert the objects in the Map to Map<String,Object>. i'm not sure if this will ever occur
                // i didn't want to make the assumption that the Map object represented by the value variable is a Map<String, Object> so i'm
                // just going with Map<Object, Object>.
                else if (Map.class.isInstance(value))
                {
                    Map<Object, Map<String, Object>> valueMap = new HashMap<Object, Map<String, Object>>();
                    for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet())
                    {
                        if (handled.containsKey(entry.getValue()))
                        {
                            valueMap.put(entry.getKey(), handled.get(entry.getValue()));
                        }
                        else
                        {
                            valueMap.put(entry.getKey(), convertToMap(entry.getValue(), handled));
                        }
                    }
                    map.put(name, valueMap);
                }
                // if the property is a List, convert the objects in the list to Map<String, Object>
                else if (List.class.isInstance(value))
                {
                    map.put(name, convertList((List<?>) value));
                }
                // if the field type is Date, convert to integer
                if (Date.class.isInstance(value))
                {
                    map.put(name, (int) (((Date) value).getTime() / 1000l));
                }
                // if the field type is TimeZone, convert to string
                else if (TimeZone.class.isInstance(value))
                {
                    map.put(name, ((TimeZone) value).getID());
                }
                // if the property is not a standardProperty it must be some kind of bean/pojo/object. convert the property to a map
                else if (!isStandardProperty(value))
                {
                    if (handled.containsKey(value))
                    {
                        map.put(name, handled.get(value));
                    }
                    else
                    {
                        map.put(name, convertToMap(value, handled));
                    }
                }
                // if we have made it this far, the value must be a String or a primitive type. Just put it in the map.
                else
                {
                    map.put(name, value);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return map;
    }

    public List<Map<String, Object>> convertList(List<?> list)
    {
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (Object o : list)
        {
            returnList.add(convertToMap(o));
        }
        return returnList;
    }

    private void setProperty(Object bean, String name, Object value)
    {
        try
        {
            Class<?> propertyType = PropertyUtils.getPropertyType(bean, name);
            // if the property type is Date, convert the integer returned in the hash map that represents seconds to a long and create a Date object
            if (propertyType != null && propertyType.equals(Date.class) && value instanceof Integer)
            {
                Integer seconds = (Integer) value;
                value = new Date(seconds.longValue() * 1000l);
            }
            if (propertyType != null && propertyType == TimeZone.class && value instanceof String)
            {
                String tzID = (String) value;
                value = TimeZone.getTimeZone(tzID);
            }
            else if (propertyType != null && propertyType.equals(Boolean.class) && value instanceof Integer)
            {
                value = ((Integer) value).equals(Integer.valueOf(0)) ? Boolean.FALSE : Boolean.TRUE;
            }
            else if (propertyType != null && BaseEnum.class.isAssignableFrom(propertyType) && value instanceof Integer)
            {
                Method valueOf = propertyType.getMethod("valueOf", Integer.class);
                if (valueOf != null)
                    value = valueOf.invoke(null, value);
            }
            else if (propertyType != null && Enum.class.isAssignableFrom(propertyType) && value instanceof String)
            {
                Method valueOf = propertyType.getMethod("valueOf", String.class);
                if (valueOf != null)
                    value = valueOf.invoke(null, value);
            }
            PropertyUtils.setProperty(bean, name, value);
        }
        catch (IllegalAccessException e)
        {
            throw new MappingException(e);
        }
        catch (InvocationTargetException e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("The property \"" + name + "\" could not be set to the value \"" + value + "\"");
            }
        }
        catch (NoSuchMethodException e)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("The property \"" + name + "\" does not exist for class: " + value.getClass().getName());
            }
        }
    }

    private static boolean isStandardProperty(Object o)
    {
        if (Number.class.isInstance(o))
            return true;
        if (Character.class.isInstance(o))
            return true;
        if (String.class.isInstance(o))
            return true;
        return false;
    }

    private static List<Field> getAllFields(Class<?> type)
    {
        List<Field> fieldList = new ArrayList<Field>();
        Class<?> clazz = type;
        while (clazz != null)
        {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    private static Field getField(String fieldName, Class<?> type) throws NoSuchFieldException
    {
        return getField(fieldName, getAllFields(type));
    }

    private static Field getField(String fieldName, List<Field> fieldList) throws NoSuchFieldException
    {
        for (Field field : fieldList)
        {
            if (field.getName().equals(fieldName))
                return field;
        }
        throw new NoSuchFieldException();

    }

}
