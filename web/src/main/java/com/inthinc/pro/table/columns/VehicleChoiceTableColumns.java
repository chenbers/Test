package com.inthinc.pro.table.columns;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.inthinc.pro.backing.model.supportData.AdminCacheBean;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Vehicle;

public class VehicleChoiceTableColumns {
    
    private enum VehicleColumn {
        
        NAME {String getItem(AdminCacheBean adminCacheBean, Vehicle vehicle){return vehicle.getName();}},
        DRIVERID {String getItem(AdminCacheBean adminCacheBean, Vehicle vehicle){
            Driver driver = (Driver)adminCacheBean.getAsset("drivers",vehicle.getDriverID());
            if (driver == null) return null;
            return driver.getPerson().getFullName();}},
        GROUPID {String getItem(AdminCacheBean adminCacheBean, Vehicle vehicle){
            return ((Group)adminCacheBean.getAsset("groups",vehicle.getGroupID())).getName();}},
        YEAR {String getItem(AdminCacheBean adminCacheBean, Vehicle vehicle){
            Integer year = vehicle.getYear();
            return  year == null?null:year.toString();}},
        MAKE {String getItem(AdminCacheBean adminCacheBean, Vehicle vehicle){return  vehicle.getMake();}},
        MODEL {String getItem(AdminCacheBean adminCacheBean, Vehicle vehicle){return  vehicle.getModel();}},
        VIN {String getItem(AdminCacheBean adminCacheBean, Vehicle vehicle){return vehicle.getVIN();}},
        DEVICEID {String getItem(AdminCacheBean adminCacheBean, Vehicle vehicle){
            Device device = (Device)adminCacheBean.getAsset("devices",vehicle.getDeviceID());
            if (device == null) return null;
            return device.getName();}};

        abstract String getItem(AdminCacheBean adminCacheBean, Vehicle vehicle);
    }
    @SuppressWarnings("unchecked")
    public List<Vehicle> getFilteredItems(AdminCacheBean adminCacheBean,String keyword, boolean matchAllWords){


        if ((keyword == null) || keyword.isEmpty()) return (List<Vehicle>)adminCacheBean.getAssets("vehicles");
        
        MatchStrategy matchStrategy = matchAllWords? new MatchOnAllWords(): new MatchOnOneWord();
        final String[] filterWords = keyword.toLowerCase().split("\\s+");

        List<Vehicle> matchingVehicles = new ArrayList<Vehicle>();
        for(Vehicle vehicle : (List<Vehicle>)adminCacheBean.getAssets("vehicles")){
            if(matchColumns(adminCacheBean,vehicle, filterWords,matchStrategy)){
                matchingVehicles.add(vehicle);
            }
        }
        return matchingVehicles;
    }
    private boolean matchColumns(AdminCacheBean adminCacheBean, Vehicle vehicle, String [] filterWords,MatchStrategy matchStrategy){
        
        for(VehicleColumn vehicleColumn: EnumSet.allOf(VehicleColumn.class)){
            String vehicleItem = vehicleColumn.getItem(adminCacheBean, vehicle);
            
            if (matchStrategy.match(vehicleItem,vehicle, filterWords)) return true;
        }
        return false;
    }
    private boolean containsKeyword(String field, String keyword){
        if (field == null) return false;
        return field.toLowerCase().contains(keyword.toLowerCase());
    }
    
    private interface MatchStrategy{
        
        public boolean match(String vehicleItem, Vehicle vehicle, String [] filterWords);
    }
    private class MatchOnOneWord implements MatchStrategy{
        
        public boolean match(String vehicleItem, Vehicle vehicle, String [] filterWords){
            for (String filterWord : filterWords){
                if(containsKeyword(vehicleItem,filterWord)){
                    return true;
                }
            }
            return false;
        }
    }
    private class MatchOnAllWords implements MatchStrategy{
        
        public boolean match(String vehicleItem, Vehicle vehicle, String [] filterWords){
            for (String filterWord : filterWords){
                if(!containsKeyword(vehicleItem,filterWord)){
                    return false;
                }
            }
            return true;
        }
    }
}
