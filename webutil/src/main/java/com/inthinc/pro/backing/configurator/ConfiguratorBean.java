package com.inthinc.pro.backing.configurator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.configurator.model.Configuration;
import com.inthinc.pro.configurator.model.ConfigurationExtractor;
import com.inthinc.pro.configurator.model.ConfigurationSet;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitionBean;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitionsByProductType;
import com.inthinc.pro.configurator.model.VehicleSettingsByProductType;
import com.inthinc.pro.configurator.model.VehicleSettingsDAO;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;

@KeepAlive(ajaxOnly=true)
public class ConfiguratorBean implements Serializable{
    
	private BaseBean baseBean;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ConfigurationExtractor.class);

    private DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;
    private List<DeviceSettingDefinitionBean> differentDeviceSettings;
    private List<DeviceSettingDefinitionBean> displaySettingsDefinitions;
    
    private VehicleSettingsDAO vehicleSettingsDAO;
    private VehicleSettingsByProductType vehicleSettingsByProductType = new VehicleSettingsByProductType();
    private List<VehicleSetting> filteredVehicleSettings;
    
    private List<String> vehicleSelectionSource = new ArrayList<String>();
    private List<String> vehicleSelectionTarget = new ArrayList<String>();
    

	private ConfigurationSet configurationSet;
	
	private ConfigurationSet compareConfigurationSet;
    
	//configuration-centric
    private ConfigurationSelectionBean configurationSelectionBean;
	private Configuration selectedConfiguration;
    private boolean differentOnly;
	private Integer selectedConfigurationID;
    
	public ConfiguratorBean() {

		differentDeviceSettings = new ArrayList<DeviceSettingDefinitionBean>();
		displaySettingsDefinitions = new ArrayList<DeviceSettingDefinitionBean>();
	}
	public void init() {

        if((configurationSelectionBean.getSelectedGroupId() == null) || configurationSelectionBean.getSelectedGroupId().isEmpty()) return;
        	
		differentOnly = false;
        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(configurationSelectionBean.getProductType());
        
        vehicleSettingsByProductType.initializeSettings(vehicleSettingsDAO.getVehicleSettings(configurationSelectionBean.getSelectedGroupIdValue()));
        
        filterMakeModelYear(configurationSelectionBean.getVehicleList(),vehicleSettingsByProductType.getVehicleSettingsByProductType(configurationSelectionBean.getProductType()));
        buildConfigurations();
    }
	private void filterMakeModelYear(List<Integer> vehicleIDs, List<VehicleSetting> vehicleSettings){
		
		if (vehicleIDs.isEmpty()){
			filteredVehicleSettings = vehicleSettings;
			return;
		}
		filteredVehicleSettings = new ArrayList<VehicleSetting>();
		for (VehicleSetting vs:vehicleSettings){
			
			if (vehicleIDs.contains(vs.getVehicleID())){
				
				filteredVehicleSettings.add(vs);
			}
		}
	}
	public ConfigurationSet getCompareConfigurationSet() {
		return compareConfigurationSet;
	}

    public List<String> getVehicleSelectionTarget() {
		return vehicleSelectionTarget;
	}
	public void setVehicleSelectionTarget(List<String> vehicleSelectionTarget) {
		this.vehicleSelectionTarget = vehicleSelectionTarget;
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
    public List<String> getVehicleSelectionSource() {
		return vehicleSelectionSource;
	}
	public Integer getSelectedConfigurationID() {
        return selectedConfigurationID;
    }
    public void setVehicleSelectionSource(List<String> vehicleSelectionSource) {
		this.vehicleSelectionSource = vehicleSelectionSource;
	}
	public void setSelectedConfigurationID(Integer selectedConfigurationID) {
        this.selectedConfigurationID = selectedConfigurationID;
    }
    public List<DeviceSettingDefinitionBean> getDisplaySettingsDefinitions() {
        return displaySettingsDefinitions;
    }
	public List<String> getSelectedVehicles() {
		return vehicleSelectionSource;
	}
	public void setSelectedVehicles(List<String> selectedVehicles) {
		this.vehicleSelectionSource = selectedVehicles;
	}

    public void buildConfigurations(){
    	
//        makeupSettings(deviceSettingDefinitionsByProductType.getDeviceSettings(productType),vehicleSettingsByProductType.getVehicleSettings(productType));
        
        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(configurationSelectionBean.getProductType());
//        configurationSet = ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettingsByProductType(configurationSelectionBean.getProductType()),
          configurationSet = ConfigurationExtractor.getConfigurations(filteredVehicleSettings,
            					deviceSettingDefinitionsByProductType.getKeys(configurationSelectionBean.getProductType()));
//        differentDeviceSettings = deviceSettingDefinitionsByProductType.deriveReducedSettings(configurationSet.getSettingIDsWithMoreThanOneValue(),configurationSelectionBean.getProductType());
    }
    public void setVehicleSettingsDAO(VehicleSettingsDAO vehicleSettingsDAO) {
        this.vehicleSettingsDAO = vehicleSettingsDAO;
    }
    
    public DeviceSettingDefinitionsByProductType getDeviceSettingDefinitionsByProductType() {
        return deviceSettingDefinitionsByProductType;
    }

    public void setDeviceSettingDefinitionsByProductType(DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType) {
        this.deviceSettingDefinitionsByProductType = deviceSettingDefinitionsByProductType;
    }
    public Object updateConfiguration(){
    	
    	logger.debug("configurator - updateConfiguration");
    	selectedConfiguration = configurationSet.getConfiguration(selectedConfigurationID);
       	for(Integer vehicleID : selectedConfiguration.getVehicleIDs()){
    		
       		//For testing only update vehicle 1
    		if (vehicleID.intValue()==1){
    			vehicleSettingsDAO.updateVehicleSettings(vehicleID, selectedConfiguration.getDifferencesMap(), 
    													baseBean.getProUser().getUser().getUserID(), 
    													selectedConfiguration.getReason());
    		}
    	}
       	return null;
    }

    public Object compareSelected(){
    	
    	differentOnly = true;
    	compareConfigurationSet = configurationSet.getSelectedConfigurations();
    	differentDeviceSettings = deviceSettingDefinitionsByProductType.deriveReducedSettings(compareConfigurationSet.getSettingIDsWithMoreThanOneValue(),configurationSelectionBean.getProductType());

    	return null;
    }
    
    public Object applySettingsToTargetVehicles(){
    	
    	logger.debug("configurator - applySettingsToTargetVehicles");
    	selectedConfiguration = configurationSet.getConfiguration(selectedConfigurationID);
       	for(String vID : vehicleSelectionTarget){
    		
       		//For testing only update vehicle 1
       		Integer vehicleID = Integer.parseInt(vID);
    		if (vehicleID==1){
    			vehicleSettingsDAO.updateVehicleSettings(	vehicleID, selectedConfiguration.getLatestDesiredValues(), 
    													baseBean.getProUser().getUser().getUserID(), 
    													selectedConfiguration.getReason());
    		}
    	}
       	return null;
    }
    public void createConfigurationsFromVehicleSettings(){
    	
        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(ProductType.TIWIPRO_R74);

        ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettingsByProductType(ProductType.TIWIPRO_R74),
        		 deviceSettingDefinitionsByProductType.getKeys(ProductType.TIWIPRO_R74));
   }

    public void setVehicleSettingsByProductType(VehicleSettingsByProductType vehicleSettingsByProductType) {
        this.vehicleSettingsByProductType = vehicleSettingsByProductType;
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
    public List<DeviceSettingDefinitionBean> getDifferentDeviceSettings() {
        return differentDeviceSettings;
    }
    public boolean getHasConfigurations(){
    	
    	return configurationSet != null && configurationSet.getHasConfigurations();
    }
    
	public BaseBean getBaseBean() {
		return baseBean;
	}
	public void setBaseBean(BaseBean baseBean) {
		this.baseBean = baseBean;
	}

	public Object prepareVehicleSelection() {

		vehicleSelectionSource = new ArrayList<String>(vehicleSettingsByProductType.getVehicleSelectItems(configurationSelectionBean.getProductType()));
		if (selectedConfigurationID != null){
			vehicleSelectionTarget = new ArrayList<String>();
	       	for(Integer vehicleID : configurationSet.getConfiguration(selectedConfigurationID).getVehicleIDs()){
	    		
	       		vehicleSelectionTarget.add(""+vehicleID);
	       		vehicleSelectionSource.remove(""+vehicleID);
	    	}
		}
		return null;
	}

 }

//    private void makeupSettings( List<DeviceSettingDefinition> settings, List<VehicleSetting> vehicleSettings){
//        
//        for(VehicleSetting vs: vehicleSettings){
//            
//            Map<Integer, String> settingMap = new HashMap<Integer, String>();
//            
//            for (DeviceSettingDefinition dsd: settings){
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
//            for (DeviceSettingDefinition dsd: settings){
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
