package com.inthinc.pro.automation.enums;

import com.inthinc.pro.automation.interfaces.AutoCapability;

public enum LoginCapability implements AutoCapability  {    
    //Login Capabilities
    RoleAdmin,
    RoleHOS,
    RoleLiveFleet,
    IsDriver,
    HasVehicle,
    HasWaySmart,
    HasTiwiPro,
    NoteTesterData,
    StatusInactive(false),
    StatusActive(true),
    PasswordChanging(true), 
    PasswordInvalid(),
    DOT,
    TeamLevelLogin,
    FleetLevelLogin,
    DivisionLevelLogin,
    AccessPointUsers,
    AccessPointUserInfo,
    AccessPointDriverInfo,
    AccessPointRFIDInfo,
    AccessPointEmployeeInfo,
    AccessPointLoginInfo,
    AccessPointNotificationsInfo,
    AccessPointVehicles,
    //TODO: todd: the new accesspoints for the vehicle would probably go here
    AccessPointsDevices,
    AccessPointsZones,
    AccessPointsRedFlags,
    AccessPointsReports,
    AccessPointsOrganization,
    AccessPointsSBS,
    AccessPointsLiveFleet,
    
    //TODO: todd: I'm including these because of the way your tests depend on a user NOT having access (there might be a better way of determining this???)
    NoAccessPointUsers,
    NoAccessPointUserInfo,
    NoAccessPointDriverInfo,
    NoAccessPointRFIDInfo,
    NoAccessPointEmployeeInfo,
    NoAccessPointLoginInfo,
    NoAccessPointNotificationsInfo,
    NoAccessPointVehicleInfo,
    ;
    private Integer intValue;
    private boolean boolValue;
    private LoginCapability(){
        
    };
    private LoginCapability(Integer intValue){
        this.intValue = intValue;
        this.boolValue = (intValue > 0);            
    }
    private LoginCapability(boolean boolValue){
        this.boolValue = boolValue;
    }

    public boolean isEnabled(){
        return boolValue;
    }
    public Integer getIntValue() {
        return intValue;
    }
}
