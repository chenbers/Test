package com.inthinc.pro.backing.configurator;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.backing.UsesBaseBean;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitionsByProductType;
import com.inthinc.pro.configurator.model.EditableMap;
import com.inthinc.pro.configurator.model.VehicleSettingsDAO;
import com.inthinc.pro.model.configurator.VehicleSetting;

@KeepAlive 
public class VehicleSettingsBean extends UsesBaseBean{
	
	private DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;
	
	private VehicleSettingsDAO vehicleSettingsDAO;
    private Integer selectedVehicleID;
    private VehicleSetting selectedVehicleSetting;
    private EditableMap<Integer, String> editedDesiredValues;
    private String reason;
    
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public EditableMap<Integer, String> getEditedDesiredValues() {
		return editedDesiredValues;
	}
	public void setEditedDesiredValues(EditableMap<Integer, String> editedDesiredValues) {
		this.editedDesiredValues = editedDesiredValues;
	}
	public DeviceSettingDefinitionsByProductType getDeviceSettingDefinitionsByProductType() {
		return deviceSettingDefinitionsByProductType;
	}
	public void setDeviceSettingDefinitionsByProductType(DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType) {
		this.deviceSettingDefinitionsByProductType = deviceSettingDefinitionsByProductType;
	}
	public Integer getSelectedVehicleID() {
		return selectedVehicleID;
	}
	public void setSelectedVehicleID(Integer selectedVehicleID) {
		this.selectedVehicleID = selectedVehicleID;
	}
	
	public VehicleSetting getSelectedVehicleSetting() {
		return selectedVehicleSetting;
	}
    public void setVehicleSettingsDAO(VehicleSettingsDAO vehicleSettingsDAO) {
        this.vehicleSettingsDAO = vehicleSettingsDAO;
    }
    public String displayVehicle(){
    	
    	if (selectedVehicleID == null) return null;
    	
    	selectedVehicleSetting = vehicleSettingsDAO.getVehicleSetting(selectedVehicleID);
    	initializeEditedDesiredSettings();
    	
    	return null;
    }
    private void initializeEditedDesiredSettings(){
    	editedDesiredValues = new EditableMap<Integer,String>(deviceSettingDefinitionsByProductType.getKeys(selectedVehicleSetting.getProductType()),selectedVehicleSetting.getDesired());
 //   	editedDesiredValues = deviceSettingDefinitionsByProductType.getCopyValuesMap(selectedVehicleSetting.getProductType(),selectedVehicleSetting.getDesired());
    }
    public Object updateVehicle(){
    	
        Map<Integer,String> newDesiredValues = editedDesiredValues.getDifferencesMap();
		vehicleSettingsDAO.updateVehicleSettings(selectedVehicleSetting.getVehicleID(), newDesiredValues, 
				getBaseBean().getProUser().getUser().getUserID(), 
				reason);
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		message.setSummary("Vehicle settings updated successfully");
		FacesContext.getCurrentInstance().addMessage("", message);

		return null;
    }
    public Object resetDesiredSettings(){
    	
    	initializeEditedDesiredSettings();
    	
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		message.setSummary("Settings reset to original values");
		FacesContext.getCurrentInstance().addMessage("", message);
   	
    	return null;
    }
}
