package com.inthinc.pro.automation.enums;

import com.inthinc.pro.automation.interfaces.AutoCapabilities;

public enum LoginCapabilities implements AutoCapabilities  {    
    //Login Capabilities
    RoleAdmin,
    IsDriver,
    HasVehicle,
    HasWaySmart,
    HasTiwiPro,
    NoteTesterData,
    StatusInactive(false),
    StatusActive(true),
    PasswordChanging(true), 
    RoleHOS,
    DOT,
    TeamLevelLogin,
    
    ;
    private Integer intValue;
    private boolean boolValue;
    private LoginCapabilities(){
        
    };
    private LoginCapabilities(Integer intValue){
        this.intValue = intValue;
        this.boolValue = (intValue > 0);            
    }
    private LoginCapabilities(boolean boolValue){
        this.boolValue = boolValue;
    }

    public boolean isEnabled(){
        return boolValue;
    }
    public Integer getIntValue() {
        return intValue;
    }
}
