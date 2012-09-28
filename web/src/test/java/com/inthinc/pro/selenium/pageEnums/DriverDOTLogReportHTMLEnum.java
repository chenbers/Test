package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum DriverDOTLogReportHTMLEnum implements SeleniumEnums {
    
    TITLE("Driver HOS Log", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[2]/span"),
    TIME_FRAME(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[2]/span"),
    

    HEADER_DATE_TIME("Date & Time", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[16]/td[2]/span"),
    HEADER_STATUS("Status", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[16]/td[3]/span"),
    HEADER_VEHICLE("Vehicle", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[16]/td[4]/span"),
    HEADER_TRAILER("Trailer", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[16]/td[5]/span"),
    HEADER_SERVICE("Service", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[16]/td[6]/span"),
    HEADER_LOCATION("Location", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[16]/td[7]/span"),
    HEADER_DETAIL("Detail", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[16]/td[8]/span"),
    HEADER_ADDED_BY("Added By", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[16]/td[9]/span"),
    
    LABEL_DRIVER("Driver", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[18]/td[2]/span"),
    VALUE_DRIVER(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[18]/td[3]/span"),
    
    ENTRY_DATE_TIME(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[2]/span"),
    ENTRY_STATUS(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[3]/span"),
    ENTRY_VEHICLE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[4]/span"),
    ENTRY_TRAILER(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[5]/span"),
    ENTRY_SERVICE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[6]/span"),
    ENTRY_LOCATION(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[7]/span"),
    ENTRY_DETAIL(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[8]/span"),
    ENTRY_ADDED_BY(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[9]/span"),
    
    ;

    private String text, url;
    private String[] IDs;
    
    private DriverDOTLogReportHTMLEnum(String url){
        this.url = url;
    }
    private DriverDOTLogReportHTMLEnum(String text, String ...IDs){
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
