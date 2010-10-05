package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.EditableVehicleSettings;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.configurator.SensitivityType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public abstract class VehicleSettingManager {

    protected static final Integer CUSTOM_SLIDER_VALUE = 99;
	protected ConfiguratorDAO configuratorDAO;
    protected VehicleSetting  vehicleSetting;
	protected Map<Integer, Integer> defaultSettings;
	protected List<SensitivityType> sensitivities;
	protected Map<Integer,Map<SensitivityType,Integer>> settingCounts;
        
    protected VehicleSettingManager(ConfiguratorDAO configuratorDAO, VehicleSetting vehicleSetting) {

        this.configuratorDAO = configuratorDAO;
        this.vehicleSetting = vehicleSetting;
        
        defaultSettings = SensitivityType.getDefaultSettings();
        sensitivities = SensitivityType.getSensitivities();
        settingCounts = new HashMap<Integer,Map<SensitivityType,Integer>>();
    }

    public abstract void init();

    public abstract Map<Integer, String> evaluateSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings);

    public abstract Map<Integer, String> evaluateChangedSettings(Boolean batchEdit, Map<String, Boolean> updateField, Integer vehicleID, EditableVehicleSettings editableVehicleSettings);

    public abstract void setVehicleSettings(Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason);

    public abstract void updateVehicleSettings(boolean batchEdit, Map<String, Boolean> updateField, Integer vehicleID, EditableVehicleSettings editableVehicleSettings, Integer userID, String reason);

    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }


	public Map<Integer, Integer> getDefaultSettings() {
		return defaultSettings;
	}

	public List<SensitivityType> getSensitivities() {
		return sensitivities;
	}

	public Map<Integer,Map<SensitivityType,Integer>> getSettingCounts() {
		return settingCounts;
	}

	public EditableVehicleSettings associateSettings(Integer vehicleID) {
	    
	    if (vehicleSetting == null){
	        
	        return createDefaultValues(vehicleID); 
	
	    }
	    else {
	        return createFromExistingValues(vehicleSetting);
	    }
	}
    protected abstract EditableVehicleSettings createDefaultValues(Integer vehicleID);
    protected abstract EditableVehicleSettings createFromExistingValues(VehicleSetting vs);
    
    public abstract class DesiredSettings{

        protected Map<Integer, String> desiredSettings;

        public DesiredSettings() {
            
            desiredSettings = new HashMap<Integer,String>();
        }

        public Map<Integer, String> getDesiredSettings() {
            return desiredSettings;
        }

        public boolean isDifferent(SensitivityType setting, String newValue, String oldValue) {
        
           if(((oldValue != null) && (newValue == null)) ||
                ((oldValue == null) && (newValue != null)) || 
                (!oldValue.equals(newValue))) return true;
           return false;
        }
        public abstract void addSettingIfNeeded(SensitivityType setting,String newValue, String oldValue); 
      }
      public class ChangedSettings extends DesiredSettings{
          
          private Boolean batchEdit;
          private Map<String, Boolean> updateField;
          
          public ChangedSettings(Boolean batchEdit, Map<String, Boolean> updateField) {

            super();
                
            this.batchEdit = batchEdit;
            this.updateField = updateField;
          }

          @Override
          public void addSettingIfNeeded(SensitivityType setting,String newValue, String oldValue){
              
           if(isRequested(setting) && isDifferent(setting,newValue,oldValue)){
               
               desiredSettings.put(setting.getSettingID(), newValue);
           }
        }
        private boolean isRequested(SensitivityType setting){
            
            if (!batchEdit) return true;
            if (updateField.get("editableVehicleSettings."+setting.getPropertyName())){
                return true;
            }
            return false;
        }
      }
      public class NewSettings extends DesiredSettings{

        @Override
        public void addSettingIfNeeded(SensitivityType setting,String newValue, String oldValue){
              
            if(isDifferent(setting,newValue,oldValue)){
               
               desiredSettings.put(setting.getSettingID(), newValue);
            }
         }
      }

}
