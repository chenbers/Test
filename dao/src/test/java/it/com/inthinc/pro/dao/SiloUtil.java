package it.com.inthinc.pro.dao;

import com.inthinc.pro.dao.hessian.exceptions.MappingException;
import com.inthinc.pro.model.AggressiveDrivingEvent;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventAttr;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.SeatBeltEvent;
import com.inthinc.pro.model.SpeedingEvent;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class SiloUtil
{
    static Logger logger = Logger.getLogger(SiloUtil.class);
    static String KEY_NOTE_ID = "noteID";
    static String KEY_NOTE_TYPE = "type";
    static String KEY_ATTR_MAP = "attrMap";

    @SuppressWarnings("unchecked")
    public static <T extends Event> Map<Long, Event> getEventMap(List<Map<String, Object>> serviceDataList) 
    {

        Map<Long, Event> returnMap = new HashMap<Long, Event>();

        for (Map<String, Object> dataMap : serviceDataList)
        {

            Long id = (Long) dataMap.get(KEY_NOTE_ID);
            Integer noteType = (Integer) dataMap.get(KEY_NOTE_TYPE);
            Class objectType = EventMapper.getEventType(noteType);

            Event event = createEventFromDataMap(dataMap, objectType);
            returnMap.put(id, event);

        }

        return returnMap;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Event> List<Event> getEventList(List<Map<String, Object>> serviceDataList)
    {

        List<Event> returnList = new ArrayList<Event>();

        for (Map<String, Object> dataMap : serviceDataList)
        {

            Integer noteType = (Integer) dataMap.get(KEY_NOTE_TYPE);
            Class objectType = EventMapper.getEventType(noteType);

            Event event = createEventFromDataMap(dataMap, objectType);
            returnList.add(event);

        }

        return returnList;
    }

    public static <T extends Event> Event createEventFromDataMap(Map<String, Object> dataMap, Class<T> objectType)
    {
        T object =null;
        try
        {
            object = objectType.newInstance();
        }
        catch (InstantiationException e)
        {
            System.out.println("instantiation exception");
            throw new MappingException(e);
        }
        catch (IllegalAccessException e)        
        {
            System.out.println("illegalaccessexception");
            throw new MappingException(e);
        }
        for (Map.Entry<String, Object> entry : dataMap.entrySet())
        {
            String key = entry.getKey();
            Object data = entry.getValue();
            // set id in base class
            if (key.equals(KEY_ATTR_MAP))
            {
                Map<Integer, Object> attrMap = (Map<Integer, Object>) data;
                for (Map.Entry<Integer, Object> attrEntry : attrMap.entrySet())
                {
                    String propertyName = EventAttr.getFieldName(attrEntry.getKey());
                    Object propertyData = attrEntry.getValue();
                    setPropertyOnObject(objectType, object, propertyName, propertyData);
                }
            }
            else
            {
                setPropertyOnObject(objectType, object, key, data);
            }
        }
        return object;
    }

    private static <T> void setPropertyOnObject(Class<T> objectType, T object, String key, Object data)
    {
        PropertyDescriptor propertyDescriptor;
        try
        {
            propertyDescriptor = new PropertyDescriptor(key, objectType);
        }
        catch (IntrospectionException e)
        {

            // just skip if you can't get the setter method
            // System.out.println("Skipping property: " + key + " for type: " + objectType.getName());
            return;
        }
        Method setMethod = propertyDescriptor.getWriteMethod();
        try
        {
            setMethod.invoke(object, new Object[] { data });
        }
        catch (IllegalAccessException e)
        {
            System.out.println("illegalaccessexception");
            throw new MappingException(e);
        }
        catch (InvocationTargetException e)
        {
            System.out.println("invocationtargetexception");
            throw new MappingException(e);
        }
    }

    private static boolean isBaseProperty(String key, PropertyDescriptor[] basePropertyDescriptors)
    {
        for (int i = 0; i < basePropertyDescriptors.length; i++)
        {
            if (key.endsWith(basePropertyDescriptors[i].getName()))
            {
                return true;
            }
        }
        return false;
    }

    // method used only for the note() method to create a note and simulate what they device
    // does -- this is currently only used to generate test data
    public static byte[] createDataBytesFromEvent(Event event)
    {
        byte[] eventBytes = new byte[200];
        int idx = 0;
        eventBytes[idx++] = (byte) (event.getType() & 0x000000FF);
        Integer eventTime = new Integer((int)event.getTime());
        idx = puti4(eventBytes, idx, eventTime);
        eventBytes[idx++] = (byte) 1; // ?? flags
        eventBytes[idx++] = (byte) 1; // maprev
        idx = putlat(eventBytes, idx, event.getLatitude());
        idx = putlng(eventBytes, idx, event.getLongitude());
        eventBytes[idx++] = (byte) (event.getSpeed() & 0x000000FF);
        idx = puti2(eventBytes, idx, event.getOdometer());

        if (event instanceof SpeedingEvent)
        {
            SpeedingEvent speedingEvent = (SpeedingEvent) event;
            eventBytes[idx++] = (byte) (EventAttr.getKey("topSpeed") & 0x000000FF);
            eventBytes[idx++] = (byte) (speedingEvent.getTopSpeed() & 0x000000FF);
            eventBytes[idx++] = (byte) (EventAttr.getKey("avgSpeed") & 0x000000FF);
            eventBytes[idx++] = (byte) (speedingEvent.getAvgSpeed() & 0x000000FF);
            eventBytes[idx++] = (byte) (EventAttr.getKey("speedLimit") & 0x000000FF);
            eventBytes[idx++] = (byte) (speedingEvent.getSpeedLimit() & 0x000000FF);
            eventBytes[idx++] = (byte) (EventAttr.getKey("distance") & 0x000000FF);
            eventBytes[idx++] = (byte) (speedingEvent.getDistance() & 0x000000FF);
            eventBytes[idx++] = (byte) (EventAttr.getKey("avgRPM") & 0x000000FF);
            eventBytes[idx++] = (byte) (speedingEvent.getAvgRPM() & 0x000000FF);
        }
        else if (event instanceof SeatBeltEvent)
        {
            SeatBeltEvent seatbeltEvent = (SeatBeltEvent) event;
            eventBytes[idx++] = (byte) (EventAttr.getKey("topSpeed") & 0x000000FF);
            eventBytes[idx++] = (byte) (seatbeltEvent.getTopSpeed() & 0x000000FF);
            eventBytes[idx++] = (byte) (EventAttr.getKey("avgSpeed") & 0x000000FF);
            eventBytes[idx++] = (byte) (seatbeltEvent.getAvgSpeed() & 0x000000FF);
            eventBytes[idx++] = (byte) (EventAttr.getKey("distance") & 0x000000FF);
            eventBytes[idx++] = (byte) (seatbeltEvent.getDistance() & 0x000000FF);
        }
        else if (event instanceof AggressiveDrivingEvent)
        {
            AggressiveDrivingEvent adEvent = (AggressiveDrivingEvent) event;
            eventBytes[idx++] = (byte) (EventAttr.getKey("avgSpeed") & 0x000000FF);
            eventBytes[idx++] = (byte) (adEvent.getAvgSpeed() & 0x000000FF);
            eventBytes[idx++] = (byte) (EventAttr.getKey("deltaVx") & 0x000000FF);
            idx = puti2(eventBytes, idx, adEvent.getDeltaX());
            eventBytes[idx++] = (byte) (EventAttr.getKey("deltaVy") & 0x000000FF);
            idx = puti2(eventBytes, idx, adEvent.getDeltaY());
            eventBytes[idx++] = (byte) (EventAttr.getKey("deltaVz") & 0x000000FF);
            idx = puti2(eventBytes, idx, adEvent.getDeltaZ());
        }

        return Arrays.copyOf(eventBytes, idx);
    }

    private static int putlat(byte[] eventBytes, int idx, Double latitude)
    {
        latitude = 90.0 - latitude;
        latitude = latitude / 180.0;

        return putlatlng(eventBytes, idx, latitude);
    }

    private static int putlng(byte[] eventBytes, int idx, Double longitude)
    {
        if (longitude < 0.0)
            longitude = longitude + 360;
        longitude = longitude / 360.0;

        return putlatlng(eventBytes, idx, longitude);
    }

    private static int putlatlng(byte[] eventBytes, int idx, Double latlng)
    {
        int i = (int) (latlng * 0x00ffffff);
        eventBytes[idx++] = (byte) ((i >> 16) & 0x000000FF);
        eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
        eventBytes[idx++] = (byte) (i & 0x000000FF);
        return idx;
    }

    private static int puti4(byte[] eventBytes, int idx, Integer i)
    {
        eventBytes[idx++] = (byte) ((i >> 24) & 0x000000FF);
        eventBytes[idx++] = (byte) ((i >> 16) & 0x000000FF);
        eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
        eventBytes[idx++] = (byte) (i & 0x000000FF);
        return idx;
    }

    private static int puti2(byte[] eventBytes, int idx, Integer i)
    {
        eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
        eventBytes[idx++] = (byte) (i & 0x000000FF);
        return idx;
    }
    
}
