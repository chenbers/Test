package com.inthinc.pro.backing.importer.validator;

import java.util.Locale;

public class CountryValidator extends Validator {

    @Override
    public boolean isValid(String value) {
        Locale[] availLocale = Locale.getAvailableLocales();
        
        for (Locale locale : availLocale) {
            if (locale.getCountry().equals(value))
                return true;
        }
        
        return false;
    }

    @Override
    public String getInvalidMessage(String value) {
        return "The country code " + value + " is invalid. ";
    }
}
