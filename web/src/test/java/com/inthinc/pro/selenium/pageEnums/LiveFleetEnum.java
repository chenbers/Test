package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum LiveFleetEnum implements SeleniumEnums {

    DEFAULT_URL("app/liveFleet"),

    /* Main LiveFleet Page Elements */
    HEADER_BOX_DISPATCH("Dispatch", "//div[@id='wrapper']/table/tbody/tr/td[1]/div/div[1]/div/span[1]"),
    LINK_SORT_DISPATCH_BY_NUMBER("#", "//div[@id='dispatchForm:driversDataTable:positionheader:sortDiv']/span"),
    LINK_SORT_DISPATCH_BY_DRIVER("Driver", "//div[@id='dispatchForm:driversDataTable:driverheader:sortDiv']/span"),
    LINK_SORT_DISPATCH_BY_VEHICLE("Vehicle", "//div[@id='dispatchForm:driversDataTable:vehicleheader:sortDiv']/span"),
    LINK_SORT_DISPATCH_BY_GROUP(null, "//div[@id='dispatchForm:driversDataTable:groupheader:sortDiv']/span/img"),
    LINK_DISPATCH_DRIVER_NEED_INDEX("_VAR_DRIVER_NAME_", "dispatchForm:driversDataTable:###:liveFleet-driverPerformance"),
    LINK_DISPATCH_VEHICLE_NEED_INDEX("_VAR_VEHICLE_NAME_", "dispatchForm:driversDataTable:###:liveFleetsVehiclePerformance"),
    LINK_DISPATCH_ICON_NEED_INDEX("_VAR_GROUP_ICON_", "//tr[###]/td[4]/a/img"),
    LINK_DISPATCH_DRIVER_NEED_NAME(null, "//"),

    HEADER_BOX_LIVE_FLEET("Live Fleet", "//div[@id='defaultMessage']/h2"),
    LINK_LIVE_FLEET_REFRESH(null, "//img[@alt='Refresh']"),
    TEXTFIELD_LIVE_FLEET_FIND_ADDRESS(null, "liveFleetAddressTextBox"),
    DROPDOWN_LIVE_FLEET_NUM_NEAREST_VEHICLES(null, "countComboBox"),
    BUTTON_LIVE_FLEET_LOCATE("Locate", "liveFleetSearch"),
    LINK_MAP_BUBBLE_DEFAULT_CHANGE_VIEW("change", "liveFleetMapForm:liveFleetGo"),
    TEXT_MAP_BUBBLE_DEFAULT_MESSAGE("This is the default view for your division.", "defaultMessage"),
    TEXT_MAP_BUBBLE_HEADER("Live Fleet", "//div[@id='defaultMessage']/h2"),
    // MAP_BUBBLE_VEHICLE_HEADER(null),
    LABEL_MAP_BUBBLE_VEHICLE_PHONE1("Phone 1:", "//div[10]/div/div[1]/div/div/table/tbody/tr[1]/td[1]"),
    LABEL_MAP_BUBBLE_VEHICLE_PHONE2("Phone 2:", "//div[10]/div/div[1]/div/div/table/tbody/tr[2]/td[1]"),
    LABEL_MAP_BUBBLE_VEHICLE_DRIVER("Driver:", "//div[10]/div/div[1]/div/div/table/tbody/tr[3]/td[1]"),
    LABEL_MAP_BUBBLE_VEHICLE_DEVICE("Device:", "//div[10]/div/div[1]/div/div/table/tbody/tr[4]/td[1]"),
    LABEL_MAP_BUBBLE_VEHICLE_UPDATED("Updated:", "//div[10]/div/div[1]/div/div/table/tbody/tr[5]/td[1]"),
    LABEL_MAP_BUBBLE_VEHICLE_LOCATION("Locateion", "//div[10]/div/div[1]/div/div/table/tbody/tr[6]/td[1]"),
    LABEL_MAP_BUBBLE_VEHICLE_DISTANCE_TO_ADDRESS("Distance to address:", "//div[10]/div/div[1]/div/div/table/tbody/tr[7]/td[1]"),
    VALUE_MAP_BUBBLE_VEHICLE_PHONE1("Phone 1:", "//div[10]/div/div[1]/div/div/table/tbody/tr[1]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_PHONE2("Phone 2:", "//div[10]/div/div[1]/div/div/table/tbody/tr[2]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_DRIVER("Driver:", "//div[10]/div/div[1]/div/div/table/tbody/tr[3]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_DEVICE("Device:", "//div[10]/div/div[1]/div/div/table/tbody/tr[4]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_UPDATED("Updated:", "//div[10]/div/div[1]/div/div/table/tbody/tr[5]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_LOCATION("Location", "//div[10]/div/div[1]/div/div/table/tbody/tr[6]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_DISTANCE_TO_ADDRESS("Distance to address:", "//div[10]/div/div[1]/div/div/table/tbody/tr[7]/td[2]"),
    IMG_LINK_MAP_ICONS_NEED_INDEX(null, "mtgt_unnamed_###"),

    HEADER_BOX_FLEET_LEGEND("Fleet Legend", "//div[@id='wrapper']/table/tbody/tr/td[3]/div[3]/div[1]/div/span[1]"),
    LINK_BOX_FLEET_LEGEND_GROUP_NEED_INDEX(null, "liveFleetLegend:_INDEX_:liveFleetsDashboard2"),

    ;

    private String text, url;
    private String[] IDs;
    
    private LiveFleetEnum(String url){
    	this.url = url;
    }
    private LiveFleetEnum(String text, String ...IDs){
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
