package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminDevicesEnum implements SeleniumEnums {
    
    DEFAULT_URL(appUrl + "/admin/devices"),
    
    PAGE_SCROLLER(null, "admin-table-form:devicesTable-dataTableScroller_table"),
    
    TITLE("Admin - Devices", "//span[@class='admin']"),
    
    PRODUCT_DHX(null, "admin-table-form:devicesTable:editDevice-productChoice"),
    STATUS_DHX(null, "admin-table-form:devicesTable:editDevice-statusChoice"),
    
    VEHICLEID_LINK("Vehicle ID", "//div[@id='admin-table-form:devicesTable:vehicleheader:sortDiv']"),
    EDIT_LINK("edit", "//a[@id='admin-table-form:devicesTable:0:edit']")
    ;

    private String text, url;
    private String[] IDs;
    
    private AdminDevicesEnum(String url){
        this.url = url;
    }
    private AdminDevicesEnum(String text, String ...IDs){
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
