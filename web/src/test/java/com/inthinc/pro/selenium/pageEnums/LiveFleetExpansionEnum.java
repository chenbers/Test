package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum LiveFleetExpansionEnum implements SeleniumEnums {

    TITLE("Live Fleet", "//form[@id='refresh']/../../span[@class='map']"),
    REFRESH(null, "refresh:details-liveFleetMapRefresh"),
    RESTORE(null, "refresh:details-liveFleetMapRestore"),
    
    LEGEND_TITLE("Fleet Legend", "//form[@id='bubbleForm']/../div/div/div/span[@class='legend']"),
    LEGEND_ENTRY(null, "icos2:###"), 
    DEFAULT_URL("app/dashboard/detail/MAP"),
    
    ;

    private String text, url;
    private String[] IDs;

    private LiveFleetExpansionEnum(String url) {
    this.url = url;
    }

    private LiveFleetExpansionEnum(String text, String... IDs) {
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
