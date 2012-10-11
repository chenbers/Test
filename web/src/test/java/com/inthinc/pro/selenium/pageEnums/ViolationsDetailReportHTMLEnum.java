package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum ViolationsDetailReportHTMLEnum implements SeleniumEnums {
    
    HEADER_LOCATION("Location", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[2]/span"),
    HEADER_DATE_TIME("Date & Time", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[3]/span"),
    HEADER_EMPLOYEE_ID("Employee ID", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[4]/span"),
    HEADER_DRIVER("Driver", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[5]/span"),
    HEADER_VEHICLE("Vehicle", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[6]/span"),
    HEADER_VIOLATION("Violation", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[7]/span"),
    HEADER_CFR("CFR", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[8]/span"),
    HEADER_LENGTH("Length", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[9]/span"),
    HEADER_RULE("Rule", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[10]/span"),
    
    
    ENTRY_LOCATION(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[2]/span"),
    ENTRY_DATE_TIME(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[3]/span"),
    ENTRY_EMPLOYEE_ID(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[4]/span"),
    ENTRY_DRIVER(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[5]/span"),
    ENTRY_VEHICLE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[6]/span"),
    ENTRY_VIOLATION(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[7]/table/tbody/tr[2]/td/span"),
    ENTRY_CFR(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[7]/table/tbody/tr[3]/td/span"),
    ENTRY_LENGTH(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[7]/table/tbody/tr[4]/td/span"),
    ENTRY_RULE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[8]/span"),
    
    
    TITLE("DOT HOS Violations Detail", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[3]/td[4]/span"),
    DATE_TIME(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[8]/td[2]/span"),
    
    ;
    
    private String text, url;
    private String[] IDs;
    
    private ViolationsDetailReportHTMLEnum(String url){
        this.url = url;
    }
    private ViolationsDetailReportHTMLEnum(String text, String ...IDs){
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
