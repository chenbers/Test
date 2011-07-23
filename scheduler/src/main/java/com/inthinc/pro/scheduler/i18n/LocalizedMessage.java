package com.inthinc.pro.scheduler.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizedMessage
{
    private static final String BUNDLE_NAME = "messages";
    
    public static String getString(String key,Locale locale)
    {   
        return getStringWithValues(key,locale);
    }
    
    public static String getStringWithValues(String key,Locale locale,String... parameters)
    {
        Locale currentLocale = null;
        if(locale != null)
            currentLocale = locale;
        else
            currentLocale = Locale.getDefault();
            
        ResourceBundle myResources = ResourceBundle.getBundle(BUNDLE_NAME, currentLocale);
        if (myResources.containsKey(key))
            return myResources.getString(key);
        return key;
    }
    
    
}
