package com.inthinc.pro.dao.hessian.mapper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.dao.hessian.exceptions.MappingException;
import com.inthinc.pro.model.BaseEnum;
import com.inthinc.pro.model.ReferenceEntity;
import com.inthinc.hos.model.EnumIntegerMapping;

public abstract class AbstractMapper implements Mapper {
    private static final long serialVersionUID = 4820133473373187598L;
    private static final Logger logger = Logger.getLogger(AbstractMapper.class);
    // private Class<T> modelClass;
    private transient Map<String, Method> convertToFieldMap = new HashMap<String, Method>();
    private transient Map<String, Method> convertToColumnMap = new HashMap<String, Method>();

    @SuppressWarnings("unchecked")
    public AbstractMapper() {
        // this.modelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        populateConverterMaps();
    }

    private void populateConverterMaps() {
        for (Method method : this.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(ConvertColumnToField.class)) {
                convertToFieldMap.put(method.getAnnotation(ConvertColumnToField.class).columnName(), method);
            } else if (method.isAnnotationPresent(ConvertFieldToColumn.class)) {
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
    public <E> E convertToModelObject(Map<String, Object> map, Class<E> type) {
        E modelObject;
        try {
            modelObject = type.newInstance();
        } catch (InstantiationException e) {
            throw new MappingException(e);
        } catch (IllegalAccessException e) {
            throw new MappingException(e);
        }
        return convertToModelObject(map, modelObject);
    }

    private <E> E convertToModelObject(Map<String, Object> map, E modelObject) {
        if (logger.isDebugEnabled()) {
            logger.debug("Returned Map converted to [" + modelObject.getClass().getSimpleName() + "]: " + map);
        }
        List<Field> fieldList = getAllFields(modelObject.getClass());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String columnName = entry.getKey();
            Object value = entry.getValue();
            String key = null;
            for (Field field : fieldList) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    if (column.name().equals(columnName)) {
                        key = field.getName();
                        break;
                    }
                }
            }
            if (key == null)
                key = columnName;
            // Check to see if the key/value pair in the map is associated with a custom converter.
            // If so, invoke the converter. If not, do normal mapping from map key/value to field in modelObject
            if (convertToFieldMap.containsKey(columnName)) {
                Method method = convertToFieldMap.get(columnName);
                try {
                    method.invoke(this, modelObject, value);
                } catch (IllegalAccessException e) {
                    throw new MappingException(e);
                } catch (InvocationTargetException e) {
                    throw new MappingException(e);
                }
                continue;
            }
            setProperty(modelObject, key, value, fieldList);
        }
        return modelObject;
    }

    protected Class<?> getFieldType(Field field) {
        Class<?> fieldType = null;
        final Column annotation = field.getAnnotation(Column.class);
        if (annotation != null)
            fieldType = annotation.type();
        if ((fieldType == null || fieldType == Object.class) && (field.getGenericType() instanceof ParameterizedType))
            fieldType = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        return fieldType;
    }

    // public List<T> convertToModelObject(List<Map<String, Object>> list)
    // {
    // return convertToModelObject(list, modelClass);
    // }
    public <E> List<E> convertToModelObject(List<Map<String, Object>> list, Class<E> type) {
        List<E> returnList = new ArrayList<E>();
        if (list != null) {
            for (Map<String, Object> map : list) {
                returnList.add(convertToModelObject(map, type));
            }
        }
        return returnList;
    }

    protected <E> List<E> convertSimpleListToModelObject(List<Object> list, Class<E> type, List<Field> fieldType) {
        List<E> returnList = new ArrayList<E>();
        if (list != null) {
            for (Object o : list) {
                try {
                    returnList.add((E) convertProperty(type, null, o, fieldType));
                } catch (NoSuchFieldException e) {} catch (Exception e) {
                    throw new MappingException(e);
                }
            }
        }
        return returnList;
    }

    private void setProperty(Object bean, String name, Object value, List<Field> fieldList) {
        try {
            Class<?> propertyType = PropertyUtils.getPropertyType(bean, name);
            // if the property type is Date, convert the long returned in the hash map that represents seconds to a long and create a Date object
            value = convertProperty(propertyType, name, value, fieldList);
            PropertyUtils.setProperty(bean, name, value);
        } catch (InvocationTargetException e) {
            if (logger.isTraceEnabled()) {
                logger.trace("The property \"" + name + "\" could not be set to the value \"" + value + "\"", e);
            }
        } catch (NoSuchMethodException e) {
            if (logger.isTraceEnabled()) {
                logger.trace("The property \"" + name + "\" could not be set to the value \"" + value + "\"", e);
            }
        } catch (NoSuchFieldException e) {
            if (logger.isTraceEnabled()) {
                logger.trace("The property \"" + name + "\" could not be set to the value \"" + value + "\"", e);
            }
        } catch (IllegalAccessException e) {
            if (logger.isTraceEnabled()) {
                logger.trace("The property \"" + name + "\" could not be set to the value \"" + value + "\"", e);
            }
            throw new MappingException(e);
        } catch (IllegalArgumentException e) {
            if (logger.isTraceEnabled()) {
                logger.trace("The property \"" + name + "\" could not be set to the value \"" + value + "\"", e);
            }
            throw new MappingException(e);
        } catch (InstantiationException e) {
            throw new MappingException(e);
        }
    }

    protected Object convertProperty(Class<?> propertyType, String key, Object value, List<Field> fieldList) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            NoSuchFieldException, InstantiationException {
        if (propertyType != null) {
            if (propertyType.equals(Date.class) && value instanceof Number) {
                // note: negative values represent dates before 1/1/1970, 1 means null
                Long seconds = ((Number) value).longValue();
                if (seconds != 1)
                    value = new Date(seconds.longValue() * 1000l);
                else
                    value = null;
            }
            if (propertyType == TimeZone.class && value instanceof String) {
                String tzID = (String) value;
                value = TimeZone.getTimeZone(tzID);
            } else if (propertyType.equals(Locale.class) && value instanceof String) {
                String[] locale = ((String) value).split("_");
                if (locale.length == 1)
                    value = new Locale(locale[0]);
                else if (locale.length == 2)
                    value = new Locale(locale[0], locale[1]);
            } else if (propertyType.equals(Boolean.class) && value instanceof Integer) {
                value = ((Integer) value).equals(Integer.valueOf(0)) ? Boolean.FALSE : Boolean.TRUE;
            } else if (ReferenceEntity.class.isAssignableFrom(propertyType) && value instanceof Integer) {
                Method valueOf = propertyType.getMethod("valueOf", Integer.class);
                if (valueOf != null)
                    value = valueOf.invoke(null, value);
            } else if (BaseEnum.class.isAssignableFrom(propertyType) && value instanceof Integer) {
                Method valueOf = propertyType.getMethod("valueOf", Integer.class);
                if (valueOf != null)
                    value = valueOf.invoke(null, value);
            } else if (EnumIntegerMapping.class.isAssignableFrom(propertyType) && value instanceof Integer) {
                Method valueOf = propertyType.getMethod("valueOf", Integer.class);
                if (valueOf != null)
                    value = valueOf.invoke(null, value);
            } else if (Enum.class.isAssignableFrom(propertyType) && value instanceof String) {
                Method valueOf = propertyType.getMethod("valueOf", String.class);
                if (valueOf != null)
                    value = valueOf.invoke(null, value);
            } else if (propertyType.isArray() && value instanceof List) {
                value = createArray((List<?>) value, propertyType, fieldList);
            } else if (Map.class.isInstance(value)) {
                final Field field = getField(key, fieldList);
                value = convertToModelObject((Map<String, Object>) value, field.getType());
            } else if (List.class.isInstance(value)) {
                final Field field = getField(key, fieldList);
                final Class<?> fieldType = getFieldType(field);
                if (fieldType != null) {
                    if (Boolean.class.isAssignableFrom(fieldType) || Number.class.isAssignableFrom(fieldType) || String.class.isAssignableFrom(fieldType)
                            || Character.class.isAssignableFrom(fieldType) || Date.class.isAssignableFrom(fieldType) || TimeZone.class.isAssignableFrom(fieldType) || fieldType.isEnum())
                        value = convertSimpleListToModelObject((List<Object>) value, fieldType, fieldList);
                    else
                        value = convertToModelObject((List<Map<String, Object>>) value, fieldType);
                }
            }
        }
        return value;
    }

    public <T> T[] createArray(List<T> list, Class<?> propertyType, List<Field> fieldList) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException,
            NoSuchFieldException {
        final T[] array = (T[]) Array.newInstance(propertyType.getComponentType(), list.size());
        for (int i = 0; i < array.length; i++)
            array[i] = (T) convertProperty(propertyType.getComponentType(), null, list.get(i), fieldList);
        return array;
    }

    @Override
    public Map<String, Object> convertToMap(Object modelObject) {
        final Map<Object, Map<String, Object>> handled = new HashMap<Object, Map<String, Object>>();
        return convertToMap(modelObject, handled, false);
    }

    @Override
    public Object[] convertToArray(Collection<?> modelObjectCollection) {
        return convertToArray(modelObjectCollection, Object.class);
    }

    @Override
    public <E> E[] convertToArray(Collection<?> modelObjectCollection, Class<E> type) {

        if (modelObjectCollection == null)
            return null;

        List<Object> list = new ArrayList<Object>();
        for (Object modelObject : modelObjectCollection) {
            list.add(convertToHessian(modelObject, null, null, false));
        }
        E[] array = (E[]) Array.newInstance(type, list.size());
        return list.toArray(array);
    }

    protected Map<String, Object> convertToMap(Object modelObject, Map<Object, Map<String, Object>> handled, boolean includeNonUpdateables) {
        if (modelObject == null)
            return null;
        Map<String, Object> map = new HashMap<String, Object>();
        if (handled.get(modelObject) != null)
            return handled.get(modelObject);
        else
            handled.put(modelObject, map);
        Class<?> clazz = modelObject.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Column column = null;
                String name = null;
                if (field.isAnnotationPresent(Column.class))
                    column = field.getAnnotation(Column.class);
                if (column != null) {
                    // If the field has been annotated with the @Column(updateable=false), then skip
                    if (!includeNonUpdateables && !column.updateable())
                        continue;
                    if (!column.name().isEmpty())
                        name = column.name();
                    else
                        name = field.getName();
                } else {
                    name = field.getName();
                }
                Object value = null;
                try {
                    value = field.get(modelObject);
                } catch (IllegalAccessException e) {
                    if (logger.isTraceEnabled()) {
                        logger.trace("Attempt to access the property \"" + field.getName() + "\" on object of type \"" + modelObject.getClass().getName() + "\" caused an exception", e);
                    }
                }
                // Start checking the value for special cases. If a case doesn't exist, just put the field name and value in the map
                if (value != null && Class.class.isInstance(value))
                    continue;
                else if (convertToColumnMap.containsKey(field.getName())) {
                    Method method = convertToColumnMap.get(field.getName());
                    try {
                        method.invoke(this, modelObject, map);
                    } catch (IllegalAccessException e) {
                        throw new MappingException(e);
                    } catch (InvocationTargetException e) {
                        throw new MappingException(e);
                    }
                } else if (value != null) {
                    map.put(name, convertToHessian(value, handled, field, includeNonUpdateables));
                } else if (BaseEnum.class.isAssignableFrom(field.getType()) || ReferenceEntity.class.isAssignableFrom(field.getType())) {
                    map.put(name, 0);
                } else if (EnumIntegerMapping.class.isAssignableFrom(field.getType()) || ReferenceEntity.class.isAssignableFrom(field.getType())) {
                    map.put(name, 0);
                } else if (Date.class.isAssignableFrom(field.getType())) {
                    // use 1 to mean an empty date: January 1, 1970 at 12:01am
                    map.put(name, 1L);
                }
            }
            clazz = clazz.getSuperclass();
        }
        if (logger.isTraceEnabled()) {
            logger.trace("Conversion of " + modelObject + " to a Map produced: " + map);
        }
        return map;
    }

    private Object convertToHessian(Object value, Map<Object, Map<String, Object>> handled, Field field, boolean includeNonUpdateables) {
        if (value == null) {
            return value;
        }
        // if the property is a Map, convert the objects in the Map to Map<String,Object>. i'm not sure if this will ever occur
        // i didn't want to make the assumption that the Map object represented by the value variable is a Map<String, Object> so i'm
        // just going with Map<Object, Object>.
        if (Map.class.isInstance(value)) {
            Map<Object, Map<String, Object>> valueMap = new HashMap<Object, Map<String, Object>>();
            for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet()) {
                if (handled.containsKey(entry.getValue())) {
                    valueMap.put(entry.getKey(), handled.get(entry.getValue()));
                } else {
                    valueMap.put(entry.getKey(), convertToMap(entry.getValue(), handled, includeNonUpdateables));
                }
            }
            value = valueMap;
        }
        // if the property is a List, convert the objects in the list to Map<String, Object> as needed
        else if (List.class.isInstance(value)) {
            if (field != null) {
                final Class<?> fieldType = getFieldType(field);
                if (fieldType != null) {
                    if (Boolean.class.isAssignableFrom(fieldType) || Number.class.isAssignableFrom(fieldType) || String.class.isAssignableFrom(fieldType)
                            || Character.class.isAssignableFrom(fieldType) || Date.class.isAssignableFrom(fieldType) || TimeZone.class.isAssignableFrom(fieldType) || fieldType.isEnum())
                        value = convertSimpleList((List<?>) value, field, handled, includeNonUpdateables);
                }
            } else {
                value = convertList((List<?>) value, handled, includeNonUpdateables);
            }
        }
        // if the property is an array, convert the array to a list
        else if (value.getClass().isArray()) {
            final ArrayList<Object> list = new ArrayList<Object>();
            for (int i = 0; i < Array.getLength(value); i++)
                list.add(convertToHessian(Array.get(value, i), handled, field, includeNonUpdateables));
            value = list;
        }
        // if the field type is Date, convert to long seconds
        else if (Date.class.isInstance(value)) {
            value = (long) (((Date) value).getTime() / 1000l);
        }
        // if the field type is TimeZone, convert to string
        else if (TimeZone.class.isInstance(value)) {
            value = ((TimeZone) value).getID();
        }
        // if the field type is Boolean, convert to integer
        else if (Boolean.class.isInstance(value)) {
            value = ((Boolean) value) ? 1 : 0;
        } else if (ReferenceEntity.class.isInstance(value)) {
            value = ((ReferenceEntity) value).retrieveID();
        } else if (Locale.class.isInstance(value)) {
            value = ((Locale) value).toString();
        } else if (BaseEnum.class.isInstance(value)) {
            value = ((BaseEnum) value).getCode();
        } else if (EnumIntegerMapping.class.isInstance(value)) {
            value = ((EnumIntegerMapping) value).getCode();
        }
        // if the property is not a standardProperty it must be some kind of bean/pojo/object. convert the property to a map
        else if (!isStandardProperty(value)) {
            if (handled.containsKey(value)) {
                value = handled.get(value);
            } else {
                value = convertToMap(value, handled, includeNonUpdateables);
            }
        }
        // if we have made it this far, the value must be a String or a primitive type. Just put it in the map.
        return value;
    }

