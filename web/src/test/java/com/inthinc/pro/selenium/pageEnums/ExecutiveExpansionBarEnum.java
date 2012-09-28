package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum ExecutiveExpansionBarEnum implements SeleniumEnums {
	TREND("Trend", "detailsTrend"),
	OVERALL_SCORE("Overall Score", "detailsOverall"),
	FLEET_MAP("Fleet Map", "detailsMap"),
	FUEL_EFFICIENCY("Fuel Efficiency", "detailsMpg"),
	
	OVERVIEW_TITLE("Overview", "//ul[@id='grid_nav']/li[2]"),
	BREADCRUMB(null, "breadcrumbitem:0:details-dashboard"),
	
	
	;

    private String text, url;
    private String[] IDs;
    
    private ExecutiveExpansionBarEnum(String url){
    	this.url = url;
    }
    private ExecutiveExpansionBarEnum(String text, String ...IDs){
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
