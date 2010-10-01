package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.model.VehicleSettingManager;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.app.SensitivityMapping;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SensitivityType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class TiwiproSettingManager extends VehicleSettingManager{

    private static final Integer CUSTOM_SLIDER_VALUE = 99;

    public TiwiproSettingManager(ConfiguratorDAO configuratorDAO, VehicleSetting vehicleSetting) {
        
        super(configuratorDAO,vehicleSetting);
        
        properties = new Properties();
        properties.put("defaultSettings",SensitivityType.getDefaultSettings());
        properties.put("sensitivities", SensitivityType.getSensitivities());
        properties.put("settingCounts",new HashMap<Integer,Map<Integer,Integer>>());
    }
    @Override
    public void init() {
        
    }
    @Override
    public VehicleSettingAdapter associateSettings(Integer vehicleID){
        
        if (vehicleSetting == null){
            
            return createDefaultValues(vehicleID); 

        }
        else {
            return createFromExistingValues(vehicleSetting);
        }
    }

    private VehicleSettingAdapter createDefaultValues(Integer vehicleID){
        
        String ephone = "";
        Integer autoLogoffSeconds = 0;
        Integer hardVertical = SensitivityType.HARD_VERT_SETTING.getDefaultSetting();
        Integer hardTurn = SensitivityType.HARD_TURN_SETTING.getDefaultSetting();
        Integer hardAcceleration = SensitivityType.HARD_ACCEL_SETTING.getDefaultSetting();
        Integer hardBrake = SensitivityType.HARD_BRAKE_SETTING.getDefaultSetting();
        Integer[] speedSettings = convertFromSpeedSettings(VehicleSetting.DEFAULT_SPEED_SET);
                
        return new TiwiproSettingAdapter(vehicleID==null?-1:vehicleID, ephone, autoLogoffSeconds, speedSettings, hardAcceleration, hardBrake, hardTurn,hardVertical);
    }
    
   @SuppressWarnings("unchecked")
   private VehicleSettingAdapter createFromExistingValues(VehicleSetting vs){
        
        String ephone = vs.getCombined(SensitivityType.EPHONE_SETTING.getSettingID());
        Integer autoLogoffSeconds = convertString(vs.getCombined(SensitivityType.AUTOLOGOFF_SETTING.getSettingID()));
        Integer hardVertical = extractSliderValue(SensitivityType.HARD_VERT_SETTING,vs.getCombined(SensitivityType.HARD_VERT_SETTING.getSettingID()));
        Integer hardTurn = extractSliderValue(SensitivityType.HARD_TURN_SETTING,vs.getCombined(SensitivityType.HARD_TURN_SETTING.getSettingID()));
        Integer hardAcceleration = extractSliderValue(SensitivityType.HARD_ACCEL_SETTING,vs.getCombined(SensitivityType.HARD_ACCEL_SETTING.getSettingID()));
        Integer hardBrake = extractSliderValue(SensitivityType.HARD_BRAKE_SETTING,vs.getCombined(SensitivityType.HARD_BRAKE_SETTING.getSettingID()));
        Integer[] speedSettings = convertFromSpeedSettings(vs.getCombined(SensitivityType.SPEED_SETTING.getSettingID()));        

        Map<Integer,Map<Integer,Integer>> settingCounts = (Map<Integer,Map<Integer,Integer>>)properties.get("settingCounts");
        settingCounts.put(vs.getVehicleID(),adjustedSettingCounts(hardVertical,hardTurn,hardAcceleration,hardBrake));
        
        return new TiwiproSettingAdapter(vs.getVehicleID(),ephone, autoLogoffSeconds, speedSettings, hardAcceleration, hardBrake, hardTurn,hardVertical);
   }
    
   private Integer extractSliderValue(SensitivityType settingType, String setting){
        
        if (setting == null) {
            
            return settingType.getDefaultSetting();
        }
        String[] values = setting.split(" ");
        
        return getMatchingSliderValue(settingType, values);
   }
   private Integer[] convertFromSpeedSettings(String speedSet){
        
        if (speedSet == null) return createSpeedSettings();
        
        Integer[] speedSettingValues= new Integer[VehicleSetting.NUM_SPEEDS];
        String[] speeds = speedSet.split(" ");
        for (int i = 0; i < speeds.length; i++){
            
            speedSettingValues[i] = convertString(speeds[i])-VehicleSetting.SPEED_LIMITS[i];
        }
        return speedSettingValues;
   }
    
   private Integer[] createSpeedSettings(){
       
       Integer[] speedSettings = new Integer[VehicleSetting.NUM_SPEEDS];
       for (int i = 0; i < VehicleSetting.NUM_SPEEDS; i++){
           speedSettings[i] = VehicleSetting.DEFAULT_SPEED_SETTING; 
       }
       return speedSettings;
   }
   private Map<Integer,Integer> adjustedSettingCounts(Integer hardVertical, Integer hardTurn,Integer hardAcceleration, Integer hardBrake){
        
        Map<Integer,Integer> settingCount = new HashMap<Integer, Integer>();
        
        settingCount.put(SensitivityType.HARD_ACCEL_SETTING.getSettingID(), SensitivityType.HARD_ACCEL_SETTING.getSettingsCount()+(hardAcceleration==99?1:0));
        settingCount.put(SensitivityType.HARD_BRAKE_SETTING.getSettingID(),SensitivityType.HARD_BRAKE_SETTING.getSettingsCount()+(hardBrake==99?1:0));
        settingCount.put(SensitivityType.HARD_TURN_SETTING.getSettingID(), SensitivityType.HARD_TURN_SETTING.getSettingsCount()+(hardTurn==99?1:0));
        settingCount.put(SensitivityType.HARD_VERT_SETTING.getSettingID(), SensitivityType.HARD_VERT_SETTING.getSettingsCount()+(hardVertical==99?1:0));

        return settingCount;
   }
    
   private String getSensitivityValue(SensitivityType settingType, Integer sliderValue){
       
       if (sliderValue > settingType.getSettingsCount()) return null;
       
       return SensitivityMapping.getSensitivityMapping().get(settingType).getValues().get(sliderValue-1);
   }
   private String getHardVerticalValue(Integer sliderValue){
       
       if (sliderValue > SensitivityType.HARD_VERT_SETTING.getSettingsCount()) return null;
       String value = SensitivityMapping.getSensitivityMapping().get(SensitivityType.HARD_VERT_SETTING).getValues().get(sliderValue-1);
       value +=" ";
       value +=SensitivityMapping.getSensitivityMapping().get(SensitivityType.SEVERE_PEAK_2_PEAK).getValues().get(sliderValue-1);
       return value;
  }
   @Override
   public Map<Integer, String> evaluateSettings(Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter){

       TiwiproSettingAdapter tiwiproSettingAdapter= (TiwiproSettingAdapter)vehicleSettingAdapter;
       
       if (vehicleSetting == null) {
           
           vehicleSetting = createVehicleSetting(vehicleID);
       }
       Map<Integer, String> desiredSettings = new HashMap<Integer, String>();
       
       // Only get if different
       desiredSettings = addIfDifferent(SensitivityType.EPHONE_SETTING.getSettingID(), 
                                        ""+tiwiproSettingAdapter.getProperties().get("ephone"), 
                                        vehicleSetting.getCombined(SensitivityType.EPHONE_SETTING.getSettingID()),
                                        desiredSettings);
       desiredSettings = addIfDifferent(SensitivityType.AUTOLOGOFF_SETTING.getSettingID(), 
                                        ""+tiwiproSettingAdapter.getProperties().get("autoLogoffSeconds"), 
                                        vehicleSetting.getCombined(SensitivityType.AUTOLOGOFF_SETTING.getSettingID()),
                                        desiredSettings);
       desiredSettings = addIfDifferent(SensitivityType.HARD_VERT_SETTING.getSettingID(),
                                        getHardVerticalValue((Integer)tiwiproSettingAdapter.getProperties().get("hardVertical")),
                                        vehicleSetting.getCombined(SensitivityType.HARD_VERT_SETTING.getSettingID()),
                                        desiredSettings);
       desiredSettings = addIfDifferent(SensitivityType.HARD_TURN_SETTING.getSettingID(), 
                                        getSensitivityValue(SensitivityType.HARD_TURN_SETTING,(Integer)tiwiproSettingAdapter.getProperties().get("hardTurn")), 
                                        vehicleSetting.getCombined(SensitivityType.HARD_TURN_SETTING.getSettingID()),
                                        desiredSettings);
       desiredSettings = addIfDifferent(SensitivityType.HARD_ACCEL_SETTING.getSettingID(), 
                                        getSensitivityValue(SensitivityType.HARD_ACCEL_SETTING,(Integer)tiwiproSettingAdapter.getProperties().get("hardAcceleration")), 
                                        vehicleSetting.getCombined(SensitivityType.HARD_ACCEL_SETTING.getSettingID()),
                                        desiredSettings);
       desiredSettings = addIfDifferent(SensitivityType.HARD_BRAKE_SETTING.getSettingID(), 
                                        getSensitivityValue(SensitivityType.HARD_BRAKE_SETTING,(Integer)tiwiproSettingAdapter.getProperties().get("hardBrake")), 
                                        vehicleSetting.getCombined(SensitivityType.HARD_BRAKE_SETTING.getSettingID()),
                                        desiredSettings);
       desiredSettings = addIfDifferent(SensitivityType.SPEED_SETTING.getSettingID(), 
                                        convertToSpeedSettings((Integer[])tiwiproSettingAdapter.getProperties().get("speedSettings")), 
                                        vehicleSetting.getCombined(SensitivityType.SPEED_SETTING.getSettingID()),
                                        desiredSettings);
       
       return desiredSettings;
   }
   @Override
   public Map<Integer, String> evaluateChangedSettings(Boolean batchEdit, Map<String, Boolean> updateField,Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter){
       
       TiwiproSettingAdapter tiwiproSettingAdapter= (TiwiproSettingAdapter)vehicleSettingAdapter;
       
       if (vehicleSetting == null) {
           
           vehicleSetting = createVehicleSetting(vehicleID);
       }
       Map<Integer, String> desiredSettings = new HashMap<Integer, String>();
       
       // Only get if different and if batchEdit is requested
       if(isRequested(batchEdit,updateField,SensitivityType.EPHONE_SETTING)){
           
           desiredSettings = addIfDifferent(SensitivityType.EPHONE_SETTING.getSettingID(), (String)tiwiproSettingAdapter.getProperties().get("ephone"), vehicleSetting.getCombined(SensitivityType.EPHONE_SETTING.getSettingID()),desiredSettings);
       }
       if(isRequested(batchEdit,updateField,SensitivityType.AUTOLOGOFF_SETTING)){
           
           desiredSettings = addIfDifferent(SensitivityType.AUTOLOGOFF_SETTING.getSettingID(), ""+tiwiproSettingAdapter.getProperties().get("autoLogoffSeconds"), vehicleSetting.getCombined(SensitivityType.AUTOLOGOFF_SETTING.getSettingID()),desiredSettings);
       }
       if(isRequested(batchEdit,updateField,SensitivityType.HARD_VERT_SETTING)){
           
           desiredSettings = addIfDifferent(SensitivityType.HARD_VERT_SETTING.getSettingID(), getHardVerticalValue((Integer)tiwiproSettingAdapter.getProperties().get("hardVertical")), vehicleSetting.getCombined(SensitivityType.HARD_VERT_SETTING.getSettingID()),desiredSettings);
       }
       if(isRequested(batchEdit,updateField,SensitivityType.HARD_TURN_SETTING)){
           
           desiredSettings = addIfDifferent(SensitivityType.HARD_TURN_SETTING.getSettingID(), getSensitivityValue(SensitivityType.HARD_TURN_SETTING,(Integer)tiwiproSettingAdapter.getProperties().get("hardTurn")), vehicleSetting.getCombined(SensitivityType.HARD_TURN_SETTING.getSettingID()),desiredSettings);
       }
       if(isRequested(batchEdit,updateField,SensitivityType.HARD_ACCEL_SETTING)){
           
           desiredSettings = addIfDifferent(SensitivityType.HARD_ACCEL_SETTING.getSettingID(), getSensitivityValue(SensitivityType.HARD_ACCEL_SETTING,(Integer)tiwiproSettingAdapter.getProperties().get("hardAcceleration")), vehicleSetting.getCombined(SensitivityType.HARD_ACCEL_SETTING.getSettingID()),desiredSettings);
       }
       if(isRequested(batchEdit,updateField,SensitivityType.HARD_BRAKE_SETTING)){
           
           desiredSettings = addIfDifferent(SensitivityType.HARD_BRAKE_SETTING.getSettingID(), getSensitivityValue(SensitivityType.HARD_BRAKE_SETTING,(Integer)tiwiproSettingAdapter.getProperties().get("hardBrake")), vehicleSetting.getCombined(SensitivityType.HARD_BRAKE_SETTING.getSettingID()),desiredSettings);
       }
       if(isRequested(batchEdit,updateField,SensitivityType.SPEED_SETTING)){
           
           desiredSettings = addIfDifferent(SensitivityType.SPEED_SETTING.getSettingID(), convertToSpeedSettings((Integer[])tiwiproSettingAdapter.getProperties().get("speedSettings")), vehicleSetting.getCombined(SensitivityType.SPEED_SETTING.getSettingID()),desiredSettings);
       }
       
       return desiredSettings;
   }
   private boolean isRequested(Boolean batchEdit, Map<String, Boolean> updateField, SensitivityType setting){
       
       if (!batchEdit) return true;
       if (updateField.get("vehicleSettingAdapter."+setting.getPropertyName())){
           return true;
       }
       return false;
   }
   private VehicleSetting createVehicleSetting(Integer vehicleID){
       
       VehicleSetting vehicleSetting = new VehicleSetting();
       vehicleSetting.setVehicleID(vehicleID);
       vehicleSetting.setDesired(new HashMap<Integer, String>());
       vehicleSetting.setProductType(ProductType.TIWIPRO_R74);

       return vehicleSetting;
   }
   private String convertToSpeedSettings(Integer[] speedSettingValues){
       
       StringBuilder speedSet = new StringBuilder();
       
       for (int i = 0; i<VehicleSetting.NUM_SPEEDS-1;i++){
           
          speedSet.append(speedSettingValues[i]+VehicleSetting.SPEED_LIMITS[i]);
          speedSet.append(' '); 
       }
       speedSet.append(speedSettingValues[VehicleSetting.NUM_SPEEDS-1]+VehicleSetting.SPEED_LIMITS[VehicleSetting.NUM_SPEEDS-1]);
       
       return speedSet.toString();
   }

   private Map<Integer, String> addIfDifferent(Integer settingID, String newValue, String oldValue, Map<Integer, String> changedSettings){
       
       if(newValue == null) return changedSettings;
       if((oldValue == null) || (!oldValue.equals(newValue))){
           
           changedSettings.put(settingID, newValue);
       }
       return changedSettings;
       
   }
   private Integer getMatchingSliderValue(SensitivityType settingType, String[] settingValues){
     
      List<String> values = SensitivityMapping.getSensitivityMapping().get(settingType).getValues();
      
      for (String value:values){
          
          String[] tokens = value.split(" ");
          if (tokens[0].equals(settingValues[0]) && tokens[1].equals(settingValues[1])) return Integer.parseInt(tokens[2]);
      }
      return CUSTOM_SLIDER_VALUE;
  }

  @Override
  public void setVehicleSettings(Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter, Integer userID, String reason){
      
      Map<Integer, String> setMap = evaluateSettings(vehicleID,vehicleSettingAdapter);
      configuratorDAO.setVehicleSettings(vehicleID, setMap, userID, reason);
  }
  @Override
  public void updateVehicleSettings(boolean batchEdit, Map<String, Boolean> updateField,Integer vehicleID, VehicleSettingAdapter vehicleSettingAdapter, Integer userID, String reason){
     
      Map<Integer, String> setMap = evaluateChangedSettings(batchEdit, updateField, vehicleID,vehicleSettingAdapter);
      configuratorDAO.updateVehicleSettings(vehicleID, setMap, userID, reason);
  }
  public class Properties extends HashMap<String,Object>{
      
      private static final long serialVersionUID = 1L;

  }

}
