package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum AdminDevicesDetailsEnum implements SeleniumEnums {
    
    BACK_TO_DEVICES("< Back to Devices", "deviceForm:deviceBack1"),
    EDIT_BUTTON("Edit", "deviceForm:deviceEdit1"),
    
    TITLE("Admin - UNITID Details", "//span[@class='admin']"),

    PRODUCT_TYPE("Product:", "//ul[@id='grid_nav']/../table/tbody/tr[1]/td[1]/table/tbody/tr[1]/td[2]"),
    DEVICE_ID("Device ID:", "//ul[@id='grid_nav']/../table/tbody/tr[1]/td[1]/table/tbody/tr[2]/td[2]"),
    SERIAL_NUMBER("Serial Number:", "//ul[@id='grid_nav']/../table/tbody/tr[1]/td[1]/table/tbody/tr[3]/td[2]"),
    IMEI("IMEI:", "//ul[@id='grid_nav']/../table/tbody/tr[1]/td[1]/table/tbody/tr[4]/td[2]"),
    SIM_CARD("SIM Card:", "//ul[@id='grid_nav']/../table/tbody/tr[1]/td[1]/table/tbody/tr[5]/td[2]"),
    DEVICE_PHONE("Device Phone:", "//ul[@id='grid_nav']/../table/tbody/tr[1]/td[1]/table/tbody/tr[6]/td[2]"),
    
    MCM_ID("MCM ID:", "//ul[@id='grid_nav']/../table/tbody/tr[1]/td[1]/table/tbody/tr[4]/td[2]"),
    ALTERNATE_IMEI("Alternate IMEI:", "//ul[@id='grid_nav']/../table/tbody/tr[1]/td[1]/table/tbody/tr[5]/td[2]"),
    WITNESS_VERSION("Witness Version:", "//ul[@id='grid_nav']/../table/tbody/tr[1]/td[1]/table/tbody/tr[6]/td[2]"),
    FIRMWARE_VERSION_DATE("Firmware Version Date:", "//ul[@id='grid_nav']/../table/tbody/tr[1]/td[1]/table/tbody/tr[7]/td[2]"),
    SAT_IMEI("Sat IMEI:", "//ul[@id='grid_nav']/../table/tbody/tr[1]/td[1]/table/tbody/tr[8]/td[2]"),
    
    STATUS("Status:", "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[1]/tbody/tr[1]/td[2]"),
    ACTIVATED("Activated:", "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[1]/tbody/tr[2]/td[2]"),
    
    ASSIGNED_VEHICLE("Assigned Vehicle:", "//ul[@id='grid_nav']/../table/tbody/tr/td[3]/table[2]/tbody/tr[1]/td[2]"),
    
    
    DEVICE_INFORMATION("Device Information", "//td[1]/div[@class='add_section_title']"),
    DEVICE_PROFILE("Device Profile", "//td[3]/div[@class='add_section_title'][1]"),
    DEVICE_ASSIGNMENT("Device Assignment", "//td[3]/div[@class='add_section_title'][2]"),
    
    DETAILS_TAB("Details", "deviceForm:details_lbl"),
    ;

    private String text, url;
    private String[] IDs;
    
    private AdminDevicesDetailsEnum(String url){
        this.url = url;
    }
    private AdminDevicesDetailsEnum(String text, String ...IDs){
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
