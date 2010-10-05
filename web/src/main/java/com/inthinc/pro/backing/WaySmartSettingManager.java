package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.backing.model.VehicleSettingManager;
import com.inthinc.pro.backing.model.WaySmartSettingsAndSliders;
import com.inthinc.pro.backing.model.WaySmartSettingsAndSliders.Setting;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SensitivityType;
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
        Integer hardVertical = SensitivityType.WS_HARD_VERT_SETTING.getDefaultSetting();
        Integer hardTurn = SensitivityType.WS_HARD_TURN_SETTING.getDefaultSetting();
        Integer hardAcceleration = SensitivityType.WS_HARD_ACCEL_SETTING.getDefaultSetting();
        Integer hardBrake = SensitivityType.WS_HARD_BRAKE_SETTING.getDefaultSetting();
        
        return new WaySmartEditableVehicleSettings(vehicleID==null?-1:vehicleID, speedLimit,speedBuffer,severeSpeed,
                                        hardAcceleration, hardBrake, hardTurn,hardVertical);
    }
    
    protected EditableVehicleSettings createFromExistingValues(VehicleSetting vs){
        
        Integer speedLimit  = NumberUtil.convertString(vs.getCombined(SensitivityType.SPEED_LIMIT.getSettingID()));
        Integer speedBuffer = NumberUtil.convertString(vs.getCombined(SensitivityType.SPEED_BUFFER.getSettingID()));
        Integer severeSpeed = NumberUtil.convertString(vs.getCombined(SensitivityType.SEVERE_SPEED.getSettingID()));
 
        Integer hardVertical =  WaySmartSettingsAndSliders.getSettingsAndSliders(WaySmartSettingsAndSliders.SliderType.HARD_VERT_SETTING).getSlider(getSettingsForHardVertical(vs));
        Integer hardTurn =      WaySmartSettingsAndSliders.getSettingsAndSliders(WaySmartSettingsAndSliders.SliderType.HARD_TURN_SETTING).getSlider(getSettingsForHardTurn(vs));
        Integer hardAcceleration = WaySmartSettingsAndSliders.getSettingsAndSliders(WaySmartSettingsAndSliders.SliderType.HARD_ACCELERATION_SETTING).getSlider(getSettingsForHardAccel(vs));
        Integer hardBrake =     WaySmartSettingsAndSliders.getSettingsAndSliders(WaySmartSettingsAndSliders.SliderType.HARD_BRAKE_SETTING).getSlider(getSettingsForHardBrake(vs));

        settingCounts.put(vs.getVehicleID(),adjustedSettingCountsToAllowForCustomValues(hardVertical,hardTurn,hardAcceleration,hardBrake));
        return new WaySmartEditableVehicleSettings(vs.getVehicleID(), speedLimit,speedBuffer,severeSpeed, 
                                          hardAcceleration, hardBrake, hardTurn,hardVertical);
    }
    private Map<Integer, String> getSettingsForHardVertical(VehicleSetting vehicleSetting){
        
        Map<Integer, String> settings = new HashMap<Integer, String>();
        settings.put(Setting.RMS_LEVEL.getSettingID(), vehicleSetting.getCombined(Setting.RMS_LEVEL.getSettingID()));
        settings.put(Setting.SEVERE_HARDVERT_LEVEL.getSettingID(), vehicleSetting.getCombined(Setting.SEVERE_HARDVERT_LEVEL.getSettingID()));
        settings.put(Setting.HARDVERT_DMM_PEAKTOPEAK.getSettingID(), vehicleSetting.getCombined(Setting.HARDVERT_DMM_PEAKTOPEAK.getSettingID()));

        return settings;
    }
    private Map<Integer, String> getSettingsForHardTurn(VehicleSetting vehicleSetting){
        
        Map<Integer, String> settings = new HashMap<Integer, String>();
        settings.put(Setting.Y_LEVEL.getSettingID(), vehicleSetting.getCombined(Setting.Y_LEVEL.getSettingID()));
        settings.put(Setting.DVY.getSettingID(), vehicleSetting.getCombined(Setting.DVY.getSettingID()));

        return settings;
    }
    private Map<Integer, String> getSettingsForHardAccel(VehicleSetting vehicleSetting){
        
        Map<Integer, String> settings = new HashMap<Integer, String>();
        settings.put(Setting.HARD_ACCEL_LEVEL.getSettingID(), vehicleSetting.getCombined(Setting.HARD_ACCEL_LEVEL.getSettingID()));
        settings.put(Setting.HARD_ACCEL_DELTAV.getSettingID(), vehicleSetting.getCombined(Setting.HARD_ACCEL_DELTAV.getSettingID()));

        return settings;
    }
    private Map<Integer, String> getSettingsForHardBrake(VehicleSetting vehicleSetting){
        
        Map<Integer, String> settings = new HashMap<Integer, String>();
        settings.put(Setting.X_ACCEL.getSettingID(), vehicleSetting.getCombined(Setting.X_ACCEL.getSettingID()));
        settings.put(Setting.DVX.getSettingID(), vehicleSetting.getCombined(Setting.DVX.getSettingID()));

        return settings;
    }
   private Map<SensitivityType,Integer> adjustedSettingCountsToAllowForCustomValues(Integer hardVertical, Integer hardTurn,Integer hardAcceleration, Integer hardBrake){
       
       Map<SensitivityType,Integer> settingCount = new HashMap<SensitivityType, Integer>();
       
       settingCount.put(SensitivityType.WS_HARD_ACCEL_SETTING, SensitivityType.WS_HARD_ACCEL_SETTING.getSettingsCount()+(hardAcceleration==99?1:0));
       settingCount.put(SensitivityType.WS_HARD_BRAKE_SETTING, SensitivityType.WS_HARD_BRAKE_SETTING.getSettingsCount()+(hardBrake==99?1:0));
       settingCount.put(SensitivityType.WS_HARD_TURN_SETTING,  SensitivityType.WS_HARD_TURN_SETTING.getSettingsCount()+(hardTurn==99?1:0));
       settingCount.put(SensitivityType.WS_HARD_VERT_SETTING,  SensitivityType.WS_HARD_VERT_SETTING.getSettingsCount()+(hardVertical==99?1:0));

       return settingCount;
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
            changedSettings.addSettingIfNeeded(SensitivityType.SPEED_LIMIT,
                                               ""+waySmartEditableVehicleSettings.getSpeedLimit(), 
                                               vehicleSetting.getCombined(SensitivityType.SPEED_LIMIT.getSettingID()));
	        
            changedSettings.addSettingIfNeeded(SensitivityType.SPEED_BUFFER, 
	                                           ""+waySmartEditableVehicleSettings.getSpeedBuffer(), 
	                                           vehicleSetting.getCombined(SensitivityType.SPEED_BUFFER.getSettingID()));
            changedSettings.addSettingIfNeeded(SensitivityType.SEVERE_SPEED, 
	                                           ""+waySmartEditableVehicleSettings.getSevereSpeed(), 
	                                           vehicleSetting.getCombined(SensitivityType.SEVERE_SPEED.getSettingID()));
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
	        
	        newSettings.addSettingIfNeeded(SensitivityType.SPEED_LIMIT, 
	                                       ""+waySmartEditableVehicleSettings.getSpeedLimit(), 
	                                       vehicleSetting.getCombined(SensitivityType.SPEED_LIMIT.getSettingID()));
	        newSettings.addSettingIfNeeded(SensitivityType.SPEED_BUFFER, 
	                                       ""+waySmartEditableVehicleSettings.getSpeedBuffer(), 
	                                       vehicleSetting.getCombined(SensitivityType.SPEED_BUFFER.getSettingID()));
	        newSettings.addSettingIfNeeded(SensitivityType.SEVERE_SPEED, 
	                                       ""+waySmartEditableVehicleSettings.getSevereSpeed(), 
	                                       vehicleSetting.getCombined(SensitivityType.SEVERE_SPEED.getSettingID()));
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
        
    	Map<Integer, String> hardVertical = WaySmartSettingsAndSliders.getSettingsAndSliders(WaySmartSettingsAndSliders.SliderType.HARD_VERT_SETTING).getSettings(waySmartEditableVehicleSettings.getHardVertical());
    	desiredSettings.addSettingIfNeeded(SensitivityType.SEVERE_HARDVERT_LEVEL, 
                                        hardVertical.get(SensitivityType.SEVERE_HARDVERT_LEVEL), 
                                        vehicleSetting.getCombined(SensitivityType.SEVERE_HARDVERT_LEVEL.getSettingID()));
    	desiredSettings.addSettingIfNeeded(SensitivityType.HARDVERT_DMM_PEAKTOPEAK, 
                                           hardVertical.get(SensitivityType.HARDVERT_DMM_PEAKTOPEAK), 
                                           vehicleSetting.getCombined(SensitivityType.HARDVERT_DMM_PEAKTOPEAK.getSettingID()));
    	desiredSettings.addSettingIfNeeded(SensitivityType.RMS_LEVEL, 
                                           hardVertical.get(SensitivityType.RMS_LEVEL), 
                                           vehicleSetting.getCombined(SensitivityType.RMS_LEVEL.getSettingID()));
    }
    private void getSliderValuesForHardTurn(DesiredSettings desiredSettings,WaySmartEditableVehicleSettings waySmartEditableVehicleSettings){
        	
    	Map<Integer, String> hardTurn = WaySmartSettingsAndSliders.getSettingsAndSliders(WaySmartSettingsAndSliders.SliderType.HARD_TURN_SETTING).getSettings(waySmartEditableVehicleSettings.getHardTurn());
    	desiredSettings.addSettingIfNeeded(SensitivityType.DVY, 
    	                                   hardTurn.get(SensitivityType.DVY), 
    	                                   vehicleSetting.getCombined(SensitivityType.DVY.getSettingID()));
    	desiredSettings.addSettingIfNeeded(SensitivityType.Y_LEVEL, 
                                           hardTurn.get(SensitivityType.Y_LEVEL), 
                                           vehicleSetting.getCombined(SensitivityType.Y_LEVEL.getSettingID()));
    }
    private  void getSliderValuesForHardAcceleration(DesiredSettings desiredSettings,WaySmartEditableVehicleSettings waySmartEditableVehicleSettings){
        
        Map<Integer, String> hardAcceleration = WaySmartSettingsAndSliders.getSettingsAndSliders(WaySmartSettingsAndSliders.SliderType.HARD_ACCELERATION_SETTING).getSettings(waySmartEditableVehicleSettings.getHardAcceleration());
        desiredSettings.addSettingIfNeeded(SensitivityType.HARD_ACCEL_LEVEL, 
                                           hardAcceleration.get(SensitivityType.HARD_ACCEL_LEVEL), 
                                           vehicleSetting.getCombined(SensitivityType.HARD_ACCEL_LEVEL.getSettingID()));
        desiredSettings.addSettingIfNeeded(SensitivityType.HARD_ACCEL_DELTAV, 
                                           hardAcceleration.get(SensitivityType.HARD_ACCEL_DELTAV), 
                                           vehicleSetting.getCombined(SensitivityType.HARD_ACCEL_DELTAV.getSettingID()));
    }
    private  void getSliderValuesForHardBrake(DesiredSettings desiredSettings,WaySmartEditableVehicleSettings waySmartEditableVehicleSettings){

        Map<Integer, String> hardBrake = WaySmartSettingsAndSliders.getSettingsAndSliders(WaySmartSettingsAndSliders.SliderType.HARD_TURN_SETTING).getSettings(waySmartEditableVehicleSettings.getHardTurn());
        desiredSettings.addSettingIfNeeded(SensitivityType.X_ACCEL, 
	                                       hardBrake.get(SensitivityType.X_ACCEL), 
	                                       vehicleSetting.getCombined(SensitivityType.X_ACCEL.getSettingID()));
        desiredSettings.addSettingIfNeeded(SensitivityType.DVX, 
	                                       hardBrake.get(SensitivityType.DVX), 
	                                       vehicleSetting.getCombined(SensitivityType.DVX.getSettingID()));
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
