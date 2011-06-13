package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.TiwiproSettingManager;
import com.inthinc.pro.backing.WaySmartSettingManager;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.app.SensitivitySliders;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleSettingsFactory {
    
    private ConfiguratorDAO configuratorDAO;
    private SensitivitySliders sensitivitySliders;
    
    public VehicleSetting getVehicleSetting(Integer vehicleID){
        return configuratorDAO.getVehicleSettings(vehicleID);
    }
    public Map<Integer, VehicleSettingManager> retrieveVehicleSettings(Integer groupID){
        
        Map<Integer, VehicleSettingManager>   vehicleSettingManagers = new HashMap<Integer, VehicleSettingManager>();
        List<VehicleSetting> vehiclesSettings = configuratorDAO.getVehicleSettingsByGroupIDDeep(groupID);
        for (VehicleSetting vehicleSetting : vehiclesSettings){

            vehicleSettingManagers.put(vehicleSetting.getVehicleID(),getSettingManager(vehicleSetting.getProductType(),vehicleSetting));
        }
        return vehicleSettingManagers;
    }
    public VehicleSettingManager getUnknownSettingManager(Integer vehicleID){ 
        
        return new UnknownSettingManager(configuratorDAO,new VehicleSetting());
    }

    public VehicleSettingManager getSettingManager(ProductType productType, Integer vehicleID){
        
        if (productType == null) return new UnknownSettingManager(configuratorDAO,new VehicleSetting());
        switch (productType){
            case WAYSMART:
               return  new WaySmartSettingManager(configuratorDAO,sensitivitySliders,productType,new VehicleSetting());
            case TIWIPRO_R71:
            case TIWIPRO_R74:
                return new TiwiproSettingManager(configuratorDAO,sensitivitySliders, productType,new VehicleSetting());
            default:
                return new UnknownSettingManager(configuratorDAO,new VehicleSetting());
       }
    }

    public VehicleSettingManager getSettingManager(ProductType productType, VehicleSetting vehicleSetting){
        
        if (productType == null) return new UnknownSettingManager(configuratorDAO,vehicleSetting);
        switch (productType){
            case WAYSMART:
               return  new WaySmartSettingManager(configuratorDAO,sensitivitySliders,productType,vehicleSetting);
            case TIWIPRO_R71:
            case TIWIPRO_R74:
                return new TiwiproSettingManager(configuratorDAO, sensitivitySliders,productType,vehicleSetting);
            default:
                return new UnknownSettingManager(configuratorDAO,vehicleSetting);
       }
    }
    public VehicleSettingManager getSettingManager(ProductType productType, Integer vehicleID, Integer deviceID){
        
        if (productType == null) return new UnknownSettingManager(configuratorDAO);
        switch (productType){
            case WAYSMART:
               return  new WaySmartSettingManager(configuratorDAO,sensitivitySliders,productType,vehicleID,deviceID);
            case TIWIPRO_R71:
            case TIWIPRO_R74:
                return new TiwiproSettingManager(configuratorDAO, sensitivitySliders,productType,vehicleID,deviceID);
            default:
                return new UnknownSettingManager(configuratorDAO);
       }
    }
    
    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }
    public void setsensitivitySliders(SensitivitySliders sensitivitySliders) {
        this.sensitivitySliders = sensitivitySliders;
    }
}
