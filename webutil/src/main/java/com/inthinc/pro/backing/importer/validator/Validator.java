package com.inthinc.pro.backing.importer.validator;

public abstract class Validator {
    
    public abstract boolean isValid(String value);
    
    public abstract String getInvalidMessage(String value); 
}
