package com.inthinc.pro.backing.dao.mapper;

import java.util.Map;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.dao.annotations.ConvertFieldToColumn;
import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.event.Event;

public class DaoUtilRedFlagMapper extends DaoUtilMapper {

    @ConvertColumnToField(columnName = "event")
    public void eventToModel(RedFlag redFlag, Object value) {

        if (value != null && value instanceof Map) {
            DaoUtilEventMapper eventMapper = new DaoUtilEventMapper();
            redFlag.setEvent(eventMapper.convertToModelObject((Map<String, Object>) value, Event.class));
        }
    }

    @ConvertFieldToColumn(fieldName = "event")
    public void eventToColumn(RedFlag redFlag, Object value) {
        
        
        if (redFlag.getEvent() != null) {
            EventHessianMapper eventMapper = new EventHessianMapper();
            ((Map<String, Object>)value).put("event", eventMapper.convertToMap(redFlag.getEvent()));
        }

    }

}
