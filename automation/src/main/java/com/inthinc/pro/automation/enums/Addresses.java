package com.inthinc.pro.automation.enums;


public enum Addresses {
	
	USER_CREATED(null, null, null, null),
    
    
    DEV("dev-pro.inthinc.com"),
    
    EC2("204.236.172.41",8099,"stage.inthinc.com",8090),

    QA("qa.tiwipro.com",8199,"qa.tiwipro.com",8190),
    
    QA2("qa2.tiwipro.com",8299,"qa2.tiwipro.com",8290),

    TEEN_QA("192.168.1.215"),

    PROD("my.inthinc.com"),
    
    CHEVRON("chevron.inthinc.com"),
    
    SCHLUMBERGER("schlumberger.inthinc.com"),
    
    WEATHORFORD("weatherford.inthinc.com"),
    
    
//    PROD_MCM_EC2("my.inthinc.com"),
//    PROD_MCM_PORT_EC2("8090"),
//    PROD_PORTAL_EC2("50.16.201.215"),
//    PROD_PORT_EC2("8099");
    
    ;

    
    private String portalUrl, mcmUrl;
    private Integer portalPort, mcmPort;
    
    private Addresses(String url) {
    	setUrlAndPort(url, 8099, url,8090);
    }
    
    private Addresses(String portalUrl, Integer portalPort, String mcmUrl, Integer mcmPort){
    	setUrlAndPort(portalUrl, portalPort, mcmUrl, mcmPort);
    }
    
    public Addresses setUrlAndPort(String portalUrl, Integer portalPort, String mcmUrl, Integer mcmPort){
    	this.portalUrl = portalUrl;
    	this.portalPort = portalPort;
    	this.mcmUrl = mcmUrl;
    	this.mcmPort = mcmPort;
    	return this;
    }

    public String getPortalUrl(){
    	return portalUrl;
    }
    
    public Integer getPortalPort(){
    	return portalPort;
    }
    
    public String getMCMUrl(){
    	return mcmUrl;
    }
    
    public Integer getMCMPort(){
    	return mcmPort;
    }
    
    public String toString(){
    	return this.name() +
    			"\n\tPortal Proxy = " + portalUrl +":"+portalPort + 
    			"\n\tMCM Proxy    = "+mcmUrl + ":" +mcmPort+"\n";
    }

}
