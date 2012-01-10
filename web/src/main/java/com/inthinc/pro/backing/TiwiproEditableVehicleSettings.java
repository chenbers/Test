package com.inthinc.pro.backing;

import java.util.EnumSet;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.inthinc.pro.backing.VehiclesBean.VehicleView;
import com.inthinc.pro.backing.ui.AutologoffSetting;
import com.inthinc.pro.backing.ui.IdlingSetting;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.TiwiproSpeedingConstants;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;

public class TiwiproEditableVehicleSettings extends EditableVehicleSettings{
    
    //    private String ephone;
    private Integer autologoffSeconds;
    private Integer idlingSeconds;
    private boolean idleBuzzer;
    private Integer[] speedSettings;
	private Integer hardAcceleration; //SensitivitySlider value
	private Integer hardBrake; //SensitivitySlider value
	private Integer hardTurn; //SensitivitySlider value
    private Integer hardVertical; //SensitivitySlider value
     
    public TiwiproEditableVehicleSettings() {
        super();
    }

    public TiwiproEditableVehicleSettings(Integer vehicleID, String ephone, Integer autoLogoffSeconds, Integer[] speedSettings,
                                 Integer hardAcceleration, Integer hardBrake, Integer hardTurn,
                                 Integer hardVertical, Integer idlingThreshold, boolean idleBuzzer) {
       
        super(vehicleID, ProductType.TIWIPRO_R74, ephone);

        this.autologoffSeconds = autoLogoffSeconds;
        this.idlingSeconds = idlingThreshold;
        this.idleBuzzer = idleBuzzer;
        setSpeedSettings(speedSettings);
        setHardAcceleration(hardAcceleration);
        setHardBrake(hardBrake);
        setHardTurn(hardTurn);
        setHardVertical(hardVertical);
    }
    public void setSpeedSettings(Integer[] speedSettings) {
        if (speedSettings == null)
        {
            this.speedSettings = new Integer[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS];
            for (int i = 0; i < TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS; i++){
                this.speedSettings[i] = TiwiproSpeedingConstants.INSTANCE.DEFAULT_SPEED_SETTING[i]; 
            }
        }
        else {
        	
        	this.speedSettings = speedSettings;
        }
    }

	public Integer getAutologoffSeconds() {
		return autologoffSeconds;
	}

	public void setAutologoffSeconds(Integer autologoffSeconds) {
		this.autologoffSeconds = autologoffSeconds;
	}

	public Integer[] getSpeedSettings() {
		
        if (speedSettings == null)
        {
            Integer[] speedSettings = new Integer[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS];
            for (int i = 0; i < TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS; i++){
                speedSettings[i] = TiwiproSpeedingConstants.INSTANCE.DEFAULT_SPEED_SETTING[i]; 
            }
        }
        return speedSettings;
	}
	public String getSpeedSettingsString(){
       
       StringBuilder speedSet = new StringBuilder();
       
       for (int i = 0; i<TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS-1;i++){
           
          speedSet.append(speedSettings[i]+TiwiproSpeedingConstants.INSTANCE.SPEED_LIMITS[i]);
          speedSet.append(' '); 
       }
       speedSet.append(speedSettings[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS-1]+TiwiproSpeedingConstants.INSTANCE.SPEED_LIMITS[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS-1]);
       
       return speedSet.toString();
	}

	public Integer getHardAcceleration() {
        return hardAcceleration;
	}

	public Integer getHardBrake() {
        return hardBrake;
	}

	public Integer getHardTurn() {
        return hardTurn;
	}

	public Integer getHardVertical() {
        return hardVertical;
	}

