package com.inthinc.pro.backing.configurator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.model.selection.Selection;

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
import com.inthinc.pro.ui.ChoiceSelectItems;

public class ConfiguratorBean extends BaseBean {
    
//    private DeviceSettingDefinitions deviceSettingDefinitions;
    private DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;
    private ChoiceSelectItems choiceSelectItems;
    private List<DeviceSettingDefinitionBean> differentDeviceSettings;
    private List<DeviceSettingDefinitionBean> displaySettingsDefinitions;
    
    private VehicleSettings vehicleSettings;
    private VehicleSettingsByProductType vehicleSettingsByProductType;
    
    private ConfigurationSet configurationSet;
    
    
    private Configuration selectedSettings;
    //vehicle-centric
    private Integer selectedVehicleID;
    private VehicleSetting selectedVehicleSetting;
    
	//configuration-centric
    private Integer selectedGroupId;
    @SuppressWarnings("unused")
    private Integer productTypeCode; //jsf needs this for reflection
    private ProductType productType;
    private boolean differentOnly;

    
    private Selection selection;
	private Integer selectedRowKey;
    
    public void init() {
        
        selectedGroupId = null;
        productType = ProductType.TIWIPRO_R74;
        differentOnly = false;
        
        choiceSelectItems = new ChoiceSelectItems(deviceSettingDefinitionsByProductType.getDeviceSettings(productType));
        
        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(productType);
        vehicleSettingsByProductType = new VehicleSettingsByProductType();
        differentDeviceSettings = Collections.emptyList();
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
    public Integer getProductTypeCode() {
        return productType.getCode();
    }
    public void setProductTypeCode(Integer productTypeCode) {
        productType = ProductType.valueOfByCode(productTypeCode);
    }
    public void changeVehicle(){
    	
    	if (selectedVehicleID == null) return;
    	
    	selectedVehicleSetting = vehicleSettings.getVehicleSetting(selectedVehicleID);
    }
    public void changeProduct(){
        
        if(selectedGroupId == null) return;
        
        vehicleSettingsByProductType.initializeSettings(vehicleSettings.getVehicleSettings(selectedGroupId));
        
        buildSettings();
    }
    public void buildSettings(){
    	
//        makeupSettings(deviceSettingDefinitionsByProductType.getDeviceSettings(productType),vehicleSettingsByProductType.getVehicleSettings(productType));
        
        configurationSet = ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettings(productType));
        validateConfigurationSet();
        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(productType);
        differentDeviceSettings = deviceSettingDefinitionsByProductType.deriveReducedSettings(configurationSet.getSettingIDsWithMoreThanOneValue(),productType);
    }
    private void validateConfigurationSet(){
    	
        for( Configuration configuration : configurationSet.getConfigurations()){
            
           Map<Integer,String> colors = new HashMap<Integer,String>();
           Map<Integer,String> messages = new HashMap<Integer,String>();
           for(DeviceSettingDefinitionBean dsdBean: deviceSettingDefinitionsByProductType.getDeviceSettings(productType)){
         	   	
                String color = dsdBean.validate(configuration.getValues().get(dsdBean.getSettingID()))?"white":"pink";
                colors.put(dsdBean.getSettingID(),color);
                String message = dsdBean.validate(configuration.getValues().get(dsdBean.getSettingID()))?"":"Invalid";
                messages.put(dsdBean.getSettingID(),message);
            }
            configuration.setColors(colors);
            configuration.setMessages(messages);
       }
    }
    public void groupChanged(){
        
        changeProduct();
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
//    public void revalidateSettings(){
//    	
//    	int i = 0;
//    	i++;
//    }
//    public void testWhatGetsCalled(){
//    	
//    	int i = 0;
//    	i++;
//    }

    public void markSetting(ValueChangeEvent valueChangeEvent){
    	
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
        
        ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettings(ProductType.TIWIPRO_R74));
   }

    public Integer getSelectedGroupId() {
        return selectedGroupId;
    }

    public void setSelectedGroupId(Integer selectedGroupId) {
        this.selectedGroupId = selectedGroupId;
    }

    public boolean isDifferentOnly() {
        return differentOnly;
    }
    public void setDifferentOnly(boolean differentOnly) {
        this.differentOnly = differentOnly;
    }
    public void editSettings(){
        
         selectedSettings = hasConfigurations()?null:configurationSet.getConfigurations().get(getSelectedRowKey());
    }
    private boolean hasConfigurations(){
    	
    	return (configurationSet == null)||(configurationSet.getConfigurations()==null) ||(configurationSet.getConfigurations().size() == 0);
    }
    public Configuration getSelectedSettings() {
        return selectedSettings;
    }
    public void setSelectedSettings(Configuration selectedSettings) {
        this.selectedSettings = selectedSettings;
    }
     public void setVehicleSettingsByProductType(VehicleSettingsByProductType vehicleSettingsByProductType) {
        this.vehicleSettingsByProductType = vehicleSettingsByProductType;
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
