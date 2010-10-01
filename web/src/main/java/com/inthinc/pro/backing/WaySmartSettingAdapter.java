package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SensitivityType;

public class WaySmartSettingAdapter extends VehicleSettingAdapter {

    public WaySmartSettingAdapter() {
        super();
        properties = new Properties();
    }


    public WaySmartSettingAdapter(int vehicleID, Integer speedLimit, Integer speedBuffer, Integer severeSpeed,
                                  Integer hardAcceleration, Integer hardBrake, Integer hardTurn,
                                  Integer hardVertical) {
        
        super(vehicleID,ProductType.WS820);
        
        properties = new Properties();
        properties.put("speed_limit", speedLimit);
        properties.put("speed_buffer", speedBuffer);
        properties.put("severe_speed", severeSpeed);
        setHardAcceleration(hardAcceleration);
        setHardBrake(hardBrake);
        setHardTurn(hardTurn);
        setHardVertical(hardVertical);

    }


    @Override
    public boolean validateSaveItems(FacesContext context, boolean isBatchEdit, Map<String, Boolean> updateField) {

        return true;
    }

    private void setHardAcceleration(Integer hardAcceleration) {
        
        properties.put("hardAcceleration",hardAcceleration==null?SensitivityType.WS_HARD_ACCEL_SETTING.getDefaultSetting():
                                hardAcceleration==99?SensitivityType.WS_HARD_ACCEL_SETTING.getSettingsCount()+1:hardAcceleration);
     }
     private void setHardBrake(Integer hardBrake) {
         
         properties.put("hardBrake",hardBrake==null?SensitivityType.WS_HARD_BRAKE_SETTING.getDefaultSetting():
                          hardBrake==99?SensitivityType.WS_HARD_BRAKE_SETTING.getSettingsCount()+1:hardBrake);
     }
     private void setHardTurn(Integer hardTurn) {

         properties.put("hardTurn",hardTurn==null?SensitivityType.WS_HARD_TURN_SETTING.getDefaultSetting():
                         hardTurn==99?SensitivityType.WS_HARD_TURN_SETTING.getSettingsCount()+1:hardTurn);
     }
     private void setHardVertical(Integer hardVertical) {
             
         properties.put("hardVertical",hardVertical==null?SensitivityType.WS_HARD_VERT_SETTING.getDefaultSetting():
                             hardVertical==99?SensitivityType.WS_HARD_VERT_SETTING.getSettingsCount()+1:hardVertical);
     }

    public class Properties extends HashMap<String,Object>{

         private static final long serialVersionUID = 1L;
         
         @Override
         public Object get(Object key) {

             if(key.equals("hardBrake")){
                 Integer hardBrake = (Integer)super.get(key);
                 if (hardBrake == null)
                     return SensitivityType.WS_HARD_BRAKE_SETTING.getDefaultSetting();
                 return hardBrake;
             }
             if(key.equals("hardAcceleration")){
                 
                 Integer hardAcceleration = (Integer)super.get(key);
                 if (hardAcceleration == null)
                     return SensitivityType.WS_HARD_ACCEL_SETTING.getDefaultSetting();
                 return hardAcceleration;
             }
             if(key.equals("hardVertical")){
                 
                 Integer hardVertical = (Integer)super.get(key);
                 if (hardVertical == null)
                     return SensitivityType.WS_HARD_VERT_SETTING.getDefaultSetting();
                 return hardVertical;
             }
             if(key.equals("hardTurn")){
                 
                 Integer hardTurn = (Integer)super.get(key);
                 if (hardTurn == null)
                     return SensitivityType.WS_HARD_TURN_SETTING.getDefaultSetting();
                 return hardTurn;
             }
             return null;

         }
    }
}
