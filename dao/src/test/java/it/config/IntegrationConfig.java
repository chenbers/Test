package it.config;

import java.util.Properties;

//import org.apache.log4j.Logger;

public class IntegrationConfig extends Properties
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

//    private static final Logger logger = Logger.getLogger(IntegrationConfig.class);

    private final static String CONFIG_PROPERTIES_FILENAME = "it.properties";
    public final static String SILO_HOST = "siloDataAccessHost";
    public final static String SILO_PORT = "siloDataAccessPort";
    public final static String MCM_HOST = "mcmDataAccessHost";
    public final static String MCM_PORT = "mcmDataAccessPort";
    public final static String MAP_SERVER_URL = "mapserver.url";

    public IntegrationConfig()
    {
        try
        {
            load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_PROPERTIES_FILENAME));
        }
        catch (Exception e)
        {
            System.out.println(CONFIG_PROPERTIES_FILENAME + " cannot be loaded.");
            e.printStackTrace();
        }
    }

    public int getIntegerProp(String key)
    {
        String value = getProperty(key);

        if (value == null)
        {
        	System.out.println("Error getting value for property: " + key);
            return 0;
        }

        try
        {
            return new Integer(value).intValue();
        }
        catch (NumberFormatException e)
        {
        	System.out.println("Error getting integer value for property: " + key);
        }
        return 0;
    }
}
