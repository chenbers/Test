package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.backing.model.VehicleSettingManager;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.configurator.SliderType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class WaySmartSettingManager extends VehicleSettingManager {

	
    public WaySmartSettingManager(ConfiguratorDAO configuratorDAO, VehicleSetting vehicleSetting) {
        
        super(configuratorDAO,vehicleSetting);

    }
    @Override
    public void init() {
 
    }
    protected EditableVehicleSettings createDefaultValues(Integer vehicleID){
        
        Integer speedLimit = 5;
        Integer speedBuffer = 10;
        Integer severeSpeed = 15;
        Integer hardVertical = getDefaultSettings().get(SliderType.HARD_BUMP_SLIDER);
        Integer hardTurn =  getDefaultSettings().get(SliderType.HARD_TURN_SLIDER);
        Integer hardAcceleration =  getDefaultSettings().get(SliderType.HARD_ACCEL_SLIDER);
        Integer hardBrake = getDefaultSettings().get(SliderType.HARD_BRAKE_SLIDER);
        
        return new WaySmartEditableVehicleSettings(vehicleID==null?-1:vehicleID, speedLimit,speedBuffer,severeSpeed,
                                        hardAcceleration, hardBrake, hardTurn,hardVertical);
    }
    
    protected EditableVehicleSettings createFromExistingValues(VehicleSetting vs){
        
        Integer speedLimit  = NumberUtil.convertString(vs.getCombined(SettingType.SPEED_LIMIT.getSettingID()));
        Integer speedBuffer = NumberUtil.convertString(vs.getCombined(SettingType.SPEED_BUFFER.getSettingID()));
        Integer severeSpeed = NumberUtil.convertString(vs.getCombined(SettingType.SEVERE_SPEED.getSettingID()));
 
        Integer hardVertical = extractSliderValue(SliderType.HARD_BUMP_SLIDER,getVehiclSettingsForSliderSettingIDs(vs,vehicleSensitivitySliders.getSensitivitySliderSettings(SliderType.HARD_BUMP_SLIDER)));
        Integer hardTurn = extractSliderValue(SliderType.HARD_TURN_SLIDER,getVehiclSettingsForSliderSettingIDs(vs,vehicleSensitivitySliders.getSensitivitySliderSettings(SliderType.HARD_TURN_SLIDER)));
        Integer hardAcceleration = extractSliderValue(SliderType.HARD_ACCEL_SLIDER,getVehiclSettingsForSliderSettingIDs(vs,vehicleSensitivitySliders.getSensitivitySliderSettings(SliderType.HARD_ACCEL_SLIDER)));
        Integer hardBrake = extractSliderValue(SliderType.HARD_BRAKE_SLIDER,getVehiclSettingsForSliderSettingIDs(vs,vehicleSensitivitySliders.getSensitivitySliderSettings(SliderType.HARD_BRAKE_SLIDER)));

        adjustedSettingCounts = adjustedSettingCountsToAllowForCustomValues(hardVertical,hardTurn,hardAcceleration,hardBrake);
        return new WaySmartEditableVehicleSettings(vs.getVehicleID(), speedLimit,speedBuffer,severeSpeed, 
                                          hardAcceleration, hardBrake, hardTurn,hardVertical);
    }
    
    @Override
    public Map<Integer, String> evaluateChangedSettings(Boolean batchEdit, Map<String, Boolean> updateField, Integer vehicleID, EditableVehicleSettings editableVehicleSettings) {

    	try{
    		
	    	WaySmartEditableVehicleSettings waySmartEditableVehicleSettings = downCastEditableVehicleSettings(editableVehicleSettings);
	    	
	        if (vehicleSetting == null) {
	            
	            vehicleSetting = createVehicleSetting(vehicleID);
	        }
	        ChangedSettings changedSettings = new ChangedSettings(batchEdit,updateField);

	        //need to get individual settings from sliders and fields
            changedSettings.addSettingIfNeeded(SettingType.SPEED_LIMIT,
                                               ""+waySmartEditableVehicleSettings.getSpeedLimit(), 
                                               vehicleSetting.getCombined(SettingType.SPEED_LIMIT.getSettingID()));
	        
            changedSettings.addSettingIfNeeded(SettingType.SPEED_BUFFER, 
	                                           ""+waySmartEditableVehicleSettings.getSpeedBuffer(), 
	                                           vehicleSetting.getCombined(SettingType.SPEED_BUFFER.getSettingID()));
            changedSettings.addSettingIfNeeded(SettingType.SEVERE_SPEED, 
	                                           ""+waySmartEditableVehicleSettings.getSevereSpeed(), 
	                                           vehicleSetting.getCombined(SettingType.SEVERE_SPEED.getSettingID()));
	        getSliderValuesForHardVertical(changedSettings,waySmartEditableVehicleSettings);
	        getSliderValuesForHardAcceleration(changedSettings,waySmartEditableVehicleSettings);
	        getSliderValuesForHardBrake(changedSettings,waySmartEditableVehicleSettings);
	        getSliderValuesForHardTurn(changedSettings,waySmartEditableVehicleSettings);
	        
	        return changedSettings.getDesiredSettings();
	    }
	    catch(IllegalArgumentException iae){
	    	
	    	return null;
	    }
        
    }

    @Override
    public Map<Integer, String> evaluateSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings) {
    	
    	try{
	    	WaySmartEditableVehicleSettings waySmartEditableVehicleSettings = this.downCastEditableVehicleSettings(editableVehicleSettings);
	        
	        if (vehicleSetting == null) {
	            
	            vehicleSetting = createVehicleSetting(vehicleID);
	        }
	        NewSettings newSettings = new NewSettings();

	        //need to get individual settings from sliders and fields
	        
	        newSettings.addSettingIfNeeded(SettingType.SPEED_LIMIT, 
	                                       ""+waySmartEditableVehicleSettings.getSpeedLimit(), 
	                                       vehicleSetting.getCombined(SettingType.SPEED_LIMIT.getSettingID()));
	        newSettings.addSettingIfNeeded(SettingType.SPEED_BUFFER, 
	                                       ""+waySmartEditableVehicleSettings.getSpeedBuffer(), 
	                                       vehicleSetting.getCombined(SettingType.SPEED_BUFFER.getSettingID()));
	        newSettings.addSettingIfNeeded(SettingType.SEVERE_SPEED, 
	                                       ""+waySmartEditableVehicleSettings.getSevereSpeed(), 
	                                       vehicleSetting.getCombined(SettingType.SEVERE_SPEED.getSettingID()));
	        getSliderValuesForHardVertical(newSettings,waySmartEditableVehicleSettings);
	        getSliderValuesForHardAcceleration(newSettings,waySmartEditableVehicleSettings);
	        getSliderValuesForHardBrake(newSettings,waySmartEditableVehicleSettings);
	        getSliderValuesForHardTurn(newSettings,waySmartEditableVehicleSettings);
	        
	        return newSettings.getDesiredSettings();
        
        }
        catch(IllegalArgumentException iae){
        	
        	return null;
        }
    }
    private void getSliderValuesForHardVertical(DesiredSettings desiredSettings,WaySmartEditableVehicleSettings waySmartEditableVehicleSettings){
        
        Map<Integer,String> settingValues = getSensitivityValue(SliderType.HARD_BUMP_SLIDER,waySmartEditableVehicleSettings.getHardVertical());

    	desiredSettings.addSettingIfNeeded(SettingType.SEVERE_HARDVERT_LEVEL, 
    	                                   settingValues.get(SettingType.SEVERE_HARDVERT_LEVEL.getSettingID()), 
                                           vehicleSetting.getCombined(SettingType.SEVERE_HARDVERT_LEVEL.getSettingID()));
    	desiredSettings.addSettingIfNeeded(SettingType.HARDVERT_DMM_PEAKTOPEAK, 
    	                                   settingValues.get(SettingType.HARDVERT_DMM_PEAKTOPEAK.getSettingID()), 
                                           vehicleSetting.getCombined(SettingType.HARDVERT_DMM_PEAKTOPEAK.getSettingID()));
    	desiredSettings.addSettingIfNeeded(SettingType.RMS_LEVEL, 
    	                                   settingValues.get(SettingType.RMS_LEVEL.getSettingID()), 
                                           vehicleSetting.getCombined(SettingType.RMS_LEVEL.getSettingID()));
    }
    private void getSliderValuesForHardTurn(DesiredSettings desiredSettings,WaySmartEditableVehicleSettings waySmartEditableVehicleSettings){
        	
    	Map<Integer, String> hardTurn = getSensitivityValue(SliderType.HARD_TURN_SLIDER,waySmartEditableVehicleSettings.getHardVertical());
    	desiredSettings.addSettingIfNeeded(SettingType.DVY, 
    	                                   hardTurn.get(SettingType.DVY), 
    	                                   vehicleSetting.getCombined(SettingType.DVY.getSettingID()));
    	desiredSettings.addSettingIfNeeded(SettingType.Y_LEVEL, 
                                           hardTurn.get(SettingType.Y_LEVEL), 
                                           vehicleSetting.getCombined(SettingType.Y_LEVEL.getSettingID()));
    }
    private  void getSliderValuesForHardAcceleration(DesiredSettings desiredSettings,WaySmartEditableVehicleSettings waySmartEditableVehicleSettings){
        
        Map<Integer, String> hardAcceleration = getSensitivityValue(SliderType.HARD_ACCEL_SLIDER,waySmartEditableVehicleSettings.getHardAcceleration());
        desiredSettings.addSettingIfNeeded(SettingType.HARD_ACCEL_LEVEL, 
                                           hardAcceleration.get(SettingType.HARD_ACCEL_LEVEL), 
                                           vehicleSetting.getCombined(SettingType.HARD_ACCEL_LEVEL.getSettingID()));
        desiredSettings.addSettingIfNeeded(SettingType.HARD_ACCEL_DELTAV, 
                                           hardAcceleration.get(SettingType.HARD_ACCEL_DELTAV), 
                                           vehicleSetting.getCombined(SettingType.HARD_ACCEL_DELTAV.getSettingID()));
    }
    private  void getSliderValuesForHardBrake(DesiredSettings desiredSettings,WaySmartEditableVehicleSettings waySmartEditableVehicleSettings){

        Map<Integer, String> hardBrake = getSensitivityValue(SliderType.HARD_BRAKE_SLIDER,waySmartEditableVehicleSettings.getHardVertical());
        desiredSettings.addSettingIfNeeded(SettingType.X_ACCEL, 
	                                       hardBrake.get(SettingType.X_ACCEL), 
	                                       vehicleSetting.getCombined(SettingType.X_ACCEL.getSettingID()));
        desiredSettings.addSettingIfNeeded(SettingType.DVX, 
	                                       hardBrake.get(SettingType.DVX), 
	                                       vehicleSetting.getCombined(SettingType.DVX.getSettingID()));
     }
    private VehicleSetting createVehicleSetting(Integer vehicleID){
        
        VehicleSetting vehicleSetting = new VehicleSetting();
        vehicleSetting.setVehicleID(vehicleID);
        vehicleSetting.setDesired(new HashMap<Integer, String>());
        vehicleSetting.setProductType(ProductType.WS820);

        return vehicleSetting;
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
    public class Properties extends HashMap<String,Object>{
        
        private static final long serialVersionUID = 1L;

    }
    private WaySmartEditableVehicleSettings downCastEditableVehicleSettings(EditableVehicleSettings editableVehicleSettings) throws IllegalArgumentException{
    	
		if (!(editableVehicleSettings instanceof WaySmartEditableVehicleSettings)){
			
			throw new IllegalArgumentException();
		}
		return(WaySmartEditableVehicleSettings)editableVehicleSettings;
    }
}
