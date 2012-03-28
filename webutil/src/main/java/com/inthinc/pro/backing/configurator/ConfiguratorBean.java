package com.inthinc.pro.backing.configurator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;

import com.inthinc.pro.backing.UsesBaseBean;
import com.inthinc.pro.configurator.model.ComparedConfigurations;
import com.inthinc.pro.configurator.model.Configuration;
import com.inthinc.pro.configurator.model.ConfigurationExtractor;
import com.inthinc.pro.configurator.model.ConfigurationSet;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitionsByProductType;
import com.inthinc.pro.configurator.ui.ConfigurationApplyBean;
import com.inthinc.pro.configurator.ui.ConfigurationSelectionBean;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.configurator.VehicleSetting;

@KeepAlive(ajaxOnly=true)
public class ConfiguratorBean extends UsesBaseBean implements Serializable{
    
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ConfigurationExtractor.class);

    private ConfigurationExtractor configurationExtractor;
    protected ConfiguratorDAO configuratorDAO;
    
	private ConfigurationSet configurationSet;
	
	private ComparedConfigurations comparedConfigurations;
    private DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;

    //configuration-centric
    private ConfigurationSelectionBean configurationSelectionBean;
	private Configuration selectedConfiguration;
	private Integer selectedConfigurationID;
    
	private Set<Integer> configurationToUpdate;
	private String reason;
	
	private ConfigurationApplyBean configurationApplyBean;
	
	private boolean editing;
	
	public ConfiguratorBean() {

        comparedConfigurations = new ComparedConfigurations();
        editing = false;
	}
	public void init() {

        if((configurationSelectionBean.getSelectedGroupId() == null) ) return;
        configurationExtractor.refreshConfigurations();
        configurationSet = configurationExtractor.getConfigurations();
        
    }

	//
	//Actions
	//
	
	private void reinitializeConfigurations(){
		
       	configurationSelectionBean.groupChanged();
       	configurationSelectionBean.fetchVehicleSettings();
       	init();
	}
	private void makeMessage(String clientId, String messageText, Severity severity){
		
		FacesMessage message = new FacesMessage();
		message.setSeverity(severity);
		message.setSummary(messageText);
		FacesContext.getCurrentInstance().addMessage(clientId, message);
		
	}
	public Object fetchConfigurations(){
	    
	    init();
	    return "go_configuratorConfigurations";
	}
    public Object editConfigurationSettings(){
        
        editing = true;
        return null;
    }
    public Object updateConfiguration(){
    	
    	logger.debug("configurator - updateConfiguration");
    	
    	if (reason == null|| reason.isEmpty()) return null;
    	
    	selectedConfiguration = configurationSet.getConfiguration(selectedConfigurationID);
    	if ((selectedConfiguration == null) || selectedConfiguration.getVehicleIDs().isEmpty()) return null;
    	
    	Map<Integer,String> differencesMap = selectedConfiguration.getDifferencesMap();
    	
    	if (differencesMap.isEmpty()) return null;
    	
       	for(Integer vehicleID : selectedConfiguration.getVehicleIDs()){
    		
       	    if(vehicleID!=null) {
	      
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
    	
    	makeMessage("","Vehicle settings updated successfully", FacesMessage.SEVERITY_INFO);
    	
       	reinitializeConfigurations();

       	return null;
    }
    
    public Object updateVehicle(){
        
        Integer vehicleID = configurationApplyBean.getSelectedVehicleID();

        Map<Integer, String> actualDesiredDifferences = getVehicleDifferences(vehicleID);

        if (actualDesiredDifferences == null) {
            
            makeMessage("selectedVehicleID","Vehicle ID"+vehicleID+" does not exist", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        configurationApplyBean.updateVehicle(actualDesiredDifferences, reason);
 
    	makeMessage("","Vehicle settings updated successfully", FacesMessage.SEVERITY_INFO);
		
    	if(!editing){
    	    reinitializeConfigurations();
    	}

       	return null;
    }

	public Object applyToConfiguration(){
		
		Configuration applyToConfiguration = configurationSet.getConfiguration(configurationApplyBean.getApplyToConfigurationID());
		if (applyToConfiguration != null){
			
			for (Integer vID : applyToConfiguration.getVehicleIDs()){
				
		        Map<Integer, String> actualDesiredDifferences = getVehicleDifferences(vID);
		    	configurationApplyBean.updateVehicle(vID,actualDesiredDifferences,reason);
			}
		}
    	makeMessage("","Vehicle settings updated successfully", FacesMessage.SEVERITY_INFO);
	
        if(!editing){
            reinitializeConfigurations();
        }

       	return null;
	}
	public Object doneEditing(){
	    
	    editing= false;
	    reinitializeConfigurations();
	    return null;
	}
    private Map<Integer,String> getVehicleDifferences(Integer vehicleID){
        
        Configuration selectedConfiguration = configurationSet.getConfiguration(selectedConfigurationID);
        Map<Integer, String> differenceMap = new HashMap<Integer, String>();
        VehicleSetting vehicleSetting = configurationSelectionBean.getVehicleSettings().getVehicleSettingsMap().get(vehicleID);
        
        if(vehicleSetting == null) return null;
        
        Map<Integer, String> combinedVehicleSettings = vehicleSetting.getCombinedSettings();
        Map<Integer, String> configurationSettings  = selectedConfiguration.getLatestDesiredValues();
        
        for (Integer settingID : configurationSettings.keySet()){
            
            if ((configurationSettings.get(settingID) != null) &&
                 !configurationSettings.get(settingID).equals(combinedVehicleSettings.get(settingID))){
                
                differenceMap.put(settingID, configurationSettings.get(settingID));
            }
        }
        return differenceMap;
    }
    public Object resetConfiguration(){
    	
    	selectedConfiguration = configurationSet.getConfiguration(selectedConfigurationID);
    	selectedConfiguration.resetDesiredValues();
    	
    	return null;
    }
    public Object compareSelected(){
    	
    	comparedConfigurations.getDifferences(configurationSet.getSelectedConfigurations(), 
    	        deviceSettingDefinitionsByProductType, 
    	        configurationSelectionBean.getProductType());

    	return null;
    }
    
    public Object clearSelected(){
    	
    	comparedConfigurations.clear();

    	return null;
    }
	//Getter and setters

    public void setConfigurationExtractor(ConfigurationExtractor configurationExtractor) {
        this.configurationExtractor = configurationExtractor;
    }

	public void setConfigurationApplyBean(ConfigurationApplyBean configurationApplyBean) {
		this.configurationApplyBean = configurationApplyBean;
	}
	public void setConfigurationSelectionBean(ConfigurationSelectionBean configurationSelectionBean) {
		this.configurationSelectionBean = configurationSelectionBean;
	}
	public Integer getSelectedConfigurationID() {
        return selectedConfigurationID;
    }
	public void setSelectedConfigurationID(Integer selectedConfigurationID) {
        this.selectedConfigurationID = selectedConfigurationID;
        selectedConfiguration = configurationSet.getConfiguration(selectedConfigurationID);
    }
    
    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
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
    public void setComparedConfigurations(ComparedConfigurations comparedConfigurations) {
        this.comparedConfigurations = comparedConfigurations;
    }

    public ComparedConfigurations getComparedConfigurations() {
        return comparedConfigurations;
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
    public void setDeviceSettingDefinitionsByProductType(DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType) {
        this.deviceSettingDefinitionsByProductType = deviceSettingDefinitionsByProductType;
    }
    public boolean isEditing(){
         return editing;
    }
    public List<Configuration> getConfigurationData(){
        
        return configurationSet.getConfigurations();
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
//    displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(ProductType.TIWIPRO);
//
//    ConfigurationExtractor.getConfigurations(configurationSelectionBean.getVehicleSettings().getVehicleSettings(),
//    		 deviceSettingDefinitionsByProductType.getKeys(ProductType.TIWIPRO));
//}
//  private void buildConfigurations(){
//	
////    makeupSettings(deviceSettingDefinitionsByProductType.getDeviceSettings(configurationSelectionBean.getProductType()),vehicleDAO.getVehicleSettings(1));
//    
//}




}