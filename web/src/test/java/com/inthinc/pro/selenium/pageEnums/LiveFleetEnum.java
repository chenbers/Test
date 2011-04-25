package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

// Enums have format NAME( Text, ID, X-Path, X-Path-Alternate )

public enum LiveFleetEnum  implements SeleniumEnums {
	
    DEFAULT_URL("app/liveFleet"),
    
	/* Main LiveFleet Page Elements*/
	HEADER_BOX_DISPATCH("Dispatch",null,"//div[@id=wrapper]/table/tbody/tr[1]/td[1]/div/div/span",null ),
	LINK_SORT_DISPATCH_BY_NUMBER("#", null, "//div[@id='dispatchForm:driversDataTable:positionheader:sortDiv']/span", null),
	LINK_SORT_DISPATCH_BY_DRIVER("Driver", null, "//div[@id='dispatchForm:driversDataTable:driverheader:sortDiv']/span",  null),
	LINK_SORT_DISPATCH_BY_VEHICLE("Vehicle", null, "//div[@id='dispatchForm:driversDataTable:vehicleheader:sortDiv']/span", null),
	LINK_SORT_DISPATCH_BY_GROUP(null, null, "//div[@id='dispatchForm:driversDataTable:groupheader:sortDiv']/span/img", null),
	LINK_DISPATCH_DRIVER_NEED_INDEX("_VAR_DRIVER_NAME_", null, "dispatchForm:driversDataTable:_INDEX_:liveFleet-driverPerformance", null),
	LINK_DISPATCH_VEHICLE_NEED_INDEX("_VAR_VEHICLE_NAME_", null, "dispatchForm:driversDataTable:_INDEX_:liveFleetsVehiclePerformance", null),
	LINK_DISPATCH_ICON_NEED_INDEX("_VAR_GROUP_ICON_", null, "//tr[_INDEX_]/td[4]/a/img", null), //TODO: jwimmer: find the best way to pull/insert indexes/variables into xpaths
	
	
	HEADER_BOX_LIVE_FLEET("Live Fleet", null, "//div[@id='defaultMessage']/h2", null),
	LINK_LIVE_FLEET_REFRESH(null, null, "//img[@alt='Refresh']", null),
	TEXTFIELD_LIVE_FLEET_FIND_ADDRESS(null, "liveFleetAddressTextBox", null, null),
	DROPDOWN_LIVE_FLEET_NUM_NEAREST_VEHICLES(null, "countComboBox", null, null),
	BUTTON_LIVE_FLEET_LOCATE("Locate", "liveFleetSearch", null, null),
	LINK_MAP_BUBBLE_DEFAULT_CHANGE_VIEW("change", "liveFleetMapForm:liveFleetGo", null, null),
	TEXT_MAP_BUBBLE_DEFAULT_MESSAGE("This is the default view for your division.", "defaultMessage", null, null ),
	TEXT_MAP_BUBBLE_HEADER("Live Fleet", null, "//div[@id='defaultMessage']/h2", null),
	MAP_BUBBLE_VEHICLE_HEADER(null, null, null, null),
	LABEL_MAP_BUBBLE_VEHICLE_PHONE1("Phone 1:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[1]/td[1]", null),
	LABEL_MAP_BUBBLE_VEHICLE_PHONE2("Phone 2:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[2]/td[1]", null),
	LABEL_MAP_BUBBLE_VEHICLE_DRIVER("Driver:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[3]/td[1]", null),
	LABEL_MAP_BUBBLE_VEHICLE_DEVICE("Device:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[4]/td[1]", null),
	LABEL_MAP_BUBBLE_VEHICLE_UPDATED("Updated:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[5]/td[1]", null),
	LABEL_MAP_BUBBLE_VEHICLE_LOCATION("Locateion", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[6]/td[1]", null),
	LABEL_MAP_BUBBLE_VEHICLE_DISTANCE_TO_ADDRESS("Distance to address:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[7]/td[1]", null),
    VALUE_MAP_BUBBLE_VEHICLE_PHONE1("Phone 1:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[1]/td[2]", null),
    VALUE_MAP_BUBBLE_VEHICLE_PHONE2("Phone 2:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[2]/td[2]", null),
    VALUE_MAP_BUBBLE_VEHICLE_DRIVER("Driver:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[3]/td[2]", null),
    VALUE_MAP_BUBBLE_VEHICLE_DEVICE("Device:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[4]/td[2]", null),
    VALUE_MAP_BUBBLE_VEHICLE_UPDATED("Updated:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[5]/td[2]", null),
    VALUE_MAP_BUBBLE_VEHICLE_LOCATION("Location", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[6]/td[2]", null),
    VALUE_MAP_BUBBLE_VEHICLE_DISTANCE_TO_ADDRESS("Distance to address:", null, "//div[10]/div/div[1]/div/div/table/tbody/tr[7]/td[2]", null),
	IMG_LINK_MAP_ICONS_NEED_INDEX(null, null, "mtgt_unnamed__INDEX_", null),
    
    HEADER_BOX_FLEET_LEGEND("Fleet Legend", null, "//div[@id='wrapper']/table/tbody/tr/td[3]/div[3]/div[1]/div/span[1]", null),
    LINK_BOX_FLEET_LEGEND_GROUP_NEED_INDEX(null, "liveFleetLegend:_INDEX_:liveFleetsDashboard2", null, null)
	
	;
	
	
	private String text, ID, xpath, xpath_alt, url;
	
	private LiveFleetEnum( String text, String ID, String xpath, String xpath_alt) {
		this.text=text;
		this.ID=ID;
		this.xpath=xpath;
		this.xpath_alt=xpath_alt;
		this.url=null;
	}
	
	private LiveFleetEnum(String url){
	    this.url = url;
	    this.text=null;
	    this.ID=null;
	    this.xpath=null;
	    this.xpath_alt=null;
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
