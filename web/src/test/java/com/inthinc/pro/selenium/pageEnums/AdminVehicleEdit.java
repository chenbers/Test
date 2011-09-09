package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminVehicleEdit implements SeleniumEnums {
    DEFAULT_URL("/app/admin/editVehicle"),

    TITLE("Admin - *** Details", "//span[@class='admin']"),
    
    TAB_DETAILS("Details", "vehicleForm:details_lbl"),
    TAB_SPEED_AND_SENSITIVITY("Speed & Sensitivity", "vehicleForm:speedSensitivity_lbl"),
    
    BTN_SAVE_TOP("Save", "edit-form:editVehicleSave1", "edit-form:editVehicleSave3"),
    BTN_CANCEL_TOP("Cancel", "edit-form:editVehicleCancel1", "edit-form:editVehicleCancel3"),
    
    BTN_SAVE_BOTTOM("Save", "edit-form:editVehicleSave2", "edit-form:editVehicleSave4"),
    BTN_CANCEL_BOTTOM("Cancel", "edit-form:editVehicleCancel2", "edit-form:editVehicleCancel4"),

    // SPEED AND SENSITIVITY
    TXT_ZONE_LIMIT_EXPECTSROWNUM(null, "vehicleForm:speed###Input"),

    TXT_HARD_ACCEL(null, "hardAccelerationLevel"),
    TXT_HARD_BRAKE(null, "hardBrakeLevel"),
    TXT_HARD_BUMP(null, "hardVerticalLevel"),
    TXT_UNSAFE_TURN(null, "hardTurnLevel"),
    TXT_IDLING_THRESHOLD(null, "idlingThreshold"),

    TXTFIELD_HARD_ACCEL(null, "edit-form:editVehicle-hardAccelerationInput"),
    TXTFIELD_HARD_BRAKE(null, "edit-form:editVehicle-hardBrakeInput"),
    TXTFIELD_HARD_BUMP(null, "edit-form:editVehicle-hardVerticalInput"),
    TXTFIELD_UNSAFE_TURN(null, "edit-form:editVehicle-hardTurnInput"),
    TXTFIELD_IDLING_THRESHOLD(null,
            "edit-form:editVehicle-idlingThresholdInput"),

    TXTFIELD_VIN("VIN:", "edit-form:editVehicle-vin"),
    TXTFIELD_MAKE("Make:", "edit-form:editVehicle-make"),
    TXTFIELD_MODEL("Model:", "edit-form:editVehicle-model"),
    DROPDOWN_YEAR("Year:", "edit-form:editVehicle-year"),
    TXTFIELD_COLOR("Color:", "edit-form:editVehicle-color"),
    TXTFIELD_WEIGHT("Weight: (lbs)", "edit-form:editVehicle-weight"),
    TXTFIELD_LICENCE("License #:", "edit-form:editVehicle-license"),
    DROPDOWN_STATE("State:", "edit-form:editVehicle-state"),
    TXTFIELD_ODO("Odometer: (UNITS)", "edit-form:editVehicle-odometer"),
    DROPDOWN_ZONE("Zone Type:", "edit-form:editVehicle-type"),
    TXTFIELD_ECALLPHONE("E-Call Phone:", "edit-form:editVehicle-ephone"),

    LABEL_AUTOLOGOFF("Auto Log Off:", "//table[@id='edit-form:editVehicle-autoLogoff']/../../td[1]"),
    SLIDER_AUTOLOGOFF(null, "edit-form:editVehicle-autoLogoff"),
    TXTFIELD_AUTOLOGOFF(null, "edit-form:editVehicle-autoLogoffInput"),
    
    TXTFIELD_VEHICLEID("Vehicle ID:", "edit-form:editVehicle-name"),
    DROPDOWN_STATUS("Status", "edit-form:editVehicle-status"),
    
    DHXDROP_TEAM("Team", "edit-form:editVehicle-groupID"),
    
    LABEL_ASSIGN_DRIVER("Assigned Driver:", "//span[@id='edit-form:driverID']/../../td[1]"),
    TEXT_ASSIGN_DRIVER(null, "edit-form:driverID"),
    LINK_ASSIGN_DRIVER(null, "editVehicle-chooseDriver"),
    
    TEXT_VALUE_PRODUCT("Product:", "//div[@class='spacer']/../table[3]/tbody/tr[1]/td[2]"),
    TEXT_VALUE_ASSIGNED_DEVICE("Assigned Device:", "//div[@class='spacer']/../table[3]/tbody/tr[2]/td[2]"),

    ;
    private String text, url;
    private String[] IDs;

    private AdminVehicleEdit(String url) {
        this.url = url;
    }

    private AdminVehicleEdit(String text, String... IDs) {
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
