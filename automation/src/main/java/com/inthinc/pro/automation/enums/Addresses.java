package com.inthinc.pro.automation.enums;

public enum Addresses implements AddressInterface{

    USER_CREATED(),

    PROD("my.inthinc.com"),

    CHEVRON("chevron.inthinc.com"),
    
    SCHLUMBERGER("schlumberger.inthinc.com"),

    WEATHORFORD("weatherford.inthinc.com"),

    CINTAS("cintas.inthinc.com"),

    BARRICK("barrick.inthinc.com"),

    TECK("teck.inthinc.com"),
    
    STAGE("stage.inthinc.com"),

    DEV("dev-pro.inthinc.com", 8888),

    EC2("204.236.172.41", prodPortalPort, "stage.inthinc.com", prodMCMPort, prodWaysPort, prodSatPort),

    QA("qa.tiwipro.com", 8199, "qa.tiwipro.com", 8090, 8988, 7880),

    QA2("qa2.tiwipro.com", 8299, "qa2.tiwipro.com", 8290, 8988, 7980),

    TEEN_QA("192.168.1.215"),

    TEEN_PROD("my.tiwi.com"),

    // PROD_MCM_EC2("my.inthinc.com"),
    // PROD_MCM_PORT_EC2("prodMCMPort"),
    // PROD_PORTAL_EC2("50.16.201.215"),
    // PROD_PORT_EC2("prodPortalPort");

    ;

    private String portalUrl, mcmUrl;
    private Integer portalPort, mcmPort, waysPort, satPort;

    private Addresses() {}

    private Addresses(String url) {
        setUrlAndPort(url, prodPortalPort, url, prodMCMPort, prodWaysPort, prodSatPort);
    }

    private Addresses(String url, Integer waysPort) {
        setUrlAndPort(url, prodPortalPort, url, prodMCMPort, waysPort, prodSatPort);
    }

    private Addresses(String url, Integer waysPort, Integer satPort) {
        setUrlAndPort(url, prodPortalPort, url, prodMCMPort, waysPort, satPort);
    }
    
    private Addresses(String portalUrl, int portalPort, String mcmUrl, int mcmPort, int waysPort) {
        setUrlAndPort(portalUrl, portalPort, mcmUrl, mcmPort, waysPort, prodSatPort);
    }

    private Addresses(String portalUrl, int portalPort, String mcmUrl, int mcmPort, int waysPort, int satPort) {
        setUrlAndPort(portalUrl, portalPort, mcmUrl, mcmPort, waysPort, satPort);
    }

    public Addresses setUrlAndPort(String portalUrl, int portalPort, String mcmUrl, int mcmPort) {
        return setUrlAndPort(portalUrl, portalPort, mcmUrl, mcmPort, prodWaysPort, prodSatPort);
    }
    
    public Addresses setUrlAndPort(String portalUrl, int portalPort, String mcmUrl, int mcmPort, int waysPort) {
        return setUrlAndPort(portalUrl, portalPort, mcmUrl, mcmPort, waysPort, prodSatPort);
    }

    public Addresses setUrlAndPort(String portalUrl, int portalPort, String mcmUrl, int mcmPort, int waysPort, int satPort) {
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
        return this.name() + "\n\tPortal Proxy  = " + portalUrl + ":" + portalPort + 
                             "\n\tMCM Proxy     = " + mcmUrl + ":" + mcmPort +
                             "\n\tWaysmart Port = " + waysPort +
                             "\n\tSatelite Port = " + satPort + "\n\n";
    }

    public Object getSatPort() {
        return satPort;
    }

}
