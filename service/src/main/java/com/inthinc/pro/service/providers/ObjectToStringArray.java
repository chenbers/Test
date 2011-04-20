package com.inthinc.pro.service.providers;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.beans.BeanUtils;

public class ObjectToStringArray {

    public static String[] convertMapToStringArray(Map<String,Object> map){
        List<String> fieldValues = new ArrayList<String>();
        for(Object value: map.values()){
            fieldValues.add(value.toString());
        }
        return fieldValues.toArray(new String[0]);
    }
    @SuppressWarnings("unchecked")
    public static String[] convertObjectToStringArray(Object object, Class type, Type genericType){
        
       if (genericType instanceof ParameterizedType){
           List<String> fieldValues = new ArrayList<String>();
           if(object instanceof List){
                Type listType = ((ParameterizedType)genericType).getActualTypeArguments()[0];
                
                for (Object item : (List) object){
                    fieldValues.addAll(getObjectFields(item,listType.getClass()));
                }
                
            }
            return fieldValues.toArray(new String[0]);
        }
       else {
          return getObjectFields(object, type).toArray(new String[0]);
       }

    }
    
    private static List<String> getObjectFields(Object object, Class type){
        
        List<String> fieldValues = new ArrayList<String>();
        
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods){
           if(isGetter(method, type)){
                try{
                    Object value = method.invoke(object);
                    if(value != null){
                        if (Boolean.class.isAssignableFrom(value.getClass()) || Number.class.isAssignableFrom(value.getClass()) || String.class.isAssignableFrom(value.getClass())
                                || Character.class.isAssignableFrom(value.getClass()) || Date.class.isAssignableFrom(value.getClass()) || TimeZone.class.isAssignableFrom(value.getClass()) || value.getClass().isEnum()){
                            fieldValues.add(value.toString());
                        }
                        else{
                            fieldValues.addAll(getObjectFields(value, value.getClass()));
//                          String[] innerObject = convertObjectToStringArray(value, value.getClass(), ) ;
                       }
                    }
                    else{
                        fieldValues.add("");
                    }
                }
                catch(InvocationTargetException ite){
                    
                }
                catch(IllegalAccessException iae){
                    
                }
                catch(IllegalArgumentException iae){
                    
                }
            }
        }
        return fieldValues;
        
    }
    @SuppressWarnings( "unchecked")
    private static boolean isGetter(Method method, Class type){
        
        try{
            if (method.getName().equals("getClass") || method.isAnnotationPresent(XmlTransient.class)) return false;
            PropertyDescriptor pd = BeanUtils.findPropertyForMethod(method);
            return (pd != null);
        }
        catch(Exception e){
            return false;
        }
    }
}
