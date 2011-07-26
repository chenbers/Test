package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum ViolationsSummaryReportTableEnum implements SeleniumEnums {
    
    DRIVING_RULE("Driving Rule (min over)", "//tr[@class='rich-subtable-header']/td[2]/div"),
    ON_DUTY_RULE("On Duty Rule (min over)", "//tr[@class='rich-subtable-header']/td[3]/div"),
    CUMULATIVE_RULE("Cumulative Rule (min over)", "//tr[@class='rich-subtable-header']/td[4]/div"),
    OFF_DUTY_RULE("Off Duty Rule (min over)", "//tr[@class='rich-subtable-header']/td[5]/div"),
    TOTAL("Total", "//tr[@class='rich-subtable-header']/td[6]/div"),
    
    GROUP_NAME("Group Name", "hosReports_tableForm:hosReports_viewTable:###:column0"),
    
    DRIVING_0_14("0-14", "hosReports_tableForm:hosReports_viewTable:###:column1"),
    DRIVING_15_29("15-29", "hosReports_tableForm:hosReports_viewTable:###:column2"),
    DRIVING_30("30+", "hosReports_tableForm:hosReports_viewTable:###:column3"),
    
    ON_DUTY_0_14("0-14", "hosReports_tableForm:hosReports_viewTable:###:column4"),
    ON_DUTY_15_29("15-29", "hosReports_tableForm:hosReports_viewTable:###:column5"),
    ON_DUTY_30("30+", "hosReports_tableForm:hosReports_viewTable:###:column6"),
    
    CUMULATIVE_0_14("0-14", "hosReports_tableForm:hosReports_viewTable:###:column7"),
    CUMULATIVE_15_29("15-29", "hosReports_tableForm:hosReports_viewTable:###:column8"),
    CUMULATIVE_30("30+", "hosReports_tableForm:hosReports_viewTable:###:column9"),
    
    OFF_DUTY_0_14("0-14", "hosReports_tableForm:hosReports_viewTable:###:column10"),
    OFF_DUTY_15_29("15-29", "hosReports_tableForm:hosReports_viewTable:###:column11"),
    OFF_DUTY_30("30+", "hosReports_tableForm:hosReports_viewTable:###:column12"),
    
    DRIVERS("Drivers", "hosReports_tableForm:hosReports_viewTable:###:column13"),
    MILES("Miles", "hosReports_tableForm:hosReports_viewTable:###:column14"),
    MILES_NO_DRIVER("Miles No Driver", "hosReports_tableForm:hosReports_viewTable:###:column15"),
    
    
    
    ;

    private String text, url;
    private String[] IDs;
    
    private ViolationsSummaryReportTableEnum(String url){
        this.url = url;
    }
    private ViolationsSummaryReportTableEnum(String text, String ...IDs){
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
