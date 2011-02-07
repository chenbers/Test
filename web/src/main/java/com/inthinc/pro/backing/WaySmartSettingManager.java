package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.backing.model.VehicleSettingManager;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class WaySmartSettingManager extends VehicleSettingManager {

//	private DeviceDAO deviceDAO;

    public WaySmartSettingManager(ConfiguratorDAO configuratorDAO, ProductType productType, VehicleSetting vehicleSetting, DeviceDAO deviceDAO) {
        
        super(configuratorDAO,productType, vehicleSetting);
//        this.deviceDAO = deviceDAO;
    }
    @Override
    public void init() {
 
    }

    protected EditableVehicleSettings createDefaultValues(Integer vehicleID){
        
        Double speedLimit = 5.0;
        Double speedBuffer = 10.0;
        Double severeSpeed = 15.0;
        Integer hardVertical = vehicleSensitivitySliders.getHardVerticalSlider().getDefaultValueIndex();
        Integer hardTurn = vehicleSensitivitySliders.getHardTurnSlider().getDefaultValueIndex();
        Integer hardAcceleration =  vehicleSensitivitySliders.getHardAccelerationSlider().getDefaultValueIndex();
        Integer hardBrake = vehicleSensitivitySliders.getHardBrakeSlider().getDefaultValueIndex();
        
        return new WaySmartEditableVehicleSettings(vehicleID==null?-1:vehicleID, speedLimit,speedBuffer,severeSpeed,
                                        hardAcceleration, hardBrake, hardTurn,hardVertical);
    }
    
    protected EditableVehicleSettings createFromExistingValues(Integer vehicleID, VehicleSetting vs){
        
        Double speedLimit  = NumberUtil.convertStringToDouble(vs.getCombined(SettingType.SPEED_LIMIT.getSettingID()));
        Double speedBuffer = NumberUtil.convertStringToDouble(vs.getCombined(SettingType.SPEED_BUFFER.getSettingID()));
        Double severeSpeed = NumberUtil.convertStringToDouble(vs.getCombined(SettingType.SEVERE_SPEED.getSettingID()));
 
        Integer hardVertical = extractHardVerticalValue(getVehicleSettingsForSliderSettingIDs(vs,vehicleSensitivitySliders.getHardVerticalSlider()));
        Integer hardTurn = extractHardTurnValue(getVehicleSettingsForSliderSettingIDs(vs,vehicleSensitivitySliders.getHardTurnSlider()));
        Integer hardAcceleration = extractHardAccelerationValue(getVehicleSettingsForSliderSettingIDs(vs,vehicleSensitivitySliders.getHardAccelerationSlider()));
        Integer hardBrake = extractHardBrakeValue(getVehicleSettingsForSliderSettingIDs(vs,vehicleSensitivitySliders.getHardBrakeSlider()));

        adjustCountsForCustomValues(hardAcceleration, hardBrake, hardTurn, hardVertical);

        return new WaySmartEditableVehicleSettings(vehicleID, speedLimit,speedBuffer,severeSpeed, 
                                          hardAcceleration, hardBrake, hardTurn,hardVertical);
    }
    
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
        
        Map<Integer,String> settingValues = 
            vehicleSensitivitySliders.getHardVerticalSlider().getSettingValuesFromSliderValue(waySmartEditableVehicleSettings.getHardVertical());

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
            vehicleSensitivitySliders.getHardTurnSlider().getSettingValuesFromSliderValue(waySmartEditableVehicleSettings.getHardTurn());
    	desiredSettings.addSettingIfNeeded(SettingType.DVY, 
    	                                   hardTurn.get(SettingType.DVY.getSettingID()), 
    	                                   vehicleSetting.getCombined(SettingType.DVY.getSettingID()));
    	desiredSettings.addSettingIfNeeded(SettingType.Y_LEVEL, 
                                           hardTurn.get(SettingType.Y_LEVEL.getSettingID()), 
                                           vehicleSetting.getCombined(SettingType.Y_LEVEL.getSettingID()));
    }
    private  void getSliderValuesForHardAcceleration(DesiredSettings desiredSettings,WaySmartEditableVehicleSettings waySmartEditableVehicleSettings){
        
        Map<Integer,String> hardAcceleration = 
            vehicleSensitivitySliders.getHardAccelerationSlider().getSettingValuesFromSliderValue(waySmartEditableVehicleSettings.getHardAcceleration());
        desiredSettings.addSettingIfNeeded(SettingType.HARD_ACCEL_LEVEL, 
                                           hardAcceleration.get(SettingType.HARD_ACCEL_LEVEL.getSettingID()), 
                                           vehicleSetting.getCombined(SettingType.HARD_ACCEL_LEVEL.getSettingID()));
        desiredSettings.addSettingIfNeeded(SettingType.HARD_ACCEL_DELTAV, 
                                           hardAcceleration.get(SettingType.HARD_ACCEL_DELTAV.getSettingID()), 
                                           vehicleSetting.getCombined(SettingType.HARD_ACCEL_DELTAV.getSettingID()));
    }
    private  void getSliderValuesForHardBrake(DesiredSettings desiredSettings,WaySmartEditableVehicleSettings waySmartEditableVehicleSettings){

        Map<Integer,String> hardBrake = 
            vehicleSensitivitySliders.getHardBrakeSlider().getSettingValuesFromSliderValue(waySmartEditableVehicleSettings.getHardBrake());
        desiredSettings.addSettingIfNeeded(SettingType.X_ACCEL, 
	                                       hardBrake.get(SettingType.X_ACCEL.getSettingID()), 
	                                       vehicleSetting.getCombined(SettingType.X_ACCEL.getSettingID()));
        desiredSettings.addSettingIfNeeded(SettingType.DVX, 
	                                       hardBrake.get(SettingType.DVX.getSettingID()), 
	                                       vehicleSetting.getCombined(SettingType.DVX.getSettingID()));
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
   @Override
   public void updateVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason){
      
       Map<Integer, String> setMap = evaluateChangedSettings(vehicleID,editableVehicleSettings);
       configuratorDAO.updateVehicleSettings(vehicleID, setMap, userID, reason);
       //send forward commands as necessary - being done on the backend
//       sendForwardCommands(vehicleID,setMap);
   }
