package com.inthinc.pro.backing.configurator;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.backing.UsesBaseBean;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitionsByProductType;
import com.inthinc.pro.configurator.model.EditableMap;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.configurator.VehicleSetting;

@KeepAlive 
public class VehicleSettingsBean extends UsesBaseBean{
	
	private DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;

	protected VehicleDAO vehicleDAO;
	protected ConfiguratorDAO configuratorDAO;

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
    public String displayVehicle(){
    	
    	if (selectedVehicleID == null) return null;
    	
    	selectedVehicleSetting = configuratorDAO.getVehicleSettings(selectedVehicleID);
    	if (selectedVehicleSetting == null){

    	    FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("No vehicle exists with that ID");
            FacesContext.getCurrentInstance().addMessage("", message);
       	}
    	else{
    	    
    	    initializeEditedDesiredSettings();
    	}
    	
    	return null;
    }
    private void initializeEditedDesiredSettings(){
    	
    	editedDesiredValues = new EditableMap<Integer,String>(deviceSettingDefinitionsByProductType.getKeys(selectedVehicleSetting.getProductType()),selectedVehicleSetting.getDesired());
    }
    public Object updateVehicle(){
    	
        Map<Integer,String> newDesiredValues = editedDesiredValues.getDifferencesMap();
        FacesMessage message = new FacesMessage();
        
        if(reason == null|| reason.isEmpty()){
            
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Please add a reason for your changes");
            FacesContext.getCurrentInstance().addMessage("", message);
            
        }
        else if (newDesiredValues.isEmpty() || selectedVehicleSetting.getVehicleID()==null){
            
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            message.setSummary("No changes have been added");
            FacesContext.getCurrentInstance().addMessage("", message);
        }
        else {

            configuratorDAO.updateVehicleSettings(selectedVehicleSetting.getVehicleID(), newDesiredValues, 
    				                              getBaseBean().getProUser().getUser().getUserID(), 
    				                              reason);
    		message.setSeverity(FacesMessage.SEVERITY_INFO);
    		message.setSummary("Vehicle settings updated successfully");
    		FacesContext.getCurrentInstance().addMessage("", message);

            //Need to update the edited values in case the user decides to make further changes
    		editedDesiredValues.update();
        }
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
    public Object clearDesiredSettings(){
        
        editedDesiredValues.clearNewValues();
        
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setSummary("Settings cleared just on this page - reset will restore");
        FacesContext.getCurrentInstance().addMessage("", message);
    
        return null;
    }
    public Object deleteVehicleSettings(){
        
        editedDesiredValues.clearAllValues();
        //TODO need to check parameters are ok
        if(reason == null|| reason.isEmpty() || selectedVehicleSetting.getVehicleID()==null){}
        else{
            configuratorDAO.setVehicleSettings(selectedVehicleSetting.getVehicleID(), new HashMap<Integer,String>(), 
                                               getBaseBean().getProUser().getUser().getUserID(), 
                                               reason);
        
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            message.setSummary("Desired settings deleted");
            FacesContext.getCurrentInstance().addMessage("", message);
        }
        return null;
    }
    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }
    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }
}
