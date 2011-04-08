package com.inthinc.pro.backing.importer.validator;

import com.inthinc.pro.model.MeasurementType;

public class MeasurementTypeValidator extends Validator {

    @Override
    public boolean isValid(String value) {
        if (value == null)
            return false;
        
        try {
            MeasurementType type = MeasurementType.valueOf(value);
            return type != null;
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    @Override
    public String getInvalidMessage(String value) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("The Measurement Type: " + value + " is invalid.  Valid types are: ");
        for (MeasurementType v : MeasurementType.values()) {
            buffer.append(v.name() + "  ");
        }
        return buffer.toString();
    }

}
