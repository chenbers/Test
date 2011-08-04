package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.backing.model.VehicleSettingManager;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.model.VehicleDOTType;
import com.inthinc.pro.model.app.SensitivitySliders;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.util.Encrypt;

public class WaySmartSettingManager extends VehicleSettingManager {

/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final double DEFAULT_SPEED_LIMIT = 75.0;
    private static final double DEFAULT_SPEED_BUFFER = 2.0;
    private static final double DEFAULT_SEVERE_SPEED = 20.0;
    private static final String WIRELINE_KILL_MOTOR_PASSCODE_PREFIX = "90";
    private static final String WIRELINE_DOOR_ALARM_PASSCODE_PREFIX = "75";

    public WaySmartSettingManager(ConfiguratorDAO configuratorDAO, SensitivitySliders sensitivitySliders,
            ProductType productType, VehicleSetting vehicleSetting) {
        
        super(configuratorDAO,sensitivitySliders,productType, vehicleSetting);
    }
    public WaySmartSettingManager(ConfiguratorDAO configuratorDAO, SensitivitySliders sensitivitySliders,
                                    ProductType productType, Integer vehicleID, Integer deviceID) {
        
        super(configuratorDAO,sensitivitySliders,productType,vehicleID,deviceID);
    }
    @Override
    public void init() {
 
    }
    public EditableVehicleSettings createDefaultValues(Integer vehicleID){
        
        Double speedLimit = DEFAULT_SPEED_LIMIT;
        Double speedBuffer = DEFAULT_SPEED_BUFFER;
        Double severeSpeed = DEFAULT_SEVERE_SPEED;
        Integer hardVertical = hardVerticalSlider.getDefaultValueIndex();
        Integer hardTurn = hardTurnSlider.getDefaultValueIndex();
        Integer hardAcceleration =  hardAccelerationSlider.getDefaultValueIndex();
        Integer hardBrake = hardBrakeSlider.getDefaultValueIndex();
        
        
        return new WaySmartEditableVehicleSettings(vehicleID==null?-1:vehicleID, speedLimit,speedBuffer,severeSpeed,
                                        hardAcceleration, hardBrake, hardTurn,hardVertical, 
                                        null, null, 15, VehicleDOTType.NON_DOT.getConfiguratorSetting());
    }
    
    protected EditableVehicleSettings createFromExistingValues(Integer vehicleID, VehicleSetting vs){
        
        Double speedLimit  = NumberUtil.convertStringToDouble(vs.getCombined(SettingType.SPEED_LIMIT.getSettingID()));
        Double speedBuffer = NumberUtil.convertStringToDouble(vs.getCombined(SettingType.SPEED_BUFFER.getSettingID()));
        Double severeSpeed = NumberUtil.convertStringToDouble(vs.getCombined(SettingType.SEVERE_SPEED.getSettingID()));
 
        Integer hardVertical = hardVerticalSlider.getSliderValueFromSettings(vs);
        Integer hardTurn = hardTurnSlider.getSliderValueFromSettings(vs);
        Integer hardAcceleration = hardAccelerationSlider.getSliderValueFromSettings(vs);
        Integer hardBrake = hardBrakeSlider.getSliderValueFromSettings(vs);

        adjustCountsForCustomValues(hardAcceleration, hardBrake, hardTurn, hardVertical);
        
        String  doorAlarmPasscode = decryptPasscode(vs.getCombined(SettingType.WIRELINE_DOOR_ALARM_PASSCODE.getSettingID()));
        String  killMotorPasscode = decryptPasscode(vs.getCombined(SettingType.WIRELINE_KILL_MOTOR_PASSCODE.getSettingID()));
        Integer autoArmTime = NumberUtil.convertString(vs.getCombined(SettingType.WIRELINE_AUTO_ARM_TIME.getSettingID()));
        Integer dotVehicleType = NumberUtil.convertString(vs.getCombined(SettingType.DOT_VEHICLE_TYPE.getSettingID()));


        return new WaySmartEditableVehicleSettings(vehicleID, speedLimit,speedBuffer,severeSpeed, 
                                          hardAcceleration, hardBrake, hardTurn,hardVertical,
                                          doorAlarmPasscode, killMotorPasscode, autoArmTime, dotVehicleType);
    }
    private String decryptPasscode(String passcode){
    	if ((passcode != null) && (passcode.length()>=2)){
	    	String decryptedPasscode = Encrypt.decrypt(passcode);
	    	//Strip prepended characters
	    	decryptedPasscode = decryptedPasscode.substring(2);
	    	return decryptedPasscode;
    	}
    	return passcode;
    }
    
