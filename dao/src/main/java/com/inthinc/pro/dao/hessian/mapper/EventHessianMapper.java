package com.inthinc.pro.dao.hessian.mapper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventAttr;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.TablePreference;

public class EventHessianMapper extends AbstractMapper
{
    private static final Logger logger = Logger.getLogger(EventHessianMapper.class);
    
    @ConvertColumnToField(columnName = "attrMap")
    public void attrMapToModel(Event event, Object value)
    {
        if (event == null || value == null)
            return;

        if (value instanceof Map)
        {
            Map<Integer, Object> attrMap = (Map<Integer, Object>) value;
            for (Map.Entry<Integer, Object> attrEntry : attrMap.entrySet())
            {
                String propertyName = EventAttr.getFieldName(attrEntry.getKey());
                Object propertyData = attrEntry.getValue();
                if (propertyName == null || propertyData == null)
                    continue;
                try
                {
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
    public <E> E convertToModelObject(Map<String, Object> map, Class<E> type)
    {
        if (type == Event.class)
        {
            Class<?> eventType = EventMapper.getEventType((Integer) map.get("type"));
            if (eventType != null)
                return type.cast(super.convertToModelObject(map, eventType));
            else
                return super.convertToModelObject(map, type);
        }
        else
        {
            return super.convertToModelObject(map, type);
        }
    }

    
    
}
