package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.TextEnum;

public enum DurationEnumeration implements TextEnum {
    DAYS_30("durationPanelHeaderDays"),
    MONTHS_3("durationPanelHeaderThreeMonths"),
    MONTHS_6("durationPanelHeaderSixMonths"),
    MONTHS_12("durationPanelHeaderTwelveMonths"), 
    ;

    private String duration;

    private DurationEnumeration(String duration) {
        this.duration = duration;
    }

    public String getText() {
        return duration;
    }
}
