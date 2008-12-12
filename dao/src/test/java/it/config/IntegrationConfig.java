package it.config;

import java.util.Properties;

import org.apache.log4j.Logger;

public class IntegrationConfig extends Properties
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(IntegrationConfig.class);

    private final static String CONFIG_PROPERTIES_FILENAME = "it.properties";
    public final static String SILO_HOST = "siloDataAccessHost";
    public final static String SILO_PORT = "siloDataAccessPort";
    public final static String MCM_HOST = "mcmDataAccessHost";
    public final static String MCM_PORT = "mcmDataAccessPort";

    public IntegrationConfig()
    {
        try
        {
            load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_PROPERTIES_FILENAME));
        }
        catch (Exception e)
        {
            logger.error(CONFIG_PROPERTIES_FILENAME + " cannot be loaded.", e);
        }
    }

//    public IntegrationConfig(File propertiesFileDir)
//    {
//        loadPropertiesFile(propertiesFileDir);
//    }
//
//    private void loadPropertiesFile(File baseDirectory)
//    {
//        try
//        {
//            load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_PROPERTIES_FILENAME));
//        }
//        catch (Exception e)
//        {
//            logger.error(CONFIG_PROPERTIES_FILENAME + " cannot be loaded.", e);
//        }
//    }

    public int getIntegerProp(String key)
    {
        String value = getProperty(key);

        if (value == null)
        {
            logger.error("Error getting value for property: " + key);
            return 0;
        }

        try
        {
            return new Integer(value).intValue();
        }
        catch (NumberFormatException e)
        {
            logger.error("Error getting integer value for property: " + key);
        }
        return 0;
    }
}
