package com.inthinc.pro.configurator.model;

import java.util.Iterator;

import com.inthinc.pro.configurator.ui.ConfigurationSelectionBean;
import com.inthinc.pro.model.configurator.VehicleSetting;

public class VehicleSettingIterator implements Iterator<VehicleSetting> {
    
    private ConfigurationSelectionBean configurationSelectionBean;
    private VehicleSetting nextVehicleSetting;
    
    public VehicleSettingIterator(ConfigurationSelectionBean configurationSelectionBean) {
        super();
        this.configurationSelectionBean = configurationSelectionBean;
        
        configurationSelectionBean.setupVehicleIterator();
    }

    @Override
    public boolean hasNext() {
        Boolean hasNextVehicle =  configurationSelectionBean.hasNextVehicle();       
        return hasNextVehicle;
    }

    @Override
    public VehicleSetting next() {
        
        nextVehicleSetting = configurationSelectionBean.getNextVehicle();

        return nextVehicleSetting;
    }

    @Override
    public void remove() {
        
        //Isn't relevant here
    }

}
