package com.inthinc.pro.backing.dao;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.inthinc.pro.backing.dao.argmapper.ArgumentMapper;
import com.inthinc.pro.backing.dao.mapper.BaseUtilMapper;
import com.inthinc.pro.backing.dao.model.DaoMethod;

public class DAOHandler implements ApplicationContextAware {

    public DAOHandler() {}

    public Object invoke(DaoMethod daoMethod, List<Class<?>> paramTypes, Object[] args) throws Exception {

        Object[] methodArgs = args;
        if (daoMethod.getUseMapper())
            methodArgs = convertArgs(daoMethod, paramTypes, args);
        Object t = null;

        if (!daoMethod.getDaoID().isEmpty()) {
            t = applicationContext.getBean(daoMethod.getDaoID());
        }
        String daoMethodName = daoMethod.getDaoMethod().isEmpty() ? daoMethod.getCrudType().getMethodName() : daoMethod.getDaoMethod();
        Method[] allMethods = t.getClass().getMethods();
        for (Method m : allMethods) {
            String mname = m.getName();
            if (!mname.equals(daoMethodName))
                continue;

            if (m.getParameterTypes().length != methodArgs.length) {
                continue;
            }
            
            // special case
            if (daoMethod.getDaoID().equals("eventDAO") && daoMethodName.equals("findByID")) {
                if (!m.getParameterTypes()[0].equals(methodArgs[0].getClass())) {
                    continue;
                }
            }

            m.setAccessible(true);
            Object obj = m.invoke(t, methodArgs);
            if (isStandardType(obj)) {
                return getReturnValueMap(daoMethod.getDaoReturnValueName(), obj);
            }
            if (!daoMethod.getUseMapper() || daoMethod.getMapperClass() == null) {
                return obj;
            }

            BaseUtilMapper mapper = (BaseUtilMapper) daoMethod.getMapperClass().newInstance();
            if (List.class.isInstance(obj)) {
                List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

                for (Object o : (List<?>) obj) {
                    returnList.add(cleanMap(mapper.convertToMap(o, true)));
                }
                return returnList;
            }

            return cleanMap(mapper.convertToMap(obj, true));

        }
        return null;

    }

    private boolean checkParamTypes(String methodName, Class<?>[] parameterTypes, Object[] methodArgs) {
        if (parameterTypes.length != methodArgs.length) {
            return false;
        }

        for (Class<?> paramType : parameterTypes) {
            if (paramType.isAssignableFrom(methodArgs.getClass())) {
                return false;
            }
        }
        return true;
    }

    private Map<String, Object> cleanMap(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        String[] removeKeys = { "serialVersionUID" };

        for (String removeKey : removeKeys) {
            map.remove(removeKey);
        }

        return map;
    }

    private boolean isStandardType(Object o) {
        if (Number.class.isInstance(o))
            return true;
        if (Character.class.isInstance(o))
            return true;
        if (String.class.isInstance(o))
            return true;
        return false;
    }

    private Map<String, Object> getReturnValueMap(String returnValueName, Object obj) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(returnValueName, obj);
        return returnMap;
    }

    private Object[] convertArgs(DaoMethod daoMethod, List<Class<?>> paramTypes, Object[] args) throws Exception {
        ArgumentMapper argumentMapper = (ArgumentMapper) daoMethod.getDaoParamMapper().newInstance();

        return argumentMapper.convertArgs(daoMethod, paramTypes, args);
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

}
