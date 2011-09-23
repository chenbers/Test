package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminZonesEnum implements SeleniumEnums {
    
    DEFAULT_URL("/app/admin/zones"),
    
    ZONE_NAME("Zone:", "zones-form:zone"),
    EDIT("Edit", "zones-form:zonesEdit"),
    CLONE("Clone", "zones-form:zonesClone"),
    DELETE("Delete", "zonesSubmit"),
    ADD_ZONE("Add Zone", "zones-form:zonesAdd"),
    ADD_ALERT("Add Alert", "zones-form:zoneAlertAdd"),
    DOWNLOAD_LINK("Download:", "downloadForm:downloadIcon"),
    DOWNLOAD_DROP_DOWN(null, "downloadForm:downloadType"),
    TITLE("Admin - Zones", "//span[@class='zones']"),
    LAST_PUBLISHED(null, "publishMessage"),
    
    OPTION_HEADER("Option", "//thead[@class='rich-table-thead']/tr/th[1]/div"),
    SETTING_HEADER("Setting", "//thead[@class='rich-table-thead']/tr/th[2]/div"),
    
    OPTION_ENTRY(null, "//thead[@class='rich-table-thead']/../tbody/tr[###]/td[2]"),
    
    
    EDIT_ZONE_NAME("Zone:", "zones-form:zoneName"),
    EDIT_RESET("Reset", "zones-form:reset"),
    EDIT_SAVE_ZONE("Save Zone", "zones-form:zonesSave"),
    EDIT_CANCEL(cancel, "zones-form:zonesCancel"),
    EDIT_FIND_ADDRESS("Find Address:", "zones-form:zoneAddress"),
    EDIT_LOCATE("Locate", "zonesSearch"),
    
    EDIT_LONGITUDE(null, "lng"),
    EDIT_LATITUDE(null, "lat"),
    EDIT_LONGITUDE_LABEL("Longitude:", "//input[@id='lng']/../../td[1]"),
    EDIT_LATITUDE_LABEL("Latitude:", "//input[@id='lat']/../../td[3]"),
    
    EDIT_VEHICLE_TYPE("Vehicle Type:", "zones-form:zoneOptions:0:vehicleType"),
    EDIT_SPEED_LIMIT(null, "zones-form:zoneOptions:1:intEdit"),
    EDIT_SPEED_LIMIT_LABEL("Speed Limit:", "//table[@id='zones-form:zoneOptions:1:intEdit']/../../td[1]"),
    EDIT_SPEED_LIMIT_NOTE("Speed Limit 0 = Use Device Default"),
    
    EDIT_CAUTION_AREA("Caution Area:", "zones-form:zoneOptions:2:onOff"),
    EDIT_REPORT_ON_ARRIVAL_DEPARTURE("Report on Arrival/Departure:", "zones-form:zoneOptions:3:onOff"),
    EDIT_MONITOR_IDLE("Monitor Idle:", "zones-form:zoneOptions:4:onOff"),
    EDIT_SEAT_BELT_VIOLATION("Seat Belt Violation:", "zones-form:zoneOptions:5:onOffDevice"),
    EDIT_SPEEDING_VIOLATION("Speeding Violation:", "zones-form:zoneOptions:6:onOffDevice"),
    EDIT_DRIVER_ID_VIOLATION("Driver ID Violation:", "zones-form:zoneOptions:7:onOffDevice"),
    EDIT_MASTER_BUZZER("Master Buzzer:", "zones-form:zoneOptions:8:onOffDevice"),
    EDIT_HARD_TURN_EVENT("Hard Turn Event:", "zones-form:zoneOptions:9:onOffDevice"),
    EDIT_HARD_BRAKE_EVENT("Hard Brake Event:", "zones-form:zoneOptions:10:onOffDevice"),
    EDIT_HARD_DIP_BUMP_EVENT("Hard Dip/Bump Event:", "zones-form:zoneOptions:11:onOffDevice"),
    
    EDIT_MESSAGE_FIRST_LINE("GPS system limitations require that zones be created with a buffer zone to allow for potential satellite drift.", "zones-form:editMessage1"),
    EDIT_MESSAGE_SECOND_LINE("(Recommended buffer zone: 10 meters/32 feet).","zones-form:editMessage2"),
    
    EDIT_MINIMIZE_OPTIONS(null, "zones-form:editZoneOptions_header"), 
    
    ACTION_MESSAGE(null, "//span[@class='rich-messages-label']"),
    
    ;
    
    private String text, url;
    private String[] IDs;
    
    private AdminZonesEnum(String url){
        this.url = url;
    }
    private AdminZonesEnum(String text, String ...IDs){
        this.text=text;
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
