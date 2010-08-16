package com.inthinc.pro.backing.configurator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.ajax4jsf.model.KeepAlive;
import org.apache.log4j.Logger;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.configurator.model.Configuration;
import com.inthinc.pro.configurator.model.ConfigurationExtractor;
import com.inthinc.pro.configurator.model.ConfigurationSet;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitionBean;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitionsByProductType;
import com.inthinc.pro.configurator.model.VehicleSettings;
import com.inthinc.pro.configurator.model.VehicleSettingsByProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition.ProductType;

@KeepAlive(ajaxOnly=true)
public class ConfiguratorBean implements Serializable{
    
	private BaseBean baseBean;
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ConfigurationExtractor.class);

    private DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;
    private List<DeviceSettingDefinitionBean> differentDeviceSettings;
    private List<DeviceSettingDefinitionBean> displaySettingsDefinitions;
    
    private VehicleSettings vehicleSettings = new VehicleSettings();
    private VehicleSettingsByProductType vehicleSettingsByProductType = new VehicleSettingsByProductType();
    
    private ConfigurationSet configurationSet;
    
    //vehicle-centric
    private Integer selectedVehicleID;
    private VehicleSetting selectedVehicleSetting;
    
	//configuration-centric
    private ConfigurationSelectionBean configurationSelectionBean;
	private Configuration selectedConfiguration;
    private boolean differentOnly;
	private Integer selectedConfigurationID;
    
	public ConfiguratorBean() {
		super();

		differentDeviceSettings = new ArrayList<DeviceSettingDefinitionBean>();
		displaySettingsDefinitions = new ArrayList<DeviceSettingDefinitionBean>();
	}
	public void init() {

        if(configurationSelectionBean.getSelectedGroupId() == null) return;
        	
		differentOnly = false;
        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(configurationSelectionBean.getProductType());
        
        vehicleSettingsByProductType.initializeSettings(vehicleSettings.getVehicleSettings(configurationSelectionBean.getSelectedGroupIdValue()));
        
        buildConfigurations();
    }
    public boolean isDifferentOnly() {
		return differentOnly;
	}
	public void setDifferentOnly(boolean differentOnly) {
		this.differentOnly = differentOnly;
	}
	public void setConfigurationSelectionBean(
			ConfigurationSelectionBean configurationSelectionBean) {
		this.configurationSelectionBean = configurationSelectionBean;
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
	public Integer getSelectedConfigurationID() {
        return selectedConfigurationID;
    }
    public void setSelectedConfigurationID(Integer selectedConfigurationID) {
        this.selectedConfigurationID = selectedConfigurationID;
    }
    public List<DeviceSettingDefinitionBean> getDisplaySettingsDefinitions() {
        return displaySettingsDefinitions;
    }
    public String displayVehicle(){
    	
    	if (selectedVehicleID == null) return null;
    	
    	selectedVehicleSetting = vehicleSettings.getVehicleSetting(selectedVehicleID);
    	
    	return null;
    }
    public void buildConfigurations(){
    	
//        makeupSettings(deviceSettingDefinitionsByProductType.getDeviceSettings(productType),vehicleSettingsByProductType.getVehicleSettings(productType));
        
        configurationSet = ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettingsByProductType(configurationSelectionBean.getProductType()));
        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(configurationSelectionBean.getProductType());
        differentDeviceSettings = deviceSettingDefinitionsByProductType.deriveReducedSettings(configurationSet.getSettingIDsWithMoreThanOneValue(),configurationSelectionBean.getProductType());
    }
    public void setVehicleSettings(VehicleSettings vehicleSettings) {
        this.vehicleSettings = vehicleSettings;
    }
    
    public DeviceSettingDefinitionsByProductType getDeviceSettingDefinitionsByProductType() {
        return deviceSettingDefinitionsByProductType;
    }

    public void setDeviceSettingDefinitionsByProductType(DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType) {
        this.deviceSettingDefinitionsByProductType = deviceSettingDefinitionsByProductType;
    }
    public Object updateConfiguration(){
    	
    	logger.debug("configurator - updateConfiguration");
    	selectedConfiguration = configurationSet.getConfigurations().get(selectedConfigurationID);
       	for(Integer vehicleID : selectedConfiguration.getVehicleIDs()){
    		
       		//For testing only update vehicle 1
    		if (vehicleID.intValue()==1){
    			vehicleSettings.updateVehicleSettings(	vehicleID, selectedConfiguration.getNewDesiredValues(), 
    													baseBean.getProUser().getUser().getUserID(), 
    													selectedConfiguration.getReason());
    		}
    	}
       	return null;
    }

    public void markSetting(ValueChangeEvent valueChangeEvent){
    	
    	logger.debug("configurator - markSetting");
    	UIComponent source = (UIComponent)valueChangeEvent.getSource();
    	
    	//extract row and settingID and set in desired settings
        String[] itemKeys = extractIdSegments(source.getClientId(FacesContext.getCurrentInstance()));
        
        Configuration configuration = configurationSet.getConfigurations().get(extractRow(itemKeys));
    	String newValue = (String)valueChangeEvent.getNewValue();
        configuration.setNewDesiredValue(extractSettingID(itemKeys), (String)newValue);
  
    }
    private String[] extractIdSegments(String id){
    	
    	return ((String)id).split(":|-");
    }
    private int extractRow(String[] itemKeys){
    	
    	return Integer.parseInt(itemKeys[2]);
    }
    private int extractSettingID(String[] itemKeys){
    	
    	return  Integer.parseInt(itemKeys[4]);
    }
    public void createConfigurationsFromVehicleSettings(){
        
        ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettingsByProductType(ProductType.TIWIPRO_R74));
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
