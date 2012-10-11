package com.inthinc.pro.backing.importer.validator;

import java.util.TimeZone;

public class TimeZoneValidator extends Validator {

    @Override
    public boolean isValid(String value) {
        
        
        for (String timeZoneID : TimeZone.getAvailableIDs()) {
            if (timeZoneID.equals(value))
                return true;
        }
        
        return false;

    }
    
    @Override
    public String getInvalidMessage(String value) {
        return "The timezone ID " + value + " is invalid.";
    }

}
