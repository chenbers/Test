package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.VehicleSetting;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition;
import com.inthinc.pro.model.configurator.DeviceSettingDefinitions;

public class ConfiguratorBean extends BaseBean {
    
    private DeviceSettingDefinitions deviceSettingDefinitions;
    private List<VehicleSetting> vehicleSettings;
    
    private ConfigurationTree<Integer,Long> configurationsTree;
    private List<SettingOptions> settingsConfigurations;
     
    public List<VehicleSetting> getVehicleSettings() {
        return vehicleSettings;
    }

    public void setVehicleSettings(List<VehicleSetting> vehicleSettings) {
        this.vehicleSettings = vehicleSettings;
    }
    
    public DeviceSettingDefinitions getDeviceSettingDefinitions() {
        return deviceSettingDefinitions;
    }

    public void setDeviceSettingDefinitions(DeviceSettingDefinitions deviceSettingDefinitions) {
        this.deviceSettingDefinitions = deviceSettingDefinitions;
    }
    
    public void createConfigurationsFromVehicleSettings(){
         buildTree();
         buildTable();
    }
    private void buildTree(){
       
        configurationsTree = new ConfigurationTree<Integer, Long>();
        List<DeviceSettingDefinition> settings = deviceSettingDefinitions.getOrderedDeviceSettings();
        ConfigurationTree<Integer, Long> lastLeaf = null;
        for (VehicleSetting vs : vehicleSettings){
            //build tree
            lastLeaf = buildVehicleSettingsPath(vs, configurationsTree, settings);

            //add the vehicleID to the last leaf
            lastLeaf.leaf.add(vs.getVehicleID());
        }
    }
    private  ConfigurationTree<Integer,Long> buildVehicleSettingsPath(  VehicleSetting vehicleSetting, 
                                            ConfigurationTree<Integer,Long> configurationsTree,
                                            List<DeviceSettingDefinition> settings){
         
        ConfigurationTree<Integer,Long> currentConfigurationsTreeNode = configurationsTree;
        for(DeviceSettingDefinition setting : settings){

            ConfigurationTree<Integer,Long> nextNode = followExistingPath(vehicleSetting,currentConfigurationsTreeNode,setting);
            if (nextNode == null){
                
                nextNode  = createNewPath(vehicleSetting,currentConfigurationsTreeNode,setting);
            }
            currentConfigurationsTreeNode = nextNode;
        }
        return currentConfigurationsTreeNode;
    }
    private ConfigurationTree<Integer, Long> followExistingPath(   VehicleSetting vehicleSetting,
                                                                            ConfigurationTree<Integer, Long> currentConfigurationsTreeNode,
                                                                            DeviceSettingDefinition setting){
        String value = vehicleSetting.getSettings().get(setting.getSettingID());
        for(ConfigurationTree<Integer,Long> ct: currentConfigurationsTreeNode.subConfigurationTrees){
            
            if (ct.value.equals(value)){
                return ct;
            }
        }
        return null;
    }
    private ConfigurationTree<Integer,Long> createNewPath( VehicleSetting vehicleSetting,
                                                                     ConfigurationTree<Integer, Long> currentConfigurationsTreeNode,
                                                                     DeviceSettingDefinition setting){
        
        String value = vehicleSetting.getSettings().get(setting.getSettingID());
        ConfigurationTree<Integer, Long> subConfigurationTree = new ConfigurationTree<Integer, Long>();
        subConfigurationTree.value = value;
        subConfigurationTree.setParent(currentConfigurationsTreeNode);
        currentConfigurationsTreeNode.addSubTree(subConfigurationTree);
        
        return subConfigurationTree;
    }
    
