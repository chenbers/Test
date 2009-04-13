package com.inthinc.pro.map.google;

import org.apache.log4j.Logger;

import com.inthinc.pro.util.WebUtil;

public class GoogleMapBacking
{
    // this backing bean handles the generic google map stuff like keys
    private static Logger logger = Logger.getLogger(GoogleMapBacking.class);
    
    private GoogleMapKeys googleMapKeys;
    private WebUtil webUtil;
    
    public String getKey()
    {
        
        String serverName = webUtil.getRequestServerName();
        int serverPort = webUtil.getRequestServerPort();
        String googleMapsKey = googleMapKeys.getProperty(serverName);
        if (serverPort != 80 && googleMapKeys.getProperty(serverName + ":" + serverPort) != null) {
            googleMapsKey = googleMapKeys.getProperty(serverName + ":" + serverPort);
        }
        return googleMapsKey;
    }

    public String getVersion()
    {
        String version = googleMapKeys.getProperty("version");
        if(version == null || version.isEmpty())
            version = "2";
        
        return version;
    }

    public GoogleMapKeys getGoogleMapKeys()
    {
        return googleMapKeys;
    }


    public void setGoogleMapKeys(GoogleMapKeys googleMapKeys)
    {
        logger.debug("in setGoogleMapKeys");
        this.googleMapKeys = googleMapKeys;
    }


    public WebUtil getWebUtil()
    {
        return webUtil;
    }


    public void setWebUtil(WebUtil webUtil)
    {
        this.webUtil = webUtil;
    }

}
