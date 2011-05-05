package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.utils.Xpath;

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

    private String text, ID, xpath, xpath_alt, url;

    private WebUtilEnum(String text, String ID, String xpath, String xpath_alt) {
        this.text = text;
        this.ID = ID;
        this.xpath = xpath;
        this.xpath_alt = xpath_alt;
    }

    private WebUtilEnum(String url) {
        this.url = url;
    }

    private WebUtilEnum(String text, String ID) {
        this(text, ID, "", null);
    }

    private WebUtilEnum(String text, String ID, String xpath) {
        this(text, ID, xpath, null);
    }

    private WebUtilEnum(String text, String ID, Xpath xpath, Xpath xpath_alt) {
        this(text, ID, xpath.toString(), xpath_alt.toString());
    }

    private WebUtilEnum(String text, String ID, Xpath xpath) {
        this(text, ID, xpath.toString(), null);
    }

    private WebUtilEnum(String text, Xpath xpath) {
        this(text, null, xpath.toString(), null);
    }

    private WebUtilEnum(Xpath xpath, Xpath xpath_alt) {
        this(null, null, xpath.toString(), xpath_alt.toString());
    }

    private WebUtilEnum(Xpath xpath) {
        this(null, null, xpath.toString(), null);
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
        this.text = text;
    }

    @Override
    public String getURL() {
        // TODO Auto-generated method stub
        return this.url;
    }
}
