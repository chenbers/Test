package com.inthinc.pro.backing.configurator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import org.richfaces.model.selection.Selection;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.configurator.model.Configuration;
import com.inthinc.pro.configurator.model.ConfigurationExtractor;
import com.inthinc.pro.configurator.model.ConfigurationSet;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitionBean;
import com.inthinc.pro.configurator.model.DeviceSettingDefinitionsByProductType;
import com.inthinc.pro.configurator.model.VehicleSettings;
import com.inthinc.pro.configurator.model.VehicleSettingsByProductType;
import com.inthinc.pro.configurator.ui.ChoiceSelectItems;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition.ProductType;

public class ConfiguratorBean extends BaseBean implements Serializable{
    
	private static final long serialVersionUID = 1L;
    private DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;
    private ChoiceSelectItems choiceSelectItems;
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
    
    private Selection selection;
	private Integer selectedRowKey;
    
	public void init() {
         
        if(configurationSelectionBean.getSelectedGroupId() == null) return;
        
        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(configurationSelectionBean.getProductType());
        
        choiceSelectItems = new ChoiceSelectItems(deviceSettingDefinitionsByProductType.getDeviceSettings(configurationSelectionBean.getProductType()));
        vehicleSettingsByProductType.initializeSettings(vehicleSettings.getVehicleSettings(configurationSelectionBean.getSelectedGroupIdValue()));
        
        buildConfigurations();
    }
    public ConfigurationSelectionBean getConfigurationSelectionBean() {
		return configurationSelectionBean;
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
    public Selection getSelection() {
		return selection;
	}
	public void setSelection(Selection selection) {
		this.selection = selection;
		
	}

    public Integer getSelectedRowKey() {
        return selectedRowKey;
    }
    public void setSelectedRowKey(Integer selectedRowKey) {
        this.selectedRowKey = selectedRowKey;
    }
    public List<DeviceSettingDefinitionBean> getDisplaySettingsDefinitions() {
        return displaySettingsDefinitions;
    }
    public void setDisplaySettingsDefinitions(List<DeviceSettingDefinitionBean> displaySettingsDefinitions) {
        this.displaySettingsDefinitions = displaySettingsDefinitions;
    }
    public String displayVehicle(){
    	
    	if (selectedVehicleID == null) return null;
    	
    	selectedVehicleSetting = vehicleSettings.getVehicleSetting(selectedVehicleID);
    	
    	return null;
    }
//    public void changeProduct(){
//        
//        if(selectedGroupId == null) return;
//        
//        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(productType);
//        
//        choiceSelectItems = new ChoiceSelectItems(deviceSettingDefinitionsByProductType.getDeviceSettings(productType));
//        
//        vehicleSettingsByProductType.initializeSettings(vehicleSettings.getVehicleSettings(getSelectedGroupIdValue()));
//        
//        buildConfigurations();
//    }
    public void buildConfigurations(){
    	
//        makeupSettings(deviceSettingDefinitionsByProductType.getDeviceSettings(productType),vehicleSettingsByProductType.getVehicleSettings(productType));
        
        configurationSet = ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettingsByProductType(configurationSelectionBean.getProductType()));
        validateConfigurationSet();
        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(configurationSelectionBean.getProductType());
        differentDeviceSettings = deviceSettingDefinitionsByProductType.deriveReducedSettings(configurationSet.getSettingIDsWithMoreThanOneValue(),configurationSelectionBean.getProductType());
    }
    private void validateConfigurationSet(){
    	
        for( Configuration configuration : configurationSet.getConfigurations()){
            
           Map<Integer,String> colors = new HashMap<Integer,String>();
           Map<Integer,String> messages = new HashMap<Integer,String>();
           for(DeviceSettingDefinitionBean dsdBean: deviceSettingDefinitionsByProductType.getDeviceSettings(configurationSelectionBean.getProductType())){
         	   	
                String color = dsdBean.validate(configuration.getValues().get(dsdBean.getSettingID()))?"white":"pink";
                colors.put(dsdBean.getSettingID(),color);
                String message = dsdBean.validate(configuration.getValues().get(dsdBean.getSettingID()))?"":"Invalid";
                messages.put(dsdBean.getSettingID(),message);
            }
            configuration.setColors(colors);
            configuration.setMessages(messages);
       }
    }
//    public void groupChanged(){
//        
//        changeProduct();
//    }
    public void setVehicleSettings(VehicleSettings vehicleSettings) {
        this.vehicleSettings = vehicleSettings;
    }
    
