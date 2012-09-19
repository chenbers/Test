package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.backing.model.VehicleSettingManager;
import com.inthinc.pro.backing.ui.AutologoffSetting;
import com.inthinc.pro.backing.ui.IdlingSetting;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.model.app.SensitivitySliders;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.configurator.TiwiproSpeedingConstants;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class TiwiproSettingManager extends VehicleSettingManager{

    private static final double DEFAULT_MAX_SPEED_LIMIT = 78.0;

    public TiwiproSettingManager(ConfiguratorDAO configuratorDAO, SensitivitySliders sensitivitySliders,
            ProductType productType, VehicleSetting vehicleSetting) {
        
        super(configuratorDAO,sensitivitySliders,productType,vehicleSetting);
        
    }
    public TiwiproSettingManager(ConfiguratorDAO configuratorDAO, SensitivitySliders sensitivitySliders,
            ProductType productType, Integer vehicleID, Integer deviceID) {
        
        super(configuratorDAO,sensitivitySliders,productType,vehicleID,deviceID);
        
    }
	@Override
    public void init() {
    }

    public EditableVehicleSettings createDefaultValues(Integer vehicleID){
        
        String ephone = "";
        Integer autoLogoffSeconds = AutologoffSetting.HOURSMAX.getSeconds();
        Integer idlingThresholdSeconds = IdlingSetting.DEFAULT.getSeconds();
        boolean idleBuzzerDefault = false;//disabled
        Integer hardVertical = hardVerticalSlider.getDefaultValueIndex();
        Integer hardTurn = hardTurnSlider.getDefaultValueIndex();
        Integer hardAcceleration =  hardAccelerationSlider.getDefaultValueIndex();
        Integer hardBrake = hardBrakeSlider.getDefaultValueIndex();
        Integer[] speedSettings = convertFromSpeedSettings(TiwiproSpeedingConstants.INSTANCE.DEFAULT_SPEED_SET);
        Double maxSpeed = DEFAULT_MAX_SPEED_LIMIT;
                
        return new TiwiproEditableVehicleSettings(vehicleID==null?-1:vehicleID, ephone, autoLogoffSeconds, speedSettings, hardAcceleration, hardBrake, hardTurn,hardVertical, idlingThresholdSeconds, idleBuzzerDefault, maxSpeed);
    }
    
    protected EditableVehicleSettings createFromExistingValues(Integer vehicleID, VehicleSetting vs){
        String ephone = vs.getBestOption(SettingType.EPHONE_SETTING.getSettingID());
        Integer autoLogoffSeconds = NumberUtil.convertString(vs.getBestOption(SettingType.AUTOLOGOFF_SETTING.getSettingID()));
        Integer idlingThresholdSeconds = NumberUtil.convertString(vs.getBestOption(SettingType.IDLING_TIMEOUT.getSettingID()));
        String idleBuzzerValue = vs.getBestOption(SettingType.BUZZER_IDLE.getSettingID());
        boolean idleBuzzer = (idleBuzzerValue!=null && idleBuzzerValue.equalsIgnoreCase("1"));
        Integer hardVertical = hardVerticalSlider.getSliderValueFromSettings(vs);
        Integer hardTurn = hardTurnSlider.getSliderValueFromSettings(vs);
        Integer hardAcceleration = hardAccelerationSlider.getSliderValueFromSettings(vs);
        Integer hardBrake = hardBrakeSlider.getSliderValueFromSettings(vs);
        Integer[] speedSettings = convertFromSpeedSettings(vs.getBestOption(SettingType.SPEED_SETTING.getSettingID())); 
        Double maxSpeed = NumberUtil.convertStringToDouble(vs.getBestOption(SettingType.TIWI_SPEED_LIMIT.getSettingID()));
        if (maxSpeed < 1.0)
            maxSpeed = DEFAULT_MAX_SPEED_LIMIT;

        adjustCountsForCustomValues(hardAcceleration, hardBrake, hardTurn, hardVertical);
        return new TiwiproEditableVehicleSettings(vs.getVehicleID(),ephone, autoLogoffSeconds, speedSettings, hardAcceleration, hardBrake, hardTurn,hardVertical, idlingThresholdSeconds, idleBuzzer, maxSpeed);
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
            speedSettings[i] = TiwiproSpeedingConstants.INSTANCE.DEFAULT_SPEED_SETTING[i]; 
        }

        return speedSettings;
    }
    public String getSpeedSettingsString(Integer[] speedSettings){
        
        StringBuilder speedSet = new StringBuilder();
        
        for (int i = 0; i<TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS-1;i++){
            
           speedSet.append(speedSettings[i]+TiwiproSpeedingConstants.INSTANCE.SPEED_LIMITS[i]);
           speedSet.append(' '); 
        }
        speedSet.append(speedSettings[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS-1]+TiwiproSpeedingConstants.INSTANCE.SPEED_LIMITS[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS-1]);
        
        return speedSet.toString();
     }

  
   @Override
   public Map<Integer, String> evaluateSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings,Map<String, Boolean> updateField){
	   
	   try{
	       TiwiproEditableVehicleSettings tiwiproEditableVehicleSettings = downCastEditableVehicleSettings(editableVehicleSettings);
	       
	       if (vehicleSetting == null) {
	           
	           vehicleSetting = createVehicleSetting(vehicleID);
	       }
	       
	       DesiredSettings newSettings = new DesiredSettings();
	       newSettings.addSettingIfNeeded(SettingType.EPHONE_SETTING,
	    		   					  tiwiproEditableVehicleSettings.getEphone(),
	    		   					  vehicleSetting.getBestOption(SettingType.EPHONE_SETTING.getSettingID()), 
	    		   					  fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.ephone"));
	       newSettings.addSettingIfNeeded(SettingType.AUTOLOGOFF_SETTING, 
	                                  ""+tiwiproEditableVehicleSettings.getAutologoffSeconds(), 
	                                  vehicleSetting.getBestOption(SettingType.AUTOLOGOFF_SETTING.getSettingID()), 
	    		   					  fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.autologoffSeconds"));
	       newSettings.addSliderIfNeeded(SettingType.HARD_VERT_SETTING,
	                                  getHardVerticalValue(tiwiproEditableVehicleSettings.getHardVertical()).get(SettingType.HARD_VERT_SETTING.getSettingID()),
                                      vehicleSetting.getBestOption(SettingType.HARD_VERT_SETTING.getSettingID()), 
	    		   					  fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardVertical"));
	       newSettings.addSliderIfNeeded(SettingType.HARD_TURN_SETTING, 
	                                  getHardTurnValue(tiwiproEditableVehicleSettings.getHardTurn()).get(SettingType.HARD_TURN_SETTING.getSettingID()), 
	                                  vehicleSetting.getBestOption(SettingType.HARD_TURN_SETTING.getSettingID()), 
	    		   					  fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardTurn"));
	       newSettings.addSliderIfNeeded(SettingType.HARD_ACCEL_SETTING, 
	                                  getHardAccelerationValue(tiwiproEditableVehicleSettings.getHardAcceleration()).get(SettingType.HARD_ACCEL_SETTING.getSettingID()), 
	                                  vehicleSetting.getBestOption(SettingType.HARD_ACCEL_SETTING.getSettingID()), 
	    		   					  fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardAcceleration"));
	       newSettings.addSliderIfNeeded(SettingType.HARD_BRAKE_SETTING, 
	                                  getHardBrakeValue(tiwiproEditableVehicleSettings.getHardBrake()).get(SettingType.HARD_BRAKE_SETTING.getSettingID()), 
	                                  vehicleSetting.getBestOption(SettingType.HARD_BRAKE_SETTING.getSettingID()), 
	    		   					  fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.hardBrake"));
	       newSettings.addSettingIfNeeded(SettingType.SPEED_SETTING, 
	                                  tiwiproEditableVehicleSettings.getSpeedSettingsString(), 
	                                  vehicleSetting.getBestOption(SettingType.SPEED_SETTING.getSettingID()), 
	                                  updateSpeedSettings(updateField));
	       newSettings.addSettingIfNeeded(SettingType.IDLING_TIMEOUT, 
                   tiwiproEditableVehicleSettings.getIdlingSeconds().toString(), 
                   vehicleSetting.getBestOption(SettingType.IDLING_TIMEOUT.getSettingID()), 
                   fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.idlingSeconds"));
	       newSettings.addSettingIfNeeded(SettingType.BUZZER_IDLE, 
                   tiwiproEditableVehicleSettings.getIdleBuzzer().toString(), 
                   vehicleSetting.getBestOption(SettingType.BUZZER_IDLE.getSettingID()), 
                   fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.idleBuzzer"));
	       newSettings.addSettingIfNeeded(SettingType.TIWI_SPEED_LIMIT, 
                                       tiwiproEditableVehicleSettings.getMaxSpeed().toString(), 
                                       vehicleSetting.getBestOption(SettingType.TIWI_SPEED_LIMIT.getSettingID()), 
                                       fieldIsIncludedInBatchEditOrNotBatchEdit(updateField,"editableVehicleSettings.maxSpeed"));

	       return newSettings.getDesiredSettings();
       }
       catch(IllegalArgumentException iae){
       	
    	   return null;
       }
   }
   private boolean updateSpeedSettings(Map<String, Boolean> updateField){
	   if(updateField == null) return true;
       String keyBase = "speed";
       for (int i=0; i< 15;i++){
           Boolean isSpeedFieldUpdated = updateField.get(keyBase+i);
           if(isSpeedFieldUpdated != null && isSpeedFieldUpdated){

        	   return true;
           }
       }
       return false;
   }
   private VehicleSetting createVehicleSetting(Integer vehicleID){
       
       VehicleSetting vehicleSetting = new VehicleSetting();
       vehicleSetting.setVehicleID(vehicleID);
       vehicleSetting.setDesired(new HashMap<Integer, String>());
       vehicleSetting.setProductType(ProductType.TIWIPRO);

       return vehicleSetting;
   }

  private TiwiproEditableVehicleSettings downCastEditableVehicleSettings(EditableVehicleSettings editableVehicleSettings) throws IllegalArgumentException{
  	
	if (!(editableVehicleSettings instanceof TiwiproEditableVehicleSettings)){
		
		throw new IllegalArgumentException();
	}
	return(TiwiproEditableVehicleSettings)editableVehicleSettings;
  }

  @Override
  public void setVehicleSettings(Integer vehicleID, 
		  						 EditableVehicleSettings editableVehicleSettings, 
		  						 Integer userID, 
		  						 String reason,
		  						 Map<String, Boolean> updateField){
      
      Map<Integer, String> setMap = evaluateSettings(vehicleID,editableVehicleSettings,updateField);
      if (vehicleID == -1) vehicleID = null;
      configuratorDAO.setVehicleSettings(vehicleID, setMap, userID, reason);
  }
  
 }
