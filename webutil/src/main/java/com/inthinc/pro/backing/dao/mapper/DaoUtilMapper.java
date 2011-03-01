package com.inthinc.pro.backing.dao.mapper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.inthinc.hos.model.EnumIntegerMapping;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.model.BaseEnum;
import com.inthinc.pro.model.ReferenceEntity;

@SuppressWarnings("serial")
public class DaoUtilMapper extends BaseUtilMapper {

	@Override
    public Map<String, Object> convertToMap(Object modelObject)
    {
        final Map<Object, Map<String, Object>> handled = new HashMap<Object, Map<String, Object>>();
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
//                    if (logger.isTraceEnabled())
//                    {
//                        logger.trace("Attempt to access the property \"" + field.getName() + "\" on object of type \"" + modelObject.getClass().getName()
//                                + "\" caused an exception", e);
//                    }
                }
                // Start checking the value for special cases. If a case doesn't exist, just put the field name and value in the map
                if (value != null && Class.class.isInstance(value))
                    continue;
/*                
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
*/                
                else if (value != null)
                {
                    map.put(name, convertToDisplay(value, handled, field, true));
                }
                else if (BaseEnum.class.isAssignableFrom(field.getType()) || ReferenceEntity.class.isAssignableFrom(field.getType()))
                {
                    map.put(name, 0);
                } else if (EnumIntegerMapping.class.isAssignableFrom(field.getType()) && value instanceof Integer) {
                    map.put(name, 0);
                }
                else if (Date.class.isAssignableFrom(field.getType()))
                {
                    // use 1 to mean an empty date: January 1, 1970 at 12:01am
                    map.put(name, 1L);
                }
            }
            clazz = clazz.getSuperclass();
        }
//        if(logger.isTraceEnabled()) {
//            logger.trace("Conversion of " + modelObject + " to a Map produced: " + map);
//        }
        return map;
    }

    private Object convertToDisplay(Object value, Map<Object, Map<String, Object>> handled, Field field, boolean includeNonUpdateables)
    {
        if (value == null)
        {
            return value;
        }
        // if the property is a Map, convert the objects in the Map to Map<String,Object>. i'm not sure if this will ever occur
        // i didn't want to make the assumption that the Map object represented by the value variable is a Map<String, Object> so i'm
        // just going with Map<Object, Object>.
        if (Map.class.isInstance(value))
        {
//            Map<Object, Map<String, Object>> valueMap = new HashMap<Object, Map<String, Object>>();
            Map<Object, Object> valueMap = new HashMap<Object, Object>();
            for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet())
            {
                if (handled.containsKey(entry.getValue()))
                {
                    valueMap.put(entry.getKey(), handled.get(entry.getValue()));
                }
                else
                {
                	Object entryValue = entry.getValue();
                	if (entryValue == null){
                	    valueMap.put(entry.getKey(),null); 
                	}
                	else{
                	    
                        Class<?> fieldType = entryValue.getClass();
                        if (Boolean.class.isAssignableFrom(fieldType) || Number.class.isAssignableFrom(fieldType) || String.class.isAssignableFrom(fieldType)
                                || Character.class.isAssignableFrom(fieldType) || Date.class.isAssignableFrom(fieldType) || TimeZone.class.isAssignableFrom(fieldType)
                                || fieldType.isEnum())
                        	valueMap.put(entry.getKey(), entryValue);
                        else valueMap.put(entry.getKey(), convertToMap(entry.getValue(), handled, includeNonUpdateables));
                	}
                }
            }
            value = valueMap;
        }
        // if the property is a List, convert the objects in the list to Map<String, Object> as needed
        else if (List.class.isInstance(value))
        {
            final Class<?> fieldType = getFieldType(field);
            if (fieldType != null)
            {
                if (Boolean.class.isAssignableFrom(fieldType) || Number.class.isAssignableFrom(fieldType) || String.class.isAssignableFrom(fieldType)
                        || Character.class.isAssignableFrom(fieldType) || Date.class.isAssignableFrom(fieldType) || TimeZone.class.isAssignableFrom(fieldType)
                        || fieldType.isEnum())
                    value = convertSimpleList((List<?>) value, field, handled, includeNonUpdateables);
                else
                    value = convertList((List<?>) value, handled, includeNonUpdateables);
            }
        }
        // if the property is an array, convert the array to a list
        else if (value.getClass().isArray())
        {
            final ArrayList<Object> list = new ArrayList<Object>();
            for (int i = 0; i < Array.getLength(value); i++)
                list.add(convertToDisplay(Array.get(value, i), handled, field, includeNonUpdateables));
            value = list;
        }
        // if the field type is Date formatted String
        else if (Date.class.isInstance(value))
        {
        	value = dateFormatBean.formatDate((Date)value);
        }
        // if the field type is TimeZone, convert to string
        else if (TimeZone.class.isInstance(value))
        {
            value = ((TimeZone) value).getID();
        }
        // if the field type is Boolean, convert to integer
        else if (Boolean.class.isInstance(value))
        {
            value = ((Boolean) value) ? "true" : "false";
        }
        else if (ReferenceEntity.class.isInstance(value))
        {
            value = ((ReferenceEntity) value).retrieveID();
        }
        else if (Locale.class.isInstance(value))
        {
            value = ((Locale)value).toString();
        }
        else if (BaseEnum.class.isInstance(value))
        {
            value = ((BaseEnum) value).toString() + "(" + ((BaseEnum) value).getCode() + ")";
        }
        else if (EnumIntegerMapping.class.isInstance(value))
        {
            value = ((EnumIntegerMapping) value).toString() + "(" + ((EnumIntegerMapping) value).getCode() + ")";
        }
        // if the property is not a standardProperty it must be some kind of bean/pojo/object. convert the property to a map
        else if (!isStandardProperty(value))
        {
            if (handled.containsKey(value))
            {
                value = handled.get(value);
            }
            else
            {
                value = convertToMap(value, handled, includeNonUpdateables);
            }
        }
        // if we have made it this far, the value must be a String or a primitive type. Just put it in the map.
        return value;
    }


}
