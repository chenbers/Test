package com.inthinc.pro.table.columns;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import com.inthinc.pro.backing.model.supportData.AdminCacheBean;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Vehicle;

public class VehicleChoiceTableColumns {
    
    private enum VehicleColumn {
        
        NAME {String getItem(AdminCacheBean adminCacheBean, Integer vehicleID){return ((Vehicle)adminCacheBean.getAsset("vehicles", vehicleID)).getName();}},
        DRIVERID {String getItem(AdminCacheBean adminCacheBean, Integer vehicleID){
            Driver driver = (Driver)adminCacheBean.getAsset("drivers",((Vehicle)adminCacheBean.getAsset("vehicles", vehicleID)).getDriverID());
            if (driver == null) return null;
            return driver.getPerson().getFullName();}},
        GROUPID {String getItem(AdminCacheBean adminCacheBean, Integer vehicleID){
            return ((Group)adminCacheBean.getAsset("groups",((Vehicle)adminCacheBean.getAsset("vehicles", vehicleID)).getGroupID())).getName();}},
        YEAR {String getItem(AdminCacheBean adminCacheBean, Integer vehicleID){
            Integer year = ((Vehicle)adminCacheBean.getAsset("vehicles", vehicleID)).getYear();
            return  year == null?null:year.toString();}},
        MAKE {String getItem(AdminCacheBean adminCacheBean, Integer vehicleID){return  ((Vehicle)adminCacheBean.getAsset("vehicles", vehicleID)).getMake();}},
        MODEL {String getItem(AdminCacheBean adminCacheBean, Integer vehicleID){return  ((Vehicle)adminCacheBean.getAsset("vehicles", vehicleID)).getModel();}},
        VIN {String getItem(AdminCacheBean adminCacheBean, Integer vehicleID){return  ((Vehicle)adminCacheBean.getAsset("vehicles", vehicleID)).getVIN();}},
        DEVICEID {String getItem(AdminCacheBean adminCacheBean, Integer vehicleID){
            Device device = (Device)adminCacheBean.getAsset("devices",((Vehicle)adminCacheBean.getAsset("vehicles", vehicleID)).getDeviceID());
            if (device == null) return null;
            return device.getName();}};

        abstract String getItem(AdminCacheBean adminCacheBean, Integer vehicleID);
    }
    @SuppressWarnings("unchecked")
    public List<Vehicle> getFilteredItems(AdminCacheBean adminCacheBean,String keyword, boolean matchAllWords){
        
        if ((keyword == null) || keyword.isEmpty()) return (List<Vehicle>)adminCacheBean.getAssets("vehicles");
        
        final String[] filterWords = keyword.toLowerCase().split("\\s+");

        List<Vehicle> matchingVehicles = new ArrayList<Vehicle>();
        for(Vehicle vehicle : (List<Vehicle>)adminCacheBean.getAssets("vehicles")){
            if(matchAllWords){
               if(matchOnAllWords(adminCacheBean,vehicle, filterWords)){
                   matchingVehicles.add(vehicle);
               }
            }
            else{
                if(matchOnOneWord(adminCacheBean,vehicle, filterWords)){
                    matchingVehicles.add(vehicle);
                }
            }
        }
        return matchingVehicles;
    }
    private boolean matchOnOneWord(AdminCacheBean adminCacheBean, Vehicle vehicle, String [] filterWords){
        
        for(VehicleColumn vehicleColumn: EnumSet.allOf(VehicleColumn.class)){
            String vehicleItem = vehicleColumn.getItem(adminCacheBean, vehicle.getVehicleID());
            for (String filterWord : filterWords){

                if(containsKeyword(vehicleItem,filterWord)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean matchOnAllWords(AdminCacheBean adminCacheBean, Vehicle vehicle, String [] filterWords){
        
        for(VehicleColumn vehicleColumn: EnumSet.allOf(VehicleColumn.class)){
            boolean containsAll = true;
            String vehicleItem = vehicleColumn.getItem(adminCacheBean, vehicle.getVehicleID());
            for (String filterWord : filterWords){
                
                if(!containsKeyword(vehicleItem,filterWord)){
                    containsAll = false;
                    break;
                }
            }
            if (containsAll) return true;
        }
        return false;
    }
    private boolean containsKeyword(String field, String keyword){
        if (field == null) return false;
        return field.toLowerCase().contains(keyword.toLowerCase());
    }
}
