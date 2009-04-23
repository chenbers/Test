package com.inthinc.pro.reports.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ReportMessageUtil
{
    private static final Logger logger = Logger.getLogger(ReportMessageUtil.class);

    private static final String BUNDLE_NAME = "com.inthinc.pro.ReportMessages";

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
