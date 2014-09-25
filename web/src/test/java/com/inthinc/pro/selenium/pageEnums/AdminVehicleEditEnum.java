package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminVehicleEditEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/admin/editVehicle"),

    TITLE("Admin - *** Details"						, "//span[@class='admin']"),
    
    TAB_DETAILS("Details"							, "edit-form:details_lbl"),
    TAB_SPEED_AND_SENSITIVITY("Speed & Sensitivity"	, "edit-form:speedSensitivity_lbl"),
    
    BTN_SAVE_TOP("Save"								, "edit-form:editVehicleSave1", "edit-form:editVehicleSave3"),
    BTN_CANCEL_TOP("Cancel"							, "edit-form:editVehicleCancel1", "edit-form:editVehicleCancel3"),
    BTN_SAVE_BOTTOM("Save"							, "edit-form:editVehicleSave2", "edit-form:editVehicleSave4"),
    BTN_CANCEL_BOTTOM("Cancel"						, "edit-form:editVehicleCancel2", "edit-form:editVehicleCancel4"),

    SLIDER_AUTOLOGOFF(null							, "edit-form:editVehicle-autoLogoff"),

    TXT_ZONE_LIMIT_EXPECTSROWNUM(null				, "edit-form:speed###Input"),
    TXT_HARD_ACCEL(null								, "//span[@id='hardAccelerationLevel']"),
    TXT_HARD_BRAKE(null								, "//span[@id='hardBrakeLevel']"),
    TXT_HARD_BUMP(null								, "//span[@id='hardVerticalLevel']"),
    TXT_UNSAFE_TURN(null							, "//span[@id='hardTurnLevel']"),
    TXT_IDLING_THRESHOLD(null						, "idlingThreshold"),
    TXT_ASSIGNED_DRIVER(null						, "edit-form:driverID"),
    TXT_PRODUCT(null								, "//table[3]/tbody/tr[1]/td[2]"),
    TXT_DEVICE(null									, "//table[3]/tbody/tr[2]/td[2]"),

    TXTFIELD_VIN("VIN:"								, "edit-form:editVehicle-vin"),
    TXTFIELD_MAKE("Make:"							, "edit-form:editVehicle-make"),
    TXTFIELD_MODEL("Model:"							, "edit-form:editVehicle-model"),
    TXTFIELD_COLOR("Color:"							, "edit-form:editVehicle-color"),
    TXTFIELD_WEIGHT("Weight: (lbs)"					, "edit-form:editVehicle-weight"),
    TXTFIELD_LICENCE("License #:"					, "edit-form:editVehicle-license"),
    TXTFIELD_ODO("Odometer: (UNITS)"				, "edit-form:editVehicle-odometer"),
    TXTFIELD_AUTOLOGOFF(null						, "edit-form:editVehicle-autoLogoffInput"),
    TXTFIELD_VEHICLEID("Vehicle ID:"				, "edit-form:editVehicle-name"),
    TXTFIELD_ECALLPHONE("E-Call Phone:"				, "edit-form:editVehicle-ephone"),
    TXTFIELD_HARD_ACCEL(null						, "edit-form:editVehicle-hardAccelerationInput"),
    TXTFIELD_HARD_BRAKE(null						, "edit-form:editVehicle-hardBrakeInput"),
    TXTFIELD_HARD_BUMP(null							, "edit-form:editVehicle-hardVerticalInput"),
    TXTFIELD_UNSAFE_TURN(null						, "edit-form:editVehicle-hardTurnInput"),
    TXTFIELD_IDLING_THRESHOLD(null					, "edit-form:editVehicle-idlingThresholdInput"),
    TXTFIELD_ZERO_TO_25(null						, "//input[@id='edit-form:speed0Input']"),
    TXTFIELD_26_TO_50(null							, "//input[@id='edit-form:speed5Input']"),
    TXTFIELD_51_TO_75(null							, "//input[@id='edit-form:speed10Input']"),
    TXTFIELD_MAX_SPEED(null							, "//input[@id='edit-form:editVehicle_maxSpeedInput']"),

    LABEL_AUTOLOGOFF("Auto Log Off:"				, "//table[@id='edit-form:editVehicle-autoLogoff']/../../td[1]"),
    LABEL_ASSIGN_DRIVER("Assigned Driver:"			, "//span[@id='edit-form:driverID']/../../td[1]"),

    DROPDOWN_STATE("State:"							, "edit-form:editVehicle-state"),
    DROPDOWN_YEAR("Year:"							, "edit-form:editVehicle-year"),
    DROPDOWN_ZONE("Zone Type:"						, "edit-form:editVehicle-type"),
    DHXDROP_TEAM("Team"								, "edit-form:editVehicle-groupID"),
    DROPDOWN_DOT(null								, "edit-form:editVehicle-DOT"),
    DROPDOWN_STATUS("Status"						, "edit-form:editVehicle-status"),
    DROPDOWN_KILL_MOTOR(null						, "edit-form:wirelineKillMotor_selectItems"),
    DROPDOWN_DOOR_ALARM(null						, "edit-form:wirelineDoorAlarm_selectItems"),
    
    LINK_ASSIGN_DRIVER(null							, "editVehicle-chooseDriver"),
    LINK_EDIT_VEHICLE_PLUS(null						, "editVehicle-plus"),
    LINK_EDIT_VEHICLE_MINUS(null					, "editVehicle-minus"),
    
    CHECKBOX_IDLE_MENTOR(null						, "edit-form:editVehicle-idleBuzzer"),
    CHECKBOX_IFTA(null								, "edit-form:editVehicle-ifta"),
    
    KILL_MOTOR_PASS(null							, "edit-form:wireline_killMotorPasscode"),
    DOOR_ALARM_PASS(null							, "edit-form:wireline_doorAlarmPasscode"),
    AUTO_ARM_TIME(null								, "edit-form:wireline_autoArm"),
    KILL_MOTOR_SEND(null							, "edit-form:wirelineKillMotor_sendButton"),
    DOOR_ALARM_SEND(null							, "edit-form:wirelineDoorAlarm_sendButton"),
    MAX_SPEED(null									, "edit-form:speedLimitInput"),
    SPEED_BUFFER(null								, "edit-form:speedBufferInput"),
    SEVERE_SPEEDING(null							, "edit-form:severeSpeedInput")
    ;
    private String text, url;
    private String[] IDs;

    private AdminVehicleEditEnum(String url) {
        this.url = url;
    }

    private AdminVehicleEditEnum(String text, String... IDs) {
        this.text = text;
        this.IDs = IDs;
    }

    @Override
    public String[] getIDs() {
        return IDs;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getURL() {
        return url;
    }
}
