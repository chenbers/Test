package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum FuelStopsEditEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/fuelStopEdit"),
    
    SAVE_TOP(save, "edit-form:editfuelStopSave1"),
    SAVE_BOTTOM(save, "edit-form:editfuelStopSave2"),
    CANCEL_TOP(cancel, "edit-form:editfuelStopCancel1"),
    CANCEL_BOTTOM(cancel, "edit-form:editfuelStopCancel2"),
    TITLE("ADD/EDIT Fuel Stop", "//span[@class='admin']"),
    HEADER("Fuel Stop Information", "//div[@class='add_section_title']"),
    DATE_BOX("Date:", "edit-form:editHosLog_dateTimeInputDate"),
    DATE_ERROR(null, "//input[@id='edit-form:editfuelStop_dateTimeInputDate']/../../span[1]/span"),
    
    TRAILER_FIELD("Trailer:", "edit-form:editfuelStop_trailer"),
    
    
    BOTH_FUEL_FIELD(null, "edit-form:editFuelStop_bothFuelFields"),
    VEHICLE_FUEL_FIELD("Vehicle Fuel:", "edit-form:editfuelStop_truckGallons"),
    TRAILER_FUEL_FIELD("Trailer Fuel:", "edit-form:editfuelStop_trailerGallons"),
    
    
    DRIVER_DROP_DOWN("Driver:", "edit-form:editfuelStop_driver"),
    
    LOCATION(null, "edit-form:editfuelStop_location"),
    LOCATION_LABEL("Location:", "//span[@id='edit-form:editfuelStop_location']/../../td[1]"),
    TIME_MESSAGE("Fuel stops more than 25 days old will not be included in the IFTA aggregation", "//div[@class='add_section_title']/../table/tbody/tr[2]/td[2]/span"),

    
    VEHICLE("Vehicle:", "//button[@id='edit-form:editfuelStopCancel1']/../../../table/tbody/tr/td/table/tbody/tr[1]/td[2]"),
    
    
    MASTER_ERROR(null, "//ul[@id='grid_nav']/../dl/dt[@class='error']"),
    
    DELETE_TOP(delete, "edit-form:editfuelStopDelete1"),
    DELETE_BOTTOM(delete, "edit-form:editfuelStopDelete2"),
    
    TIME_CHANGER(null, "edit-form:fuelStopEdit_***"),
    ;
    private String text, url;
    private String[] IDs;

    private FuelStopsEditEnum(String url) {
        this.url = url;
    }

    private FuelStopsEditEnum(String text, String... IDs) {
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
