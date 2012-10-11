package com.inthinc.pro.scheduler.i18n;

import java.text.MessageFormat;
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
        
        String message = null;
        if (myResources.containsKey(key))
            message =  myResources.getString(key);
        else 
            message = key;
        
        if(parameters != null) {
            String translatedParameters[] = new String[parameters.length];
            int idx = 0;
            for (String parameter : parameters) {
                if (myResources.containsKey(parameter))
                    parameter = myResources.getString(parameter);
                translatedParameters[idx++] = parameter;
            }
            message = MessageFormat.format(message, translatedParameters);
        }
            
        return message;
            
    }
    
    
}
