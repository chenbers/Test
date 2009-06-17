package com.inthinc.pro.reports.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class MessageUtil
{
    private static final Logger logger = Logger.getLogger(MessageUtil.class);

    private static final String BUNDLE_NAME = "com.inthinc.pro.ReportMessages";
    
    public static String getMessageString(String key, Locale locale)
    {
        ResourceBundle bundle = getBundle(locale);
        return getBundleString(bundle, key);
    }
    

    private static String getBundleString(ResourceBundle bundle, String key)
    {
        try
        {
            return bundle == null ? key : bundle.getString(key);
        }
        catch (MissingResourceException e)
        {
            return key;
        }
    }

    public static ResourceBundle getBundle(Locale locale)
    {
        try
        {
            // Last resort is the context class loader
            return ResourceBundle.getBundle(BUNDLE_NAME, locale, Thread.currentThread().getContextClassLoader());
        }
        catch (MissingResourceException ext)
        {
            logger.error("resource bundle " + BUNDLE_NAME + " could not be found");
            return null;
        }
    }
}
