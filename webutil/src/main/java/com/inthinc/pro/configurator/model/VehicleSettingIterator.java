package com.inthinc.pro.configurator.model;

import java.util.Iterator;

import com.inthinc.pro.configurator.ui.VehicleFilterBean;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleSettingIterator implements Iterator<VehicleSetting> {
    
    private VehicleFilterBean vehicleFilterBean;
    private VehicleSetting nextVehicleSetting;
    
    public VehicleSettingIterator(VehicleFilterBean vehicleFilterBean) {
        super();
        this.vehicleFilterBean = vehicleFilterBean;
        
        vehicleFilterBean.setupVehicleIterator();
    }

    @Override
    public boolean hasNext() {
        Boolean hasNextVehicle =  vehicleFilterBean.hasNextVehicle();       
        return hasNextVehicle;
    }

    @Override
    public VehicleSetting next() {
        
        nextVehicleSetting = vehicleFilterBean.getNextVehicle();

        return nextVehicleSetting;
    }

    @Override
    public void remove() {
        
        //Isn't relevant here
    }

}
