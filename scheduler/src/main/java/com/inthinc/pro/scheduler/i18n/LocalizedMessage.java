package com.inthinc.pro.scheduler.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizedMessage
{
    private static final String BUNDLE_NAME = "messages";
    
    public static String getString(String key)
    {   
        return getStringWithValues(key);
    }
    
    public static String getStringWithValues(String key,String... parameters)
    {
        ResourceBundle myResources = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());
        
        String message = null;
        if (myResources.containsKey(key))
            message =  myResources.getString(key);
        else 
            message = key;
        
        if(parameters != null)
            message = MessageFormat.format(message, parameters);
        
        return message;
    }
    
    
}
