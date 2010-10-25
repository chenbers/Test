package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.backing.model.VehicleSettingManager;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.configurator.SliderType;
import com.inthinc.pro.model.configurator.TiwiproSpeedingConstants;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class TiwiproSettingManager extends VehicleSettingManager{

    public TiwiproSettingManager(ConfiguratorDAO configuratorDAO, ProductType productType, VehicleSetting vehicleSetting) {
        
        super(configuratorDAO,productType,vehicleSetting);
        
    }
	@Override
    public void init() {
    }

    public EditableVehicleSettings createDefaultValues(Integer vehicleID){
        
        String ephone = "";
        Integer autoLogoffSeconds = 0;
        Integer hardVertical = getDefaultSettings().get(SliderType.HARD_BUMP_SLIDER);
        Integer hardTurn =  getDefaultSettings().get(SliderType.HARD_TURN_SLIDER);
        Integer hardAcceleration =  getDefaultSettings().get(SliderType.HARD_ACCEL_SLIDER);
        Integer hardBrake = getDefaultSettings().get(SliderType.HARD_BRAKE_SLIDER);
        Integer[] speedSettings = convertFromSpeedSettings(TiwiproSpeedingConstants.INSTANCE.DEFAULT_SPEED_SET);
                
        return new TiwiproEditableVehicleSettings(vehicleID==null?-1:vehicleID, ephone, autoLogoffSeconds, speedSettings, hardAcceleration, hardBrake, hardTurn,hardVertical);
    }
    
    public EditableVehicleSettings createFromExistingValues(VehicleSetting vs){
        
        String ephone = vs.getCombined(SettingType.EPHONE_SETTING.getSettingID());
        Integer autoLogoffSeconds = NumberUtil.convertString(vs.getCombined(SettingType.AUTOLOGOFF_SETTING.getSettingID()));
        Integer hardVertical = extractSliderValue(SliderType.HARD_BUMP_SLIDER,getVehiclSettingsForSliderSettingIDs(vs,vehicleSensitivitySliders.getSensitivitySliderSettings(SliderType.HARD_BUMP_SLIDER)));
        Integer hardTurn = extractSliderValue(SliderType.HARD_TURN_SLIDER,getVehiclSettingsForSliderSettingIDs(vs,vehicleSensitivitySliders.getSensitivitySliderSettings(SliderType.HARD_TURN_SLIDER)));
        Integer hardAcceleration = extractSliderValue(SliderType.HARD_ACCEL_SLIDER,getVehiclSettingsForSliderSettingIDs(vs,vehicleSensitivitySliders.getSensitivitySliderSettings(SliderType.HARD_ACCEL_SLIDER)));
        Integer hardBrake = extractSliderValue(SliderType.HARD_BRAKE_SLIDER,getVehiclSettingsForSliderSettingIDs(vs,vehicleSensitivitySliders.getSensitivitySliderSettings(SliderType.HARD_BRAKE_SLIDER)));
        Integer[] speedSettings = convertFromSpeedSettings(vs.getCombined(SettingType.SPEED_SETTING.getSettingID()));        

        adjustedSettingCounts = vehicleSensitivitySliders.adjustedSettingCountsToAllowForCustomValues(hardVertical,hardTurn,hardAcceleration,hardBrake);
        
        return new TiwiproEditableVehicleSettings(vs.getVehicleID(),ephone, autoLogoffSeconds, speedSettings, hardAcceleration, hardBrake, hardTurn,hardVertical);
    }
    
    private Integer[] convertFromSpeedSettings(String speedSet){
        
        if (speedSet == null) return createSpeedSettings();
        
        Integer[] speedSettingValues= new Integer[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS];
        String[] speeds = speedSet.split(" ");
        for (int i = 0; i < speeds.length; i++){
            
            speedSettingValues[i] = NumberUtil.convertString(speeds[i])-TiwiproSpeedingConstants.INSTANCE.SPEED_LIMITS[i];
        }
        return speedSettingValues;
    }
    
    private Integer[] createSpeedSettings(){
       
        Integer[] speedSettings = new Integer[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS];
        for (int i = 0; i < TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS; i++){
            speedSettings[i] = TiwiproSpeedingConstants.INSTANCE.DEFAULT_SPEED_SETTING; 
        }
        return speedSettings;
    }
    private String getHardVerticalValue(Integer sliderValue){
       
       Map<Integer,String> settingValues = vehicleSensitivitySliders.getSensitivitySliderSettings(SliderType.HARD_BUMP_SLIDER).getSettingValuesFromSliderValue(sliderValue);
       if (settingValues.isEmpty()) return null;
       
       String value = settingValues.get(SettingType.HARD_VERT_SETTING.getSettingID());
       value +=" ";
       value +=settingValues.get(SettingType.SEVERE_PEAK_2_PEAK.getSettingID());
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
	       newSettings.addSettingIfNeeded(SettingType.EPHONE_SETTING,
	    		   					  tiwiproEditableVehicleSettings.getEphone(),
	    		   					  vehicleSetting.getCombined(SettingType.EPHONE_SETTING.getSettingID()));
	       newSettings.addSettingIfNeeded(SettingType.AUTOLOGOFF_SETTING, 
	                                  ""+tiwiproEditableVehicleSettings.getAutologoffSeconds(), 
	                                  vehicleSetting.getCombined(SettingType.AUTOLOGOFF_SETTING.getSettingID()));
	       newSettings.addSliderIfNeeded(SettingType.HARD_VERT_SETTING,
                                      getHardVerticalValue(tiwiproEditableVehicleSettings.getHardVertical()),
                                      vehicleSetting.getCombined(SettingType.HARD_VERT_SETTING.getSettingID()));
	       newSettings.addSliderIfNeeded(SettingType.HARD_TURN_SETTING, 
	                                  getSensitivityValue(SliderType.HARD_TURN_SLIDER,tiwiproEditableVehicleSettings.getHardTurn()).get(SettingType.HARD_TURN_SETTING.getSettingID()), 
	                                  vehicleSetting.getCombined(SettingType.HARD_TURN_SETTING.getSettingID()));
	       newSettings.addSliderIfNeeded(SettingType.HARD_ACCEL_SETTING, 
	                                  getSensitivityValue(SliderType.HARD_ACCEL_SLIDER,tiwiproEditableVehicleSettings.getHardAcceleration()).get(SettingType.HARD_TURN_SETTING.getSettingID()), 
	                                  vehicleSetting.getCombined(SettingType.HARD_ACCEL_SETTING.getSettingID()));
	       newSettings.addSliderIfNeeded(SettingType.HARD_BRAKE_SETTING, 
	                                  getSensitivityValue(SliderType.HARD_BRAKE_SLIDER,tiwiproEditableVehicleSettings.getHardBrake()).get(SettingType.HARD_TURN_SETTING.getSettingID()), 
	                                  vehicleSetting.getCombined(SettingType.HARD_BRAKE_SETTING.getSettingID()));
	       newSettings.addSettingIfNeeded(SettingType.SPEED_SETTING, 
	                                  tiwiproEditableVehicleSettings.getSpeedSettingsString(), 
	                                  vehicleSetting.getCombined(SettingType.SPEED_SETTING.getSettingID()));
	       
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
	       changedSettings.addSettingIfNeeded(SettingType.EPHONE_SETTING,
    		   								  tiwiproEditableVehicleSettings.getEphone(),
    		   								  vehicleSetting.getCombined(SettingType.EPHONE_SETTING.getSettingID()));
	       changedSettings.addSettingIfNeeded(SettingType.AUTOLOGOFF_SETTING,
    		   								  ""+tiwiproEditableVehicleSettings.getAutologoffSeconds(),
    		   								  vehicleSetting.getCombined(SettingType.AUTOLOGOFF_SETTING.getSettingID()));
	       changedSettings.addSliderIfNeeded(SettingType.HARD_VERT_SETTING, 
    		   								  getHardVerticalValue(tiwiproEditableVehicleSettings.getHardVertical()),
    		   								  vehicleSetting.getCombined(SettingType.HARD_VERT_SETTING.getSettingID()));
	       changedSettings.addSliderIfNeeded(SettingType.HARD_TURN_SETTING,
    		   								  getSensitivityValue(SliderType.HARD_TURN_SLIDER,tiwiproEditableVehicleSettings.getHardTurn()).get(SettingType.HARD_TURN_SETTING.getSettingID()),
    		   								  vehicleSetting.getCombined(SettingType.HARD_TURN_SETTING.getSettingID()));
	       changedSettings.addSliderIfNeeded(SettingType.HARD_ACCEL_SETTING,
    		   								  getSensitivityValue(SliderType.HARD_ACCEL_SLIDER,tiwiproEditableVehicleSettings.getHardAcceleration()).get(SettingType.HARD_TURN_SETTING.getSettingID()),
    		   								  vehicleSetting.getCombined(SettingType.HARD_ACCEL_SETTING.getSettingID()));
	       changedSettings.addSliderIfNeeded(SettingType.HARD_BRAKE_SETTING,
    		   								  getSensitivityValue(SliderType.HARD_BRAKE_SLIDER,tiwiproEditableVehicleSettings.getHardBrake()).get(SettingType.HARD_TURN_SETTING.getSettingID()),
    		   								  vehicleSetting.getCombined(SettingType.HARD_BRAKE_SETTING.getSettingID()));
	       changedSettings.addSettingIfNeeded(SettingType.SPEED_SETTING,
    		   								  tiwiproEditableVehicleSettings.getSpeedSettingsString(),
    		   								  vehicleSetting.getCombined(SettingType.SPEED_SETTING.getSettingID()));
	       
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
  
  public TiwiproSettingManager getThis(){
      
      return this;
  }
}
