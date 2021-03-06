package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum ReportsDevicesEnum implements SeleniumEnums {
	DEFAULT_URL(appUrl + "/reports/devicesReport"),
    
	DEVICE_ID_SORT("Device ID", "devices-form:devices:deviceNameheader:sortDiv"),
	VEHICLE_SORT("Assigned Vehicle", "devices-form:devices:vehicleNameheader:sortDiv"),
	IMEI_SORT("IMEI", "devices-form:devices:imeiheader:sortDiv"),
	PHONE_SORT("Device Phone #", "devices-form:devices:phoneheader:sortDiv"),
	STATUS_NONSORT("Status", "devices-form:devices:statusheader:sortDiv"),
	
	DEVICE_ID_FILTER(null, "devices-form:devices:deviceNamefsp"),
	VEHICLE_FILTER(null, "devices-form:devices:vehicleNamefsp"),
	IMEI_FILTER(null, "devices-form:devices:imeifsp"),
	PHONE_FILTER(null, "devices-form:devices:phonefsp"),
		
	DEVICE_ID_VALUE(null, "devices-form:devices:###:deviceName"),
	VEHICLE_VALUE(null, "devices-form:devices:###:vehicleName"),
	IMEI_VALUE(null, "devices-form:devices:###:imei"),
	PHONE_VALUE(null, "devices-form:devices:###:phone"),
	STATUS_VALUE(null, "devices-form:devices:###:status"),
		
	
	;

    private String text, url;
    private String[] IDs;
    
    private ReportsDevicesEnum(String url){
    	this.url = url;
    }
    private ReportsDevicesEnum(String text, String ...IDs){
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
