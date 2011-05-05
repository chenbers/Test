package com.inthinc.pro.selenium.pageEnums;



import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Xpath;



// Enums have format NAME( Text, ID, X-Path, X-Path-Alternate )

public enum LiveFleetEnum  implements SeleniumEnums {
	
    DEFAULT_URL("app/liveFleet"),
    
	/* Main LiveFleet Page Elements*/
	HEADER_BOX_DISPATCH("Dispatch",null,"//div[@id=wrapper]/table/tbody/tr[1]/td[1]/div/div/span"),
	LINK_SORT_DISPATCH_BY_NUMBER("#", null, "//div[@id='dispatchForm:driversDataTable:positionheader:sortDiv']/span"),
	LINK_SORT_DISPATCH_BY_DRIVER("Driver", null, "//div[@id='dispatchForm:driversDataTable:driverheader:sortDiv']/span"),
	LINK_SORT_DISPATCH_BY_VEHICLE("Vehicle", null, "//div[@id='dispatchForm:driversDataTable:vehicleheader:sortDiv']/span"),
	LINK_SORT_DISPATCH_BY_GROUP(null, null, "//div[@id='dispatchForm:driversDataTable:groupheader:sortDiv']/span/img"),
	LINK_DISPATCH_DRIVER_NEED_INDEX("_VAR_DRIVER_NAME_", null, "dispatchForm:driversDataTable:###:liveFleet-driverPerformance"),
	LINK_DISPATCH_VEHICLE_NEED_INDEX("_VAR_VEHICLE_NAME_", null, "dispatchForm:driversDataTable:###:liveFleetsVehiclePerformance"),
	LINK_DISPATCH_ICON_NEED_INDEX("_VAR_GROUP_ICON_", null, "//tr[###]/td[4]/a/img"),
	LINK_DISPATCH_DRIVER_NEED_NAME(null, null, "//"),
	
	
	HEADER_BOX_LIVE_FLEET("Live Fleet", null, "//div[@id='defaultMessage']/h2"),
	LINK_LIVE_FLEET_REFRESH(null, null, "//img[@alt='Refresh']"),
	TEXTFIELD_LIVE_FLEET_FIND_ADDRESS(null, "liveFleetAddressTextBox"),
	DROPDOWN_LIVE_FLEET_NUM_NEAREST_VEHICLES(null, "countComboBox"),
	BUTTON_LIVE_FLEET_LOCATE("Locate", "liveFleetSearch"),
	LINK_MAP_BUBBLE_DEFAULT_CHANGE_VIEW("change", "liveFleetMapForm:liveFleetGo"),
	TEXT_MAP_BUBBLE_DEFAULT_MESSAGE("This is the default view for your division.", "defaultMessage"),
	TEXT_MAP_BUBBLE_HEADER("Live Fleet", null, "//div[@id='defaultMessage']/h2"),
//	MAP_BUBBLE_VEHICLE_HEADER(null),
	LABEL_MAP_BUBBLE_VEHICLE_PHONE1("Phone 1:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[1]/td[1]"),
	LABEL_MAP_BUBBLE_VEHICLE_PHONE2("Phone 2:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[2]/td[1]"),
	LABEL_MAP_BUBBLE_VEHICLE_DRIVER("Driver:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[3]/td[1]"),
	LABEL_MAP_BUBBLE_VEHICLE_DEVICE("Device:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[4]/td[1]"),
	LABEL_MAP_BUBBLE_VEHICLE_UPDATED("Updated:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[5]/td[1]"),
	LABEL_MAP_BUBBLE_VEHICLE_LOCATION("Locateion", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[6]/td[1]"),
	LABEL_MAP_BUBBLE_VEHICLE_DISTANCE_TO_ADDRESS("Distance to address:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[7]/td[1]"),
    VALUE_MAP_BUBBLE_VEHICLE_PHONE1("Phone 1:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[1]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_PHONE2("Phone 2:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[2]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_DRIVER("Driver:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[3]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_DEVICE("Device:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[4]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_UPDATED("Updated:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[5]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_LOCATION("Location", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[6]/td[2]"),
    VALUE_MAP_BUBBLE_VEHICLE_DISTANCE_TO_ADDRESS("Distance to address:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[7]/td[2]"),
	IMG_LINK_MAP_ICONS_NEED_INDEX(null, null, "mtgt_unnamed_###"),
    
    HEADER_BOX_FLEET_LEGEND("Fleet Legend", null, "//div[@id='wrapper']/table/tbody/tr/td[3]/div[3]/div[1]/div/span[1]"),
    LINK_BOX_FLEET_LEGEND_GROUP_NEED_INDEX(null, "liveFleetLegend:_INDEX_:liveFleetsDashboard2"),
    
    
    
    
	
	;
	
	
	private String text, ID, xpath, xpath_alt, url;
	
	private LiveFleetEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private LiveFleetEnum(String url) {
        this.url = url;
    }

    private LiveFleetEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private LiveFleetEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private LiveFleetEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private LiveFleetEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private LiveFleetEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private LiveFleetEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private LiveFleetEnum(Xpath xpath) {
        this(null, null, xpath.toString(), null);
    }
	
	public String getURL(){
	    return url;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text=text;
	}

	public String getID() {
		return ID;
	}

	public String getXpath() {
		return xpath;
	}

	public String getXpath_alt() {
		return xpath_alt;
	}
	
	public String getError() {
	    return this.name();
	}
	


}
