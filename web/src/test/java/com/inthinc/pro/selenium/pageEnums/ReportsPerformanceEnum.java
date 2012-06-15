package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum ReportsPerformanceEnum implements SeleniumEnums {
    /* Permanent Items */
    TITLE("Performance Report", "//span[@class='performance']"),
    
    DEFAULT_URL(appUrl + "/reports/performanceReport"),
    
    ERROR_REQUIRED_MESSAGE(null, "peformanceReports_form:paramsError"),
    REPORT_DROP_DOWN(null, "performanceReports_form:performanceReports-report"),
    
    TIME_FRAME_DROP_DOWN(null,"//select(@id,'performanceReports_form:timeframe:0:performanceReports_reportDaysTimeFrame"),
    TIME_FRAME_LABEL("Group(s)", "//span[@id='performanceReports_form:groupListSelect']/../td[1]"),
        
    EXCEL("Excel", "performanceReports_form:performanceReports_excelIcon"),
    EMAIL(email, "performanceReports_form:performanceReports_emailIcon"),
    
    GROUP_LABEL("Group(s)", "//span[@id='performanceReports_form:groupListSelect']/../td[1]"),
    GROUP_SELECTOR("Group(s)", "performanceReports_form:performanceReportsgroupListParam"),
    GROUP_ARROW(null, "//span[@id='performanceReports_form:groupListSelect']/span[1]/span/div"),
 
       
    ;

    private String text, url;
    private String[] IDs;

    private ReportsPerformanceEnum(String url) {
        this.url = url;
    }

    private ReportsPerformanceEnum(String text, String... IDs) {
        this.text = text;
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
