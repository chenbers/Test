package com.inthinc.pro.notegen;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.EventAttrEnum;

public abstract class PackageNote {
    
    public abstract byte[] packageNote(Event event, Integer noteTypeID);
    
    
    public void longToByte(ByteArrayOutputStream baos, Long toAdd, int numOfBytes){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        byte[] array = null;
        
        try {
            dos.writeLong(toAdd);
            dos.flush();
            array = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i=array.length-numOfBytes; i<array.length;i++){
            baos.write(array[i]);
        }
    }
    
    public void longToByte(ByteArrayOutputStream baos, Integer toAdd, int numOfBytes){
        longToByte(baos, toAdd.longValue(), numOfBytes);
    }
    
    public int concatenateTwoInts(int one, int two){
        int value = (int)(  one << 4)  & 0xF0 | two & 0x0F;
        return value;
    }

    public int encodeLat(double lat){
        return (int)( ((90.0 - lat ) / 180.0) * 0x00FFFFFF );
    }
    
    
    public int encodeLng(double lng){
        return (int)( (lng / 360.0) * 0x00FFFFFF );
    }
    

    private void encodeAttribute(ByteArrayOutputStream baos, EventAttr key, Object object) {
        try {
            if (object instanceof EventAttrEnum) {
                longToByte(baos, ((EventAttrEnum)object).mapToInteger(), key.getSize());
            }
            else if (object instanceof Number){
                if (object instanceof Integer){
                    longToByte(baos, ((Integer)object).longValue(), key.getSize());
                } else if (object instanceof Long){
                    longToByte(baos, (Long)object, key.getSize());    
                }
            } else if (object instanceof String){
                byte[] str = ((String)object).getBytes();
                int size = key.getSize();
                baos.write(str, 0, str.length);
                if (key.isZeroTerminated()){
                    baos.write(0x0);
                } else {
                    size -= str.length;
                    for (int i=0;i<size;i++){
                        baos.write(0x0);
                    }
                }
            }
        } catch (NullPointerException e){
            
        }
    }
    
    public void encodeAttributes(ByteArrayOutputStream baos, Event event) {
        if (event.getAttrMap() != null) 
            for (Entry<Object, Object> entry : event.getAttrMap().entrySet()) {
                EventAttr attr = EventAttr.findByNameCode(entry.getKey().toString());
                encodeAttribute(baos, attr, entry.getValue());
            }
    
        // go through fields of class also
        List<Field> fieldList = getAllFields(event.getClass());
        for (Field field : fieldList) {
            if (field.isAnnotationPresent(EventAttrID.class)) {
                EventAttrID attributeID = field.getAnnotation(EventAttrID.class);
                
                EventAttr attr = EventAttr.valueOf(attributeID.name());
                try {
                    encodeAttribute(baos, attr, PropertyUtils.getProperty(event, field.getName()));
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

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



}
