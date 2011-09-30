package com.inthinc.pro.automation.enums;

import java.util.EnumSet;
import java.util.HashMap;

import com.inthinc.pro.automation.interfaces.AddressInterface;

public enum Addresses implements AddressInterface {

    USER_CREATED(),

    PROD("my.inthinc.com"),

    CHEVRON("chevron.inthinc.com"),

    SCHLUMBERGER("schlumberger.inthinc.com"),

    WEATHERFORD("weatherford.inthinc.com"),

    CINTAS("cintas.inthinc.com"),

    BARRICK("barrick.inthinc.com"),

    TECK("teck.inthinc.com"),

    STAGE("stage.inthinc.com"),
    
    STAGE_DIRECT("ec2-50-18-81-171.us-west-1.compute.amazonaws.com"),

    DEV("dev-pro.inthinc.com", 8081, null, null, 7780, 8888, null),

    EC2("204.236.172.41", null, null, "stage.inthinc.com", null, null, null),

    QA("qa.tiwipro.com", 8423, 8199, "qa.tiwipro.com", 8190, 8988, 7880),

    // QA2("qa2.tiwipro.com", 8299, "qa2.tiwipro.com", 8290, 8988, 7980),

    TEEN_QA("192.168.1.215"),

    TEEN_PROD("my.tiwi.com"),

    // PROD_MCM_EC2("my.inthinc.com"),
    // PROD_MCM_PORT_EC2("prodMCMPort"),
    // PROD_PORTAL_EC2("50.16.201.215"),
    // PROD_PORT_EC2("prodPortalPort");

    ;

    private String portalUrl, mcmUrl;
    private Integer portalPort, mcmPort, waysPort, satPort;
    private String protocol = "https";
    private String appName = "tiwipro";
    private Integer webPort;

    private Addresses() {}

    private Addresses(String url) {
        this(url, null, prodPortalPort, url, prodMCMPort, prodWaysPort,
                prodSatPort);
    }

    private Addresses(String portalUrl, Integer webPort, Integer portalPort,
            String mcmUrl, Integer mcmPort, Integer waysPort, Integer satPort) {
        this.portalUrl = portalUrl;
        this.webPort = webPort;
        this.portalPort = (portalPort != null ? portalPort : prodPortalPort);
        this.mcmUrl = (mcmUrl != null ? mcmUrl : portalUrl);
        this.mcmPort = (mcmPort != null ? mcmPort : prodMCMPort);
        this.waysPort = (waysPort != null ? waysPort : prodWaysPort);
        this.satPort = (satPort != null ? satPort : prodSatPort);
    }

    public Addresses setUrlAndPort(String portalUrl, int portalPort,
            String mcmUrl, int mcmPort) {
        return setUrlAndPort(portalUrl, portalPort, mcmUrl, mcmPort,
                prodWaysPort, prodSatPort);
    }

    public Addresses setUrlAndPort(String portalUrl, int portalPort,
            String mcmUrl, int mcmPort, int waysPort) {
        return setUrlAndPort(portalUrl, portalPort, mcmUrl, mcmPort, waysPort,
                prodSatPort);
    }

    public Addresses setUrlAndPort(String portalUrl, int portalPort,
            String mcmUrl, int mcmPort, int waysPort, int satPort) {
        this.portalUrl = portalUrl;
        this.portalPort = portalPort;
        this.mcmUrl = mcmUrl;
        this.mcmPort = mcmPort;
        this.waysPort = waysPort;
        this.satPort = satPort;
        return this;
    }

    public String getPortalUrl() {
        return portalUrl;
    }

    public String getWebAddress() {
        return (protocol != null ? protocol : "https") + "://" + portalUrl
                + (webPort != null ? ":" + webPort : "")
                + (appName != null ? "/" + appName + "/" : "");
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setWebPort(Integer webPort) {
        this.webPort = webPort;
    }

    public Integer getPortalPort() {
        return portalPort;
    }

    public String getMCMUrl() {
        return mcmUrl;
    }

    public Integer getMCMPort() {
        return mcmPort;
    }

    public Integer getWaysPort() {
        return waysPort;
    }

    public String toString() {
        return this.name() + "\n\t" + getWebAddress() + "\n\tPortal Proxy  = "
                + portalUrl + ":" + portalPort + "\n\tMCM Proxy     = "
                + mcmUrl + ":" + mcmPort + "\n\tWaysmart Port = " + waysPort
                + "\n\tSatelite Port = " + satPort + "\n\n";
    }

    public Object getSatPort() {
        return satPort;
    }

    private static HashMap<String, Addresses> lookupByName = new HashMap<String, Addresses>();

    static {
        for (Addresses b : EnumSet.allOf(Addresses.class)) {
            lookupByName.put(b.name().toLowerCase(), b);
        }
    }

    public static Addresses getSilo(String silo) {
        return lookupByName.get(silo.toLowerCase());
    }

}
