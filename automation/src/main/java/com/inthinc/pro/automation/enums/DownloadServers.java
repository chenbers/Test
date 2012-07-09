package com.inthinc.pro.automation.enums;

public enum DownloadServers {
    
    DEV("192.168.11.111"),
    QA("192.168.11.115"),
    
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
