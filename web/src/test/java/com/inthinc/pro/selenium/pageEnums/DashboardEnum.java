package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.selenium.SeleniumEnums;

public enum DashboardEnum implements SeleniumEnums {
    OVERALL_TOOLS(null,null,"//img[@title='Tool Menu']",null,null),
    OVERALL_TOOLS_EMAIL(null,null,"executive-overallScore_detail_form:executive-overallScore_email:anchor",null,null),
    OVERALL_TOOLS_PDF(null,null,"executive-overallScore_detail_form:executive-overallScore-export:anchor",null,null),
    OVERALL_TOOLS_EMAIL_CANCEL(null,null,"//button[@id='emailReportPopUpSubmit' and @type='submit' and @onclick=\"Richfaces.hideModalPanel('executive-overallScore_singleEmail'); return false;\"]",null,null),
    
    TREND_TOOLS(null,null,"//span[@id='executive-trend_toolImage']/img",null,null),
    TREND_TOOLS_EMAIL(null,null,"executive-trend_detail_form:executive-trend_email:anchor",null,null),
    TREND_TOOLS_PDF(null,null,"executive-trend_detail_form:executive-trend-export:anchor",null,null),
    TREND_TOOLS_EMAIL_CANCEL(null,null,"//button[@id='emailReportPopUpSubmit' and @type='submit' and @onclick=\"Richfaces.hideModalPanel('executive-trend_singleEmail'); return false;\"]",null,null),
    
    SPEED_PERCENTAGE_TOOLS(null,null,"//span[@id='executive-speedPercentagePanel_toolImage']/img",null,null),
    SPEED_PERCENTAGE_TOOLS_EMAIL(null,null,"executive-speedPercentagePanel_detail_form:executive-speedPercentagePanel_email:anchor",null,null),
    SPEED_PERCENTAGE_TOOLS_PDF(null,null,"executive-speedPercentagePanel_detail_form:executive-speedPercentagePanel-export:anchor",null,null),
    SPEED_PERCENTAGE_TOOLS_EMAIL_CANCEL(null,null,"//button[@id='emailReportPopUpSubmit' and @type='submit' and @onclick=\"Richfaces.hideModalPanel('executive-speedPercentagePanel_singleEmail'); return false;\"]",null,null),

    IDLING_PERCENTAGE_TOOLS(null,null,"//span[@id='executive-idlingPercentagePanel_toolImage']/img",null,null),
    IDLING_PERCENTAGE_TOOLS_EMAIL(null,null,"executive-idlingPercentagePanel_detail_form:executive-idlingPercentagePanel_email:anchor",null,null),
    IDLING_PERCENTAGE_TOOLS_PDF(null,null,"executive-idlingPercentagePanel_detail_form:executive-idlingPercentagePanel-export:anchor",null,null),
    IDLING_PERCENTAGE_TOOLS_EMAIL_CANCEL(null,null,"//button[@id='emailReportPopUpSubmit' and @type='submit' and @onclick=\"Richfaces.hideModalPanel('executive-idlingPercentagePanel_singleEmail'); return false;\"]",null,null),
    
    MPG_CHART_TOOLS(null,null,"//span[@id='executive-mpgChart_toolImage']/img",null,null),
    MPG_CHART_TOOLS_EMAIL(null,null,"executive-mpgChart_detail_form:executive-mpgChart_email:anchor",null,null),
    MPG_CHART_TOOLS_PDF(null,null,"executive-mpgChart_detail_form:executive-mpgChart-export:anchor",null,null),
    MPG_CHART_TOOLS_EMAIL_CANCEL(null,null,"//button[@id='emailReportPopUpSubmit' and @type='submit' and @onclick=\"Richfaces.hideModalPanel('executive-mpgChart_singleEmail'); return false;\"]",null,null),
    
    HELP_INVOKE(null,null,"//img[@title='Help ']",null,null),
    HELP_OPEN(null,null,"__webCshStub",null,null),
    
    OVERALL_DURATION(null,"executive-overallScore_form:executive-overallScore-",null,null,null),
    TREND_DURATION(null,"executive-trend_form:executive-trend-",null,null,null),
    FUEL_EFFICIENCY_DURATION(null,"executive-mpgChart_form:executive-mpgChart-",null,null,null),
    SPEEDING_DURATION(null,"executive-speedPercentagePanel_form:executive-speedPercentagePanel-",null,null,null),
    IDLING_DURATION(null,"executive-idlingPercentagePanel_form:executive-idlingPercentagePanel-",null,null,null),  
    
    OVERALL_EXPAND(null,"executive-overallScore_details",null,null,null),
    TREND_EXPAND(null,"executive-trend_details",null,null,null),
    FUEL_EFFICIENCY_EXPAND(null,"executive-mpgChart_details",null,null,null),
    LIVE_FLEET_EXPAND(null,"refresh:executive-liveFleetMapRestore",null,null,null),
    
    OVERALL_RESTORE(null,"details_details",null,null,null),
    TREND_RESTORE(null,"details-trend_details",null,null,null),
    FUEL_EFFICIENCY_RESTORE(null,"details-mpgChart_details",null,null,null),
    LIVE_FLEET_RESTORE(null,"refresh:details-liveFleetMapRestore",null,null,null),
    
    LIVE_FLEET_REFRESH(null,"refresh:executive-liveFleetMapRefresh",null,null,null),
    TREND_TABLE_LINE(null,"trendTable:executive:0:executive-trendGroup",null,null,null)
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
