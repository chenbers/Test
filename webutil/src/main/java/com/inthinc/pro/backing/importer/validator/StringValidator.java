package com.inthinc.pro.backing.importer.validator;

public class StringValidator extends Validator {
    
    private int maxLength;
    
    public StringValidator(int maxLength)
    {
        this.maxLength = maxLength;
    }

    @Override
    public boolean isValid(String value) {
        return value.length() <= maxLength;
    }
    @Override
    public String getInvalidMessage() {
        return "The maximum length is " + maxLength + ".";
    }
}
