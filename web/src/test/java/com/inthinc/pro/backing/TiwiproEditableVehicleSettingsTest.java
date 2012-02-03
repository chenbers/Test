package com.inthinc.pro.backing;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.inthinc.pro.backing.VehiclesBean.VehicleView;


public class TiwiproEditableVehicleSettingsTest {
    
    Integer defaultSpeedSettings [] = {
            0,0,0,0,0,
            0,0,0,0,0,
            0,0,0,0,0,
    };
   
    Integer guiSpeedSettings [] = {
            5,0,0,0,0,
            10,0,0,0,0,
            15,0,0,0,0,
    };
    Integer expectedSpeedSettings [] = {
            5,5,5,5,5,
            10, 10, 10, 10, 10,
            15,15,15,15,15,
    };
    
    @Test
    public void dealWithSpecialSettings()
    {
        TiwiproEditableVehicleSettings tiwiproEditableVehicleSettings = new TiwiproEditableVehicleSettings();
        tiwiproEditableVehicleSettings.setSpeedSettings(guiSpeedSettings);
        
        // not batch
        VehicleView vehicle = new VehicleView();
        vehicle.setEditableVehicleSettings(tiwiproEditableVehicleSettings);
        
        tiwiproEditableVehicleSettings.dealWithSpecialSettings(vehicle, null, null, Boolean.FALSE);

        assertEquals("expected non batch speed settings", expectedSpeedSettings.length, ((TiwiproEditableVehicleSettings)vehicle.getEditableVehicleSettings()).getSpeedSettings().length);
        for (int i = 0; i < expectedSpeedSettings.length; i++)
            assertEquals("expected non batch speed settings " + i, expectedSpeedSettings[i], ((TiwiproEditableVehicleSettings)vehicle.getEditableVehicleSettings()).getSpeedSettings()[i]);

        // batch
        vehicle = new VehicleView();
        TiwiproEditableVehicleSettings tiwiproDefaultEditableVehicleSettings = new TiwiproEditableVehicleSettings();
        tiwiproDefaultEditableVehicleSettings.setSpeedSettings(defaultSpeedSettings);
        vehicle.setEditableVehicleSettings(tiwiproDefaultEditableVehicleSettings);

        VehicleView batchItem = new VehicleView();
        batchItem.setEditableVehicleSettings(tiwiproEditableVehicleSettings);
        Map<String, Boolean> updateField = new HashMap<String, Boolean>();
        updateField.put("speed0", Boolean.TRUE);
        updateField.put("speed5", Boolean.TRUE);
        updateField.put("speed10", Boolean.TRUE);
        tiwiproEditableVehicleSettings.dealWithSpecialSettings(vehicle, batchItem, updateField, Boolean.TRUE);

        assertEquals("expected non batch speed settings", expectedSpeedSettings.length, ((TiwiproEditableVehicleSettings)vehicle.getEditableVehicleSettings()).getSpeedSettings().length);
        for (int i = 0; i < expectedSpeedSettings.length; i++)
            assertEquals("expected non batch speed settings " + i, expectedSpeedSettings[i], ((TiwiproEditableVehicleSettings)vehicle.getEditableVehicleSettings()).getSpeedSettings()[i]);
        
    }
}