//    public Integer[] convertEnumList(List<?> list) {
//        if (list == null)
//            return null;
//        if (list.size() == 0)
//            return null;
//        Integer[] intArray = new Integer[list.size()];
//        int i = 0;
//        for (Object e : list) {
//            intArray[i] = ((BaseEnum) e).getCode();
//            i++;
//        }
//        return intArray;
//    }

    public List<Map<String, Object>> convertList(List<?> list) {
        final Map<Object, Map<String, Object>> handled = new HashMap<Object, Map<String, Object>>();
        return convertList(list, handled, false);
    }

    protected List<Map<String, Object>> convertList(List<?> list, Map<Object, Map<String, Object>> handled, boolean includeNonUpdateables) {
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (Object o : list) {
            returnList.add(convertToMap(o, handled, includeNonUpdateables));
        }
        return returnList;
    }

    protected List<Object> convertSimpleList(List<?> list, Field field, Map<Object, Map<String, Object>> handled, boolean includeNonUpdateables) {
        List<Object> returnList = new ArrayList<Object>();
        for (Object o : list) {
            returnList.add(convertToHessian(o, handled, field, includeNonUpdateables));
        }
        return returnList;
    }

    protected static boolean isStandardProperty(Object o) {
        if (Number.class.isInstance(o))
            return true;
        if (Character.class.isInstance(o))
            return true;
        if (String.class.isInstance(o))
            return true;
        return false;
    }

    protected static List<Field> getAllFields(Class<?> type) {
        List<Field> fieldList = new ArrayList<Field>();
        Class<?> clazz = type;
        while (clazz != null) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    protected static Field getField(String fieldName, Class<?> type) throws NoSuchFieldException {
        return getField(fieldName, getAllFields(type));
    }

    protected static Field getField(String fieldName, List<Field> fieldList) throws NoSuchFieldException {
        for (Field field : fieldList) {
            if (field.getName().equals(fieldName))
                return field;
        }
        throw new NoSuchFieldException();
    }
}
