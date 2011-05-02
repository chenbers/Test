package com.inthinc.pro.selenium.pageEnums;

import java.util.List;

import com.inthinc.pro.automation.enums.SeleniumEnumUtil;
import com.inthinc.pro.automation.enums.SeleniumEnums;

public enum WebUtilEnum implements SeleniumEnums {
    DURATION_30DAYS("durationPanelHeaderDays",null,null,null,null),
    DURATION_3MONTHS("durationPanelHeaderThreeMonths",null,null,null,null),
    DURATION_6MONTHS("durationPanelHeaderSixMonths",null,null,null,null),
    DURATION_12MONTHS("durationPanelHeaderTwelveMonths",null,null,null,null),
    FILTER_REMOVE("","1",null,null,null),
    FILTER_0_TO_1("0.0 - 1.0","2",null,null,null),
    FILTER_1_TO_2("1.1 - 2.0","3",null,null,null),
    FILTER_2_TO_3("2.1 - 3.0","4",null,null,null),
    FILTER_3_TO_4("3.1 - 4.0","5",null,null,null),
    FILTER_4_TO_5("4.1 - 5.0","6",null,null,null)
    ;
    
    private String text, ID, xpath, xpath_alt, url;
    
    private WebUtilEnum( String text, String ID, String xpath, String xpath_alt, String url) {
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
        this.text=text;
    }

    @Override
    public String getURL() {
        // TODO Auto-generated method stub
        return this.url;
    }
    @Override
    public List<String> getLocators() {        
        return SeleniumEnumUtil.getLocators(this);
    }
}
