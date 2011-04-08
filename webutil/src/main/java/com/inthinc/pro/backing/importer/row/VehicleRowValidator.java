package com.inthinc.pro.backing.importer.row;

import java.util.List;

import com.inthinc.pro.backing.importer.VehicleTemplateFormat;

public class VehicleRowValidator extends RowValidator {

    private VehicleTemplateFormat vehicleTemplateFormat;
    
    public VehicleTemplateFormat getVehicleTemplateFormat() {
        return vehicleTemplateFormat;
    }

    public void setVehicleTemplateFormat(VehicleTemplateFormat vehicleTemplateFormat) {
        this.vehicleTemplateFormat = vehicleTemplateFormat;
    }

    @Override
    public List<String> validateRow(List<String> rowData, boolean includeWarnings) {
        
        List<String> errorList = super.checkFormat(rowData, vehicleTemplateFormat);
        
        if (errorList.isEmpty()) {
            // more checks
            return null;
        }
        return errorList;
    }
}
