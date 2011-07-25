package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum HosReportsEnum implements SeleniumEnums {

    TITLE("HOS Reports", "//span[@class='hos']"),
    ERROR_REQUIRED_MESSAGE(null, "hosReports_form:paramsError"),
    
    REPORT_DROP_DOWN(null, "hosReports_form:hosReports-report"),
    
    START_DATE("Date Range:", "//input[contains(@id,'hosReports_startCalendarInputDate']"),
    STOP_DATE(null, "//input[contains(@id,'hosReports_endCalendarInputDate']"),
    
    TABLE("Table", "hosReports_form:hosReports_tableIcon"),
    HTML("HTML", "hosReports_form:hosReports_htmlIcon"),
    PDF("PDF", "hosReports_form:hosReports_pdfIcon"),
    EXCEL("Excel", "hosReports_form:hosReports_excelIcon"),
    EMAIL(email, "hosReports_form:hosReports_emailIcon"),
    
    
    
    REPORT_ON_DROP_DOWN("Report On:", "hosReports_form:grouporDriverSelection"),
    REPORT_ON_DRIVER_DROP_DOWN(null, "hosReports_form:hosReports-groupDriverParam"),
    
    GROUP_LABEL("Group(s)", "//span[@id='hosReports_form:groupListSelect']/../td[1]"),
    
    GROUP_SELECTOR("Group(s)", "hosReports_form:hosReportsgroupListParam"),
    GROUP_ARROW(null, "//span[@id='hosReports_form:groupListSelect']/span[1]/span/div"),
    
    DRIVER_DROP_DOWN("Driver:", "hosReports_form:hosReports-driverParam"),
    
    
    
    ;
    
    private String text, url;
    private String[] IDs;
    
    private HosReportsEnum(String url){
        this.url = url;
    }
    private HosReportsEnum(String text, String ...IDs){
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
