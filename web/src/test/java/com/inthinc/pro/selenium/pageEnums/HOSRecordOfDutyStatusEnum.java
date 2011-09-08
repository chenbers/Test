package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum HOSRecordOfDutyStatusEnum implements SeleniumEnums {

    TIME_STAMP(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[3]/td/span"),
    
    TITLE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[5]/td/span"),
    SUB_TITLE("(ONE CALENDAR DAY - 24 HOURS)", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[6]/td/span"),
    
    LABEL_DATE("DATE:", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[2]/span"),
    VALUE_DATE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[3]/span"),
    
    RECAP_RECAP("RECAP", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[5]/table/tbody/tr[2]/td/span"),
    
    RECAP_VALUE_DAY_NO(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[5]/table/tbody/tr[4]/td/span"),
    RECAP_LABEL_DAY_NO("DAY NO.", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[5]/table/tbody/tr[5]/td/span"),
    
    RECAP_DRIVER_TYPE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[5]/table/tbody/tr[7]/td/span"),
    
    RECAP_VALUE_HRS_AVAIL_TODAY(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[5]/table/tbody/tr[10]/td/span"),
    RECAP_LABEL_HRS_AVAIL_TODAY("HRS AVAIL TODAY", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[5]/table/tbody/tr[11]/td/span"),
    
    RECAP_VALUE_HRS_WORKED_TODAY(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[5]/table/tbody/tr[13]/td/span"),
    RECAP_LABEL_HRS_WORKED_TODAY("HRS WORKED TODAY", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[5]/table/tbody/tr[14]/td/span"),
    
    RECAP_VALUE_TOTAL_HRS_7DAYS(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[5]/table/tbody/tr[16]/td/span"),
    RECAP_LABEL_TOTAL_HRS_7DAYS("TOTAL HRS 7 DAYS", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[5]/table/tbody/tr[17]/td/span"),
    
    RECAP_VALUE_TOTAL_HRS_8DAYS(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[5]/table/tbody/tr[19]/td/span"),
    RECAP_LABEL_TOTAL_HRS_8DAYS("TOTAL HOURS 8 DAYS", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[5]/table/tbody/tr[20]/td/span"),
    
    RECAP_VALUE_HRS_AVAIL_TOMORROW(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[5]/table/tbody/tr[22]/td/span"),
    RECAP_LABEL_HRS_AVAIL_TOMORROW("HRS AVAIL TOMORROW", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[5]/table/tbody/tr[23]/td/span"),
    
    

    VALUE_START_ODOMETER(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[8]/td[2]/table/tbody/tr[3]/td[2]/span"),
    VALUE_VEHICLES_DISTANCE_DRIVEN_TODAY(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[8]/td[2]/table/tbody/tr[3]/td[4]/span"),
    

    VALUE_RULESET(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[9]/td[4]/span"),
    
    LABEL_START_ODOMETER("Start Odometer", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[2]/span"),
    LABEL_VEHICLES_DISTANCE_DRIVEN_TODAY("Vehicles (mi driven today)", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[4]/span"),
    LABEL_RULESET("Rule", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[12]/td[6]/span"),

    VALUE_DRIVER_NAME(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[14]/td[2]/span"),
    VALUE_MILES_DRIVEN_TODAY(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[14]/td[4]/span"),
    
    LABEL_DRIVER_NAME(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[17]/td[2]/span"),
    LABEL_MILES_DRIVEN_TODAY(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[17]/td[4]/span"),
    
    VALUE_CARRIER(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[19]/td[2]/span"),
    
    VALUE_NAME_OF_CO_DRIVER(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[20]/td[3]/span"),
    
    LABEL_CARRIER("(Carrier(s))", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[24]/td[2]/span"),
    LABEL_NAME_OF_CO_DRIVER("(Name of Co-Driver)", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[24]/td[4]/span"),
    
    VALUE_MAIN_OFFICE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[25]/td[2]/span"),
    VALUE_HOME_TERMINAL(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[25]/td[4]/span"),
    
    LABEL_MAIN_OFFICE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[28]/td[2]/span"),
    LABEL_HOME_TERMINAL(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[28]/td[4]/span"),
    
    DRIVER_SIGNATURE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[43]/td[4]/span" ),
    
    DRIVER_SIGNATURE_NEW_PAGE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[7]/td[4]/span"),
    
    LABEL_REMARKS("REMARKS", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[43]/td[2]/span"),
    
    LABEL_REMARKS_NEW_PAGE("REMARKS", "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[3]/td[2]/span"),
    
    REMARKS_DATE_TIME_NEW_PAGE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[5]/td[2]/table/tbody/tr[###]/td[1]/span"),
    REMARKS_CITY_NEW_PAGE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[5]/td[2]/table/tbody/tr[###]/td[2]/span"),
    REMARKS_HOS_STATUS_NEW_PAGE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[5]/td[2]/table/tbody/tr[###]/td[3]/span"),
    REMARKS_ODOMETER_NEW_PAGE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[5]/td[2]/table/tbody/tr[###]/td[4]/span"),
    REMARKS_EDITED_BY_NEW_PAGE(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[5]/td[2]/table/tbody/tr[###]/td[5]/span"), 
    
    REMARKS_DATE_TIME(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[41]/td[2]/table/tbody/tr[###]/td[1]/span"),
    REMARKS_CITY(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[5]/td[41]/table/tbody/tr[###]/td[2]/span"),
    REMARKS_HOS_STATUS(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[41]/td[2]/table/tbody/tr[###]/td[3]/span"),
    REMARKS_ODOMETER(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[41]/td[2]/table/tbody/tr[###]/td[4]/span"),
    REMARKS_EDITED_BY(null, "//span[@id='hosReports_htmlText']/table[***]/tbody/tr[41]/td[2]/table/tbody/tr[###]/td[5]/span"), 
    ;
    
    private String text, url;
    private String[] IDs;
    
    private HOSRecordOfDutyStatusEnum(String url){
        this.url = url;
    }
    private HOSRecordOfDutyStatusEnum(String text, String ...IDs){
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
