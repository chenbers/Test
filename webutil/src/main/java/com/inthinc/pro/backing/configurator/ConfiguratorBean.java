package com.inthinc.pro.backing.configurator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;

import com.inthinc.pro.backing.UsesBaseBean;
import com.inthinc.pro.configurator.model.Configuration;
import com.inthinc.pro.configurator.model.ConfigurationExtractor;
import com.inthinc.pro.configurator.model.ConfigurationSet;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitionBean;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitionsByProductType;
import com.inthinc.pro.configurator.ui.ConfigurationApplyBean;
import com.inthinc.pro.configurator.ui.ConfigurationSelectionBean;
import com.inthinc.pro.dao.ConfiguratorDAO;

@KeepAlive(ajaxOnly=true)
public class ConfiguratorBean extends UsesBaseBean implements Serializable{
    
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ConfigurationExtractor.class);

    private DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;

    private List<DeviceSettingDefinitionBean> displaySettingsDefinitions;
    
    private List<DeviceSettingDefinitionBean> differentDeviceSettings;
    
    protected ConfiguratorDAO configuratorDAO;
    
	private ConfigurationSet configurationSet;
	
	private ConfigurationSet compareConfigurationSet;
    
	//configuration-centric
    private ConfigurationSelectionBean configurationSelectionBean;
	private Configuration selectedConfiguration;
    private boolean differentOnly;
	private Integer selectedConfigurationID;
    
	private Set<Integer> configurationToUpdate;
	private String reason;
	
	private ConfigurationApplyBean configurationApplyBean;
	
	public ConfiguratorBean() {

		displaySettingsDefinitions = new ArrayList<DeviceSettingDefinitionBean>();
		differentDeviceSettings = new ArrayList<DeviceSettingDefinitionBean>();
	}
	public void init() {

        if((configurationSelectionBean.getSelectedGroupId() == null) ) return;
        	
		differentOnly = false;
		
        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(configurationSelectionBean.getProductType());
        
        configurationSet = ConfigurationExtractor.getConfigurations(configurationSelectionBean.getFilteredVehicleSettings(),
							deviceSettingDefinitionsByProductType.getKeys(configurationSelectionBean.getProductType()));
    }

	//
	//Actions
	//
	
	private void reinitializeConfigurations(){
		
       	configurationSelectionBean.groupChanged();
       	configurationSelectionBean.fetchConfigurations();
       	init();
	}
	private void makeMessage(String messageText){
		
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		message.setSummary(messageText);
		FacesContext.getCurrentInstance().addMessage("", message);
		
	}
    public Object updateConfiguration(){
    	
    	logger.debug("configurator - updateConfiguration");
    	selectedConfiguration = configurationSet.getConfiguration(selectedConfigurationID);
    	Map<Integer,String> differencesMap = selectedConfiguration.getDifferencesMap();
       	for(Integer vehicleID : selectedConfiguration.getVehicleIDs()){
    		
	      if(reason == null|| reason.isEmpty() || differencesMap.isEmpty() || vehicleID==null) {}
	      else{
	      
	          configuratorDAO.updateVehicleSettings(vehicleID, differencesMap, 
	                                                getBaseBean().getUser().getUserID(), 
	                                                reason);
	      }
    	}
       	
       	reinitializeConfigurations();
       	
       	return null;
    }
    public Object applySettingsToTargetVehicles(){
    	
    	logger.debug("configurator - applySettingsToTargetVehicles");
    	
    	configurationApplyBean.applySettingsToTargetVehicles(configurationSet.getConfiguration(selectedConfigurationID), reason);
    	
    	makeMessage("Vehicle settings updated successfully");
    	
       	reinitializeConfigurations();

       	return null;
    }
    
    public Object updateVehicle(){
        
        Map<Integer, String> actualDesiredDifferences = configurationSelectionBean.getVehicleDifferences(configurationApplyBean.getSelectedVehicleID(), configurationSet.getConfiguration(selectedConfigurationID));

//    	configurationApplyBean.updateVehicle(configurationSet.getConfiguration(selectedConfigurationID));
        configurationApplyBean.updateVehicle(actualDesiredDifferences, reason);
 
    	makeMessage("Vehicle settings updated successfully");
		
       	reinitializeConfigurations();

       	return null;
    }

	public Object applyToConfiguration(){
		
		Configuration applyToConfiguration = configurationSet.getConfiguration(configurationApplyBean.getApplyToConfigurationID());
		if (applyToConfiguration != null){
			
			for (Integer vID : applyToConfiguration.getVehicleIDs()){
				
		        Map<Integer, String> actualDesiredDifferences = configurationSelectionBean.getVehicleDifferences(configurationApplyBean.getSelectedVehicleID(), configurationSet.getConfiguration(selectedConfigurationID));
		    	configurationApplyBean.updateVehicle(vID,actualDesiredDifferences,reason);
			}
		}
    	makeMessage("Vehicle settings updated successfully");
	
       	reinitializeConfigurations();

       	return null;
	}
	
    public Object resetConfiguration(){
    	
    	selectedConfiguration = configurationSet.getConfiguration(selectedConfigurationID);
    	selectedConfiguration.resetDesiredValues();
    	
    	return null;
    }
    public Object compareSelected(){
    	
    	differentOnly = true;
    	compareConfigurationSet = configurationSet.getSelectedConfigurations();
    	differentDeviceSettings = deviceSettingDefinitionsByProductType.deriveReducedSettings(compareConfigurationSet.getSettingIDsWithMoreThanOneValue(),configurationSelectionBean.getProductType());

    	return null;
    }
    
    public Object clearSelected(){
    	
    	differentOnly = false;
    	compareConfigurationSet = null;

    	return null;
    }
	//Getter and setters
	public ConfigurationSet getCompareConfigurationSet() {
		return compareConfigurationSet;
	}

	public void setConfigurationApplyBean(ConfigurationApplyBean configurationApplyBean) {
		this.configurationApplyBean = configurationApplyBean;
	}
	public boolean isDifferentOnly() {
		return differentOnly;
	}
	public void setDifferentOnly(boolean differentOnly) {
		this.differentOnly = differentOnly;
	}
	public void setConfigurationSelectionBean(ConfigurationSelectionBean configurationSelectionBean) {
		this.configurationSelectionBean = configurationSelectionBean;
	}
	public Integer getSelectedConfigurationID() {
        return selectedConfigurationID;
    }
	public void setSelectedConfigurationID(Integer selectedConfigurationID) {
        this.selectedConfigurationID = selectedConfigurationID;
    }
    public List<DeviceSettingDefinitionBean> getDisplaySettingsDefinitions() {
        return displaySettingsDefinitions;
    }
    
    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }
    public DeviceSettingDefinitionsByProductType getDeviceSettingDefinitionsByProductType() {
        return deviceSettingDefinitionsByProductType;
    }

    public void setDeviceSettingDefinitionsByProductType(DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType) {
        this.deviceSettingDefinitionsByProductType = deviceSettingDefinitionsByProductType;
    }

    public Configuration getSelectedConfiguration() {
		return selectedConfiguration;
	}
	public void setSelectedConfiguration(Configuration selectedConfiguration) {
		this.selectedConfiguration = selectedConfiguration;
	}
	public ConfigurationSet getConfigurationSet() {
        return configurationSet;
    }
    public boolean getHasConfigurations(){
    	
    	return configurationSet != null && configurationSet.getHasConfigurations();
    }
	public List<DeviceSettingDefinitionBean> getDifferentDeviceSettings() {
		return differentDeviceSettings;
	}
	public Set<Integer> getConfigurationToUpdate() {
		return configurationToUpdate;
	}
	public void setConfigurationToUpdate(Set<Integer> configurationToUpdate) {
		this.configurationToUpdate = configurationToUpdate;
	}
    
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

