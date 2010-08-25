package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleSettingsByProductType {

    private Map<ProductType, List<VehicleSetting>> vehicleSettingsByProductType;
    private Map<ProductType, List<String>> vehicleSelectItems;
    
    public void initializeSettings(List<VehicleSetting> allVehicleSettings){
        
        setup();
        distributeSettings(allVehicleSettings);
    }
    private void setup(){
        
        vehicleSettingsByProductType = new HashMap<ProductType,List<VehicleSetting>>();
        vehicleSelectItems = new HashMap<ProductType,List<String>>();
        
        for (ProductType productType : ProductType.getSet()){
            
            vehicleSettingsByProductType.put(productType,new ArrayList<VehicleSetting>()); 
            vehicleSelectItems.put(productType,new ArrayList<String>()); 
        }
    }
    private void distributeSettings(List<VehicleSetting> allVehicleSettings){

        for(VehicleSetting vehicleSetting:allVehicleSettings){
            
            vehicleSettingsByProductType.get(vehicleSetting.getProductType()).add(vehicleSetting);
            vehicleSelectItems.get(vehicleSetting.getProductType()).add(""+vehicleSetting.getVehicleID());
        }
    }
    public List<VehicleSetting> getVehicleSettingsByProductType(ProductType key) {
        
        return vehicleSettingsByProductType.get(key);
    }
    
    public List<String> getVehicleSelectItems(ProductType productType){
    	
    	if (vehicleSelectItems == null) return Collections.emptyList();
    	return vehicleSelectItems.get(productType);
    }
}
