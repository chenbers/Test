package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.model.app.SensitivityMapping;
import com.inthinc.pro.model.configurator.SensitivityType;
@Ignore
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
        Map<Integer,String> settings = new HashMap<Integer,String>();
        Integer slider = sands.getSlider(settings);
        Assert.assertTrue(slider.intValue()==99);
        
        settings.put(SensitivityType.HARD_ACCEL_LEVEL.getSettingID(), "300");
        settings.put(SensitivityType.HARD_ACCEL_DELTAV.getSettingID(), "4");
        slider = sands.getSlider(settings);
        Assert.assertTrue(slider.intValue()==1);


        settings = new HashMap<Integer,String>();
        settings.put(SensitivityType.DVX.getSettingID(), "10");
        settings.put(SensitivityType.X_ACCEL.getSettingID(), "225");
        slider = sands.getSlider(settings);
        Assert.assertTrue(slider.intValue()==1);

        settings = new HashMap<Integer,String>();
        settings.put(SensitivityType.DVY.getSettingID(), "10");
        settings.put(SensitivityType.Y_LEVEL.getSettingID(), "225");
        slider = sands.getSlider(settings);
        Assert.assertTrue(slider.intValue()==1);

        settings = new HashMap<Integer,String>();
        settings.put(SensitivityType.RMS_LEVEL.getSettingID(), "400");
        settings.put(SensitivityType.SEVERE_HARDVERT_LEVEL.getSettingID(), "400");
        settings.put(SensitivityType.HARDVERT_DMM_PEAKTOPEAK.getSettingID(), "400");
        slider = sands.getSlider(settings);
        Assert.assertTrue(slider.intValue()==1);

        settings = new HashMap<Integer,String>();
        settings.put(SensitivityType.DVX.getSettingID(), "6");
        settings.put(SensitivityType.X_ACCEL.getSettingID(), "140");
        slider = sands.getSlider(settings);
        Assert.assertTrue(slider.intValue()==2);
    }
}
