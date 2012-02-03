package com.inthinc.pro.dao.hessian.mapper;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.inthinc.pro.model.RedFlagAlert;


public class RedFlagAlertMapperTest {
    private static Integer all5SpeedSettings[] = new Integer[] {
        5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,
    };
    private static String all5SpeedSettingsStr = "5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 ";
    
    private static Integer firstLast5SpeedSettings[] = new Integer[] {
        5,null, null, null, null,null, null, null, null,null, null, null, null,null,5,
    };
    private static String firstLast5SpeedSettingsStr = "5              5 ";

    
    @Test
    public void speedSettingsToModelTest()
    {
        RedFlagAlertMapper redFlagAlertMapper = new RedFlagAlertMapper();
        RedFlagAlert redFlagAlert = new RedFlagAlert();
        
        // maxSpeed
        redFlagAlertMapper.speedSettingsToModel(redFlagAlert, "~88");
        assertTrue("useMaxSpeed should be true", redFlagAlert.getUseMaxSpeed());
        assertEquals("maxSpeed should be set", 88, redFlagAlert.getMaxSpeed().intValue());
        
        redFlagAlertMapper.speedSettingsToModel(redFlagAlert, "~invalid");
        assertTrue("useMaxSpeed should be true", redFlagAlert.getUseMaxSpeed());
        assertNull("maxSpeed should be set to null due to invalid number", redFlagAlert.getMaxSpeed());
        
        // English speed settings
        redFlagAlertMapper.speedSettingsToModel(redFlagAlert, all5SpeedSettingsStr);
        assertFalse("useMaxSpeed should be false", redFlagAlert.getUseMaxSpeed());
        Integer speedSettings[] = redFlagAlert.getSpeedSettings();
        assertEquals("15 speeds should be set", 15, speedSettings.length);
        for (int i = 0; i < 15; i++)
            assertEquals("15 speeds should be set to 5", 5, speedSettings[i].intValue());
        
        redFlagAlertMapper.speedSettingsToModel(redFlagAlert, firstLast5SpeedSettingsStr);
        assertFalse("useMaxSpeed should be false", redFlagAlert.getUseMaxSpeed());
        speedSettings = redFlagAlert.getSpeedSettings();
        assertEquals("15 speeds should be set", 15, speedSettings.length);
        assertEquals("1st speeds should be set to 5", 5, speedSettings[0].intValue());
        assertEquals("last speeds should be set to 5", 5, speedSettings[14].intValue());
        for (int i = 1; i < 14; i++)
            assertNull("speed should be null", speedSettings[i]);
    }
    

    @Test
    public void speedSettingsToColumnTest()
    {
        RedFlagAlertMapper redFlagAlertMapper = new RedFlagAlertMapper();
        Map<String, Object> hessianMap = new HashMap<String, Object>();
        
        RedFlagAlert redFlagAlert = new RedFlagAlert();
        redFlagAlert.setUseMaxSpeed(Boolean.FALSE);
        redFlagAlert.setSpeedSettings(all5SpeedSettings);
        
        redFlagAlertMapper.speedSettingsToColumn(redFlagAlert, hessianMap);
        assertEquals("hessian map speedSettings", all5SpeedSettingsStr, hessianMap.get("speedSettings"));

    
        redFlagAlert.setUseMaxSpeed(Boolean.FALSE);
        redFlagAlert.setSpeedSettings(firstLast5SpeedSettings);
        
        redFlagAlertMapper.speedSettingsToColumn(redFlagAlert, hessianMap);
        assertEquals("hessian map speedSettings", firstLast5SpeedSettingsStr, hessianMap.get("speedSettings"));

    }

    @Test
    public void maxSpeedToColumnTest()
    {
        RedFlagAlertMapper redFlagAlertMapper = new RedFlagAlertMapper();
        Map<String, Object> hessianMap = new HashMap<String, Object>();
        
        RedFlagAlert redFlagAlert = new RedFlagAlert();
        redFlagAlert.setUseMaxSpeed(Boolean.TRUE);
        redFlagAlert.setMaxSpeed(88);
        
        redFlagAlertMapper.maxSpeedToColumn(redFlagAlert, hessianMap);
        assertEquals("hessian map maxSpeed", "~88", hessianMap.get("speedSettings"));
    }

}
