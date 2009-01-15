package com.inthinc.pro.dao.hessian.mapper;

import java.util.Map;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.RedFlag;

public class RedFlagHessianMapper extends AbstractMapper
{
    @ConvertColumnToField(columnName = "note")
    public void noteToModel(RedFlag redFlag, Object value)
    {
        
        if (value != null && value instanceof Map)
        {
            EventHessianMapper eventMapper = new EventHessianMapper();
            redFlag.setEvent(eventMapper.convertToModelObject((Map)value, Event.class));

        }
    }
}
