package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminDevicesEditEnum implements SeleniumEnums {
    
    URL(appUrl + "/admin/editDevice"),
    
    TITLE("Admin - Edit Device", "//span[@class='admin']"),
    DETAILS_TAB("Details", "edit-form:details_lbl"),
    
    INFORMATION_HEADER("Device Information", "//tr[1]/td[1]/div[@class='add_section_title']"),
    ASSIGNMENT_HEADER("Device Assignment", "//tr[2]/td[1]/div[@class='add_section_title']"),
    PROFILE_HEADER("Device Profile", "//td[3]/div[@class='add_section_title']"),
    
    PRODUCT("Product:", "//div[@class='add_section_title']/../../td[1]/table/tbody/tr[1]/td[2]"),
    DEVICE_ID("Device ID:", "//div[@class='add_section_title']/../../td[1]/table/tbody/tr[2]/td[2]"),
    SERIAL_NUMBER("Serial Number:", "//div[@class='add_section_title']/../../td[1]/table/tbody/tr[3]/td[2]"),
    IMEI("IMEI:", "//div[@class='add_section_title']/../../td[1]/table/tbody/tr[4]/td[2]"),
    SIM_CARD("SIM Card:", "//div[@class='add_section_title']/../../td[1]/table/tbody/tr[5]/td[2]"),
    DEVICE_PHONE("Device Phone:", "//div[@class='add_section_title']/../../td[1]/table/tbody/tr[6]/td[2]"),
    
    MCM_ID("MCM ID:", "//div[@class='add_section_title']/../../td[1]/table/tbody/tr[4]/td[2]"),
    ALTERNATE_IMEI("Alternate IMEI:", "//div[@class='add_section_title']/../../td[1]/table/tbody/tr[5]/td[2]"),
    WITNESS_VERSION("Witness Version:", "//div[@class='add_section_title']/../../td[1]/table/tbody/tr[6]/td[2]"),
    FIRMWARE_VERSION_DATE("Firmware Version Date:", "//div[@class='add_section_title']/../../td[1]/table/tbody/tr[7]/td[2]"),
    SAT_IMEI("Sat IMEI:", "//div[@class='add_section_title']/../../td[1]/table/tbody/tr[8]/td[2]"),
    
    ASSIGNED_VEHICLE_LABEL("Assigned Vehicle", "//span[@id='edit-form:chosenVehicleShow']/../../td[1]"),
    ASSIGNED_VEHICLE("Assigned Vehicle:", "edit-form:chosenVehicleShow"),
    
    STATUS("Status:", "edit-form:editDevice-status"),
    
    TOP_SAVE(save, "edit-form:editDeviceSave1"),
    BOTTOM_SAVE(save, "edit-form:editDeviceSave2"),
    TOP_CANCEL(cancel, "edit-form:editDeviceCancel1"),
    BOTTOM_CANCEL(cancel, "edit-form:editDeviceCancel2"),
    
    SHOW_HIDE_VEHICLES_LINK("Show vehicles for assignment", "//a[@id='edit-form:j_id318']"),
    ASSIGNED_LINK("Assigned", "//div[@id='edit-form:editDevice-chooseVehicleTable:vehicleAssignedheader:sortDiv']"),
    
    VEHICLE_TABLE_STATUS_FILTER(null, "edit-form:editDevice-chooseVehicleTable:editVehicle-statusChoice"),
    VEHICLE_TABLE_PRODUCT_FILTER(null, "edit-form:editDevice-chooseVehicleTable:chooseVehicle-productChoice"),
    
    VEHICLE_TABLE_SELECT(null, "edit-form:editDevice-chooseVehicleTable:0:assignVehicle"),
    VEHICLE_TABLE_VEHICLE_ID("Vehicle ID", "edit-form:editDevice-chooseVehicleTable:###:name"),
    VEHICLE_TABLE_DRIVER("Driver", "edit-form:editDevice-chooseVehicleTable:###:fullName"),
    VEHICLE_TABLE_TEAM("Team", "edit-form:editDevice-chooseVehicleTable:###:group"),
    VEHICLE_TABLE_STATUS("Status", "edit-form:editDevice-chooseVehicleTable:###:vehicleStatus"),
    VEHICLE_TABLE_ASSIGNED("Assigned", "edit-form:editDevice-chooseVehicleTable:###:vehicleAssigned"),
    VEHICLE_TABLE_PRODUCT("Product", "edit-form:editDevice-chooseVehicleTable:###:productVersion"),
    VEHICLE_TABLE_DEVICE("Device", "edit-form:editDevice-chooseVehicleTable:###:device"),
    VEHICLE_TABLE_VIN("VIN", "edit-form:editDevice-chooseVehicleTable:###:vin"),
    VEHICLE_TABLE_YEAR("Year", "edit-form:editDevice-chooseVehicleTable:###:year"),
    VEHICLE_TABLE_MAKE("Make", "edit-form:editDevice-chooseVehicleTable:###:make"),
    VEHICLE_TABLE_MODEL("Model", "edit-form:editDevice-chooseVehicleTable:###:model"),
    
    ;

    private String text, url;
    private String[] IDs;
    
    private AdminDevicesEditEnum(String url){
        this.url = url;
    }
    private AdminDevicesEditEnum(String text, String ...IDs){
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