    // TODO: is there a difference between evaluateChangedSettings and evaluateSettings() ??
    
    @Override
    public Map<Integer, String> evaluateChangedSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings) {

    	try{
    		
	    	WaySmartEditableVehicleSettings waySmartEditableVehicleSettings = downCastEditableVehicleSettings(editableVehicleSettings);
	    	
	        if (vehicleSetting == null) {
	            
	            vehicleSetting = createVehicleSetting(vehicleID);
	        }
	        ChangedSettings changedSettings = new ChangedSettings();

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

	        
            changedSettings.addSettingIfNeeded(SettingType.WIRELINE_KILL_MOTOR_PASSCODE,
                    encryptPasscode(waySmartEditableVehicleSettings.getKillMotorPasscode(),WIRELINE_KILL_MOTOR_PASSCODE_PREFIX), 
                    vehicleSetting.getCombined(SettingType.WIRELINE_KILL_MOTOR_PASSCODE.getSettingID()));
            changedSettings.addSettingIfNeeded(SettingType.WIRELINE_DOOR_ALARM_PASSCODE,
            		encryptPasscode(waySmartEditableVehicleSettings.getDoorAlarmPasscode(),WIRELINE_DOOR_ALARM_PASSCODE_PREFIX), 
                    vehicleSetting.getCombined(SettingType.WIRELINE_DOOR_ALARM_PASSCODE.getSettingID()));
            changedSettings.addSettingIfNeeded(SettingType.WIRELINE_AUTO_ARM_TIME,
                    ""+waySmartEditableVehicleSettings.getAutoArmTime(), 
                    vehicleSetting.getCombined(SettingType.WIRELINE_AUTO_ARM_TIME.getSettingID()));

            changedSettings.addSettingIfNeeded(SettingType.DOT_VEHICLE_TYPE,
                    ""+waySmartEditableVehicleSettings.getDotVehicleType(), 
                    vehicleSetting.getCombined(SettingType.DOT_VEHICLE_TYPE.getSettingID()));
            
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
	        
	        newSettings.addSettingIfNeeded(SettingType.WIRELINE_KILL_MOTOR_PASSCODE,
                    encryptPasscode(waySmartEditableVehicleSettings.getKillMotorPasscode(),WIRELINE_KILL_MOTOR_PASSCODE_PREFIX), 
                    vehicleSetting.getCombined(SettingType.WIRELINE_KILL_MOTOR_PASSCODE.getSettingID()));
	        newSettings.addSettingIfNeeded(SettingType.WIRELINE_DOOR_ALARM_PASSCODE,
            		encryptPasscode(waySmartEditableVehicleSettings.getDoorAlarmPasscode(),WIRELINE_DOOR_ALARM_PASSCODE_PREFIX), 
                    vehicleSetting.getCombined(SettingType.WIRELINE_DOOR_ALARM_PASSCODE.getSettingID()));
	        newSettings.addSettingIfNeeded(SettingType.WIRELINE_AUTO_ARM_TIME,
                    ""+waySmartEditableVehicleSettings.getAutoArmTime(), 
                    vehicleSetting.getCombined(SettingType.WIRELINE_AUTO_ARM_TIME.getSettingID()));

            newSettings.addSettingIfNeeded(SettingType.DOT_VEHICLE_TYPE,
                    ""+waySmartEditableVehicleSettings.getDotVehicleType(), 
                    vehicleSetting.getCombined(SettingType.DOT_VEHICLE_TYPE.getSettingID()));
	        return newSettings.getDesiredSettings();
        
        }
        catch(IllegalArgumentException iae){
        	
        	return null;
        }
    }
    private void getSliderValuesForHardVertical(DesiredSettings desiredSettings,WaySmartEditableVehicleSettings waySmartEditableVehicleSettings){
        
        Map<Integer,String> settingValues = 
            hardVerticalSlider.getSettingValuesFromSliderValue(waySmartEditableVehicleSettings.getHardVertical());

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
        	
        Map<Integer,String> hardTurn = 
            hardTurnSlider.getSettingValuesFromSliderValue(waySmartEditableVehicleSettings.getHardTurn());
    	desiredSettings.addSettingIfNeeded(SettingType.DVY, 
    	                                   hardTurn.get(SettingType.DVY.getSettingID()), 
    	                                   vehicleSetting.getCombined(SettingType.DVY.getSettingID()));
    	desiredSettings.addSettingIfNeeded(SettingType.Y_LEVEL, 
                                           hardTurn.get(SettingType.Y_LEVEL.getSettingID()), 
                                           vehicleSetting.getCombined(SettingType.Y_LEVEL.getSettingID()));
    }
    private  void getSliderValuesForHardAcceleration(DesiredSettings desiredSettings,WaySmartEditableVehicleSettings waySmartEditableVehicleSettings){
        
        Map<Integer,String> hardAcceleration = 
            hardAccelerationSlider.getSettingValuesFromSliderValue(waySmartEditableVehicleSettings.getHardAcceleration());
        desiredSettings.addSettingIfNeeded(SettingType.HARD_ACCEL_LEVEL, 
                                           hardAcceleration.get(SettingType.HARD_ACCEL_LEVEL.getSettingID()), 
                                           vehicleSetting.getCombined(SettingType.HARD_ACCEL_LEVEL.getSettingID()));
        desiredSettings.addSettingIfNeeded(SettingType.HARD_ACCEL_DELTAV, 
                                           hardAcceleration.get(SettingType.HARD_ACCEL_DELTAV.getSettingID()), 
                                           vehicleSetting.getCombined(SettingType.HARD_ACCEL_DELTAV.getSettingID()));
    }
    private  void getSliderValuesForHardBrake(DesiredSettings desiredSettings,WaySmartEditableVehicleSettings waySmartEditableVehicleSettings){

        Map<Integer,String> hardBrake = 
            hardBrakeSlider.getSettingValuesFromSliderValue(waySmartEditableVehicleSettings.getHardBrake());
        desiredSettings.addSettingIfNeeded(SettingType.X_ACCEL, 
	                                       hardBrake.get(SettingType.X_ACCEL.getSettingID()), 
	                                       vehicleSetting.getCombined(SettingType.X_ACCEL.getSettingID()));
        desiredSettings.addSettingIfNeeded(SettingType.DVX, 
	                                       hardBrake.get(SettingType.DVX.getSettingID()), 
	                                       vehicleSetting.getCombined(SettingType.DVX.getSettingID()));
    }
    private String encryptPasscode(String passcode, String prepend){
    	if (passcode != null) {
    		return Encrypt.encrypt(prepend + passcode);
    	}
    	return passcode;
    }
    private VehicleSetting createVehicleSetting(Integer vehicleID){
        
        VehicleSetting vehicleSetting = new VehicleSetting();
        vehicleSetting.setVehicleID(vehicleID);
        vehicleSetting.setDesired(new HashMap<Integer, String>());
        vehicleSetting.setProductType(ProductType.WAYSMART);

        return vehicleSetting;
    }


   @Override
   public void setVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason){
       
       Map<Integer, String> setMap = evaluateSettings(vehicleID,editableVehicleSettings);
       configuratorDAO.setVehicleSettings(vehicleID, setMap, userID, reason);
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