	public void setHardAcceleration(Integer hardAcceleration) {
        
       this.hardAcceleration = hardAcceleration;
    }
	public void setHardBrake(Integer hardBrake) {
        
        this.hardBrake = hardBrake;
    }
	public void setHardTurn(Integer hardTurn) {

        this.hardTurn = hardTurn;
    }
	public void setHardVertical(Integer hardVertical) {
            
        this.hardVertical = hardVertical;
    }
    public void setAutoLogoffSlider(Integer autoLogoffSlider){
    	
    	setAutologoffSeconds(AutologoffSetting.valueOf((Integer)autoLogoffSlider).getSeconds());
    }
    public Integer getAutoLogoffSlider(){	
        if (autologoffSeconds == null) return AutologoffSetting.OFF.getSlider();
        return AutologoffSetting.findBySeconds(autologoffSeconds).getSlider();
    }
    /**
     * Returns an Integer value representing the idleEvent setting.
     * @return 1 if event should be true
     *         0 if event should be false
     */
    public Integer getIdlingEvent() {
        return (idlingSeconds == null || idlingSeconds == 0)?0:1;
    }
    public void setIdlingSlider(Integer idlingSlider){
    	setIdlingSeconds(IdlingSetting.valueOf((Integer)idlingSlider).getSeconds());
    }
    public Integer getIdlingSlider(){	
        if (idlingSeconds == null) return IdlingSetting.OFF.getSlider();
        return IdlingSetting.findBySeconds(idlingSeconds).getSlider();
    }
	public Integer getIdlingSeconds() {
		return idlingSeconds;
	}
	public void setIdlingSeconds(Integer idlingSeconds) {
		this.idlingSeconds = idlingSeconds;
	}
	public Integer getIdlingSecondsDefault() {
		return IdlingSetting.getDefault().getSlider();
	}
	public Integer getIdlingSecondsCount() {
		return IdlingSetting.values().length;
	}
	public Integer getIdlingSecondsMin() {
	    return IdlingSetting.OFF.getSlider();
	}
	public Integer getIdlingSecondsMax() {
	    return IdlingSetting.MAX.getSlider();
	}
	public boolean isIdleBuzzer() {
        return idleBuzzer;
    }
	/**
	 * Returns an Integer value representing the idleBuzzer setting.
	 * @return 1 if idleBuzzer set to true
	 *         0 if idleBuzzer set to false
	 */
	public Integer getIdleBuzzer() {
	    return idleBuzzer?1:0;
	}
    public void setIdleBuzzer(boolean idleBuzzer) {
        this.idleBuzzer = idleBuzzer;
    }
    public String getAutologoffDisplayString(){
        
        if (autologoffSeconds == null || autologoffSeconds == 0){
           
            return MessageUtil.getMessageString(AutologoffSetting.OFF.getUnit());
        }
        if (autologoffSeconds == AutologoffSetting.HOURSMAX.getSeconds()){
            
            return MessageUtil.getMessageString(AutologoffSetting.HOURSMAX.getUnit());
        }
	    for (AutologoffSetting setting :  EnumSet.allOf(AutologoffSetting.class)){
	        
	        if (autologoffSeconds <= setting.getSeconds()){
	            
	            return setting.getValue()+" "+MessageUtil.getMessageString(setting.getUnit());
	        }
	    }
        return MessageUtil.getMessageString(AutologoffSetting.OFF.getUnit());
    }
    public boolean validateSaveItems(FacesContext context, boolean isBatchEdit, Map<String, Boolean> updateField){
        
        boolean valid = true;
        // Ephone
        if (!isBatchEdit || (isBatchEdit && updateField.get("ephone")))
        {
            valid = validateEPhone(context); 
        }
        return valid;
    }
    private boolean validateEPhone(FacesContext context){
                
        if (getEphone() == null || getEphone().isEmpty())
        {
            addErrorMessage(context,"edit-form:editVehicle-ephone","required");
            return false;
        }
        if( (getEphone().length() >22) || (MiscUtil.unformatPhone(getEphone()).length() > 15) )
        {
            addErrorMessage(context,"edit-form:editVehicle-ephone","editDevice_phoneFormat");
            return false;
        }
       return true;
    }
    @Override
    public void dealWithSpecialSettings(VehicleView vehicle, VehicleView batchItem, Map<String, Boolean> updateField){
        
        String keyBase = "speed";
        for (int i=0; i< 15;i++){
            Boolean isSpeedFieldUpdated = updateField.get(keyBase+i);
            if(isSpeedFieldUpdated != null && isSpeedFieldUpdated){
                ((TiwiproEditableVehicleSettings)vehicle.getEditableVehicleSettings()).getSpeedSettings()[i] = 
                    ((TiwiproEditableVehicleSettings)batchItem.getEditableVehicleSettings()).getSpeedSettings()[i];
            }
        }
    }
    private void addErrorMessage(FacesContext context, String field, String errorMessage){
        
        final String summary = MessageUtil.getMessageString(errorMessage);
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        context.addMessage(field, message);

    }
}
