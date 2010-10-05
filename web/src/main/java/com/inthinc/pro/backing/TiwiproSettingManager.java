package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.model.VehicleSettingManager;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.model.app.SensitivityMapping;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SensitivityType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class TiwiproSettingManager extends VehicleSettingManager{

    public TiwiproSettingManager(ConfiguratorDAO configuratorDAO, VehicleSetting vehicleSetting) {
        
        super(configuratorDAO,vehicleSetting);
        
    }
	@Override
    public void init() {
        
    }
    public EditableVehicleSettings createDefaultValues(Integer vehicleID){
        
        String ephone = "";
        Integer autoLogoffSeconds = 0;
        Integer hardVertical = SensitivityType.HARD_VERT_SETTING.getDefaultSetting();
        Integer hardTurn = SensitivityType.HARD_TURN_SETTING.getDefaultSetting();
        Integer hardAcceleration = SensitivityType.HARD_ACCEL_SETTING.getDefaultSetting();
        Integer hardBrake = SensitivityType.HARD_BRAKE_SETTING.getDefaultSetting();
        Integer[] speedSettings = convertFromSpeedSettings(VehicleSetting.DEFAULT_SPEED_SET);
                
        return new TiwiproEditableVehicleSettings(vehicleID==null?-1:vehicleID, ephone, autoLogoffSeconds, speedSettings, hardAcceleration, hardBrake, hardTurn,hardVertical);
    }
    
    public EditableVehicleSettings createFromExistingValues(VehicleSetting vs){
        
        String ephone = vs.getCombined(SensitivityType.EPHONE_SETTING.getSettingID());
        Integer autoLogoffSeconds = NumberUtil.convertString(vs.getCombined(SensitivityType.AUTOLOGOFF_SETTING.getSettingID()));
        Integer hardVertical = extractSliderValue(SensitivityType.HARD_VERT_SETTING,vs.getCombined(SensitivityType.HARD_VERT_SETTING.getSettingID()));
        Integer hardTurn = extractSliderValue(SensitivityType.HARD_TURN_SETTING,vs.getCombined(SensitivityType.HARD_TURN_SETTING.getSettingID()));
        Integer hardAcceleration = extractSliderValue(SensitivityType.HARD_ACCEL_SETTING,vs.getCombined(SensitivityType.HARD_ACCEL_SETTING.getSettingID()));
        Integer hardBrake = extractSliderValue(SensitivityType.HARD_BRAKE_SETTING,vs.getCombined(SensitivityType.HARD_BRAKE_SETTING.getSettingID()));
        Integer[] speedSettings = convertFromSpeedSettings(vs.getCombined(SensitivityType.SPEED_SETTING.getSettingID()));        

        settingCounts.put(vs.getVehicleID(),adjustedSettingCountsToAllowForCustomValues(hardVertical,hardTurn,hardAcceleration,hardBrake));
        
        return new TiwiproEditableVehicleSettings(vs.getVehicleID(),ephone, autoLogoffSeconds, speedSettings, hardAcceleration, hardBrake, hardTurn,hardVertical);
    }
    
    private Integer extractSliderValue(SensitivityType settingType, String setting){
        
        if (setting == null) {
            
            return settingType.getDefaultSetting();
        }
        return getMatchingSliderValue(settingType, setting);
    }
   
    private Integer getMatchingSliderValue(SensitivityType settingType, String setting){
     
        String[] settingValues = setting.split(" ");
        List<String> values = SensitivityMapping.getSensitivityMapping().get(settingType).getValues();
          
        for (String value:values){
              
            String[] tokens = value.split(" ");
            if (tokens[0].equals(settingValues[0]) && tokens[1].equals(settingValues[1])) return Integer.parseInt(tokens[2]);
        }
        return CUSTOM_SLIDER_VALUE;
    }
    private Integer[] convertFromSpeedSettings(String speedSet){
        
        if (speedSet == null) return createSpeedSettings();
        
        Integer[] speedSettingValues= new Integer[VehicleSetting.NUM_SPEEDS];
        String[] speeds = speedSet.split(" ");
        for (int i = 0; i < speeds.length; i++){
            
            speedSettingValues[i] = NumberUtil.convertString(speeds[i])-VehicleSetting.SPEED_LIMITS[i];
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
    private Map<SensitivityType,Integer> adjustedSettingCountsToAllowForCustomValues(Integer hardVertical, Integer hardTurn,Integer hardAcceleration, Integer hardBrake){
        
	   
	   	Map<SensitivityType,Integer> settingCount = new HashMap<SensitivityType,Integer>();
        
        settingCount.put(SensitivityType.HARD_ACCEL_SETTING, SensitivityType.HARD_ACCEL_SETTING.getSettingsCount()+(hardAcceleration==99?1:0));
        settingCount.put(SensitivityType.HARD_BRAKE_SETTING, SensitivityType.HARD_BRAKE_SETTING.getSettingsCount()+(hardBrake==99?1:0));
        settingCount.put(SensitivityType.HARD_TURN_SETTING,  SensitivityType.HARD_TURN_SETTING.getSettingsCount()+(hardTurn==99?1:0));
        settingCount.put(SensitivityType.HARD_VERT_SETTING,  SensitivityType.HARD_VERT_SETTING.getSettingsCount()+(hardVertical==99?1:0));

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
   public Map<Integer, String> evaluateSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings){
	   
	   try{
	       TiwiproEditableVehicleSettings tiwiproEditableVehicleSettings = downCastEditableVehicleSettings(editableVehicleSettings);
	       
	       if (vehicleSetting == null) {
	           
	           vehicleSetting = createVehicleSetting(vehicleID);
	       }
	       
	       NewSettings newSettings = new NewSettings();
	       newSettings.addSettingIfNeeded(SensitivityType.EPHONE_SETTING,
	    		   					  tiwiproEditableVehicleSettings.getEphone(),
	    		   					  vehicleSetting.getCombined(SensitivityType.EPHONE_SETTING.getSettingID()));
	       newSettings.addSettingIfNeeded(SensitivityType.AUTOLOGOFF_SETTING, 
	                                  ""+tiwiproEditableVehicleSettings.getAutologoffSeconds(), 
	                                  vehicleSetting.getCombined(SensitivityType.AUTOLOGOFF_SETTING.getSettingID()));
	       newSettings.addSettingIfNeeded(SensitivityType.HARD_VERT_SETTING,
                                      getHardVerticalValue(tiwiproEditableVehicleSettings.getHardVertical()),
                                      vehicleSetting.getCombined(SensitivityType.HARD_VERT_SETTING.getSettingID()));
	       newSettings.addSettingIfNeeded(SensitivityType.HARD_TURN_SETTING, 
	                                  getSensitivityValue(SensitivityType.HARD_TURN_SETTING,tiwiproEditableVehicleSettings.getHardTurn()), 
	                                  vehicleSetting.getCombined(SensitivityType.HARD_TURN_SETTING.getSettingID()));
	       newSettings.addSettingIfNeeded(SensitivityType.HARD_ACCEL_SETTING, 
	                                  getSensitivityValue(SensitivityType.HARD_ACCEL_SETTING,tiwiproEditableVehicleSettings.getHardAcceleration()), 
	                                  vehicleSetting.getCombined(SensitivityType.HARD_ACCEL_SETTING.getSettingID()));
	       newSettings.addSettingIfNeeded(SensitivityType.HARD_BRAKE_SETTING, 
	                                  getSensitivityValue(SensitivityType.HARD_BRAKE_SETTING,tiwiproEditableVehicleSettings.getHardBrake()), 
	                                  vehicleSetting.getCombined(SensitivityType.HARD_BRAKE_SETTING.getSettingID()));
	       newSettings.addSettingIfNeeded(SensitivityType.SPEED_SETTING, 
	                                  tiwiproEditableVehicleSettings.getSpeedSettingsString(), 
	                                  vehicleSetting.getCombined(SensitivityType.SPEED_SETTING.getSettingID()));
	       
	       return newSettings.getDesiredSettings();
       }
       catch(IllegalArgumentException iae){
       	
    	   return null;
       }
   }
   @Override
   public Map<Integer, String> evaluateChangedSettings(Boolean batchEdit, Map<String, Boolean> updateField,Integer vehicleID, EditableVehicleSettings editableVehicleSettings){
       
	   try{
	       TiwiproEditableVehicleSettings tiwiproEditableVehicleSettings = downCastEditableVehicleSettings(editableVehicleSettings);
       
	       if (vehicleSetting == null) {
	           
	           vehicleSetting = createVehicleSetting(vehicleID);
	       }
	       ChangedSettings changedSettings = new ChangedSettings(batchEdit,updateField);
	       
	       // Only get if different and if batchEdit is requested
	       changedSettings.addSettingIfNeeded(SensitivityType.EPHONE_SETTING,
    		   								  tiwiproEditableVehicleSettings.getEphone(),
    		   								  vehicleSetting.getCombined(SensitivityType.EPHONE_SETTING.getSettingID()));
	       changedSettings.addSettingIfNeeded(SensitivityType.AUTOLOGOFF_SETTING,
    		   								  ""+tiwiproEditableVehicleSettings.getAutologoffSeconds(),
    		   								  vehicleSetting.getCombined(SensitivityType.AUTOLOGOFF_SETTING.getSettingID()));
	       changedSettings.addSettingIfNeeded(SensitivityType.HARD_VERT_SETTING, 
    		   								  getHardVerticalValue(tiwiproEditableVehicleSettings.getHardVertical()),
    		   								  vehicleSetting.getCombined(SensitivityType.HARD_VERT_SETTING.getSettingID()));
	       changedSettings.addSettingIfNeeded(SensitivityType.HARD_TURN_SETTING,
    		   								  getSensitivityValue(SensitivityType.HARD_TURN_SETTING,tiwiproEditableVehicleSettings.getHardTurn()),
    		   								  vehicleSetting.getCombined(SensitivityType.HARD_TURN_SETTING.getSettingID()));
	       changedSettings.addSettingIfNeeded(SensitivityType.HARD_ACCEL_SETTING,
    		   								  getSensitivityValue(SensitivityType.HARD_ACCEL_SETTING,tiwiproEditableVehicleSettings.getHardAcceleration()),
    		   								  vehicleSetting.getCombined(SensitivityType.HARD_ACCEL_SETTING.getSettingID()));
	       changedSettings.addSettingIfNeeded(SensitivityType.HARD_BRAKE_SETTING,
    		   								  getSensitivityValue(SensitivityType.HARD_BRAKE_SETTING,tiwiproEditableVehicleSettings.getHardBrake()),
    		   								  vehicleSetting.getCombined(SensitivityType.HARD_BRAKE_SETTING.getSettingID()));
	       changedSettings.addSettingIfNeeded(SensitivityType.SPEED_SETTING,
    		   								  tiwiproEditableVehicleSettings.getSpeedSettingsString(),
    		   								  vehicleSetting.getCombined(SensitivityType.SPEED_SETTING.getSettingID()));
	       
	       return changedSettings.getDesiredSettings();
       }
       catch(IllegalArgumentException iae){
       	
    	   return null;
       }
   }
   private VehicleSetting createVehicleSetting(Integer vehicleID){
       
       VehicleSetting vehicleSetting = new VehicleSetting();
       vehicleSetting.setVehicleID(vehicleID);
       vehicleSetting.setDesired(new HashMap<Integer, String>());
       vehicleSetting.setProductType(ProductType.TIWIPRO_R74);

       return vehicleSetting;
   }

  private TiwiproEditableVehicleSettings downCastEditableVehicleSettings(EditableVehicleSettings editableVehicleSettings) throws IllegalArgumentException{
  	
	if (!(editableVehicleSettings instanceof TiwiproEditableVehicleSettings)){
		
		throw new IllegalArgumentException();
	}
	return(TiwiproEditableVehicleSettings)editableVehicleSettings;
  }

  @Override
  public void setVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason){
      
      Map<Integer, String> setMap = evaluateSettings(vehicleID,editableVehicleSettings);
      configuratorDAO.setVehicleSettings(vehicleID, setMap, userID, reason);
  }
  
  @Override
  public void updateVehicleSettings(boolean batchEdit, Map<String, Boolean> updateField,Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason){
     
      Map<Integer, String> setMap = evaluateChangedSettings(batchEdit, updateField, vehicleID,editableVehicleSettings);
      configuratorDAO.updateVehicleSettings(vehicleID, setMap, userID, reason);
  }
  
}
