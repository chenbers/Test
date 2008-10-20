package com.inthinc.pro.backing;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;


public class VersionBean implements ApplicationContextAware
{
    private static final Logger logger = Logger.getLogger(VersionBean.class);
    ApplicationContext applicationContext;
    private String version = "Unknown";

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }

    public void init()
    {
        try
        {
            Resource pom = applicationContext.getResource("META-INF/maven/com.inthinc.pro.web/web/pom.properties");
            if(logger.isDebugEnabled())
            {
                logger.debug("Does the pom.properties file exist: " + pom.exists());
            }
            if (pom.exists())
            {
                Properties properties = new Properties();
                properties.load(pom.getInputStream());
                version = properties.getProperty("version");
            }
        }
        catch (IOException e)
        {
            logger.debug("Maven version info was not found.", e);
        }
    }

    public String getVersion()
    {
        return version;
    }

}
