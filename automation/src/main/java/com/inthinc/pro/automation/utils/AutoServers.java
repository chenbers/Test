package com.inthinc.pro.automation.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.inthinc.pro.automation.AutoPropertiesHandler;
import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.enums.AutoSilos;

public class AutoServers {


    private final static String automationApp = "automation.application";
    private final static String automationSilo = "automation.testsilo";
    private final static String defaultApp = "tiwipro";
    private final static String defaultSilo = "qa";
    private final static int defaultSize = 2;
    private final static String getMcmPort = "mcmport";
    private final static String getMcmUrl = "mcmurl";
    private final static String getPortalPort = "portalport";
    private final static String getPortalUrl = "url";
    private final static String getProtocol = "protocol";
    private final static String getSatPort = "satport";
    private final static String getWaysPort = "waysport";
    private final static String getWebPort = "webport";
    private final static String server = "servers";

    private String app;
    private Integer mcmPort;
    private String mcmUrl;
    private Integer portalPort;
    private String portalUrl;
    private String protocol;
    private Integer satPort;
    private String silo;
    private Integer waysPort;
    private Integer webPort;

    public AutoServers(AutoSilos silo){
    	this();
    	setBySilo(silo);
    }
    
    public AutoServers() {

        silo = defaultSilo;
        app = defaultApp;

        AutomationPropertiesBean.getPropertyBean();

        Map<String, String> properties = AutoPropertiesHandler.getProperties();


        if (properties.containsKey(automationSilo)) {
            silo = properties.get(automationSilo);
        }

        if (properties.containsKey(automationApp)) {
            app = properties.get(automationApp);
        }

        setSettings(properties);

    }
    
    
    private void assignDefaults(Map<String, String> defaults, Map<String, String> props) {
        for (Map.Entry<String, String> entry : defaults.entrySet()) {
            String[] split = entry.getKey().split("[.]");
            if (split[0].equals(server)) {
                if (split.length == defaultSize) {
                    if (!props.containsKey(split[1])) {
                        if (!split[1].equals(getPortalUrl)) {
                            props.put(split[1], entry.getValue());
                        } else {
                            props.put(split[1], silo + entry.getValue());
                        }
                    }
                }
            } else {
                continue;
            }
        }
    }

    public String getApp() {
        return app;
    }

    public String getBaseAddress() {
        return protocol + "://" + portalUrl
                + (webPort != null ? ":" + webPort : "");
    }
    
    public Integer getMcmPort() {
        return mcmPort;
    }

    public String getMcmUrl() {
        return mcmUrl;
    }

    public Integer getPortalPort() {
        return portalPort;
    }
    
    public String getPortalUrl() {
        return portalUrl;
    }

    public String getProtocol() {
        return protocol;
    }

    public Integer getSatPort() {
        return satPort;
    }

    public String getSilo() {
        return silo;
    }

    public Integer getWaysPort() {
        return waysPort;
    }

    public String getWebAddress() {
        return getBaseAddress() + (app != null ? "/" + app + "/" : "");
    }

    public Integer getWebPort() {
        return webPort;
    }

    public void setApp(String app) {
        this.app = app;
        setSettings(AutoPropertiesHandler.getProperties());
    }

    public void setBySilo(AutoSilos silo){
        setSilo(silo.name().toLowerCase());
    }

    public void setMcmPort(Integer mcmPort) {
        this.mcmPort = mcmPort;
    }
    
    public void setMcmUrl(String mcmUrl) {
        this.mcmUrl = mcmUrl;
    }

    public void setPortalPort(Integer portalPort) {
        this.portalPort = portalPort;
    }

    public void setProtocol(String protocol){
        this.protocol = protocol;
    }
    
    public void setSatPort(Integer satPort) {
        this.satPort = satPort;
    }

    private void setSettings(Map<String, String> properties){


        Map<String, String> propertyValues = new HashMap<String, String>();
        for (Entry<String, String> entry : properties.entrySet()) {
            String siloKey = String.format("%s.%s.%s.", server, app, silo);
            if (entry.getKey().contains(siloKey)) {
                String key = entry.getKey().replace(siloKey, "");
                propertyValues.put(key, entry.getValue());
            }
        }
        
        assignDefaults(properties, propertyValues);

        portalUrl = propertyValues.get(getPortalUrl);
        setMcmUrl(propertyValues.containsKey(getMcmUrl) ? propertyValues.get(getMcmUrl) : portalUrl);
        if (propertyValues.containsKey(getWebPort)) {
            webPort = Integer.parseInt(propertyValues.get(getWebPort));
        }
        protocol = propertyValues.get(getProtocol);
        portalPort = Integer.parseInt(propertyValues.get(getPortalPort));
        mcmPort = Integer.parseInt(propertyValues.get(getMcmPort));
        waysPort = Integer.parseInt(propertyValues.get(getWaysPort));
        satPort = Integer.parseInt(propertyValues.get(getSatPort));
    }

    public void setSilo(String silo) {
        this.silo = silo;
        setSettings(AutoPropertiesHandler.getProperties());
    }

    public void setUrl(String url) {
        this.portalUrl = url;
    }
    
    public void setWaysPort(Integer waysPort) {
        this.waysPort = waysPort;
    }


    public void setWebPort(Integer webPort) {
        this.webPort = webPort;
    }


    @Override
    public String toString(){
        return AutomationStringUtil.toString(ObjectConverter.convertToJSONObject(this, "Server"));
    }
}