    public DeviceSettingDefinitionsByProductType getDeviceSettingDefinitionsByProductType() {
        return deviceSettingDefinitionsByProductType;
    }

    public void setDeviceSettingDefinitionsByProductType(DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType) {
        this.deviceSettingDefinitionsByProductType = deviceSettingDefinitionsByProductType;
    }
    public Object updateSettings(){
     	
        Iterator<Object> it = selection.getKeys();
        while(it.hasNext()){
        	
        	Integer rowKey = (Integer)it.next();
        	Configuration configuration = configurationSet.getConfigurations().get(rowKey);
        	for(Integer vehicleID : configuration.getVehicleIDs()){
        		
        		if (vehicleID.intValue()==1){
        			vehicleSettings.updateVehicleSettings(vehicleID, configuration.getNewDesiredValues(), getProUser().getUser().getUserID(), "testing"/*configuration.getReason()*/);
        		}
        	}
        }
        return null;
    }
    public void updateConfiguration(){
    	
       	for(Integer vehicleID : selectedConfiguration.getVehicleIDs()){
    		
       		//For testing only update vehicle 1
    		if (vehicleID.intValue()==1){
    			vehicleSettings.updateVehicleSettings(vehicleID, selectedConfiguration.getNewDesiredValues(), getProUser().getUser().getUserID(), "testing"/*configuration.getReason()*/);
    		}
    	}

    }

    public void markSetting(ValueChangeEvent valueChangeEvent){
    	
    	System.out.println("configurator - markSetting");
    	UIComponent source = (UIComponent)valueChangeEvent.getSource();
    	String id = source.getClientId(FacesContext.getCurrentInstance());

    	String newValue = (String)valueChangeEvent.getNewValue();
//    	String oldValue = (String)valueChangeEvent.getOldValue();
    	
    	//extract row and settingID and set in desired settings
        String[] itemKeys = ((String) id).split(":|-");
        int row = Integer.parseInt(itemKeys[2]);
        int settingID =  Integer.parseInt(itemKeys[4]);
        Configuration configuration = configurationSet.getConfigurations().get(row);
        configuration.setNewDesiredValue(settingID, (String)newValue);
//        if (deviceSettingDefinitionsByProductType.getDeviceSetting(productType,settingID).validate(newValue)){
//        	configuration.getColors().put(settingID, "white");
//        }
//        else{
//        	configuration.getColors().put(settingID, "pink");
//       	
//        }
  
    }
    public void createConfigurationsFromVehicleSettings(){
        
        ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettingsByProductType(ProductType.TIWIPRO_R74));
   }

    public void editSettings(){
        
         selectedConfiguration = getHasConfigurations()?configurationSet.getConfigurations().get(getSelectedRowKey()):null;
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
    public ChoiceSelectItems getChoiceSelectItems() {
        return choiceSelectItems;
    }
//    public Object displayConfigurations(){
//    	
//        if(configurationSelectionBean.getSelectedGroupId() == null) return null;
//        
//        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(configurationSelectionBean.getProductType());
//        
//        choiceSelectItems = new ChoiceSelectItems(deviceSettingDefinitionsByProductType.getDeviceSettings(configurationSelectionBean.getProductType()));
//        
//        vehicleSettingsByProductType.initializeSettings(vehicleSettings.getVehicleSettings(configurationSelectionBean.getSelectedGroupIdValue()));
//        
//        buildConfigurations();
//
//    	return "go_configurator";
//    }
    public boolean getHasConfigurations(){
    	
    	return configurationSet != null && configurationSet.getHasConfigurations();
    }
	public void validateUserID(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		System.out.println(value.toString());
		throw new ValidatorException(new FacesMessage("invalid"));
	}
	public void validateUsername(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		System.out.println(value.toString());
		throw new ValidatorException(new FacesMessage("invalid"));
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
