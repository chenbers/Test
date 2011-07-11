package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum TrendExpansionEnum implements SeleniumEnums {

	

	TIME_SELECTOR(null, "details-trend_form:details-trend-***"),
	RETURN(null, "details-trend_details"),
	TOOLS(null, "details-trend_toolImage"),
	EMAIL_REPORT(email+" This report", "details-trend_detail_form:details-trend_email:icon"),
	EXPORT_PDF("Export To PDF", "details-trend_detail_form:details-trend-export:icon"),
	TITLE("Trend", "//span[@class='line']"),
	
	AVERAGE_CHECKBOX(null, "trendTable:details-showCheckBox"),
	AVERAGE_COLOR_BOX(null, "//tbody[@id='trendTable:summaryitems:tb']/tr[1]/td[2]/img"),
	AVERAGE_GROUP_NAME(null, "//tbody[@id='trendTable:summaryitems:tb']/tr[1]/td[3]"),
	AVERAGE_SCORE_BOX(null, "//tbody[@id='trendTable:summaryitems:tb']/tr[1]/td[4]"),
	AVERAGE_CRASH_COUNT(null, "//tbody[@id='trendTable:summaryitems:tb']/tr[1]/td[5]"),
	
	HEADER_DIVISION_TEAM("Division/Team", "//tr[@class='rich-table-subheader']/th[3]"),
	HEADER_SCORE("Score", "//tr[@class='rich-table-subheader']/th[4]"),
	HEADER_CRASH_COUNT("Crash/Mil", "//tr[@class='rich-table-subheader']/th[3]"),
	
	ENTRY_CHECKBOX(null, "trendTable:details:###:details-showCheckBox"),
	ENTRY_COLOR_BOX(null, "//table[@id='trendTable:details:tb']/tr[###]/td[2]/img"),
	ENTRY_GROUP_NAME(null, "trendTable:details:###:details-trendGroup"),
	ENTRY_SCORE_BOX(null, "//table[@id='trendTable:details:tb']/tr[###]/td[4]"),
	ENTRY_CRASH_COUNT(null, "trendTable:details:###:crashes"),
	
	
	
	;

    private String text, url;
    private String[] IDs;
    
    private TrendExpansionEnum(String url){
    	this.url = url;
    }
    private TrendExpansionEnum(String text, String ...IDs){
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
