package com.inthinc.pro.map.google;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.inthinc.pro.map.GoogleMapKeyFinder;
import com.inthinc.pro.util.WebUtil;

public class GoogleMapBacking implements GoogleMapKeyFinder{
    // this backing bean handles the generic google map stuff like keys
    private static Logger logger = Logger.getLogger(GoogleMapBacking.class);

    // private GoogleMapKeys googleMapKeys;
    private Properties googleMapProperties;

    @Override
    public String getKey() {

        String serverName = WebUtil.getRequestServerName();
        int serverPort = WebUtil.getRequestServerPort();
        String googleMapsKey = googleMapProperties.getProperty(serverName);
        if (serverPort != 80 && googleMapProperties.getProperty(serverName + ":" + serverPort) != null) {
            googleMapsKey = googleMapProperties.getProperty(serverName + ":" + serverPort);
        }
        return googleMapsKey;
    }

    public String getVersion() {
        String version = googleMapProperties.getProperty("version");
        if (version == null || version.isEmpty())
            version = "2";

        return version;
    }

    public Properties getGoogleMapProperties() {
        return googleMapProperties;
    }

    public void setGoogleMapProperties(Properties googleMapProperties) {
        this.googleMapProperties = googleMapProperties;
    }

	@Override
	public void setKey(String key) {
		//This will not be used as getKey will find it and set it
	}

}
