package com.inthinc.pro.automation.rest;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.automation.models.Device;
import com.inthinc.pro.automation.models.DeviceStatus;
import com.inthinc.pro.automation.models.ProductType;

public class AutomationDevices {
    

    public Device getTiwi(){
        List<Device> devices = getDevices();
        for (Device device : devices){
            if (device.getProductVersion().equals(ProductType.TIWIPRO)){
                return device;
            }
        }
        return null;
    }
    
    public Device getWaysmart(){
        List<Device> devices = getDevices();
        for (Device device : devices){
            if (device.getProductVersion().equals(ProductType.WAYSMART)){
                return device;
            }
        }
        return null;
    }
    
    public List<Device> getDevices(){
        List<Device> devices=null;
        List<Device> active = new ArrayList<Device>();
        for (Device device : devices){
            if (device.getStatus().equals(DeviceStatus.ACTIVE)){
                active.add(device);
            }
        }
        return active;
    }
    

}
