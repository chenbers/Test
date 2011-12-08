package com.inthinc.pro.automation.enums;

import com.inthinc.pro.automation.interfaces.AutoCapability;

public enum AccountCapability implements AutoCapability {

	//Account capabilities
    WaySmartEnabled(true),
    WaySmartNotEnabled(false),
    
    HOSEnabled(true),
    HOSNotEnabled(false),
    
    LoginExpireNever(false),
    LoginExpire15(15),
    LoginExpire30(30),
    LoginExpire45(45),
    LoginExpire60(60),
    LoginExpire75(75),
    LoginExpire90(90),
    
    PasswordExpireNever(false),
    PasswordExpire90(90),
    PasswordExpire120(120),
    PasswordExpire180(180),
    PasswordExpire360(360),
    
    PasswordMinStrengthNone(false),
    PasswordMinStrengthWeak(16),
    PasswordMinStrengthFair(25),
    PasswordMinStrengthStrong(35),
    
    PasswordRequireInitialChangeNone(false),
    PasswordRequireInitalChangeWarn(),
    PasswordRequireInitalChangeRequire(true),
    
    
    
    ;
    private Integer intValue;
    private boolean boolValue;
    private AccountCapability(){
        
    };
    private AccountCapability(Integer intValue){
        this.intValue = intValue;
        this.boolValue = (intValue > 0);            
    }
    private AccountCapability(boolean boolValue){
        this.boolValue = boolValue;
    }
    public boolean isEnabled(){
        return boolValue;
    }
    public Integer getIntValue() {
        return intValue;
    }
}
