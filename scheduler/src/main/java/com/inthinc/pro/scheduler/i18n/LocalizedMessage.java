package com.inthinc.pro.scheduler.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizedMessage
{
    private static String bundleName = "messages";
    
    public static String getString(String key)
    {

        ResourceBundle myResources = ResourceBundle.getBundle(bundleName, Locale.getDefault());
        
        if (myResources.containsKey(key))
            return myResources.getString(key);
        
        return "";
    }
    
    
}
