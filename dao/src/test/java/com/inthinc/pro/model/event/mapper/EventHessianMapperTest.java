package com.inthinc.pro.model.event.mapper;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.TrailerDataEvent;

public class EventHessianMapperTest {

    @Test
    public void testTrailerDataEvent() {
        
        EventHessianMapper mapper = new EventHessianMapper();
        
        TrailerDataEvent event = new TrailerDataEvent();
        
        Map<Integer, Object> attrMap = new HashMap<Integer, Object>();
        
        attrMap.put(EventAttr.HAZMAT_FLAG.getIndex(), new Integer(1));
        
        mapper.attrMapToModel(event, attrMap);
        
        assertEquals("Expected TRUE hazMatFlag", Integer.valueOf(1), event.getHazmatFlag());
        
        
    }

}
