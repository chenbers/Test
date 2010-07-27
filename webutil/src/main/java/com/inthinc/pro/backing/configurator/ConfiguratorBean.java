package com.inthinc.pro.backing.configurator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.backing.model.ConfigurationExtractor;
import com.inthinc.pro.backing.model.ConfigurationSet;
import com.inthinc.pro.backing.model.SettingOptions;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.DeviceSettingDefinitions;
import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.model.configurator.VehicleSettings;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition.ProductType;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition.VarType;
import com.inthinc.pro.ui.ChoiceSelectItems;

public class ConfiguratorBean extends BaseBean {
    
    private DeviceSettingDefinitions deviceSettingDefinitions;
    private DeviceSettingDefinitionsByProductType deviceSettingDefinitionsByProductType;
    private ChoiceSelectItems choiceSelectItems;
    private List<DeviceSettingDefinition> differentDeviceSettings;
    private List<DeviceSettingDefinition> displaySettingsDefinitions;
    
    private VehicleSettings vehicleSettings;
    private VehicleSettingsByProductType vehicleSettingsByProductType;
    
    private ConfigurationSet configurationSet;
    
    
     private SettingOptions selectedSettings;
    
    private Integer selectedGroupId;
    @SuppressWarnings("unused")
    private Integer productTypeCode; //jsf needs this for reflection
    private ProductType productType;
    private boolean differentOnly;

    
//    private Selection selectedRow;
    private Integer selectedRowKey;
    
    public void init() {
        
        selectedGroupId = null;
        productType = ProductType.valueOfByCode(16);
        differentOnly = false;
        
        deviceSettingDefinitionsByProductType.init();
        choiceSelectItems = new ChoiceSelectItems();
        choiceSelectItems.init(deviceSettingDefinitionsByProductType.getDeviceSettings(productType));
        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(productType);
        vehicleSettingsByProductType = new VehicleSettingsByProductType();
        differentDeviceSettings = Collections.emptyList();
    }
    public Object setupConfigurator(){
        
        selectedGroupId = null;
        productType = ProductType.valueOfByCode(16);
        differentOnly = false;
        
        deviceSettingDefinitionsByProductType.init();
        choiceSelectItems = new ChoiceSelectItems();
        choiceSelectItems.init(deviceSettingDefinitionsByProductType.getDeviceSettings(productType));
        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(productType);
        vehicleSettingsByProductType = new VehicleSettingsByProductType();
        differentDeviceSettings = Collections.emptyList();
        
        return "go_configurator";
    }
    public Integer getSelectedRowKey() {
        return selectedRowKey;
    }
    public void setSelectedRowKey(Integer selectedRowKey) {
        this.selectedRowKey = selectedRowKey;
    }
    public List<DeviceSettingDefinition> getDisplaySettingsDefinitions() {
        return displaySettingsDefinitions;
    }
    public void setDisplaySettingsDefinitions(List<DeviceSettingDefinition> displaySettingsDefinitions) {
        this.displaySettingsDefinitions = displaySettingsDefinitions;
    }
    public Integer getProductTypeCode() {
        return productType.getCode();
    }
    public void setProductTypeCode(Integer productTypeCode) {
        productType = ProductType.valueOfByCode(productTypeCode);
    }
    public void changeProduct(){
        
        if(selectedGroupId == null) return;
        
        vehicleSettingsByProductType.initializeSettings(vehicleSettings.getVehicleSettings(selectedGroupId));
        
        //temporary until real data comes
        makeupSettings(deviceSettingDefinitionsByProductType.getDeviceSettings(productType),vehicleSettingsByProductType.getVehicleSettings(productType));
        configurationSet = ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettings(productType));
        validateConfigurationSet();
        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(productType);
        differentDeviceSettings = deviceSettingDefinitionsByProductType.deriveReducedSettings(configurationSet.getSettingIDsWithMoreThanOneValue(),productType);
    }
    public void buildSettings(){
        makeupSettings(deviceSettingDefinitionsByProductType.getDeviceSettings(productType),vehicleSettingsByProductType.getVehicleSettings(productType));
        
        configurationSet = ConfigurationExtractor.getConfigurations(vehicleSettingsByProductType.getVehicleSettings(productType));
        validateConfigurationSet();
        displaySettingsDefinitions = deviceSettingDefinitionsByProductType.getDeviceSettings(productType);
        differentDeviceSettings = deviceSettingDefinitionsByProductType.deriveReducedSettings(configurationSet.getSettingIDsWithMoreThanOneValue(),productType);
    }
    private void validateConfigurationSet(){
        for( SettingOptions settingOptions : configurationSet.getConfigurations()){
            
           Map<Integer,String> colors = new HashMap<Integer,String>();
           for(DeviceSettingDefinition dsd: deviceSettingDefinitionsByProductType.getDeviceSettings(productType)){
                
                String color = dsd.validateValue(settingOptions.getValues().get(dsd.getSettingID()))?"white":"pink";
                colors.put(dsd.getSettingID(),color);
            }
            settingOptions.setColors(colors);
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
    public Object save(){
        
        int i =0;
        i ++;
        return null;
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
        
        if((configurationSet == null)||(configurationSet.getConfigurations()==null) ||(configurationSet.getConfigurations().size() == 0)) {
            selectedSettings = null;
        }
        else{
            selectedSettings = configurationSet.getConfigurations().get(getSelectedRowKey());
        }
    }
    public SettingOptions getSelectedSettings() {
        return selectedSettings;
    }
    public void setSelectedSettings(SettingOptions selectedSettings) {
        this.selectedSettings = selectedSettings;
    }
     public void setVehicleSettingsByProductType(VehicleSettingsByProductType vehicleSettingsByProductType) {
        this.vehicleSettingsByProductType = vehicleSettingsByProductType;
    }
    public ConfigurationSet getConfigurationSet() {
        return configurationSet;
    }
    private void makeupSettings( List<DeviceSettingDefinition> settings, List<VehicleSetting> vehicleSettings){
        
        for(VehicleSetting vs: vehicleSettings){
            
            Map<Integer, String> settingMap = new HashMap<Integer, String>();
            
            for (DeviceSettingDefinition dsd: settings){
                String value = "";
                if (dsd.getChoices().size()>1){
                    
                    value =  dsd.getChoices().get(new Double(Math.random()*dsd.getChoices().size()).intValue());
                }
                else if (dsd.getVarType().equals(VarType.VTBOOLEAN)){
                    
                    value = ""+(new Double(Math.random()*2).intValue()==0);
                }
                else if (dsd.getVarType().equals(VarType.VTDOUBLE) || dsd.getVarType().equals(VarType.VTINTEGER)){
                    
                    if ((dsd.getMin() != null) && (dsd.getMax() != null)){
                        
                        int range = new Integer(Integer.parseInt(dsd.getMax()))-new Integer(Integer.parseInt(dsd.getMin()));
                        int valuePlus = new Double(Math.random()*range).intValue();
                        value=""+new Integer(Integer.parseInt(dsd.getMin())).intValue() +valuePlus;
                    }
                    else{
                        
                        value = ""+0;
                    }
                }
                else {
                    
                   value = "made_up_value";
                }
                settingMap.put(dsd.getSettingID(),value);
            }
            vs.setActual(settingMap);
        }
    }
    public List<DeviceSettingDefinition> getDifferentDeviceSettings() {
        return differentDeviceSettings;
    }
    public ChoiceSelectItems getChoiceSelectItems() {
        return choiceSelectItems;
    }
    public DeviceSettingDefinitions getDeviceSettingDefinitions() {
        return deviceSettingDefinitions;
    }
 }
