package com.inthinc.pro.backing.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.TiwiproSettingManager;
import com.inthinc.pro.backing.WaySmartSettingManager;
import com.inthinc.pro.dao.ConfiguratorDAO;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.app.SensitivitySliders;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleSettingsFactory {
    
    private ConfiguratorDAO configuratorDAO;
    private DeviceDAO deviceDAO;
    private SensitivitySliders sensitivitySliders;
    
    public VehicleSetting getVehicleSetting(Integer vehicleID){
        return configuratorDAO.getVehicleSettings(vehicleID);
    }

    // TODO: CJ ADDED THIS DURING PAGINATION REFACTOR - REVIEW IT WITH JACQUIE
    public Map<Integer, VehicleSettingManager> retrieveVehicleSettings(Map<Integer, VehicleSettingManager> vehicleSettingManagers, List<Vehicle> vehicles){
        
//        Map<Integer, VehicleSettingManager>   vehicleSettingManagers = new HashMap<Integer, VehicleSettingManager>();
//        for (Vehicle vehicle : vehicles) {
//            Integer vehicleID = vehicle.getVehicleID();
//            VehicleSetting vehicleSetting = getVehicleSetting(vehicleID);
//            if (vehicleSetting == null) {
//                System.out.println("vehicleID: " + vehicleID + " vehicleSetting is null!!!");
//                continue;
//            }
//            vehicleSettingManagers.put(vehicleID,getSettingManagerForExistingSetting(vehicleSetting.getProductType(),vehicleSetting));
//            setToSettingManagerIfSettingsDontExist(vehicleSettingManagers, vehicle);
//        }
        for (Vehicle vehicle :vehicles){
            
            setToSettingManagerIfSettingsDontExist(vehicleSettingManagers, vehicle);
        }
        return vehicleSettingManagers;
    }

    
    public Map<Integer, VehicleSettingManager> retrieveVehicleSettings(Integer groupID, List<Vehicle> vehicles){
        
        Map<Integer, VehicleSettingManager>   vehicleSettingManagers = new HashMap<Integer, VehicleSettingManager>();
        List<VehicleSetting> vehiclesSettings = configuratorDAO.getVehicleSettingsByGroupIDDeep(groupID);
        for (VehicleSetting vehicleSetting : vehiclesSettings){

            vehicleSettingManagers.put(vehicleSetting.getVehicleID(),getSettingManagerForExistingSetting(vehicleSetting.getProductType(),vehicleSetting));
        }
        if (vehicles != null) {
            for (Vehicle vehicle :vehicles){
                setToSettingManagerIfSettingsDontExist(vehicleSettingManagers, vehicle);
            }
        }
        return vehicleSettingManagers;
    }
    private void setToSettingManagerIfSettingsDontExist(Map<Integer, VehicleSettingManager> vehicleSettingManagers, Vehicle vehicle){
            
        if(vehicleSettingManagers.get(vehicle.getVehicleID()) == null){
        	if (vehicle.getDeviceID() == null) {
        		vehicleSettingManagers.put(vehicle.getVehicleID(), new UnknownSettingManager());
        		return;
        	}
        	Device device = deviceDAO.findByID(vehicle.getDeviceID());
        	ProductType productType = device.getProductVersion();
            switch (productType){
            case WAYSMART:
        		vehicleSettingManagers.put(vehicle.getVehicleID(),new WaySmartSettingManager(configuratorDAO,sensitivitySliders,productType,vehicle.getVehicleID(),device.getDeviceID()));
            case TIWIPRO:
        		vehicleSettingManagers.put(vehicle.getVehicleID(),new TiwiproSettingManager(configuratorDAO, sensitivitySliders,productType,vehicle.getVehicleID(),device.getDeviceID()));
            default:
        		vehicleSettingManagers.put(vehicle.getVehicleID(),new UnknownSettingManager());
            }

        }
    }
    public void setDeviceDAO(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}
    public void resetToSettingManager(Map<Integer, VehicleSettingManager> vehicleSettingManagers, Vehicle vehicle){
        
    	if (vehicle.getDeviceID() == null) {
    		vehicleSettingManagers.put(vehicle.getVehicleID(), new UnknownSettingManager());
    		return;
    	}
        VehicleSetting vehicleSetting = configuratorDAO.getVehicleSettings(vehicle.getVehicleID());
        if (vehicleSetting != null){
            vehicleSettingManagers.put(vehicleSetting.getVehicleID(),getSettingManagerForExistingSetting(vehicleSetting.getProductType(),vehicleSetting));
        }
        else{
        	setToSettingManagerIfSettingsDontExist(vehicleSettingManagers, vehicle);
        }
    }
	public VehicleSettingManager getSettingManagerForExistingSetting(ProductType productType, VehicleSetting vehicleSetting){
        
        if (productType == null) return new UnknownSettingManager();
        switch (productType){
            case WAYSMART:
               return  new WaySmartSettingManager(configuratorDAO,sensitivitySliders,productType,vehicleSetting);
            case TIWIPRO:
                return new TiwiproSettingManager(configuratorDAO, sensitivitySliders,productType,vehicleSetting);
            default:
                return new UnknownSettingManager();
       }
    }
    public VehicleSettingManager getUnknownSettingManager(Integer vehicleID){ 
        
        return new UnknownSettingManager();
    }

    public VehicleSettingManager getSettingManagerForBatchEditing(ProductType productType){
        
        if (productType == null) return new UnknownSettingManager();
        switch (productType){
            case WAYSMART:
               return  new WaySmartSettingManager(configuratorDAO,sensitivitySliders,productType,null,null);
            case TIWIPRO:
                return new TiwiproSettingManager(configuratorDAO,sensitivitySliders, productType,null,null);
            default:
                return new UnknownSettingManager();
       }
    }

    public VehicleSettingManager getSettingManagerForNewSettingWithKnownDevice(ProductType productType, Integer vehicleID, Integer deviceID){
        
        if (productType == null) return new UnknownSettingManager();
        switch (productType){
            case WAYSMART:
               return  new WaySmartSettingManager(configuratorDAO,sensitivitySliders,productType,vehicleID,deviceID);
            case TIWIPRO:
                return new TiwiproSettingManager(configuratorDAO, sensitivitySliders,productType,vehicleID,deviceID);
            default:
                return new UnknownSettingManager();
       }
    }
    
    public void setConfiguratorDAO(ConfiguratorDAO configuratorDAO) {
        this.configuratorDAO = configuratorDAO;
    }
    public void setSensitivitySliders(SensitivitySliders sensitivitySliders) {
        this.sensitivitySliders = sensitivitySliders;
    }
}
