package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NonDOTViolationsSummaryReportHTMLEnum implements SeleniumEnums {
    
    TITLE("Non-DOT Driver Violation Summary", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[4]/td[4]/span"),
    TIME_FRAME(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[2]/span"),
    
    VALUE_LABEL_LOCATION("Location", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[2]/span"),
    VALUE_ACTUAL_LOCATION("Location", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[3]/span"),
    
    LABEL_NON_DOT_DRIVER_IN_DOT_VEHICLE("Location", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[9]/td[2]/span"),
    LABEL_DRIVERS("Location", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[9]/td[3]/span"),
    LABEL_VIOLATIONS_PERCENT("Location", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[9]/td[4]/span"),
    
    VALUE_NON_DOT_DRIVER_IN_DOT_VEHICLE("Location", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[2]/span"),
    VALUE_DRIVERS("Location", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[4]/span"),
    VALUE_VIOLATIONS_PERCENT("Location", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[6]/span"),
    
    ;

    private String text, url;
    private String[] IDs;
    
    private NonDOTViolationsSummaryReportHTMLEnum(String url){
        this.url = url;
    }
    private NonDOTViolationsSummaryReportHTMLEnum(String text, String ...IDs){
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
