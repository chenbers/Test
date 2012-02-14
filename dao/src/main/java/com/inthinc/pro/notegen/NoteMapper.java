package com.inthinc.pro.notegen;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.model.EnumIntegerMapping;
import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.model.BaseEnum;
import com.inthinc.pro.model.ReferenceEntity;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.NoteType;

public class NoteMapper extends EventHessianMapper {
    private static String TYPE = "type";

    @Override
    public <E> E convertToModelObject(Map<String, Object> map, Class<E> type) {
        
        
        Map<String, Object> convertedMap = convertMap(map);
        return super.convertToModelObject(convertedMap, type);
    }

    private Map<String, Object> convertMap(Map<String, Object> eventMap) {
//      Integer type = Integer.valueOf(eventMap.get(TYPE).toString());
        String noteTypeStr = eventMap.get(TYPE).toString();
        int i1 = noteTypeStr.indexOf("(");
        int i2 = noteTypeStr.indexOf(")");
        Integer type = null;
        if (i1 != -1 && i2 != -1)
            type = Integer.valueOf(noteTypeStr.substring(i1+1, i2));
        else type = Integer.valueOf(noteTypeStr);
        
        eventMap.put(TYPE, type);
        NoteType noteType = NoteType.valueOf(type);
System.out.println("noteType: " + noteType);        
//        if (noteType != null && noteType.getEventClass()!=null)
//        {
//            List<Field> fieldList = getAllFields(noteType.getEventClass());
//
//System.out.println("class: " + noteType.getEventClass().getName() + " field cnt: " + fieldList.size());        
//            for (Field field : fieldList) {
//                
//                String fieldName = field.getName();
//                if (field.isAnnotationPresent(Column.class)) {
//                    Column column = field.getAnnotation(Column.class);
//                    fieldName = column.name();
//                }
//                if (fieldName.equals(TYPE))
//                    continue;
//                
//                if (eventMap.containsKey(fieldName)) {
//                    
//                    System.out.println(field.getName() + " " + field.getType() + " " + eventMap.get(fieldName));
//                    
//// TO do:map it (Only types in events)
//                    try {
//                        Object obj = convertProperty(field.getType(), field.getName(), eventMap.get(fieldName), fieldList);
//                        eventMap.put(fieldName, obj);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    
//                }
//            }
//        }
        return eventMap;
    }

    @Override
    protected Object convertProperty(Class<?> propertyType, String key, Object value, List<Field> fieldList) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
        NoSuchFieldException, InstantiationException {
        
        if (propertyType == null || value == null) 
            return null;

        if (key.equals("attrMap") && value instanceof String) {
            return convertEventAttributeMap(value);
        }
        
        if (propertyType.equals(Date.class) && value instanceof String) 
            return convertTime(value.toString());

        if (propertyType.equals(Boolean.class) && value instanceof String) {
            Integer iValue = Integer.valueOf(value.toString());
            return iValue.equals(Integer.valueOf(0)) ? Boolean.FALSE : Boolean.TRUE;
        }

        if (propertyType.equals(Integer.class) && value instanceof String) {
            return Integer.valueOf(value.toString());
        }
        
        if (propertyType.equals(Double.class) && value instanceof String) {
            return Double.valueOf(value.toString());
        }
        
        
        if ((ReferenceEntity.class.isAssignableFrom(propertyType) ||
                BaseEnum.class.isAssignableFrom(propertyType) ||
                EnumIntegerMapping.class.isAssignableFrom(propertyType) ||
                Enum.class.isAssignableFrom(propertyType))
                && value instanceof String) {
            Integer iValue = Integer.valueOf(value.toString());
            Method valueOf = propertyType.getMethod("valueOf", Integer.class);
            if (valueOf != null)
                return valueOf.invoke(null, iValue);
            return iValue;
        }

        return super.convertProperty(propertyType, key, value, fieldList);
    }

    private Object convertEventAttributeMap(Object obj) {
        Map<Object, Object> attrMap = new HashMap<Object, Object>();
        
        String value = obj.toString();
        
        String[] values = value.substring(1, value.length()-1).split(",");
        for (int i = 0; i < values.length; i++) {
            
            int i1 = values[i].indexOf("(");
            int i2 = values[i].indexOf(")");
            Integer type = null;
            if (i1 != -1 && i2 != -1)
                type = Integer.valueOf(values[i].substring(i1+1, i2));
            else continue;
            
            
            int i3 = values[i].indexOf("=");
            if (i3 == -1)
                continue;
            String val = values[i].substring(i3+1).trim();
            
            attrMap.put(EventAttr.valueOf(type), Integer.valueOf(val));
        }
        
        return attrMap;
    }

    private static DateTimeFormatter NOTE_DATE_FORMAT = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
    private Date convertTime(String dateString) {
        try {
            return NOTE_DATE_FORMAT.parseDateTime(dateString).toDate();
        }
        catch (Exception e) {
            return null;
        }
    }

    
}
