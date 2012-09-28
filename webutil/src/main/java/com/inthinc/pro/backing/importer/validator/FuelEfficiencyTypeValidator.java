package com.inthinc.pro.backing.importer.validator;

import com.inthinc.pro.model.FuelEfficiencyType;

public class FuelEfficiencyTypeValidator extends Validator {

    @Override
    public boolean isValid(String value) {
        if (value == null)
            return false;
        
        try {
            FuelEfficiencyType type = FuelEfficiencyType.valueOf(value);
            return type != null;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    @Override
    public String getInvalidMessage(String value) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("The Fuel Efficiency Type: " +  value + " is invalid.  Valid types are: ");
        for (FuelEfficiencyType v : FuelEfficiencyType.values()) {
            buffer.append(v.name() + "  ");
        }
        return buffer.toString();
    }

}