//   private void sendForwardCommands(Integer vehicleID, Map<Integer, String> setMap){
//       Integer deviceID = vehicleSetting.getDeviceID();
//       if (deviceID != null){
//           //Speed
//           queueForwardCommandIfNeeded(deviceID,SettingType.SPEED_BUFFER,setMap.get(SettingType.SPEED_BUFFER.getSettingID()), ForwardCommandType.SET_SPEED_BUFFER_VARIABLE);
//           queueForwardCommandIfNeeded(deviceID,SettingType.SPEED_LIMIT,setMap.get(SettingType.SPEED_LIMIT.getSettingID()), ForwardCommandType.SET_SPEED_LIMIT_VARIABLE);
//           queueForwardCommandIfNeeded(deviceID,SettingType.SEVERE_SPEED,setMap.get(SettingType.SEVERE_SPEED.getSettingID()), ForwardCommandType.SET_SEVERE_SPEED_LIMIT_VARIABLE);
//           //Hard vertical
////           queueForwardCommandIfNeeded(deviceID,SettingType.SEVERE_HARDVERT_LEVEL,setMap.get(SettingType.SEVERE_HARDVERT_LEVEL.getSettingID()), ForwardCommandType.SET_TRIAX_HARDVERT_PEAK_TO_PEAK_LEVEL_THRESHOLD);
////           queueForwardCommandIfNeeded(deviceID,SettingType.HARDVERT_DMM_PEAKTOPEAK,setMap.get(SettingType.HARDVERT_DMM_PEAKTOPEAK.getSettingID()), ForwardCommandType.SEVERE_PEAK_2_PEAK);
////           queueForwardCommandIfNeeded(deviceID,SettingType.RMS_LEVEL,setMap.get(SettingType.RMS_LEVEL.getSettingID()), ForwardCommandType.SET_TRIAX_RMS_LEVEL);
////           //Hard brake
////           queueForwardCommandIfNeeded(deviceID,SettingType.X_ACCEL,setMap.get(SettingType.X_ACCEL.getSettingID()), ForwardCommandType.SET_TRIAX_X_ACCEL);
////           queueForwardCommandIfNeeded(deviceID,SettingType.DVX,setMap.get(SettingType.DVX.getSettingID()), ForwardCommandType.SET_TRIAX_DVX);
////           //Hard turn
////           queueForwardCommandIfNeeded(deviceID,SettingType.DVY,setMap.get(SettingType.DVY.getSettingID()), ForwardCommandType.SET_TRIAX_DVY);
////           queueForwardCommandIfNeeded(deviceID,SettingType.Y_LEVEL,setMap.get(SettingType.Y_LEVEL.getSettingID()), ForwardCommandType.SET_TRIAX_Y_LEVEL);
////           //Hard acceleration
////           queueForwardCommandIfNeeded(deviceID,SettingType.HARD_ACCEL_LEVEL,setMap.get(SettingType.HARD_ACCEL_LEVEL.getSettingID()), ForwardCommandType.SET_TRIAX_HARDACCEL_DELTAV);
////           queueForwardCommandIfNeeded(deviceID,SettingType.HARD_ACCEL_DELTAV,setMap.get(SettingType.HARD_ACCEL_DELTAV.getSettingID()), ForwardCommandType.SET_TRIAX_HARDACCEL_LEVEL);
//       }
//   }
//   private void queueForwardCommandIfNeeded(Integer deviceID,SettingType settingType,String data,ForwardCommandType forwardCommandType){
//       if(data != null){
//           Integer dataValue = new Double(Double.parseDouble(data)).intValue();
//           ForwardCommand fwdCmd = new ForwardCommand(0, forwardCommandType.getCode(), dataValue, ForwardCommandStatus.STATUS_QUEUED);
//           deviceDAO.queueForwardCommand(deviceID, fwdCmd);
//       }
//       
//   }
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
