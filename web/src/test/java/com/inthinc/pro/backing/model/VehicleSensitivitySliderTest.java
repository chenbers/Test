package com.inthinc.pro.backing.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SliderType;

public class VehicleSensitivitySliderTest {
    private SensitivitySlidersMockDataCreator sensitivitySlidersMockDataCreator;
    @Before
    public void setupSensitivitySliderValues(){
        sensitivitySlidersMockDataCreator = new SensitivitySlidersMockDataCreator();
    }
        

    @Test
    public void createVehicleSensitivitySlider(){
        VehicleSensitivitySlider vehicleSensitivitySlider = new VehicleSensitivitySlider(sensitivitySlidersMockDataCreator.getSensitivitySliders(), 
                SliderType.HARD_ACCEL_SLIDER,
                ProductType.TIWIPRO, 
                0,1000000) ;
        assertEquals(new Integer(4),(Integer)vehicleSensitivitySlider.getDefaultValueIndex());
        assertTrue(vehicleSensitivitySlider.getSettingIDsForThisSlider().size()==1);
        assertTrue(vehicleSensitivitySlider.getSettingIDsForThisSlider().contains(157));
        assertTrue(vehicleSensitivitySlider.getSettingValuesFromSliderValue(1).containsKey(157));
        assertTrue(vehicleSensitivitySlider.getSettingValuesFromSliderValue(1).containsValue("3000 40 1"));

        vehicleSensitivitySlider = new VehicleSensitivitySlider(sensitivitySlidersMockDataCreator.getSensitivitySliders(), 
                SliderType.HARD_ACCEL_SLIDER,
                ProductType.WAYSMART, 
                0,1000000) ;
        assertEquals(new Integer(8),(Integer)vehicleSensitivitySlider.getDefaultValueIndex());
        assertTrue(vehicleSensitivitySlider.getSettingIDsForThisSlider().size()==2);
        assertTrue(vehicleSensitivitySlider.getSettingIDsForThisSlider().contains(1234));
        assertTrue(vehicleSensitivitySlider.getSettingIDsForThisSlider().contains(1232));
        assertTrue(vehicleSensitivitySlider.getSettingValuesFromSliderValue(1).containsKey(1234));
        assertTrue(vehicleSensitivitySlider.getSettingValuesFromSliderValue(1).containsValue("300"));
        assertTrue(vehicleSensitivitySlider.getSettingValuesFromSliderValue(1).containsKey(1232));
        assertTrue(vehicleSensitivitySlider.getSettingValuesFromSliderValue(1).containsValue("4"));
     }
}
