package com.inthinc.pro.automation.enums;

public enum AutoProperties {
    
    SERVER
    
    
    ;
    
    
    public AutoProperties valueOfLower(String prop){
        return valueOf(prop.toUpperCase());
    }

}
