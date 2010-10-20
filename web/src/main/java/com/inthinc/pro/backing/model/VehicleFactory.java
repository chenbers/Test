package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.TiwiproSettingManager;
import com.inthinc.pro.backing.WaySmartSettingManager;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleFactory {
    
    private ConfiguratorDAO configuratorDAO;

    public Map<Integer, VehicleSettingManager> initSettings(Integer groupID){
        
        Map<Integer, VehicleSettingManager>   vehicleSettingManagers = new HashMap<Integer, VehicleSettingManager>();
        List<VehicleSetting> vehiclesSettings = configuratorDAO.getVehicleSettingsByGroupIDDeep(groupID);
        for (VehicleSetting vehicleSetting : vehiclesSettings){

            switch (vehicleSetting.getProductType()){
                case WS820:
                    vehicleSettingManagers.put(vehicleSetting.getVehicleID(),  new WaySmartSettingManager(configuratorDAO,vehicleSetting));
                    break;
                case TIWIPRO_R71:
                case TIWIPRO_R74:
                    vehicleSettingManagers.put(vehicleSetting.getVehicleID(),  new TiwiproSettingManager(configuratorDAO,vehicleSetting));
                    break;
                default:
                    //Temporarily turn these into waysmarts
//                    vehicleSetting.setProductType(ProductType.WS820);
//                    vehicleSetting.getDesired().put(1028, "50");//speed limit
//                    vehicleSetting.getDesired().put(1029, "20");//speed buffer
//                    vehicleSetting.getDesired().put(1030, "35");//severe speed
//                    vehicleSetting.getDesired().put(1224, "40");//rms level
//                    vehicleSetting.getDesired().put(1225, "40");//hardvert_dmm_peaktopeak
//                    vehicleSetting.getDesired().put(1165, "40");//severe_hardvert_level
//                    vehicleSetting.getDesired().put(1226, "225");//y_level
//                    vehicleSetting.getDesired().put(1228, "10");//dvy
//                    vehicleSetting.getDesired().put(1229, "225");//x_accel
//                    vehicleSetting.getDesired().put(1231, "10");//dvx
//                    vehicleSetting.getDesired().put(1232, "300");//hard_accel_level
//                    vehicleSetting.getDesired().put(1234, "4");//hard_accel_deltav
//
//                    vehicleSettingManagers.put(vehicleSetting.getVehicleID(),  new WaySmartSettingManager(configuratorDAO,vehicleSetting));
                    vehicleSetting.setProductType(ProductType.UNKNOWN);
                    vehicleSettingManagers.put(vehicleSetting.getVehicleID(),  new UnknownSettingManager(configuratorDAO,vehicleSetting));
                    
                    //Temporarily turn these into tiwipros
//                    vehicleSetting.setProductType(ProductType.TIWIPRO_R74);
//                    vehicleSettingManagers.put(vehicleSetting.getVehicleID(),  new TiwiproSettingManager(configuratorDAO,vehicleSetting));
                    
            }
        }
        return vehicleSettingManagers;
    }
    public VehicleSettingManager getUnknownSettingManager(Integer vehicleID){ 
        
        return new UnknownSettingManager(configuratorDAO,new VehicleSetting());
    }
    public ConfiguratorDAO getConfiguratorDAO() {
        return configuratorDAO;
    }
    
    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }
}
