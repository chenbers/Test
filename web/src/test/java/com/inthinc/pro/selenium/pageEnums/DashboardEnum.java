package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.selenium.SeleniumEnums;

public enum DashboardEnum implements SeleniumEnums {
    DASHBOARD_HELP(null,null,"javascript:RH_ShowHelp(0, &quot;/web/secured/help/WebHelp/inthincPortalUserGuide.htm&gt;NewWindow&quot;, 15, 26)",null,null),
    OVERALL_DURATION(null,"executive-overallScore_form:executive-overallScore-",null,null,null),
    TREND_DURATION(null,"executive-trend_form:executive-trend-",null,null,null),
    FUEL_EFFICIENCY_DURATION(null,"executive-mpgChart_form:executive-mpgChart-",null,null,null),
    SPEEDING_DURATION(null,"executive-speedPercentagePanel_form:executive-speedPercentagePanel-",null,null,null),
    IDLING_DURATION(null,"executive-idlingPercentagePanel_form:executive-idlingPercentagePanel-",null,null,null),  
    OVERALL_EXPAND(null,"executive-overallScore_details",null,null,null),
    TREND_EXPAND(null,"executive-trend_details",null,null,null),
    FUEL_EFFICIENCY_EXPAND(null,"executive-mpgChart_details",null,null,null),
    LIVE_FLEET_EXPAND(null,"refresh:executive-liveFleetMapRestore",null,null,null),
    DASHBOARD_OVERALL_RESTORE(null,"details_details",null,null,null),
    DASHBOARD_TREND_RESTORE(null,"details-trend_details",null,null,null),
    DASHBOARD_FUEL_EFFICIENCY_RESTORE(null,"details-mpgChart_details",null,null,null),
    DASHBOARD_LIVE_FLEET_RESTORE(null,"refresh:details-liveFleetMapRestore",null,null,null),
    LIVE_FLEET_REFRESH(null,"refresh:executive-liveFleetMapRefresh",null,null,null)
    ; 
    
    private String text, ID, xpath, xpath_alt, url;
    
    private DashboardEnum( String text, String ID, String xpath, String xpath_alt, String url) {
        this.text=text;
        this.ID=ID;
        this.xpath=xpath;
        this.xpath_alt=xpath_alt;
        this.url=url;
    }

    @Override
    public String getID() {
        // TODO Auto-generated method stub
        return this.ID;
    }

    @Override
    public String getText() {
        // TODO Auto-generated method stub
        return this.text;
    }

    @Override
    public String getXpath() {
        // TODO Auto-generated method stub
        return this.xpath;
    }

    @Override
    public String getXpath_alt() {
        // TODO Auto-generated method stub
        return this.xpath_alt;
    }

    @Override
    public void setText(String text) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getURL() {
        // TODO Auto-generated method stub
        return null;
    }

}
