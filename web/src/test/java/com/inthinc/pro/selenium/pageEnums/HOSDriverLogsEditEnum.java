package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum HOSDriverLogsEditEnum implements SeleniumEnums {
    
    TITLE(null, "//span[@class='admin'"),
    HEADER("HOS Log Information", "//div[@class='add_section_title']"),
    
    SAVE_TOP(save, "edit-form:editHosLogSave1"),
    SAVE_BOTTOM(save, "edit-form:editHosLogSave2"),
    
    CANCEL_TOP(cancel, "edit-form:editHosLogCancel1"),
    CANCEL_BOTTOM(cancel, "edit-form:editHosLogCancel2"),
    
    DATE("Date:", "edit-form:editHosLog_dateTimeInputDate"),
    TIME_CHANGER("Time:", "edit-form:hosEdit_***"),
    STATUS("Status", "edit-form:editHosLog_status"),
    TRAILER("Trailer:", "edit-form:editHosLog_trailer"),
    SERVICE("Service:", "edit-form:editHosLog_service"),
    DRIVER("Driver:", "edit-form:editHosLog_driver"),
    VEHICLE("Vehicle:", "edit-form:editHosLog_vehicle"),
    LOCATION("Location:", "edit-form:editHosLog_location"),
    CITY_STATE("City, State/Province", "//div[@class='add_section_title']/../table/tbody/tr[9]/td[2]/span"),
    DOT("DOT:", "edit-form:editHosLog_dot"),
    
    
    ;
    private String text, url;
    private String[] IDs;
    
    private HOSDriverLogsEditEnum(String url){
        this.url = url;
    }
    private HOSDriverLogsEditEnum(String text, String ...IDs){
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
