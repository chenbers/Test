package com.inthinc.pro.backing;

import java.util.EnumSet;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.inthinc.pro.backing.ui.AutologoffSetting;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SensitivityType;
import com.inthinc.pro.model.configurator.VehicleSetting;
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
            this.speedSettings = new Integer[VehicleSetting.NUM_SPEEDS];
            for (int i = 0; i < VehicleSetting.NUM_SPEEDS; i++)
                this.speedSettings[i] = VehicleSetting.DEFAULT_SPEED_SETTING; 
                
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
            speedSettings = new Integer[VehicleSetting.NUM_SPEEDS];
            for (int i = 0; i < VehicleSetting.NUM_SPEEDS; i++)
                speedSettings[i] = VehicleSetting.DEFAULT_SPEED_SETTING; 
        }
        return speedSettings;
	}
	public String getSpeedSettingsString(){
       
       StringBuilder speedSet = new StringBuilder();
       
       for (int i = 0; i<VehicleSetting.NUM_SPEEDS-1;i++){
           
          speedSet.append(speedSettings[i]+VehicleSetting.SPEED_LIMITS[i]);
          speedSet.append(' '); 
       }
       speedSet.append(speedSettings[VehicleSetting.NUM_SPEEDS-1]+VehicleSetting.SPEED_LIMITS[VehicleSetting.NUM_SPEEDS-1]);
       
       return speedSet.toString();
	}

	public Integer getHardAcceleration() {
        if (hardAcceleration == null)
            return SensitivityType.HARD_ACCEL_SETTING.getDefaultSetting();
        return hardAcceleration;
	}

	public Integer getHardBrake() {
        if (hardBrake == null)
            return SensitivityType.HARD_BRAKE_SETTING.getDefaultSetting();
        return hardBrake;
	}

	public Integer getHardTurn() {
        if (hardTurn == null)
            return SensitivityType.HARD_TURN_SETTING.getDefaultSetting();
        return hardTurn;
	}

	public Integer getHardVertical() {
        if (hardVertical == null)
            return SensitivityType.HARD_VERT_SETTING.getDefaultSetting();
        return hardVertical;
	}

	private void setHardAcceleration(Integer hardAcceleration) {
        
       this.hardAcceleration = hardAcceleration==null?SensitivityType.HARD_ACCEL_SETTING.getDefaultSetting():
                               hardAcceleration==99?SensitivityType.HARD_ACCEL_SETTING.getSettingsCount()+1:hardAcceleration;
    }
    private void setHardBrake(Integer hardBrake) {
        
        this.hardBrake = hardBrake==null?SensitivityType.HARD_BRAKE_SETTING.getDefaultSetting():
                         hardBrake==99?SensitivityType.HARD_BRAKE_SETTING.getSettingsCount()+1:hardBrake;
    }
    private void setHardTurn(Integer hardTurn) {

        this.hardTurn = hardTurn==null?SensitivityType.HARD_TURN_SETTING.getDefaultSetting():
                        hardTurn==99?SensitivityType.HARD_TURN_SETTING.getSettingsCount()+1:hardTurn;
    }
    private void setHardVertical(Integer hardVertical) {
            
        this.hardVertical = hardVertical==null?SensitivityType.HARD_VERT_SETTING.getDefaultSetting():
                            hardVertical==99?SensitivityType.HARD_VERT_SETTING.getSettingsCount()+1:hardVertical;
    }
    public void setAutoLogoffSlider(Integer autoLogoffSlider){
    	
    	setAutologoffSeconds(AutologoffSetting.valueOf((Integer)autoLogoffSlider).getSeconds());
    }
    public Integer getAutoLogoffSlider(){
    	
        return AutologoffSetting.findBySeconds(autologoffSeconds).getSlider();
    }
    public String getAutologoffDisplayString(){
	    for (AutologoffSetting setting :  EnumSet.allOf(AutologoffSetting.class)){
	        
	        if (autologoffSeconds == 0){
	            
	             return MessageUtil.getMessageString(setting.getUnit());
	        }
	        if (autologoffSeconds <= setting.getSeconds()){
	            
	            return setting.getValue()+" "+MessageUtil.getMessageString(setting.getUnit());
	        }
	    }
	    return MessageUtil.getMessageString(AutologoffSetting.HOURSMAX.getUnit());
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
            final String summary = MessageUtil.getMessageString("required");
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
            context.addMessage("edit-form:editDevice-ephone", message);
            return false;
        }
        if( (ephone.length() >22) || (MiscUtil.unformatPhone(ephone).length() > 15) )
        {
            final String summary = MessageUtil.getMessageString("editDevice_phoneFormat");
            final FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
            context.addMessage("edit-form:editDevice-ephone", message);
            
            return false;
        }
       return true;
    }
