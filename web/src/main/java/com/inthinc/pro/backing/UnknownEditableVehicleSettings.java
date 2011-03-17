package com.inthinc.pro.backing;

import com.inthinc.pro.model.configurator.ProductType;

public class UnknownEditableVehicleSettings extends EditableVehicleSettings {

    public UnknownEditableVehicleSettings() {
        super(null, ProductType.UNKNOWN,"");
    }

    public UnknownEditableVehicleSettings(Integer vehicleID) {
        super(vehicleID, ProductType.UNKNOWN,"");
    }
}
