package com.inthinc.pro.backing;

import com.inthinc.pro.model.configurator.ProductType;

public class UnknownEditableVehicleSettings extends EditableVehicleSettings {

    public UnknownEditableVehicleSettings() {
        super();
    }

    public UnknownEditableVehicleSettings(Integer vehicleID) {
        super(vehicleID, ProductType.UNKNOWN);
    }

    public UnknownEditableVehicleSettings getSelf(){
        return this;
    }
                                                   
}
