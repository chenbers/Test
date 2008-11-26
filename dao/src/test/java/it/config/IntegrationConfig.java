package it.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class IntegrationConfig extends Properties
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(IntegrationConfig.class);
    
    private final static String CONFIG_PROPERTIES_FILENAME="it.properties";
    public final static String SILO_HOST="siloDataAccessHost";
    public final static String SILO_PORT="siloDataAccessPort";

    
    public IntegrationConfig(File propertiesFileDir)
    {
        loadPropertiesFile(propertiesFileDir);
    }
    
    private void loadPropertiesFile(File baseDirectory) 
    {
        File propertiesFile = new File(baseDirectory, CONFIG_PROPERTIES_FILENAME);
        
        try {
            FileInputStream inStream = new FileInputStream(propertiesFile);
            load(inStream);
        } catch (Exception e) {
            logger.error(propertiesFile.getAbsolutePath() + " cannot be loaded.", e);
        }
    }
    
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
