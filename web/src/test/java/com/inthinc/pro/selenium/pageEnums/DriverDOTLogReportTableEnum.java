package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum DriverDOTLogReportTableEnum implements SeleniumEnums {

    DATE_TIME("Date/Time", "hosReports_tableForm:hosReports_viewTable:###:column0"),
    DRIVER("Driver", "hosReports_tableForm:hosReports_viewTable:###:column1"),
    VEHICLE("Vehicle", "hosReports_tableForm:hosReports_viewTable:###:column2"),
    TRAILER("Trailer", "hosReports_tableForm:hosReports_viewTable:###:column3"),
    SERVICE("Service", "hosReports_tableForm:hosReports_viewTable:###:column4"),
    LOCATION("Location", "hosReports_tableForm:hosReports_viewTable:###:column5"),
    DETAIL("Detail", "hosReports_tableForm:hosReports_viewTable:###:column6"),
    ADDED_BY("Added By", "hosReports_tableForm:hosReports_viewTable:###:column7"),
    
    
    ;

    private String text, url;
    private String[] IDs;
    
    private DriverDOTLogReportTableEnum(String url){
        this.url = url;
    }
    private DriverDOTLogReportTableEnum(String text, String ...IDs){
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
