package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum WebUtilEnum implements SeleniumEnums {
    DURATION_30DAYS(null, "durationPanelHeaderDays"),
    DURATION_3MONTHS(null, "durationPanelHeaderThreeMonths"),
    DURATION_6MONTHS(null, "durationPanelHeaderSixMonths"),
    DURATION_12MONTHS(null, "durationPanelHeaderTwelveMonths"),
    FILTER_REMOVE("", "1"),
    FILTER_0_TO_1("0.0 - 1.0", "2"),
    FILTER_1_TO_2("1.1 - 2.0", "3"),
    FILTER_2_TO_3("2.1 - 3.0", "4"),
    FILTER_3_TO_4("3.1 - 4.0", "5"),
    FILTER_4_TO_5("4.1 - 5.0", "6");

    private String text, url;
    private String[] IDs;
    
    private WebUtilEnum(String url){
    	this.url = url;
    }
    private WebUtilEnum(String text, String ...IDs){
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
