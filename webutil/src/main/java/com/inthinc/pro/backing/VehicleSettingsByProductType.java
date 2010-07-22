package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.configurator.VehicleSetting;
import com.inthinc.pro.model.configurator.DeviceSettingDefinition.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting.ProductVersion;

public class VehicleSettingsByProductType {

    private Map<ProductType, List<VehicleSetting>> vehicleSettings;
    
    public void initializeSettings(List<VehicleSetting> allVehicleSettings){
        
        setup();
        distributeSettings(allVehicleSettings);
    }
    private void setup(){
        
        vehicleSettings = new HashMap<ProductType,List<VehicleSetting>>();
        
        for (ProductVersion productVersion : ProductVersion.getSet()){
            
            vehicleSettings.put(getProductType(productVersion),new ArrayList<VehicleSetting>()); 
        }
    }
    private void distributeSettings(List<VehicleSetting> allVehicleSettings){

        for(VehicleSetting vehicleSetting:allVehicleSettings){
            
            vehicleSettings.get(getProductType(vehicleSetting.getProductVer())).add(vehicleSetting);
        }
    }
    private ProductType getProductType(ProductVersion productVersion){
        
        return ProductType.valueOfByVersion(productVersion.getCode());
    }
    
    public List<VehicleSetting> getVehicleSettings(ProductType key) {
        
        return vehicleSettings.get(key);
    }
}
