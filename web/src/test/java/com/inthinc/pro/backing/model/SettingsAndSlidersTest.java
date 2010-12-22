package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.model.app.SensitivitySliderValuesMapping;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.configurator.Slider;
import com.inthinc.pro.model.configurator.SliderKey;
import com.inthinc.pro.model.configurator.SliderType;
@Ignore
public class SettingsAndSlidersTest {

    private SensitivitySliderValuesMapping sensitivitySliderValuesMapping;
    @Before
    public void setUp() throws Exception
    {
        sensitivitySliderValuesMapping = new SensitivitySliderValuesMapping();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testGetSlider() {
        SliderKey sliderKey = new SliderKey(SliderType.HARD_ACCEL_SLIDER.getCode(), ProductType.WAYSMART.getCode(), 0, 1000000);
        Slider sands = new Slider(sliderKey);
        Map<Integer,String> settings = new HashMap<Integer,String>();
        Integer slider = sands.getSliderValueFromSettings(settings);
        Assert.assertTrue(slider.intValue()==99);
        
        settings.put(SettingType.HARD_ACCEL_LEVEL.getSettingID(), "300");
        settings.put(SettingType.HARD_ACCEL_DELTAV.getSettingID(), "4");
        slider = sands.getSliderValueFromSettings(settings);
        Assert.assertTrue(slider.intValue()==1);


        settings = new HashMap<Integer,String>();
        settings.put(SettingType.DVX.getSettingID(), "10");
        settings.put(SettingType.X_ACCEL.getSettingID(), "225");
        slider = sands.getSliderValueFromSettings(settings);
        Assert.assertTrue(slider.intValue()==1);

        settings = new HashMap<Integer,String>();
        settings.put(SettingType.DVY.getSettingID(), "10");
        settings.put(SettingType.Y_LEVEL.getSettingID(), "225");
        slider = sands.getSliderValueFromSettings(settings);
        Assert.assertTrue(slider.intValue()==1);

        settings = new HashMap<Integer,String>();
        settings.put(SettingType.RMS_LEVEL.getSettingID(), "400");
        settings.put(SettingType.SEVERE_HARDVERT_LEVEL.getSettingID(), "400");
        settings.put(SettingType.HARDVERT_DMM_PEAKTOPEAK.getSettingID(), "400");
        slider = sands.getSliderValueFromSettings(settings);
        Assert.assertTrue(slider.intValue()==1);

        settings = new HashMap<Integer,String>();
        settings.put(SettingType.DVX.getSettingID(), "6");
        settings.put(SettingType.X_ACCEL.getSettingID(), "140");
        slider = sands.getSliderValueFromSettings(settings);
        Assert.assertTrue(slider.intValue()==2);
    }
}
