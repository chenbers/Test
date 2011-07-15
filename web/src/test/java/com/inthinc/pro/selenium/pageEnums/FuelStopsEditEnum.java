package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum FuelStopsEditEnum implements SeleniumEnums {

    SAVE_TOP(save, "edit-form:editfuelStopSave1"),
    SAVE_BOTTOM(save, "edit-form:editfuelStopSave2"),
    CANCEL_TOP(cancel, "edit-form:editfuelStopCancel1"),
    CANCEL_BOTTOM(cancel, "edit-form:editfuelStopCancel2"),
    TITLE("Add Fuel Stop", "//span[@class='admin']"),
    HEADER("Fuel Stop Information", "//div[@class='add_section_title']"),
    DATE_BOX(null, "edit-form:editfuelStop_dateTimeInputDate"),
    DATE_BOX_AFTER(null, "//span[@id='edit-form:editfuelStop_dateTimePopup']/../../td[2]/text()"),
    TRAILER_FIELD("Trailer:", "edit-form:editfuelStop_trailer"),
    VEHICLE_FUEL_FIELD("Vehicle Fuel:", "edit-form:editfuelStop_truckGallons"),
    TRAILER_FUEL_FIELD("Trailer Fuel:", "edit-form:editfuelStop_trailerGallons"),
    DRIVER_DROP_DOWN("Driver:", "edit-form:editfuelStop_driver"),
    VEHICLE_DROP_DOWN("Vehicle:", "edit-form:editfuelStop_vehicle"),
    LOCATION(null, "edit-form:editfuelStop_location"),
    LOCATION_LABEL("Location:", "//span[@id='edit-form:editfuelStop_location']/../../td[1]"),
    TIME_MESSAGE("Fuel stops more than 25 days old will not be included in the IFTA aggregation", "//div[@class='add_section_title']/../table/tbody/tr[2]/td[2]/span"),

    DELETE_TOP(delete, "edit-form:editfuelStopDelete1"),
    DELETE_BOTTOM(delete, "edit-form:editfuelStopDelete2"),
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
