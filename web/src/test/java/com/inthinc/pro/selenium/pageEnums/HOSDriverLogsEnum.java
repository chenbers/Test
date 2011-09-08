package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum HOSDriverLogsEnum implements SeleniumEnums {
    
    DEFAULT_URL("/hos"),
    
    TITLE("HOS Driver Logs", "//span[@class='hos']"),
    
    DRIVER_FIELD("Driver:", "hos-table-form:driverName"),
    DRIVER_SUGGESTION(null, "hos-table-form:driverSuggestionBoxId:suggest"),
    
    
    START_FIELD("Date Range:", "hos-table-form:hosTable_startCalendarInputDate"),
    STOP_FIELD(null, "hos-table-form:hosTable_endCalendarInputDate"),
    DATE_ERROR(null, "hos-table-form:hosTable_dateError"),
    
    REFRESH("Refresh", "hos-table-form:hosTable_refresh"),
    ADD("Add", "hos-table-form:hosTable-hosTableAdd"),
    BATCH_EDIT(batchEdit, "hos-table-form:hosTable-hosTableEdit"),
    
    EDIT_COLUMNS(null, "hos-table-form:hosTable-hosTableEditColumns"),
    
    SHIP_LOGS(null, "hos-table-form:hosTable-hosSendLogs"),
    SHIP_LOGS_STATUS(null, "hos-table-form:hosTable-hosSendLogsStatus"),
    SHIP_LOGS_MESSAGE(null, "hos-table-form:sendLogsError"),
    
    
    COUNTER("Showing XXX to YYY of ZZZ records", "hos-table-form:header"),
    
    CHECK_ALL(null, "hos-table-form:hosTable:selectAll"),
    
    ENTRY_CHECK_BOX(null, "hos-table-form:hosTable:###:select"),
    ENTRY_DATE_TIME("Date/Time", "hos-table-form:hosTable:###:datetime"),
    ENTRY_DRIVER("Driver", "hos-table-form:hosTable:###:driver"),
    ENTRY_VEHICLE("Vehicle", "hos-table-form:hosTable:###:vehicle"),
    ENTRY_SERVICE("Service", "hos-table-form:hosTable:###:service"),
    ENTRY_TRAILER("Trailer", "hos-table-form:hosTable:###:trailer"),
    ENTRY_LOCATION("Location", "hos-table-form:hosTable:###:location"),
    ENTRY_STATUS("Status", "hos-table-form:hosTable:###:status"),
    ENTRY_EDITED("Edited", "hos-table-form:hosTable:###:edited"),
    ENTRY_EDIT(null, "hos-table-form:hosTable:###:edit"),
    
    
    
    ;
    private String text, url;
    private String[] IDs;
    
    private HOSDriverLogsEnum(String url){
        this.url = url;
    }
    private HOSDriverLogsEnum(String text, String ...IDs){
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
