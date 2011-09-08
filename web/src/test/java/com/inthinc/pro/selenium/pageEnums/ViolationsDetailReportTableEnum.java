package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum ViolationsDetailReportTableEnum implements SeleniumEnums {
    
    LOCATION("Location", "hosReports_tableForm:hosReports_viewTable:###:column0"),
    DATE_TIME("Date/Time", "hosReports_tableForm:hosReports_viewTable:###:column1"),
    DRIVER("Driver", "hosReports_tableForm:hosReports_viewTable:###:column2"),
    VEHICLE("Vehicle", "hosReports_tableForm:hosReports_viewTable:###:column3"),
    VIOLATION("Violation", "hosReports_tableForm:hosReports_viewTable:###:column4"),
    CFR("CFR", "hosReports_tableForm:hosReports_viewTable:###:column5"),
    LENGTH("Length", "hosReports_tableForm:hosReports_viewTable:###:column6"),
    RULE("Rule", "hosReports_tableForm:hosReports_viewTable:###:column7"),
    
    ;
    
    private String text, url;
    private String[] IDs;
    
    private ViolationsDetailReportTableEnum(String url){
        this.url = url;
    }
    private ViolationsDetailReportTableEnum(String text, String ...IDs){
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
