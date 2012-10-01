package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AdminVehiclesEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/admin/vehicles"),
    
    TITLE("Admin - Vehicles", Xpath.start().span(Id.clazz("admin")).toString()),
    
    
    PRODUCT_DHX(null, "admin-table-form:vehiclesTable:editVehicle-productChoice"),

    ZONE_TYPE_DHX(null, "admin-table-form:vehiclesTable:editVehicle-vehicleTypeChoice"),
    
    STATUS_DHX(null, "admin-table-form:vehiclesTable:editVehicle-statusChoice"),
    
    DEVICE_LINK("Driver", "//div[@id='admin-table-form:vehiclesTable:deviceheader:sortDiv']"),
    EDIT_LINK("edit", "admin-table-form:vehiclesTable:0:edit"),
    SEARCH_VEHICLE_FIELD("Search", "admin-table-form:vehiclesTable-filterTable"),
    SEARCH_VEHICLE_BUTTON("Search", "admin-table-form:vehiclesTable-adminTableSearch")
    ;

    private String text, url;
    private String[] IDs;
    
    private AdminVehiclesEnum(String url){
    	this.url = url;
    }
    private AdminVehiclesEnum(String text, String ...IDs){
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
