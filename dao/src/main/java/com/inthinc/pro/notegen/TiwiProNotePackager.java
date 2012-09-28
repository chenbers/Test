package com.inthinc.pro.notegen;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventAttr;

public class TiwiProNotePackager extends PackageNote {

    @Override
    public byte[] packageNote(Event event, Integer noteTypeID) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        longToByte(bos, noteTypeID, 1);
        longToByte(bos, DateUtil.convertDateToSeconds(event.getTime()), 4);
        longToByte(bos, concatenateTwoInts(event.getHeading() == null ? 0 : event.getHeading(), event.getSats() == null ? 10 : event.getSats()), 1);
        longToByte(bos, (event.getMaprev() == null) ? 1 : event.getMaprev(), 1);
        longToByte(bos, encodeLat(event.getLatitude()), 3);
        longToByte(bos, encodeLng(event.getLongitude()), 3);
        longToByte(bos, event.getSpeed(), 1);
        longToByte(bos, event.getOdometer(), 2);
        
        encodeAttributes(bos, event);
        
        return bos.toByteArray();   

    }

    private void encodeAttributeKey(ByteArrayOutputStream baos, EventAttr key) {
        int keyCode = key.getIndex();
        if (keyCode < Math.pow(2, 1*Byte.SIZE)){
            longToByte(baos, keyCode, 1);    
        } else if (keyCode < Math.pow(2, 2*Byte.SIZE)){
            longToByte(baos, keyCode, 2);   
        } else if (keyCode < Math.pow(2, 3*Byte.SIZE)){
            longToByte(baos, keyCode, 3);   
        }
    }
    
    public void encodeAttributes(ByteArrayOutputStream baos, Event event) {
        Map<EventAttr, Boolean> processedAttr = new HashMap<EventAttr, Boolean>();
        if (event.getAttrMap() != null) 
            for (Entry<Object, Object> entry : event.getAttrMap().entrySet()) {
                EventAttr attr = EventAttr.findByNameCode(entry.getKey().toString());
                encodeAttributeKey(baos, attr);
                encodeAttribute(baos, attr, entry.getValue());
                processedAttr.put(attr, Boolean.TRUE);
            }
    
        // go through fields of class also
        List<Field> fieldList = getAllFields(event.getClass());
        for (Field field : fieldList) {
            if (field.isAnnotationPresent(EventAttrID.class)) {
                EventAttrID attributeID = field.getAnnotation(EventAttrID.class);
                
                EventAttr attr = EventAttr.valueOf(attributeID.name());
                if (processedAttr.containsKey(attr))
                    continue;
                try {
                    encodeAttributeKey(baos, attr);
                    encodeAttribute(baos, attr, PropertyUtils.getProperty(event, field.getName()));
                    processedAttr.put(attr, Boolean.TRUE);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    

}
