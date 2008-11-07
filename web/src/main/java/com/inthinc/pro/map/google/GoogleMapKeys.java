package com.inthinc.pro.map.google;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class GoogleMapKeys
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(GoogleMapKeys.class);

    // private static GoogleMapKeys googleMapsKeys = null;

    private File googleMapKeyPropertiesFile;

    private Properties googleMapKeyProperties;

    public GoogleMapKeys()
    {
        logger.debug("****************in google map keys");

    }

    // public static void initProperties()
    // {
    // googleMapsKeys = new GoogleMapKeys();
    // }
    //  
    // public static GoogleMapKeys getInstance()
    // {
    // if (googleMapsKeys == null)
    // {
    // // logger.error("Gain Properties not properly initialized. Servlet must initial this.");
    // googleMapsKeys = new GoogleMapKeys();
    // }
    //      
    // return googleMapsKeys;
    //          
    // }

    private void loadPropertiesFile()
    {
        FileInputStream inStream = null;
        try
        {
            logger.debug("Google Maps Keys file name is: "+googleMapKeyPropertiesFile);
            inStream = new FileInputStream(googleMapKeyPropertiesFile);
            googleMapKeyProperties = new Properties();
            googleMapKeyProperties.load(inStream);
        }
        catch (Exception e)
        {
            logger.error("Google Maps Keys cannot be loaded.", e);
        }
        finally
        {
            if(inStream != null)
            {
                try
                {
                    inStream.close();
                }
                catch (IOException e)
                {
                    logger.debug("IOException thrown while trying to close googleMapKeyPropertiesFile InputStream", e);
                }
            }
        }

    }

    public String getProperty(String key)
    {
        if (googleMapKeyProperties == null)
        {
            loadPropertiesFile();
        }
        return googleMapKeyProperties.getProperty(key.toLowerCase());
    }

    public File getGoogleMapKeyPropertiesFile()
    {
        return googleMapKeyPropertiesFile;
    }

    public void setGoogleMapKeyPropertiesFile(File googleMapKeyPropertiesFile)
    {
        this.googleMapKeyPropertiesFile = googleMapKeyPropertiesFile;
    }

}
