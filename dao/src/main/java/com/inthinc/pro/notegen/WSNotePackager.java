package com.inthinc.pro.notegen;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;

import com.inthinc.pro.dao.annotations.event.EventAttrID;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.NoteType;

public class WSNotePackager extends PackageNote{
    public final static int nVersion = 2;
    public final static int duration = 0;

    @Override
    public byte[] packageNote(Event event, Integer noteTypeID) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0);
        longToByte(baos, noteTypeID, 1);
        longToByte(baos, nVersion, 1);
        longToByte(baos, DateUtil.convertDateToSeconds(event.getTime()), 4);
        longToByte(baos, concatenateTwoInts(event.getHeading(), event.getSats()), 1);
        longToByte(baos, encodeLat(event.getLatitude()), 3);
        longToByte(baos, encodeLng(event.getLongitude()), 3);
        longToByte(baos, event.getSpeed(), 1);
        longToByte(baos, event.getOdometer(), 3);
        longToByte(baos, duration, 2);
        
        encodeAttributes(baos, event);

        byte[] temp = baos.toByteArray();
        temp[0] = (byte) (temp.length & 0xFF);
        return temp;
        
    }
    
    
    public void encodeAttributes(ByteArrayOutputStream baos, Event event) {
        if (event.getEventAttrList() == null)
            return;
        List<Field> fieldList = getAllFields(event.getClass());
        
        for (EventAttr eventAttr : event.getEventAttrList()) {
            if (event.getAttrMap() != null && event.getAttrMap().containsKey(eventAttr)) {

                if (eventAttr == EventAttr.DELTA_VS) {
                    Integer deltaX = 0, deltaY = 0, deltaZ = 0;
                    for (Field field : fieldList) {
                        if (field.isAnnotationPresent(EventAttrID.class)) {
                            EventAttrID attributeID = field.getAnnotation(EventAttrID.class);
                            EventAttr attr = EventAttr.valueOf(attributeID.name());
                            try {
                                if (attr == EventAttr.DELTAV_X) 
                                    deltaX = (Integer) PropertyUtils.getProperty(event, field.getName());
                                else if (attr == EventAttr.DELTAV_Y)
                                    deltaY = (Integer) PropertyUtils.getProperty(event, field.getName());
                                else if (attr == EventAttr.DELTAV_Z)
                                    deltaZ = (Integer) PropertyUtils.getProperty(event, field.getName());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Long packedDeltaV = ((-deltaX) + 600l) * 1464100l + // Turn deltaX negative because the DB expects the opposite from waysmarts
                                     ((deltaY) + 600l) * 1210l +
                                     ((deltaZ) + 600l);
                    encodeAttribute(baos, eventAttr, packedDeltaV);
                }
                else {
                    encodeAttribute(baos, eventAttr, event.getAttrMap().get(eventAttr));
                }
            }
            else {
        
                for (Field field : fieldList) {
                    if (field.isAnnotationPresent(EventAttrID.class)) {
                        EventAttrID attributeID = field.getAnnotation(EventAttrID.class);
                        EventAttr attr = EventAttr.valueOf(attributeID.name());
                        if (attr == eventAttr) {
                            try {
                                encodeAttribute(baos, attr, PropertyUtils.getProperty(event, field.getName()));
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
        }

    }
    

}