//    private void makeupSettings( List<DeviceSettingDefinitionBean> settings, List<VehicleSetting> vehicleSettings){
//        
//        for(VehicleSetting vs: vehicleSettings){
//            
//            Map<Integer, String> settingMap = new HashMap<Integer, String>();
//            
//            for (DeviceSettingDefinitionBean dsd: settings){
//                String value = "";
//                if (dsd.getChoices().size()>1){
//                    
//                    value =  dsd.getChoices().get(new Double(Math.random()*dsd.getChoices().size()).intValue());
//                }
//                else if (dsd.getVarType().equals(VarType.VTBOOLEAN)){
//                    
//                    value = ""+(new Double(Math.random()*2).intValue()==0);
//                }
//                else if (dsd.getVarType().equals(VarType.VTDOUBLE) || dsd.getVarType().equals(VarType.VTINTEGER)){
//                    
//                    if ((dsd.getMin() != null) && (dsd.getMax() != null)){
//                        
//                        int range = new Integer(Integer.parseInt(dsd.getMax()))-new Integer(Integer.parseInt(dsd.getMin()));
//                        int valuePlus = new Double(Math.random()*range).intValue();
//                        value=""+new Integer(Integer.parseInt(dsd.getMin())).intValue() +valuePlus;
//                    }
//                    else{
//                        
//                        value = ""+0;
//                    }
//                }
//                else {
//                    
//                   value = "made_up_value";
//                }
//                settingMap.put(dsd.getSettingID(),value);
//            }
//            vs.setActual(settingMap);
//            Map<Integer, String> desiredSettingMap = new HashMap<Integer, String>();
//            for (DeviceSettingDefinitionBean dsd: settings){
//            	
//                 if (dsd.getVarType().equals(VarType.VTSTRING)){
//                    
//                	desiredSettingMap.put(dsd.getSettingID(),"desired_value");
//                }
//            }
//            
//            vs.setDesired(desiredSettingMap);
//       }
//    }
//  public void createConfigurationsFromVehicleSettings(){
//	
//    displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(ProductType.TIWIPRO_R74);
//
//    ConfigurationExtractor.getConfigurations(configurationSelectionBean.getVehicleSettings().getVehicleSettings(),
//    		 deviceSettingDefinitionsByProductType.getKeys(ProductType.TIWIPRO_R74));
//}
//  private void buildConfigurations(){
//	
////    makeupSettings(deviceSettingDefinitionsByProductType.getDeviceSettings(configurationSelectionBean.getProductType()),vehicleDAO.getVehicleSettings(1));
//    
//}




}