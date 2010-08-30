package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.configurator.ProductType;

public class DeviceSettingDefinitionsByProductType {

    private Map<ProductType, Map<Integer,DeviceSettingDefinitionBean>> deviceSettings;
    private Map<ProductType, Map<Integer,DeviceSettingDefinitionBean>> ignoredSettings;
    private Map<ProductType, List<DeviceSettingDefinitionBean>> deviceSettingLists;
    
    public void init(){
        
        initProductSettings();
        distributeSettings();
        sortSettings();
    }
    private void initProductSettings(){
        
       deviceSettings = new HashMap<ProductType, Map<Integer,DeviceSettingDefinitionBean>>();
       deviceSettingLists = new HashMap<ProductType, List<DeviceSettingDefinitionBean>>();
       ignoredSettings = new HashMap<ProductType, Map<Integer,DeviceSettingDefinitionBean>>();
       
       for (ProductType productType : ProductType.getSet()){
            
            deviceSettings.put(productType,new HashMap<Integer,DeviceSettingDefinitionBean>()); 
            deviceSettingLists.put(productType, new ArrayList<DeviceSettingDefinitionBean>());
            ignoredSettings.put(productType,new HashMap<Integer,DeviceSettingDefinitionBean>()); 
       }
    }
    private void distributeSettings(){
        
        for(DeviceSettingDefinitionBean dsdBean:DeviceSettingDefinitions.getDeviceSettingDefinitions()){
            
            distributeSetting(dsdBean);
        }
    }
    private void distributeSetting(DeviceSettingDefinitionBean dsdBean){

    	for (ProductType productType :ProductType.getSet()){
            
            if (productType.maskBitSet(dsdBean.getProductMask())){
                
                if(dsdBean.isInclude()){
                    
                    deviceSettings.get(productType).put(dsdBean.getSettingID(),dsdBean);
                    deviceSettingLists.get(productType).add(dsdBean);
                }
                else{
                    
                    ignoredSettings.get(productType).put(dsdBean.getSettingID(),dsdBean);
                }
            }
        }
    }
    private void sortSettings(){
    	for( List<DeviceSettingDefinitionBean> deviceSettingList : deviceSettingLists.values()){
    		
    		Collections.sort(deviceSettingList);
    	}
    }
    public List<DeviceSettingDefinitionBean> deriveReducedSettings(List<Integer> settingIDsWithMoreThanOneValue,ProductType productType){
        
        List<DeviceSettingDefinitionBean> reducedSettings = new ArrayList<DeviceSettingDefinitionBean>();
        
        if (settingIDsWithMoreThanOneValue.isEmpty()) return reducedSettings;
        
        Collections.sort(settingIDsWithMoreThanOneValue);
        for (DeviceSettingDefinitionBean dsdBean:getDeviceSettings(productType)){
            
            if(settingIDsWithMoreThanOneValue.contains(dsdBean.getSettingID())){
            
                reducedSettings.add(dsdBean);
            }
        }
        return reducedSettings;
    }
    public List<DeviceSettingDefinitionBean> getDeviceSettings(ProductType key) {
    	
        return deviceSettingLists.get(key);
    }

    public Map<ProductType, List<DeviceSettingDefinitionBean>> getDeviceSettingLists() {
		return deviceSettingLists;
	}
	public DeviceSettingDefinitionBean getDeviceSetting(ProductType key, Integer settingID) {
    	
        return deviceSettings.get(key).get(settingID);
    }
    public List<Integer> getKeys(ProductType key){
    	
    	return new ArrayList<Integer>(deviceSettings.get(key).keySet());
    }
    public List<DeviceSettingDefinitionBean> getIgnoredSettings(ProductType key) {
    	List<DeviceSettingDefinitionBean> iss = new ArrayList<DeviceSettingDefinitionBean>(ignoredSettings.get(key).values());
    	Collections.sort(iss);
    	
    	return iss;
    }
    public boolean validValue(Integer settingID, String value){
        
        return DeviceSettingDefinitions.getDeviceSettingDefinition(settingID).validate(value);
    }
//    public EditableMap<Integer, String> getCopyValuesMap(ProductType selectedProductType, Map<Integer,String> mapToCopy){
//    	
//    	Map<Integer, String> copyMap = new HashMap<Integer,String>();
//    	
//    	for(DeviceSettingDefinitionBean dsdb: deviceSettingLists.get(selectedProductType)){
//    		
//    		if (mapToCopy.get(dsdb.getSettingID()) == null){
//    			
//    			copyMap.put(dsdb.getSettingID(),"");
//    		}
//    		else {
//    			copyMap.put(dsdb.getSettingID() ,mapToCopy.get(dsdb.getSettingID()));
//    		}
//    	}
//    	return new EditableMap<Integer,String>(copyMap);
//    }
}
