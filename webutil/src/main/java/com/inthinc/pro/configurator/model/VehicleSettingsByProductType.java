package com.inthinc.pro.configurator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting.ProductVersion;

public class VehicleSettingsByProductType {

    private Map<ProductType, List<VehicleSetting>> vehicleSettingsByProductType;
    
    public void initializeSettings(List<VehicleSetting> allVehicleSettings){
        
        setup();
        distributeSettings(allVehicleSettings);
    }
    private void setup(){
        
        vehicleSettingsByProductType = new HashMap<ProductType,List<VehicleSetting>>();
        
        for (ProductVersion productVersion : ProductVersion.getSet()){
            
            vehicleSettingsByProductType.put(getProductType(productVersion),new ArrayList<VehicleSetting>()); 
        }
    }
    private void distributeSettings(List<VehicleSetting> allVehicleSettings){

        for(VehicleSetting vehicleSetting:allVehicleSettings){
            
            vehicleSettingsByProductType.get(getProductType(vehicleSetting.getProductVer())).add(vehicleSetting);
        }
    }
    private ProductType getProductType(ProductVersion productVersion){
        
        return ProductType.valueOfByVersion(productVersion.getCode());
    }
    
    public List<VehicleSetting> getVehicleSettingsByProductType(ProductType key) {
        
        return vehicleSettingsByProductType.get(key);
    }
}
