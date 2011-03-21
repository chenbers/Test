package com.inthinc.pro.dao.hessian.mapper;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.NoteType;

@SuppressWarnings("serial")
public class EventHessianMapper extends AbstractMapper
{
    private static final Logger logger = Logger.getLogger(EventHessianMapper.class);
    
    @SuppressWarnings("unchecked")
    @ConvertColumnToField(columnName = "attrMap")
    public void attrMapToModel(Event event, Object value)
    {
        if (event == null || value == null)
            return;

        if (value instanceof Map<?,?>)
        {
            Map<Integer, Object> attrMap = (Map<Integer, Object>) value;
            Map<String, String> attrValueMap = new HashMap<String, String>();
            for (Map.Entry<Integer, Object> attrEntry : attrMap.entrySet())
            {
                String propertyName = EventAttr.getFieldName(attrEntry.getKey());
                Object propertyData = attrEntry.getValue();
                if (propertyName == null || propertyData == null)
                    continue;
                attrValueMap.put(propertyName, propertyData.toString());
                try
                {
                    PropertyUtils.setProperty(event, "attrMap", attrValueMap);
                    PropertyUtils.setProperty(event, propertyName, propertyData);
                }
                catch (IllegalAccessException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (InvocationTargetException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (NoSuchMethodException e)
                {
                    if (logger.isTraceEnabled())
                    {
                        logger.trace("The property \"" + propertyName + "\" could not be set to the value \"" + propertyData + "\"", e);
                    }
                }

            }
            
        }
    }


    @Override
    public <E> E  convertToModelObject(Map<String, Object> map, Class<E> type)
    {
        if (map == null) return null;
        
        if (type == Event.class)
        {
            NoteType noteType = NoteType.valueOf((Integer) map.get("type"));
            if (noteType != null && noteType.getEventClass()!=null)
            {
                E e = type.cast(super.convertToModelObject(map, noteType.getEventClass()));
//                Event event=(Event) e;
                return e;
            }
            else
                return super.convertToModelObject(map, type);
        }
        else
        {
            return super.convertToModelObject(map, type);
        }
    }

    
    
}