//    public class Properties extends HashMap<String,Object>{
//        
//        private static final long serialVersionUID = 1L;
//
//        @Override
//        public Object put(String key,Object value){
//            
//            if(key.equals("autoLogoffSlider")){
//                
//                super.put("autoLogoffSeconds",AutologoffSetting.valueOf((Integer)value).getSeconds());
//                return value;
//            }
//            return super.put(key, value);
//        }
//        
//        @Override
//        public Object get(Object key) {
//            
//            if (key.equals("ephone") ||
//                key.equals("autoLogoffSeconds")){
//                
//                return super.get(key);
//                
//            }
//            if (key.equals("speedSettings")){
//                
//                Integer[] speedSettings = (Integer[])super.get("speedSettings");
//                if (speedSettings == null)
//                {
//                    speedSettings = new Integer[VehicleSetting.NUM_SPEEDS];
//                    for (int i = 0; i < VehicleSetting.NUM_SPEEDS; i++)
//                        speedSettings[i] = VehicleSetting.DEFAULT_SPEED_SETTING; 
//                        
//                }
//                return speedSettings;
//            }
//            if(key.equals("autoLogoffSlider")){
//                
//                return AutologoffSetting.findBySeconds(((Integer)super.get("autoLogoffSeconds"))).getSlider();
// 
//            }
//            if(key.equals("hardBrake")){
//                Integer hardBrake = (Integer)super.get(key);
//                if (hardBrake == null)
//                    return SensitivityType.HARD_BRAKE_SETTING.getDefaultSetting();
//                return hardBrake;
//            }
//            if(key.equals("hardAcceleration")){
//                
//                Integer hardAcceleration = (Integer)super.get(key);
//                if (hardAcceleration == null)
//                    return SensitivityType.HARD_ACCEL_SETTING.getDefaultSetting();
//                return hardAcceleration;
//            }
//            if(key.equals("hardVertical")){
//                
//                Integer hardVertical = (Integer)super.get(key);
//                if (hardVertical == null)
//                    return SensitivityType.HARD_VERT_SETTING.getDefaultSetting();
//                return hardVertical;
//            }
//            if(key.equals("hardTurn")){
//                
//                Integer hardTurn = (Integer)super.get(key);
//                if (hardTurn == null)
//                    return SensitivityType.HARD_TURN_SETTING.getDefaultSetting();
//                return hardTurn;
//            }
//            if(key.equals("autologoffDisplayString")){
//                
//                Integer autoLogoffSeconds = (Integer)super.get("autoLogoffSeconds");
//                
//                for (AutologoffSetting setting :  EnumSet.allOf(AutologoffSetting.class)){
//                    
//                    if (autoLogoffSeconds == 0){
//                        
//                         return MessageUtil.getMessageString(setting.getUnit());
//                    }
//                    if (autoLogoffSeconds <= setting.getSeconds()){
//                        
//                        return setting.getValue()+" "+MessageUtil.getMessageString(setting.getUnit());
//                    }
//                }
//                return MessageUtil.getMessageString(AutologoffSetting.HOURSMAX.getUnit());
//            }
//            return null;
//        }
//    }
}
