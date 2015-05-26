package com.inthinc.pro.model.event.mapper;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.IdleEvent;
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
        
        IdleEvent eventIdle = new IdleEvent();        
        String attribs = ";241=123;";        
        mapper.attrMapToModelAttribs(eventIdle, attribs);
        
        assertEquals("Expected ptoIdle = 123", Integer.valueOf(123), eventIdle.getPtoIdle());
        assertEquals("Expected highIdle = 0", Integer.valueOf(0), eventIdle.getHighIdle());
        assertEquals("Expected highIdleDuration = 00:02:03", "00:02:03", eventIdle.getHighIdleDuration());
        assertEquals("Expected totalIdling = 123", Integer.valueOf(123), eventIdle.getTotalIdling());
        assertEquals("Expected details = 123", "Low Idle: 0 seconds, High Idle: 123 seconds.", eventIdle.getDetails("Low Idle: {0} seconds, High Idle: {1} seconds.", MeasurementType.ENGLISH, "mph", "miles"));
        
        eventIdle.setHighIdle(11);
        
        assertEquals("Expected ptoIdle = 123", Integer.valueOf(123), eventIdle.getPtoIdle());
        assertEquals("Expected highIdle = 11", Integer.valueOf(11), eventIdle.getHighIdle());
        assertEquals("Expected highIdleDuration = 00:00:11", "00:00:11", eventIdle.getHighIdleDuration());
        assertEquals("Expected totalIdling = 11", Integer.valueOf(11), eventIdle.getTotalIdling());
        assertEquals("Expected details = 11", "Low Idle: 0 seconds, High Idle: 11 seconds.", eventIdle.getDetails("Low Idle: {0} seconds, High Idle: {1} seconds.", MeasurementType.ENGLISH, "mph", "miles"));
        
        eventIdle.setHighIdle(null);
        
        assertEquals("Expected ptoIdle = 123", Integer.valueOf(123), eventIdle.getPtoIdle());
        assertEquals("Expected highIdle = null", null, eventIdle.getHighIdle());
        assertEquals("Expected highIdleDuration = 00:02:03", "00:02:03", eventIdle.getHighIdleDuration());
        assertEquals("Expected totalIdling = 123", Integer.valueOf(123), eventIdle.getTotalIdling());
        assertEquals("Expected details = 123", "Low Idle: 0 seconds, High Idle: 123 seconds.", eventIdle.getDetails("Low Idle: {0} seconds, High Idle: {1} seconds.", MeasurementType.ENGLISH, "mph", "miles"));
        
        eventIdle = new IdleEvent();
        attribs = "";        
        mapper.attrMapToModelAttribs(eventIdle, attribs);
        eventIdle.setHighIdle(null);
        
        assertEquals("Expected ptoIdle = 0", Integer.valueOf(0), eventIdle.getPtoIdle());
        assertEquals("Expected highIdle = null", null, eventIdle.getHighIdle());
        assertEquals("Expected highIdleDuration = \"\"", "", eventIdle.getHighIdleDuration());
        assertEquals("Expected totalIdling = 0", Integer.valueOf(0), eventIdle.getTotalIdling());
        assertEquals("Expected details = 0", "Low Idle: 0 seconds, High Idle: 0 seconds.", eventIdle.getDetails("Low Idle: {0} seconds, High Idle: {1} seconds.", MeasurementType.ENGLISH, "mph", "miles"));
        
        eventIdle = new IdleEvent();
        attribs = null;
        mapper.attrMapToModelAttribs(eventIdle, attribs);
        eventIdle.setHighIdle(null);
        
        assertEquals("Expected ptoIdle = 0", Integer.valueOf(0), eventIdle.getPtoIdle());
        assertEquals("Expected highIdle = null", null, eventIdle.getHighIdle());
        assertEquals("Expected highIdleDuration = \"\"", "", eventIdle.getHighIdleDuration());
        assertEquals("Expected totalIdling = 0", Integer.valueOf(0), eventIdle.getTotalIdling());
        assertEquals("Expected details = 0", "Low Idle: 0 seconds, High Idle: 0 seconds.", eventIdle.getDetails("Low Idle: {0} seconds, High Idle: {1} seconds.", MeasurementType.ENGLISH, "mph", "miles"));
        
        eventIdle.setPtoIdle(null);
        
        assertEquals("Expected ptoIdle = null", null, eventIdle.getPtoIdle());
        assertEquals("Expected highIdle = null", null, eventIdle.getHighIdle());
        assertEquals("Expected highIdleDuration = \"\"", "", eventIdle.getHighIdleDuration());
        assertEquals("Expected totalIdling = 0", Integer.valueOf(0), eventIdle.getTotalIdling());
        assertEquals("Expected details = 0", "Low Idle: 0 seconds, High Idle: 0 seconds.", eventIdle.getDetails("Low Idle: {0} seconds, High Idle: {1} seconds.", MeasurementType.ENGLISH, "mph", "miles"));
        
    }

}
