package com.inthinc.pro.dao.hessian.mapper;

import java.util.Map;

import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;

public class EventHessianMapper extends AbstractMapper
{

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
