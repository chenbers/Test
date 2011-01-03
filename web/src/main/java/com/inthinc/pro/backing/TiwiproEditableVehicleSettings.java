package com.inthinc.pro.backing;

import java.util.EnumSet;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.inthinc.pro.backing.ui.AutologoffSetting;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.TiwiproSpeedingConstants;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;

public class TiwiproEditableVehicleSettings extends EditableVehicleSettings{
    
	private String ephone;
	private Integer autologoffSeconds;
	private Integer[] speedSettings;
	private Integer hardAcceleration;
	private Integer hardBrake;
	private Integer hardTurn;
    private Integer hardVertical;
     
    public TiwiproEditableVehicleSettings() {
        super();
    }

    public TiwiproEditableVehicleSettings(Integer vehicleID, String ephone, Integer autoLogoffSeconds, Integer[] speedSettings,
                                 Integer hardAcceleration, Integer hardBrake, Integer hardTurn,
                                 Integer hardVertical) {
       
        super(vehicleID, ProductType.TIWIPRO_R74);

        this.ephone = ephone;
        this.autologoffSeconds = autoLogoffSeconds;
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
            for (int i = 0; i < TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS; i++)
                this.speedSettings[i] = TiwiproSpeedingConstants.INSTANCE.DEFAULT_SPEED_SETTING; 
                
        }
        else {
        	
        	this.speedSettings = speedSettings;
        }
    }
    public String getEphone() {
		return ephone;
	}

	public void setEphone(String ephone) {
		this.ephone = ephone;
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
            speedSettings = new Integer[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS];
            for (int i = 0; i < TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS; i++)
                speedSettings[i] = TiwiproSpeedingConstants.INSTANCE.DEFAULT_SPEED_SETTING; 
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
                
        if (ephone == null || ephone.isEmpty())
        {
            addErrorMessage(context,"required");
            return false;
        }
        if( (ephone.length() >22) || (MiscUtil.unformatPhone(ephone).length() > 15) )
        {
            addErrorMessage(context,"editDevice_phoneFormat");
            return false;
        }
       return true;
    }
    private void addErrorMessage(FacesContext context, String errorMessage){
        
        final String summary = MessageUtil.getMessageString(errorMessage);
        final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        context.addMessage("edit-form:editVehicle-ephone", message);

    }
}
