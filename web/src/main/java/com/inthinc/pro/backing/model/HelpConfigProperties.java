package com.inthinc.pro.backing.model;

import java.util.Properties;

import org.apache.log4j.Logger;

public class HelpConfigProperties extends Properties {
    
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(HelpConfigProperties.class);
    private static final String DEFAULT_KEY = "default";
    
    private String location;
    
    public HelpConfigProperties() {

    }
    
    public void init() {
        try
        {
            
            load(Thread.currentThread().getContextClassLoader().getResourceAsStream(getLocation()));
        }
        catch (Exception e)
        {
            logger.error(getLocation() + " cannot be loaded.", e);
            e.printStackTrace();
        }
        
    }
    
    public String getDefault() {
        Object def = get(DEFAULT_KEY);
        return (def == null) ? "" : def.toString();
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
