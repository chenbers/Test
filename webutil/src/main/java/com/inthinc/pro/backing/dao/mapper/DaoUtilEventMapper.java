package com.inthinc.pro.backing.dao.mapper;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.inthinc.pro.dao.annotations.ConvertColumnToField;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.NoteType;

public class DaoUtilEventMapper extends DaoUtilMapper {

	@ConvertColumnToField(columnName = "attrMap")
    public void attrMapToModel(Event event, Object value)
    {
        if (event == null || value == null)
            return;

        if (value instanceof Map)
        {
            Map<Integer, Object> attrMap = (Map<Integer, Object>) value;
            Map<String, String> attrValueMap = new HashMap<String, String>();
            for (Map.Entry<Integer, Object> attrEntry : attrMap.entrySet())
            {
                String propertyName = (EventAttr.getFieldName(attrEntry.getKey()) == null ? "" : EventAttr.getFieldName(attrEntry.getKey())) + "(" + attrEntry.getKey() + ")";
                Object propertyData = attrEntry.getValue();
                if (propertyName == null || propertyData == null)
                    continue;
                attrValueMap.put(propertyName, propertyData.toString());
            }
        	try {
        		PropertyUtils.setProperty(event, "attrMap", attrValueMap);
        	}
            catch (Exception e) {
            	
            }
        }
    }

    @Override
    public <E> E  convertToModelObject(Map<String, Object> map, Class<E> type)
    {
        if (type == Event.class)
        {
            Class<?> eventType = getEventType((Integer) map.get("type"));
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
    
    private Class getEventType(Integer proEventType)
    {
        NoteType noteType = NoteType.valueOf(proEventType);
        if (noteType.getEventClass() == null)
        {
            //logger.error("Unsupported Event Type requested type = [" + proEventType + "] returning Base Event");
            return NoteType.BASE.getEventClass();
        }
        
        return noteType.getEventClass();
    }


}
