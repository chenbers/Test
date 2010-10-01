package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.model.app.SensitivityMapping;
import com.inthinc.pro.model.configurator.SensitivityType;

public class SettingsAndSlidersTest {

    private SensitivityMapping sensitivityMapping;
    @Before
    public void setUp() throws Exception
    {
        sensitivityMapping = new SensitivityMapping();
        sensitivityMapping.init(); 
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testGetSlider() {
        
        SettingsAndSliders sands = new SettingsAndSliders();
        Map<SensitivityType,String> settings = new HashMap<SensitivityType,String>();
        Integer slider = sands.getSlider(settings);
        Assert.assertTrue(slider.intValue()==99);
        
        settings.put(SensitivityType.HARD_ACCEL_LEVEL, "300");
        settings.put(SensitivityType.HARD_ACCEL_DELTAV, "4");
        slider = sands.getSlider(settings);
        Assert.assertTrue(slider.intValue()==1);


        settings = new HashMap<SensitivityType,String>();
        settings.put(SensitivityType.DVX, "10");
        settings.put(SensitivityType.X_ACCEL, "225");
        slider = sands.getSlider(settings);
        Assert.assertTrue(slider.intValue()==1);

        settings = new HashMap<SensitivityType,String>();
        settings.put(SensitivityType.DVY, "10");
        settings.put(SensitivityType.Y_LEVEL, "225");
        slider = sands.getSlider(settings);
        Assert.assertTrue(slider.intValue()==1);

        settings = new HashMap<SensitivityType,String>();
        settings.put(SensitivityType.RMS_LEVEL, "400");
        settings.put(SensitivityType.SEVERE_HARDVERT_LEVEL, "400");
        settings.put(SensitivityType.HARDVERT_DMM_PEAKTOPEAK, "400");
        slider = sands.getSlider(settings);
        Assert.assertTrue(slider.intValue()==1);

        settings = new HashMap<SensitivityType,String>();
        settings.put(SensitivityType.DVX, "6");
        settings.put(SensitivityType.X_ACCEL, "140");
        slider = sands.getSlider(settings);
        Assert.assertTrue(slider.intValue()==2);
    }
}
