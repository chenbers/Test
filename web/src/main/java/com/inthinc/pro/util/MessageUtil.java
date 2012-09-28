package com.inthinc.pro.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessageUtil
{
    public static String getMessageString(String key)
    {
        return getMessageString(FacesContext.getCurrentInstance(), key, null);
    }
    
    public static String getMessageString(String key, Locale locale)
    {
        return getMessageString(FacesContext.getCurrentInstance(), key,locale);
    }
    
    public static String getMessageString(FacesContext facesContext, String key)
    {
        return getMessageString(facesContext, key,null);
    }

    public static String getMessageString(FacesContext facesContext, String key, Locale locale)
    {
        if (facesContext == null)
        {
            facesContext = FacesContext.getCurrentInstance();
            if (facesContext == null)
                return key;
        }
        
        ResourceBundle bundle = getApplicationBundle(facesContext, locale);
        return getBundleString(bundle, key);
    }

    public static String formatMessageString(String key, Object... values)
    {
        return formatMessageString(FacesContext.getCurrentInstance(), key, values);
    }

    public static String formatMessageString(FacesContext facesContext, String key, Object... values)
    {
        final String message = getMessageString(facesContext, key,null);
        if (message != null)
            return MessageFormat.format(message, values);
        return null;
    }

    public static String getBundleString(ResourceBundle bundle, String key)
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


    public  static ResourceBundle getApplicationBundle(FacesContext facesContext, Locale locale)
    {
        String bundleName = facesContext.getApplication().getMessageBundle();
        
        return bundleName != null ? getBundle(facesContext, locale != null ? locale : facesContext.getViewRoot() != null ? facesContext.getViewRoot().getLocale() : facesContext.getExternalContext().getRequestLocale(), bundleName) : null;
    }

    public  static ResourceBundle getDefaultBundle(FacesContext facesContext,
                                                   Locale locale)
    {
        return getBundle(facesContext, locale, FacesMessage.FACES_MESSAGES);
    }

    public  static ResourceBundle getBundle(FacesContext facesContext,
                                            Locale locale,
                                            String bundleName)
    {
        try
        {
            //First we try the JSF implementation class loader
            return ResourceBundle.getBundle(bundleName,
                                            locale,
                                            facesContext.getClass().getClassLoader());
        }
        catch (MissingResourceException ignore1)
        {
            try
            {
                //Next we try the JSF API class loader
                return ResourceBundle.getBundle(bundleName,
                                                locale,
                                                MessageUtil.class.getClassLoader());
            }
            catch (MissingResourceException ignore2)
            {
                try
                {
                    //Last resort is the context class loader
                    return ResourceBundle.getBundle(bundleName,
                                                    locale,
                                                    Thread.currentThread().getContextClassLoader());
                }
                catch (MissingResourceException damned)
                {
                    facesContext.getExternalContext().log("resource bundle " + bundleName + " could not be found");
                    return null;
                }
            }
        }
    }
    

}
