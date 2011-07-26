package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum ViolationsSummaryReportHTMLEnum implements SeleniumEnums {
    
    TITLE("DOT HOS Summary", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[4]/td[2]/span"),
    
    TIME_FRAME(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[9]/td[2]/span"),

    DRIVING_LINE1("Driving Rule", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[13]/td[2]/span"),
    DRIVING_LINE2("Minutes Over", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[14]/td[2]/span"),
    
    ON_DUTY_RULE_LINE1("On Duty Rule", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[13]/td[4]/span"),
    ON_DUTY_RULE_LINE2("Minutes Over", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[14]/td[4]/span"),
    
    CUMULATIVE_RULE_LINE1("Cumulative Rule", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[13]/td[6]/span"),
    CUMULATIVE_RULE_LINE2("Minutes Over", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[14]/td[6]/span"),
    
    OFF_DUTY_RULE_LINE1("Off Duty Rule/Elapsed", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[13]/td[8]/span"),
    OFF_DUTY_RULE_LINE3("Minutes Over", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[14]/td[8]/span"),
    
    TOTAL("Total Miles", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[15]/td[6]/span"),
    
    ZERO_MILES("Zero Miles", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[15]/td[6]/span"),
    
    ZERO_MILES_PERCENT("Zero Miles %", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[15]/td[6]/span"),
    
    VIOLATIONS_PERCENT("Violations %", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[15]/td[6]/span"),
    
    LOCATION_LABEL("Location:", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[2]/span"),
    LOCATION_VALUE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[3]/span"),
    

    HEADER_DRIVING_0_14("0-14", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[17]/td[2]/span"),
    HEADER_DRIVING_15_29("15-29", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[17]/td[3]/span"),
    HEADER_DRIVING_30("30+", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[17]/td[4]/span"),
    
    HEADER_ON_DUTY_0_14("0-14", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[17]/td[5]/span"),
    HEADER_ON_DUTY_15_29("15-29", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[17]/td[6]/span"),
    HEADER_ON_DUTY_30("30+", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[17]/td[7]/span"),
    
    HEADER_CUMULATIVE_0_14("0-14", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[17]/td[8]/span"),
    HEADER_CUMULATIVE_15_29("15-29", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[17]/td[9]/span"),
    HEADER_CUMULATIVE_30("30+", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[17]/td[10]/span"),
    
    HEADER_OFF_DUTY_0_14("0-14", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[17]/td[11]/span"),
    HEADER_OFF_DUTY_15_29("15-29", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[17]/td[12]/span"),
    HEADER_OFF_DUTY_30("30+", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[17]/td[13]/span"),
    
    HEADER_DRIVERS("Drivers", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[16]/td[2]/span"),
    
    DRIVING_0_14("0-14", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[2]/span"),
    DRIVING_15_29("15-29", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[3]/span"),
    DRIVING_30("30+", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[4]/span"),
    
    ON_DUTY_0_14("0-14", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[5]/span"),
    ON_DUTY_15_29("15-29", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[6]/span"),
    ON_DUTY_30("30+", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[7]/span"),
    
    CUMULATIVE_0_14("0-14", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[8]/span"),
    CUMULATIVE_15_29("15-29", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[9]/span"),
    CUMULATIVE_30("30+", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[10]/span"),
    
    OFF_DUTY_0_14("0-14", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[11]/span"),
    OFF_DUTY_15_29("15-29", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[12]/span"),
    OFF_DUTY_30("30+", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[13]/span"),
    
    DRIVERS("Drivers", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[14]/span"),
    MILES("Miles", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[15]/span"),
    
    ENTRY_ZERO_MILES(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[16]/span"),
    ENTRY_ZERO_MILES_PERCENT(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[17]/span"),
    ENTRY_VIOLATIONS_PERCENT(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[###]/td[18]/span"),
    
    
    ;

    private String text, url;
    private String[] IDs;
    
    private ViolationsSummaryReportHTMLEnum(String url){
        this.url = url;
    }
    private ViolationsSummaryReportHTMLEnum(String text, String ...IDs){
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
