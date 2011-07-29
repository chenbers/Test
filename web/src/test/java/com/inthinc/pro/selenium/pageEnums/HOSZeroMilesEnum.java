package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum HOSZeroMilesEnum implements SeleniumEnums {
    
    LOCATION("Location", "hosReports_tableForm:hosReports_viewTable:###:column0"),
    VEHICLE("Vehicle", "hosReports_tableForm:hosReports_viewTable:###:column1"),
    TOTAL_MILES_NO_DRIVER("Total Miles No Driver", "hosReports_tableForm:hosReports_viewTable:###:column2"),
    
    ;

    private String text, url;
    private String[] IDs;
    
    private HOSZeroMilesEnum(String url){
        this.url = url;
    }
    private HOSZeroMilesEnum(String text, String ...IDs){
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
