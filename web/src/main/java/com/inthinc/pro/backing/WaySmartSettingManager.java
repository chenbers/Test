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

	private static final double DEFAULT_SPEED_LIMIT = 75.0;
    private static final double DEFAULT_SPEED_BUFFER = 2.0;
    private static final double DEFAULT_SEVERE_SPEED = 20.0;
    private static final String WIRELINE_KILL_MOTOR_PASSCODE_PREFIX = "90";
    private static final String WIRELINE_DOOR_ALARM_PASSCODE_PREFIX = "75";

    public WaySmartSettingManager(ConfiguratorDAO configuratorDAO, SensitivitySliders sensitivitySliders,
            VehicleSetting vehicleSetting) {
        
        super(configuratorDAO,sensitivitySliders,ProductType.WAYSMART, vehicleSetting);
    }
    public WaySmartSettingManager(ConfiguratorDAO configuratorDAO, SensitivitySliders sensitivitySliders,
                                    Integer vehicleID, Integer deviceID) {
        
        super(configuratorDAO,sensitivitySliders,ProductType.WAYSMART,vehicleID,deviceID);
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
        
        Double speedLimit  = NumberUtil.convertStringToDouble(vs.getBestOption(SettingType.SPEED_LIMIT.getSettingID()));
        Double speedBuffer = NumberUtil.convertStringToDouble(vs.getBestOption(SettingType.SPEED_BUFFER.getSettingID()));
        Double severeSpeed = NumberUtil.convertStringToDouble(vs.getBestOption(SettingType.SEVERE_SPEED.getSettingID()));
 
        Integer hardVertical = hardVerticalSlider.getSliderValueFromSettings(vs);
        Integer hardTurn = hardTurnSlider.getSliderValueFromSettings(vs);
        Integer hardAcceleration = hardAccelerationSlider.getSliderValueFromSettings(vs);
        Integer hardBrake = hardBrakeSlider.getSliderValueFromSettings(vs);

        adjustCountsForCustomValues(hardAcceleration, hardBrake, hardTurn, hardVertical);
        
        String  doorAlarmPasscode = decryptPasscode(vs.getBestOption(SettingType.WIRELINE_DOOR_ALARM_PASSCODE.getSettingID()));
        String  killMotorPasscode = decryptPasscode(vs.getBestOption(SettingType.WIRELINE_KILL_MOTOR_PASSCODE.getSettingID()));
        Integer autoArmTime = NumberUtil.convertString(vs.getBestOption(SettingType.WIRELINE_AUTO_ARM_TIME.getSettingID()));
        Integer dotVehicleType = NumberUtil.convertString(vs.getBestOption(SettingType.DOT_VEHICLE_TYPE.getSettingID()));


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
    

    @Override
    public Map<Integer, String> evaluateSettings(Integer vehicleID, 
    											 EditableVehicleSettings editableVehicleSettings, 
    											 Map<String, Boolean> updateField) {
    	
    	try{
	    	WaySmartEditableVehicleSettings waySmartEditableVehicleSettings = this.downCastEditableVehicleSettings(editableVehicleSettings);
	        
	        if (vehicleSetting == null) {
	            
	            vehicleSetting = new VehicleSetting(vehicleID,ProductType.WAYSMART);
	        }
	        DesiredSettings newSettings = new DesiredSettings();

	        //need to get individual settings from sliders and fields
	        
	        newSettings.addSettingIfNeeded(SettingType.SPEED_LIMIT, 
	                                       ""+waySmartEditableVehicleSettings.getSpeedLimit(), 
	                                       vehicleSetting.getBestOption(SettingType.SPEED_LIMIT.getSettingID()),
	                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.speedLimit"));
	        newSettings.addSettingIfNeeded(SettingType.SPEED_BUFFER, 
	                                       ""+waySmartEditableVehicleSettings.getSpeedBuffer(), 
	                                       vehicleSetting.getBestOption(SettingType.SPEED_BUFFER.getSettingID()),
	                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.speedBuffer"));
	        newSettings.addSettingIfNeeded(SettingType.SEVERE_SPEED, 
	                                       ""+waySmartEditableVehicleSettings.getSevereSpeed(), 
	                                       vehicleSetting.getBestOption(SettingType.SEVERE_SPEED.getSettingID()),
	                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.severeSpeed"));
	        getSliderValuesForHardVertical(newSettings,waySmartEditableVehicleSettings, updateField);
	        getSliderValuesForHardAcceleration(newSettings,waySmartEditableVehicleSettings, updateField);
	        getSliderValuesForHardBrake(newSettings,waySmartEditableVehicleSettings, updateField);
	        getSliderValuesForHardTurn(newSettings,waySmartEditableVehicleSettings, updateField);
	        
	        newSettings.addSettingIfNeeded(SettingType.WIRELINE_KILL_MOTOR_PASSCODE,
                    encryptPasscode(waySmartEditableVehicleSettings.getKillMotorPasscode(),WIRELINE_KILL_MOTOR_PASSCODE_PREFIX), 
                    vehicleSetting.getBestOption(SettingType.WIRELINE_KILL_MOTOR_PASSCODE.getSettingID()),
                    fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.killMotorPasscode"));
	        newSettings.addSettingIfNeeded(SettingType.WIRELINE_DOOR_ALARM_PASSCODE,
            		encryptPasscode(waySmartEditableVehicleSettings.getDoorAlarmPasscode(),WIRELINE_DOOR_ALARM_PASSCODE_PREFIX), 
                    vehicleSetting.getBestOption(SettingType.WIRELINE_DOOR_ALARM_PASSCODE.getSettingID()),
                    fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.doorAlarmPasscode"));
	        newSettings.addSettingIfNeeded(SettingType.WIRELINE_AUTO_ARM_TIME,
                    ""+waySmartEditableVehicleSettings.getAutoArmTime(), 
                    vehicleSetting.getBestOption(SettingType.WIRELINE_AUTO_ARM_TIME.getSettingID()),
                    fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.autoArmTime"));

            newSettings.addSettingIfNeeded(SettingType.DOT_VEHICLE_TYPE,
                    ""+waySmartEditableVehicleSettings.getDotVehicleType(), 
                    vehicleSetting.getBestOption(SettingType.DOT_VEHICLE_TYPE.getSettingID()),
                    fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.dotVehicleType"));
	        return newSettings.getDesiredSettings();
        
        }
        catch(IllegalArgumentException iae){
        	
        	return null;
        }
    }
    private void getSliderValuesForHardVertical(DesiredSettings desiredSettings,
    											WaySmartEditableVehicleSettings waySmartEditableVehicleSettings, 
   											 	Map<String, Boolean> updateField){
        
        Map<Integer,String> settingValues = 
            hardVerticalSlider.getSettingValuesFromSliderValue(waySmartEditableVehicleSettings.getHardVertical());

    	desiredSettings.addSettingIfNeeded(SettingType.SEVERE_HARDVERT_LEVEL, 
    	                                   settingValues.get(SettingType.SEVERE_HARDVERT_LEVEL.getSettingID()), 
                                           vehicleSetting.getBestOption(SettingType.SEVERE_HARDVERT_LEVEL.getSettingID()),
	                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardVertical"));
    	desiredSettings.addSettingIfNeeded(SettingType.HARDVERT_DMM_PEAKTOPEAK, 
    	                                   settingValues.get(SettingType.HARDVERT_DMM_PEAKTOPEAK.getSettingID()), 
                                           vehicleSetting.getBestOption(SettingType.HARDVERT_DMM_PEAKTOPEAK.getSettingID()),
	                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardVertical"));
    	desiredSettings.addSettingIfNeeded(SettingType.RMS_LEVEL, 
    	                                   settingValues.get(SettingType.RMS_LEVEL.getSettingID()), 
                                           vehicleSetting.getBestOption(SettingType.RMS_LEVEL.getSettingID()),
	                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardVertical"));
    }
    private void getSliderValuesForHardTurn(DesiredSettings desiredSettings,
    										WaySmartEditableVehicleSettings waySmartEditableVehicleSettings, 
											Map<String, Boolean> updateField){
        	
        Map<Integer,String> hardTurn = 
            hardTurnSlider.getSettingValuesFromSliderValue(waySmartEditableVehicleSettings.getHardTurn());
    	desiredSettings.addSettingIfNeeded(SettingType.DVY, 
    	                                   hardTurn.get(SettingType.DVY.getSettingID()), 
    	                                   vehicleSetting.getBestOption(SettingType.DVY.getSettingID()),
	                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardTurn"));
    	desiredSettings.addSettingIfNeeded(SettingType.Y_LEVEL, 
                                           hardTurn.get(SettingType.Y_LEVEL.getSettingID()), 
                                           vehicleSetting.getBestOption(SettingType.Y_LEVEL.getSettingID()),
	                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardTurn"));
    }
    private  void getSliderValuesForHardAcceleration(DesiredSettings desiredSettings,
    												 WaySmartEditableVehicleSettings waySmartEditableVehicleSettings, 
        											 Map<String, Boolean> updateField){
        
        Map<Integer,String> hardAcceleration = 
            hardAccelerationSlider.getSettingValuesFromSliderValue(waySmartEditableVehicleSettings.getHardAcceleration());
        desiredSettings.addSettingIfNeeded(SettingType.HARD_ACCEL_LEVEL, 
                                           hardAcceleration.get(SettingType.HARD_ACCEL_LEVEL.getSettingID()), 
                                           vehicleSetting.getBestOption(SettingType.HARD_ACCEL_LEVEL.getSettingID()),
	                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardAcceleration"));
        desiredSettings.addSettingIfNeeded(SettingType.HARD_ACCEL_DELTAV, 
                                           hardAcceleration.get(SettingType.HARD_ACCEL_DELTAV.getSettingID()), 
                                           vehicleSetting.getBestOption(SettingType.HARD_ACCEL_DELTAV.getSettingID()),
	                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardAcceleration"));
    }
    private  void getSliderValuesForHardBrake(DesiredSettings desiredSettings,
    										  WaySmartEditableVehicleSettings waySmartEditableVehicleSettings, 
 											 Map<String, Boolean> updateField){

        Map<Integer,String> hardBrake = 
            hardBrakeSlider.getSettingValuesFromSliderValue(waySmartEditableVehicleSettings.getHardBrake());
        desiredSettings.addSettingIfNeeded(SettingType.X_ACCEL, 
	                                       hardBrake.get(SettingType.X_ACCEL.getSettingID()), 
	                                       vehicleSetting.getBestOption(SettingType.X_ACCEL.getSettingID()),
	                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardBrake"));
        desiredSettings.addSettingIfNeeded(SettingType.DVX, 
	                                       hardBrake.get(SettingType.DVX.getSettingID()), 
	                                       vehicleSetting.getBestOption(SettingType.DVX.getSettingID()),
	                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardBrake"));
    }
    private String encryptPasscode(String passcode, String prepend){
    	if (passcode != null) {
    		return Encrypt.encrypt(prepend + passcode);
    	}
    	return passcode;
    }
//    private VehicleSetting createVehicleSetting(Integer vehicleID){
//        
//        VehicleSetting vehicleSetting = new VehicleSetting(vehicleID,ProductType.WAYSMART);
//
//        return vehicleSetting;
//    }


   @Override
   public void setVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason,
			  Map<String, Boolean> updateField){
       Map<Integer, String> setMap = evaluateSettings(vehicleID,editableVehicleSettings,updateField);
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
