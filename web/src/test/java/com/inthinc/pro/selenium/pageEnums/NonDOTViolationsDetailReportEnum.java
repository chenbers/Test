package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NonDOTViolationsDetailReportEnum implements SeleniumEnums {
    
    TITLE("Non-DOT Driver Violation Detail", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[3]/td[4]/span"),
    TIME_FRAME(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[8]/td[2]/span"),
    
    HEADER_LOCATION("", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[2]/span"),
    HEADER_DATE_TIME("", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[3]/span"),
    HEADER_EMPLOYEE("", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[4]/span"),
    HEADER_DRIVER("", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[5]/span"),
    HEADER_VEHICLE("", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[6]/span"),
    HEADER_VIOLATION("", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[7]/span"),
    HEADER_LENGTH("", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[8]/span"),
    HEADER_RULE("", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[9]/span"),
    
    VALUE_LOCATION(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[2]/span"),
    VALUE_DATE_TIME(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[4]/span"),
    VALUE_EMPLOYEE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[6]/span"),
    VALUE_DRIVER(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[8]/span"),
    VALUE_VEHICLE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[9]/span"),
    VALUE_VIOLATION(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[10]/table/tbody/tr[2]/td[2]/span"),
    VALUE_LENGTH(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[10]/table/tbody/tr[3]/td[3]/span"),
    VALUE_RULE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[11]/span"),
    
    ;
    
    private String text, url;
    private String[] IDs;
    
    private NonDOTViolationsDetailReportEnum(String url){
        this.url = url;
    }
    private NonDOTViolationsDetailReportEnum(String text, String ...IDs){
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
