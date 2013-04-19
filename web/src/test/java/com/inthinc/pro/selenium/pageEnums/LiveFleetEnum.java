package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum LiveFleetEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/liveFleet"),

    /* Main LiveFleet Page Elements */
    HEADER_BOX_DISPATCH("Dispatch", "//div[@id='wrapper']/table/tbody/tr/td[1]/div/div[1]/div/span[1]"),
    LINK_SORT_DISPATCH_BY_NUMBER("#", "dispatchForm:driversDataTable:positionheader:sortDiv"),
    LINK_SORT_DISPATCH_BY_DRIVER("Driver", "dispatchForm:driversDataTable:driverheader:sortDiv"),
    LINK_SORT_DISPATCH_BY_VEHICLE("Vehicle", "dispatchForm:driversDataTable:vehicleheader:sortDiv"),
    LINK_SORT_DISPATCH_BY_GROUP(null, "dispatchForm:driversDataTable:groupheader:sortDiv"),
    
    LINK_DISPATCH_DRIVER_NEED_INDEX(null, "dispatchForm:driversDataTable:###:driver"),
    LINK_DISPATCH_VEHICLE_NEED_INDEX(null, "dispatchForm:driversDataTable:###:vehicle"),
    LINK_DISPATCH_GROUP_ICON_NEED_INDEX(null, "dispatchForm:driversDataTable:###:group"),
    
    TEXT_POSITION_NEED_INDEX(null, "dispatchForm:driversDataTable:###:position"),
    
    BUTTON_REFRESH(null, "//img[@title='Refresh']"),
    
    MAP_MARKER(null, "//div[@class='gmnoprint' and @title]"),

    HEADER_BOX_LIVE_FLEET("Live Fleet", "//div[@id='defaultMessage']/h2"),
    LINK_LIVE_FLEET_REFRESH(null, "//img[@alt='Refresh']"),
    TEXTFIELD_LIVE_FLEET_FIND_ADDRESS(null, "liveFleetAddressTextBox"),
    DROPDOWN_LIVE_FLEET_NUM_NEAREST_VEHICLES(null, "countComboBox"),
    BUTTON_LIVE_FLEET_LOCATE("Locate", "liveFleetSearch"),
    LINK_MAP_BUBBLE_DEFAULT_CHANGE_VIEW("change", "liveFleetMapForm:liveFleetGo"),
    TEXT_MAP_BUBBLE_DEFAULT_MESSAGE("This is the default view for your division.", "defaultMessage"),
    TEXT_MAP_BUBBLE_HEADER("Live Fleet", "//div[@id='defaultMessage']/h2"),
    
    // MAP_BUBBLE_VEHICLE_HEADER(null),
    VALUE_MAP_BUBBLE_VEHICLE_PHONE1("Phone 1:", "//div[@id='vehicleBubble']/table/tbody/tr[1]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_PHONE2("Phone 2:", "//div[@id='vehicleBubble']/table/tbody/tr[2]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_DRIVER("Driver:", "//div[@id='vehicleBubble']/table/tbody/tr[3]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_DEVICE("Device:", "//div[@id='vehicleBubble']/table/tbody/tr[4]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_UPDATED("Updated:", "//div[@id='vehicleBubble']/table/tbody/tr[5]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_LOCATION("Location", "//div[@id='vehicleBubble']/table/tbody/tr[6]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_DISTANCE_TO_ADDRESS("Distance to address: XXX.YYY UNITS", "//div[@id='vehicleBubble']/table/tfoot/tr/td"),
    IMG_LINK_MAP_ICONS_NEED_INDEX(null, "mtgt_unnamed_###"),

    HEADER_BOX_FLEET_LEGEND("Fleet Legend", "//div[@id='wrapper']/table/tbody/tr/td[3]/div[3]/div[1]/div/span[1]"),
    LINK_BOX_FLEET_LEGEND_GROUP_NEED_INDEX(null, "liveFleetLegend:###:liveFleetsDashboard2"),
    
    PAGER(null, "dispatchForm:driverScroller_table"), 
    
    VALUE_MAP_BUBBLE_VEHICLE_NAME(null, "liveFleetVehicleBubbles:liveFleet-vehiclePerformance"),
    
    
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
