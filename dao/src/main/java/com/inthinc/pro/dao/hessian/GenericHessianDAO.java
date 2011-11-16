package com.inthinc.pro.dao.hessian;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.SiloAware;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.dao.annotations.SimpleName;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.exceptions.HessianException;
import com.inthinc.pro.dao.hessian.mapper.Mapper;
import com.inthinc.pro.dao.hessian.mapper.SimpleMapper;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.app.Silos;
import com.inthinc.pro.model.silo.SiloDef;
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class GenericHessianDAO<T, ID> implements GenericDAO<T, ID>, Serializable, SiloAware {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(GenericHessianDAO.class);
    private SiloService siloService;
    private Class<T> modelClass;
    private Class<ID> idClass;
    private Method findMethod;
    private Method deleteMethod;
    private Method createMethod;
    private Method updateMethod;
    private Mapper mapper;

    private Map<String, Method> convertToFieldMap = new HashMap<String, Method>();
    private Map<String, Method> convertToColumnMap = new HashMap<String, Method>();
    private Map<String, String> columnMap = new HashMap<String, String>();

    public GenericHessianDAO() {
        this.modelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.idClass = (Class<ID>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        mapper = new SimpleMapper();
        populateConverterMaps();
        populateColumnMap();
        populateCRUDMethods();
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

    private void populateColumnMap() {
        Class<?> clazz = modelClass;

        while (clazz != null) {
            for (Field field : modelClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    columnMap.put(field.getAnnotation(Column.class).name(), field.getName());
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    private void populateCRUDMethods() {
        String className = null;
        if (modelClass.isAnnotationPresent(SimpleName.class)) {
            className = modelClass.getAnnotation(SimpleName.class).simpleName();
        } else {
            className = modelClass.getSimpleName();
        }
        String findMethodString = "get" + className;
        String deleteMethodString = "delete" + className;
        String createMethodString = "create" + className;
        String updateMethodString = "update" + className;

        // Try to find the crud methods. If a method doesn't exist, don't worry about it and continue
        try {
            findMethod = SiloService.class.getDeclaredMethod(findMethodString, Integer.class);
        } catch (NoSuchMethodException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("The finder method \"" + findMethodString + "\" does not exist on service interface: " + SiloService.class.getName());
            }
        }

        try {
            deleteMethod = SiloService.class.getDeclaredMethod(deleteMethodString, Integer.class);
        } catch (NoSuchMethodException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("The delete method \"" + deleteMethodString + "\" does not exist on service interface: " + SiloService.class.getName());
            }
        }

        try {
            createMethod = SiloService.class.getDeclaredMethod(createMethodString, Integer.class, Map.class);
        } catch (NoSuchMethodException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("The create method \"" + createMethodString + "\" does not exist on service interface: " + SiloService.class.getName());
            }
        }

        try {
            updateMethod = SiloService.class.getDeclaredMethod(updateMethodString, Integer.class, Map.class);
        } catch (NoSuchMethodException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("The update method \"" + updateMethodString + "\" does not exist on service interface: " + SiloService.class.getName());
            }
        }

    }

    public SiloService getSiloService() {
        return siloService;
    }

    public void setSiloService(SiloService siloService) {
        this.siloService = siloService;
    }

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Integer deleteByID(ID id) {
        if (deleteMethod == null)
            throw new NotImplementedException();

        try {
            return getChangedCount((Map) deleteMethod.invoke(getSiloService(), id));
        } catch (IllegalAccessException e) {
            if (logger.isDebugEnabled())
                logger.debug(e);
            return 0;
        } catch (InvocationTargetException e) {
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
    public T findByID(ID id) {
        if (findMethod == null)
            throw new NotImplementedException();

        if (id == null)
            return null;

        T modelObject = null;
        try {
            Object returnObject = null;
            Class<?> returnType = findMethod.getReturnType();

            if (Map.class.isAssignableFrom(returnType)) {
                returnObject = getMapper().convertToModelObject((Map) findMethod.invoke(getSiloService(), id), modelClass);
            } else if (List.class.isAssignableFrom(returnType)) {
                returnObject = getMapper().convertToModelObject((List) findMethod.invoke(getSiloService(), id), modelClass);
            }

            if (modelClass.isInstance(returnObject)) {
                modelObject = modelClass.cast(returnObject);
            }
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
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
    public ID create(ID id, T entity) {
        if (createMethod == null)
            throw new NotImplementedException();

        try {
            return getReturnKey((Map) createMethod.invoke(getSiloService(), id, getMapper().convertToMap(entity)));
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
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
    public Integer update(T entity) {
        if (updateMethod == null)
            throw new NotImplementedException();

        try {
            ID entityID = getID(entity);
            return getChangedCount((Map) updateMethod.invoke(getSiloService(), entityID, getMapper().convertToMap(entity)));
        } catch (InvocationTargetException e) {
            if (e.getTargetException() instanceof EmptyResultSetException)
                return null;
            else if (e.getTargetException() instanceof HessianException)
                throw (HessianException) e.getTargetException();

            if (logger.isDebugEnabled())
                logger.debug("Error invoking the DAO update method of " + this.getClass().getName(), e);
            return 0;
        } catch (Exception e) {
            if (logger.isDebugEnabled())
                logger.debug(e);
            return 0;
        }
    }

    protected ID getID(T entity) throws IllegalArgumentException, IntrospectionException, IllegalAccessException, InvocationTargetException {
        ID id = null;
        for (Field f : modelClass.getDeclaredFields()) {
            if (f.isAnnotationPresent(com.inthinc.pro.dao.annotations.ID.class)) {
                try {
                    id = (ID) f.get(entity);
                } catch (Exception e) {
                    // if (logger.isDebugEnabled())
                    // logger.debug(e);

                    for (PropertyDescriptor property : Introspector.getBeanInfo(modelClass).getPropertyDescriptors())
                        if (property.getName().equals(f.getName())) {
                            id = (ID) property.getReadMethod().invoke(entity, new Object[0]);
                            break;
                        }
                }
                break;
            }
        }
        return id;
    }

    protected ID getReturnKey(Map<String, Object> map) {
        String name = null;
        for (Field f : modelClass.getDeclaredFields()) {
            if (f.isAnnotationPresent(com.inthinc.pro.dao.annotations.ID.class)) {
                if (f.isAnnotationPresent(Column.class)) {
                    Column column = f.getAnnotation(Column.class);
                    name = column.name();
                } else {
                    name = f.getName();
                }
                break;
            }
        }

        return idClass.cast(map.get(name));
    }

    protected ID getReturnKey(Map<String, Object> map, Class<?> clzz) {
        String name = null;
        for (Field f : clzz.getDeclaredFields()) {
            if (f.isAnnotationPresent(com.inthinc.pro.dao.annotations.ID.class)) {
                if (f.isAnnotationPresent(Column.class)) {
                    Column column = f.getAnnotation(Column.class);
                    name = column.name();
                } else {
                    name = f.getName();
                }
                break;
            }
        }

        return idClass.cast(map.get(name));
    }

    protected Integer getChangedCount(Map<String, Object> map) {
        return (Integer) map.get("count");
    }

    protected Integer getCount(Map<String, Object> map) {
        return (Integer) map.get("count");
    }

    protected Integer getCentralId(Map<String, Object> map) {
        return (Integer) map.get("id");
    }

    @Override
    public void switchSilo(Integer siloID)
    {
        SiloDef siloDef = Silos.getSiloById(siloID);
        
        if (siloDef == null || siloDef.getProxyHost() == null || siloDef.getProxyPort() == null) {
            throw new ProDAOException("Switch Silo Failed, Cannot find siloID: " + siloID);
        }
        SiloServiceCreator ssc = new SiloServiceCreator(siloDef.getProxyHost(), siloDef.getProxyPort());
        setSiloService(ssc.getService());

    }
}
