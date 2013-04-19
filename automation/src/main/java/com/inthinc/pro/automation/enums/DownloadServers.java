package com.inthinc.pro.automation.enums;

public enum DownloadServers {
    
	TEEN_QA("192.168.11.81"),
    DEV("192.168.11.111"),
    QA("qa.inthinc.com"),
    
    ;
    
    private final String address;
    
    private DownloadServers(String address){
        this.address = address;
    }
    
    public String getAddress(){
        return address;
    }
    
    public int getPort(){
        return 8096;
    }
    

}
