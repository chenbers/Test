package com.inthinc.pro.automation.enums;

public enum LoginCapabilities {

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
    
    //Login Capabilities
    RoleAdmin(),
    IsDriver(),
    HasVehicle(),
    HasDevice(),
    NoteTesterData(),
    
    
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
//	
//	private AutomationLogins(String username, String password, List<LoginCapabilities> capabilities){
//	    this.username = username;
//	    this.password = password;
//	    
//	    this.capabilites = capabilities;
//	}
//	private UniqueValues(String name, Boolean string){
//		this.name = name;
//		this.string = string;
//	}
//	
//	public String getName(){
//		return name;
//	}
//	
//	public Boolean isString(){
//		return string;
//	}
    public boolean isEnabled(){
        return boolValue;
    }
    public Integer getIntValue() {
        return intValue;
    }
}
