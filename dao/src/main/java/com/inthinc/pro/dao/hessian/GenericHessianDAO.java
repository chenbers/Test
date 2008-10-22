package com.inthinc.pro.dao.hessian;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.Converter;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.HessianException;
import com.inthinc.pro.dao.hessian.exceptions.MappingException;
import com.inthinc.pro.dao.service.DAOService;
import com.inthinc.pro.dao.service.ServiceCreator;
import com.inthinc.pro.dao.service.SiloService;

public abstract class GenericHessianDAO<T, ID, S extends DAOService> implements GenericDAO<T, ID>
{
  private static final Logger logger = Logger.getLogger(GenericHessianDAO.class);
  private ServiceCreator<SiloService> siloServiceCreator;
  private S service;
  private ServiceCreator<S> serviceCreator;
  private Class<T> modelClass;
  private Class<ID> idClass;
  private Class<S> serviceClass;
  private Method findMethod;
  private Method deleteMethod;
  private Method createMethod;
  private Method updateMethod;

  private Map<String, Method> converterMap = new HashMap<String, Method>();
  private Map<String, String> columnMap = new HashMap<String, String>();

  @SuppressWarnings("unchecked")
  public GenericHessianDAO()
  {
logger.debug("GenericHessianDAO constructor");      
    this.modelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    this.idClass = (Class<ID>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    this.serviceClass = (Class<S>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
    populateConverterMethods();
    populateColumnMap();
    populateCRUDMethods();
  }

  private void populateConverterMethods()
  {
    for (Method method : this.getClass().getDeclaredMethods())
    {
      if (method.isAnnotationPresent(Converter.class))
      {
        converterMap.put(method.getAnnotation(Converter.class).columnName(), method);
      }
    }

  }

  private void populateColumnMap()
  {
    for (Field field : modelClass.getDeclaredFields())
    {
      if (field.isAnnotationPresent(Column.class))
      {
        columnMap.put(field.getAnnotation(Column.class).name(), field.getName());
      }
    }
  }

  private void populateCRUDMethods()
  {
    String className = modelClass.getSimpleName();
    String findMethodString = "get" + className;
    String deleteMethodString = "delete" + className;
    String createMethodString = "create" + className;
    String updateMethodString = "update" + className;

    // Try to find the crud methods. If a method doesn't exist, don't worry about it and continue
    try
    {
      findMethod = SiloService.class.getDeclaredMethod(findMethodString, Integer.class);
    }
    catch (NoSuchMethodException e)
    {
      if (logger.isDebugEnabled())
      {
        logger.debug("The finder method \"" + findMethodString + "\" does not exist for on service interface: " + SiloService.class.getName(), e);
      }
    }

    try
    {
      deleteMethod = SiloService.class.getDeclaredMethod(deleteMethodString, Integer.class);
    }
    catch (NoSuchMethodException e)
    {
      if (logger.isDebugEnabled())
      {
        logger.debug("The delete method \"" + deleteMethodString + "\" does not exist for on service interface: " + SiloService.class.getName(), e);
      }
    }

    try
    {
      createMethod = SiloService.class.getDeclaredMethod(createMethodString, Integer.class, Map.class);
    }
    catch (NoSuchMethodException e)
    {
      if (logger.isDebugEnabled())
      {
        logger.debug("The create method \"" + createMethodString + "\" does not exist for on service interface: " + SiloService.class.getName(), e);
      }
    }

    try
    {
      updateMethod = SiloService.class.getDeclaredMethod(updateMethodString, Integer.class, Map.class);
    }
    catch (NoSuchMethodException e)
    {
      if (logger.isDebugEnabled())
      {
        logger.debug("The update method \"" + updateMethodString + "\" does not exist for on service interface: " + SiloService.class.getName(), e);
      }
    }

  }

  public SiloService getSiloService()
  {
    return siloServiceCreator.getService();
  }

  public ServiceCreator<SiloService> getSiloServiceCreator()
  {
    return siloServiceCreator;
  }

  public void setSiloServiceCreator(ServiceCreator<SiloService> siloServiceCreator)
  {
    this.siloServiceCreator = siloServiceCreator;
  }

  public S getService()
  {
    return serviceCreator.getService();
  }

  public ServiceCreator<S> getServiceCreator()
  {
    return serviceCreator;
  }

  public void setServiceCreator(ServiceCreator<S> serviceCreator)
  {
    this.serviceCreator = serviceCreator;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Integer deleteByID(ID id)
  {
    if (deleteMethod == null)
      throw new NotImplementedException();

    try
    {
      return getChangedCount((Map) deleteMethod.invoke(getSiloService(), id));
    }
    catch (IllegalAccessException e)
    {
      if (logger.isDebugEnabled())
        logger.debug(e);
      return 0;
    }
    catch (InvocationTargetException e)
    {
      if (e.getTargetException() instanceof EmptyResultSetException)
        return null;
      else if (e.getTargetException() instanceof HessianException)
        throw (HessianException) e.getTargetException();

      if (logger.isDebugEnabled())
        logger.debug("Error invoking the DAO create method of " + this.getClass().getName(), e);
      return 0;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public T findByID(ID id)
  {
    if (findMethod == null)
      throw new NotImplementedException();

    T modelObject = null;
    try
    {
      Object returnObject = null;
      Class<?> returnType = findMethod.getReturnType();

      if (Map.class.isAssignableFrom(returnType))
      {
        returnObject = convertToModelObject((Map) findMethod.invoke(getSiloService(), id));
      }
      else if (List.class.isAssignableFrom(returnType))
      {
        returnObject = convertToModelObject((List) findMethod.invoke(getSiloService(), id));
      }

      if (modelClass.isInstance(returnObject))
      {
        modelObject = modelClass.cast(returnObject);
      }
    }
    catch (IllegalAccessException e)
    {
      return null;
    }
    catch (InvocationTargetException e)
    {
      if (e.getTargetException() instanceof EmptyResultSetException)
        return null;
      else if (e.getTargetException() instanceof HessianException)
        throw (HessianException) e.getTargetException();

      if (logger.isDebugEnabled())
        logger.debug("Error invoking the DAO find method of " + this.getClass().getName(), e);
      return null;
    }

    return modelObject;
  }

  @Override
  @SuppressWarnings("unchecked")
  public ID create(ID id, T entity)
  {
    if (createMethod == null)
      throw new NotImplementedException();

    try
    {
      return getReturnKey((Map) createMethod.invoke(getSiloService(), id));
    }
    catch (IllegalAccessException e)
    {
      return null;
    }
    catch (InvocationTargetException e)
    {
      if (e.getTargetException() instanceof EmptyResultSetException)
        return null;
      else if (e.getTargetException() instanceof HessianException)
        throw (HessianException) e.getTargetException();

      if (logger.isDebugEnabled())
        logger.debug("Error invoking the DAO create method of " + this.getClass().getName(), e);
      return null;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public Integer update(T entity)
  {
    if (updateMethod == null)
      throw new NotImplementedException();

    try
    {
      return getChangedCount((Map) updateMethod.invoke(getSiloService(), entity));
    }
    catch (IllegalAccessException e)
    {
      if (logger.isDebugEnabled())
        logger.debug(e);
      return 0;
    }
    catch (InvocationTargetException e)
    {
      if (e.getTargetException() instanceof EmptyResultSetException)
        return null;
      else if (e.getTargetException() instanceof HessianException)
        throw (HessianException) e.getTargetException();

      if (logger.isDebugEnabled())
        logger.debug("Error invoking the DAO update method of " + this.getClass().getName(), e);
      return 0;
    }
  }

  protected ID getReturnKey(Map<String, Object> map)
  {
    String name = null;
    for (Field f : modelClass.getDeclaredFields())
    {
      if (f.isAnnotationPresent(com.inthinc.pro.dao.annotations.ID.class))
      {
        if (f.isAnnotationPresent(Column.class))
        {
          Column column = f.getAnnotation(Column.class);
          name = column.name();
        }
        else
        {
          name = f.getName();
        }
        break;
      }
    }

    return idClass.cast(map.get(name));
  }

  protected Integer getChangedCount(Map<String, Object> map)
  {
    return (Integer) map.get("count");
  }

  protected T convertToModelObject(Map<String, Object> map)
  {

    T modelObject;
    try
    {
      modelObject = modelClass.newInstance();
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

  private <E> E convertToModelObject(Map<String, Object> map, Class<E> type)
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

    for (Map.Entry<String, Object> entry : map.entrySet())
    {
      String columnName = entry.getKey();
      Object value = entry.getValue();
      String key;

      if (columnMap.containsKey(columnName))
        key = columnMap.get(columnName);
      else
        key = columnName;

      // Check to see if the key/value pair in the map is associated with a custom converter. If so, invoke the converter. If not, do normal mapping from map key/value to field in
      // modelObject
      if (converterMap.containsKey(columnName))
      {
        Method method = converterMap.get(columnName);
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
      }
      else if (value instanceof Map)
      {
        try
        {
          Field field = modelObject.getClass().getDeclaredField(key);
          Class<?> fieldType = field.getType();
          setProperty(modelObject, key, convertToModelObject((Map<String, Object>) value, fieldType));
        }
        catch (NoSuchFieldException e)
        {
          // If the field doesn't exist, continue
          if (logger.isDebugEnabled())
          {
            logger.debug("The field \"" + key + "\" does not exist for class: " + value.getClass().getName(), e);
          }

        }

      }
      else if (value instanceof List)
      {
        try
        {
          Field field = modelObject.getClass().getDeclaredField(key);
          Column column = field.getAnnotation(Column.class);
          setProperty(modelObject, key, convertToModelObject((List<Map<String, Object>>) value, column.type()));
        }
        catch (NoSuchFieldException e)
        {
          // If the field doesn't exist, continue
          if (logger.isDebugEnabled())
          {
            logger.debug("The field \"" + key + "\" does not exist for class: " + value.getClass().getName(), e);
          }
        }
      }
      else
      {
        setProperty(modelObject, key, value);
      }

    }

    return modelObject;
  }

  protected List<T> convertToModelObject(List<Map<String, Object>> list)
  {
    return convertToModelObject(list, modelClass);
  }

  private <E> List<E> convertToModelObject(List<Map<String, Object>> list, Class<E> type)
  {
    List<E> returnList = new ArrayList<E>();
    for (Map<String, Object> map : list)
    {
      returnList.add(convertToModelObject(map, type));
    }
    return returnList;
  }

  protected Map<String, Object> convertToMap(Object entity)
  {
    Map<String, Object> map = new HashMap<String, Object>();
    List<Field> fields = Arrays.asList(entity.getClass().getDeclaredFields());
    fields.addAll(Arrays.asList(entity.getClass().getFields()));
    

    for (PropertyDescriptor property : PropertyUtils.getPropertyDescriptors(entity))
    {
      // if the field/property is marked as updateable=false, ignore it
      try
      {

        Field field = entity.getClass().getDeclaredField(property.getName());
        if (field.isAnnotationPresent(Column.class))
        {
          if (!field.getAnnotation(Column.class).updateable())
            continue;
        }
      }
      catch (NoSuchFieldException e)
      {
        if (logger.isDebugEnabled())
        {
          logger.debug("The property \"" + property.getName() + "\" does not exist for object of type \"" + entity.getClass().getName() + "\"", e);
        }
        continue;
      }

      try
      {
        String name = property.getName();
        Method readMethod = property.getReadMethod();
        Object value = readMethod.invoke(entity);
        if(value == null)
          continue;
        Class<?> valueType = value.getClass();

        // if the property is a Map, convert the objects in the Map to Map<String,Object>. i'm not sure if this will ever occur
        // i didn't want to make the assumption that the Map object represented by the value variable is a Map<String, Object> so i'm
        // just going with Map<Object, Object>.
        if (Map.class == valueType)
        {
          Map<Object, Map<String, Object>> valueMap = new HashMap<Object, Map<String, Object>>();
          for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) value).entrySet())
          {
            valueMap.put(entry.getKey(), convertToMap(entry.getValue()));
          }
          map.put(name, valueMap);
        }
        // if the property is a List, convert the objects in the list to Map<String, Object>
        else if (List.class == valueType)
        {
          map.put(name, convertList((List<?>) value));
        }
        // if the property is not a standardProperty it must be some kind of bean/pojo/object. convert the property to a map
        else if (!isStandardProperty(valueType))
        {
          map.put(name, convertToMap(value));
        }
        // if we have made it this far, the value must be a String or a primitive type. Just put it in the map.
        else
        {
          map.put(name, value);
        }
      }
      catch (IllegalAccessException e)
      {
        if (logger.isDebugEnabled())
        {
          logger.debug("Attempt to access the property \"" + property.getName() + "\" on object of type \"" + entity.getClass().getName() + "\" caused an exception", e);
        }
      }
      catch (InvocationTargetException e)
      {
        if (logger.isDebugEnabled())
        {
          logger.debug("Attempt to invoke property \"" + property.getName() + "\" on object of type \"" + entity.getClass().getName() + "\" caused an exception", e);
        }
      }
    }

    return map;
  }

  protected List<Map<String, Object>> convertList(List<?> list)
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
      else if (propertyType != null && propertyType.equals(Boolean.class) && value instanceof Integer)
      {
        value = ((Integer)value).equals(Integer.valueOf(0)) ? Boolean.FALSE : Boolean.TRUE;
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
        logger.debug("The property \"" + name + "\" could not be set to the value \"" + value + "\"", e);
      }
    }
    catch (NoSuchMethodException e)
    {
      if (logger.isDebugEnabled())
      {
        logger.debug("The property \"" + name + "\" does not exist for class: " + value.getClass().getName(), e);
      }
    }
  }

  private static boolean isStandardProperty(Class<?> clazz)
  {
    if (clazz.isPrimitive())
      return true;
    if (clazz == Byte.class)
      return true;
    if (clazz == Short.class)
      return true;
    if (clazz == Integer.class)
      return true;
    if (clazz == Long.class)
      return true;
    if (clazz == Float.class)
      return true;
    if (clazz == Double.class)
      return true;
    if (clazz == Character.class)
      return true;
    if (clazz == String.class)
      return true;
    return false;
  }

}