    private void buildTable(){
        
        //build a row for each path through the tree
        //go through  left most branch to the leaf
        // copy path for each leaf
        //back up one copy path for each branch
//        settingsConfigurations = new ArrayList<SettingOptions>();
//        getSettingsFromTree(configurationsTree, null);
        settingsConfigurations = new ArrayList<SettingOptions>();
        for(int i=0;i<4;i++){
            //create table row
            SettingOptions settingOptions = new SettingOptions();
            Map<String,String> settings = new HashMap<String,String>();
            for(int j=0;j<32;j++){
                settings.put("string_"+j,"string_"+ new Double(Math.random()*100).intValue());
            }
            settingOptions.setValueList(settings);
            List<Long> vehicleIDs = new ArrayList<Long>();
            int max = new Double(Math.random()*5).intValue();
            for(long j=0l;j<Math.random()*5;j++){
                vehicleIDs.add(j);
            }
            settingOptions.setVehicleIDs(vehicleIDs);
            settingsConfigurations.add(settingOptions);
        }
     }
    public void init() {

        deviceSettingDefinitions = new DeviceSettingDefinitions();
        List<DeviceSettingDefinition> dsdList = new ArrayList<DeviceSettingDefinition>();
        for (int i=0;i<32;i++){
            
           DeviceSettingDefinition dsd = new DeviceSettingDefinition();
           dsd.setSettingID(i);
           dsdList.add(dsd);
        }
        deviceSettingDefinitions.setDeviceSettings(dsdList);
        setDeviceSettingDefinitions(deviceSettingDefinitions);
        
        List<VehicleSetting> vehicleSettings = new ArrayList<VehicleSetting>();
        for (int i=0;i<100;i++){
            
            VehicleSetting vs = new VehicleSetting();
            vs.setVehicleID(i);
            Map<Integer,String> settings = new HashMap<Integer,String>();
            
            for(int j= 0;j<32;j++){
                
                settings.put(j, "string_"+j);
            }
            vs.setSettings(settings);
            vehicleSettings.add(vs);
         }
         setVehicleSettings(vehicleSettings);
         buildTable();
    }

    private void getSettingsFromTree(ConfigurationTree<Integer,Long> currentNode, SettingOptions currentSettingOptions){
        
        SettingOptions settingOptions = currentSettingOptions;
        for(ConfigurationTree<Integer,Long> subTree: currentNode.subConfigurationTrees){
            
            if (currentSettingOptions == null){
                
                //back at the top so add a new row
                settingOptions = new SettingOptions();
                settingOptions.addValue(currentNode.value);
            }
            else {
                
            }
        }
    }
//   private class ConfigurationStructure{
//        
//        private List<SettingOptions> settingOptions;
//        private ConfigurationTree<Integer, List<?>> configurationTree;
//    }
    private class ConfigurationTree<K extends Comparable<K>,L> {
        
        private String value;
//        private K element;
        private ConfigurationTree<K,L> parent;
        private List<ConfigurationTree<K,L>> subConfigurationTrees;
        private List<L> leaf;
        
        public ConfigurationTree() {
            super();
            subConfigurationTrees = new ArrayList<ConfigurationTree<K,L>>();
            leaf = new ArrayList<L>();
        }
        
        public void addSubTree(ConfigurationTree<K,L >subTree){
            
            subConfigurationTrees.add(subTree);
        }

        public ConfigurationTree<K, L> getParent() {
            return parent;
        }

        public void setParent(ConfigurationTree<K, L> parent) {
            this.parent = parent;
        }
        
    }
    
    public class SettingOptions{
        
        private List<Long> vehicleIDs;
        private Map<String, String> valueList;
        private List<String> values;
        
        public SettingOptions() {
            super();
            valueList = new HashMap<String,String>();
            values = new ArrayList<String>();
        }
        public void addValue(String value){
            
            if (valueList.get(value) == null) {
                valueList.put(value, value);
                values.add(value);
            }
        }
        public Map<String, String> getValueList() {
            return valueList;
        }
        public void setValueList(Map<String, String> valueList) {
            this.valueList = valueList;
            values = new ArrayList<String>(valueList.values());
        }
        public List<Long> getVehicleIDs() {
            return vehicleIDs;
        }
        public void setVehicleIDs(List<Long> vehicleIDs) {
            this.vehicleIDs = vehicleIDs;
        }
        public void copy(SettingOptions settingOptions){
            valueList = settingOptions.valueList;
        }
        public Integer getCount(){
            return vehicleIDs.size();
        }
        public List<String> getValueListEntries(){

            return values;
        }
    }

    public List<SettingOptions> getSettingsConfigurations() {
        return settingsConfigurations;
    }

    public void setSettingsConfigurations(List<SettingOptions> settingsConfigurations) {
        this.settingsConfigurations = settingsConfigurations;
    }
}
