package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.Id;
import com.inthinc.pro.automation.utils.Xpath;

public enum AdminVehiclesEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/admin/vehicles"),
    
    TITLE("Admin - Vehicles", Xpath.start().span(Id.clazz("admin")).toString()),
    VEH_TITLE("Admin - Vehicles", "//div/div/div[@class='panel_title']/span[@class='admin']"),
    
//    PRODUCT_DHX(null, "admin-table-form:vehiclesTable:editVehicle-productChoice"),
//  ZONE_TYPE_DHX(null, "admin-table-form:vehiclesTable:editVehicle-vehicleTypeChoice"),
//  STATUS_DHX(null, "admin-table-form:vehiclesTable:editVehicle-statusChoice"),
//  EDIT_LINK("edit", "admin-table-form:vehiclesTable:0:edit"),
    PRODUCT("Product", "//select[@id='vehiclesTable-form:vehiclesTable:editVehicle-productChoice']"),
    PRODUCT2(null, "//select[@id='vehiclesTable-form:vehiclesTable:editVehicle-productChoice']"),
    ZONE_TYPE(null, "vehiclesTable-form:vehiclesTable:editVehicle-vehicleTypeChoice"),
    STATUS(null, "vehiclesTable-form:vehiclesTable:editVehicle-statusChoice"),
    DEVICE_LINK("Driver", "//div[@id='admin-table-form:vehiclesTable:deviceheader:sortDiv']"),
    EDIT_LINK("edit", "//*[@id='vehicles-table-form:vehiclesTable:0:edit']"),
    EDIT("edit", "//a[@id='vehiclesTable-form:vehiclesTable:###:edit']"),
    SEARCH_VEHICLE_FIELD("Search", "admin-table-form:vehiclesTable-filterTable"),
    SEARCH_VEHICLE_BUTTON("Search", "admin-table-form:vehiclesTable-adminTableSearch"),
    REFRESH_LINK(null, "//div/div/div[@class='panel_title']/span[@class='panel_links']/form[@action='/tiwipro/app/admin/vehicles']"),
    
    VEH_BATCH_EDIT("Batch Edit", "vehiclesTable-form:vehiclesTable-adminTableEdit"),
    VEH_EDIT_COLUMNS("Edit Columns", "//a[@id='vehiclesTable-form:vehiclesTable-adminTableEditColumns']"),
    VEH_DELETE_BUTTON("Delete", "//button[@id='vehiclesTable-form:vehiclesTable-adminTableDelete']"),
    VEH_ID(null, "//a[@id='vehiclesTable-form:vehiclesTable:###:vehiclesTableName']"),
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
