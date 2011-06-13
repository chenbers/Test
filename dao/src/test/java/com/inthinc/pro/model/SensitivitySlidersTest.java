package com.inthinc.pro.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SensitivitySlider;
import com.inthinc.pro.model.configurator.SliderKey;
import com.inthinc.pro.model.configurator.SliderType;

public class SensitivitySlidersTest {
    private SensitivitySlidersMockDataCreator sensitivitySlidersMockDataCreator;
    @Before
    public void setupSensitivitySliderValues(){
        sensitivitySlidersMockDataCreator = new SensitivitySlidersMockDataCreator();
    }
    @Test
    public void testSliderValues(){
        SensitivitySlider slider =  sensitivitySlidersMockDataCreator.
                                    getSensitivitySliders().
                                    getSensitivitySliders().get(new SliderKey(SliderType.HARD_ACCEL_SLIDER,ProductType.TIWIPRO_R74,0,1000000));
        
        assertEquals(4,slider.getDefaultValueIndex());
        Set<Integer> idsForThisSlider = slider.getSettingIDsForThisSlider();
        assertTrue(idsForThisSlider.contains(new Integer(157)));
        assertTrue(idsForThisSlider.size()==1);
        
        Map<Integer,SensitivitySliderValues> settings = slider.getSettingsForThisSlider();
        assertTrue(settings.size()==1);
        assertTrue(settings.get(157) != null);
        SensitivitySliderValues sensitivitySliderValues = settings.get(157);
        assertEquals(new Integer(4),(Integer)sensitivitySliderValues.getDefaultValueIndex());
        
        Map<Integer,String> settingValues = slider.getSettingValuesFromSliderValue(1);
        assertTrue(settingValues.size()==1);
        assertTrue(settingValues.get(157) != null);
        assertEquals("3000 40 1",settingValues.get(157));

        Integer sliderValue = slider.getSliderValueFromSettings(settingValues);
        assertTrue(sliderValue==1);
        
        slider = sensitivitySlidersMockDataCreator.getSensitivitySliders().
                                                   getSensitivitySliders().
                                                   get(new SliderKey(SliderType.HARD_ACCEL_SLIDER,ProductType.WAYSMART,0,1000000));
        
        assertEquals(slider.getDefaultValueIndex(),8);
        idsForThisSlider = slider.getSettingIDsForThisSlider();
        assertTrue(idsForThisSlider.contains(new Integer(1234)));
        assertTrue(idsForThisSlider.contains(new Integer(1232)));
        assertTrue(idsForThisSlider.size()==2);
        
        settings = slider.getSettingsForThisSlider();
        assertTrue(settings.size()==2);
        assertTrue(settings.get(1234) != null);
        sensitivitySliderValues = settings.get(1234);
        assertEquals(new Integer(8),(Integer)sensitivitySliderValues.getDefaultValueIndex());
        
        settingValues = slider.getSettingValuesFromSliderValue(1);
        assertTrue(settingValues.size()==2);
        assertTrue(settingValues.get(1234) != null);
        assertEquals("300",settingValues.get(1234));

        sliderValue = slider.getSliderValueFromSettings(settingValues);
        assertTrue(sliderValue==1);
    }
}
