package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.TiwiproSettingManager;
import com.inthinc.pro.backing.WaySmartSettingManager;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleSettingsFactory {
    
    private ConfiguratorDAO configuratorDAO;

    public Map<Integer, VehicleSettingManager> retrieveVehicleSettings(Integer groupID){
        
        Map<Integer, VehicleSettingManager>   vehicleSettingManagers = new HashMap<Integer, VehicleSettingManager>();
        List<VehicleSetting> vehiclesSettings = configuratorDAO.getVehicleSettingsByGroupIDDeep(groupID);
        for (VehicleSetting vehicleSetting : vehiclesSettings){

            vehicleSettingManagers.put(vehicleSetting.getVehicleID(),getSettingManager(vehicleSetting.getProductType(),vehicleSetting.getVehicleID(),vehicleSetting));
        }
        return vehicleSettingManagers;
    }
    public VehicleSettingManager getUnknownSettingManager(Integer vehicleID){ 
        
        return new UnknownSettingManager(configuratorDAO,new VehicleSetting());
    }
    public VehicleSettingManager getSettingManager(ProductType productType, Integer vehicleID, VehicleSetting vehicleSetting){
        
        if (productType == null) return new UnknownSettingManager(configuratorDAO,vehicleSetting==null?new VehicleSetting():vehicleSetting);
        switch (productType){
            case WS820:
               return  new WaySmartSettingManager(configuratorDAO,productType,vehicleSetting==null?new VehicleSetting():vehicleSetting);
            case TIWIPRO_R71:
            case TIWIPRO_R74:
                return new TiwiproSettingManager(configuratorDAO, productType,vehicleSetting==null?new VehicleSetting():vehicleSetting);
            default:
                return new UnknownSettingManager(configuratorDAO,vehicleSetting==null?new VehicleSetting():vehicleSetting);
       }
    }
    public ConfiguratorDAO getConfiguratorDAO() {
        return configuratorDAO;
    }
    
    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }
    
}
