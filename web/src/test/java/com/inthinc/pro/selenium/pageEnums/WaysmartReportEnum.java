package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum WaysmartReportEnum implements SeleniumEnums{
    
    TITLE("waySmart Reports", "//span[@class='hos']"),
    
    DEFAULT_URL("/app/reports/waysmartReport"),
    ERROR_REQUIRED_MESSAGE(null, "waysmartReports_form:paramsError"),
    
    REPORT_DROP_DOWN(null, "waysmartReports_form:hosReports-report"),
    
    START_DATE("Date Range:", "//input[contains(@id,'waysmartsReports_startCalendarInputDate']"),
    STOP_DATE(null, "//input[contains(@id,'waysmartReports_endCalendarInputDate']"),
    
    TABLE("Table", "waysmartReports_form:waysmartReports_tableIcon"),
    HTML("HTML", "waysmartReports_form:waysmartReports_htmlIcon"),
    PDF("PDF", "waysmartReports_form:waysmartReports_pdfIcon"),
    EXCEL("Excel", "waysmartReports_form:waysmartReports_excelIcon"),
    EMAIL(email, "waysmartReports_form:waysmartReports_emailIcon"),
    
    
    REPORT_ON_DROP_DOWN("Report On:", "waysmartReports_form:grouporDriverSelection"),
    REPORT_ON_DRIVER_DROP_DOWN(null, "waysmartReports_form:waysmartReports-groupDriverParam"),
    
    GROUP_LABEL("Group(s)", "//span[@id='waysmartReports_form:groupListSelect']/../td[1]"),
    GROUP_SELECTOR("Group(s)", "waysmartReports_form:waysmartReportsgroupListParam"),
    GROUP_ARROW(null, "//span[@id='waysmartReports_form:groupListSelect']/span[1]/span/div"),
    
    DRIVER_DROP_DOWN("Driver:", "waysmartReports_form:waysmartReports-driverParam"),
    
    ;
    
    private String text, url;
    private String[] IDs;
    
    private WaysmartReportEnum(String url){
        this.url = url;
    }
    private WaysmartReportEnum(String text, String ...IDs){
        this.text=text;
        this.IDs = IDs;
    }

    @Override
    public String getText() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getIDs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getURL() {
        // TODO Auto-generated method stub
        return null;
    }

}
