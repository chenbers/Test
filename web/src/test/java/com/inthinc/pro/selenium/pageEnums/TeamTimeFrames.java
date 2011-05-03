package com.inthinc.pro.selenium.pageEnums;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.automation.enums.SeleniumEnum.SeleniumEnums;
import com.inthinc.pro.automation.selenium.CoreMethodLib;

public enum TeamTimeFrames implements SeleniumEnums {
    DAYS_AGO_TODAY(CoreMethodLib.getTimeFrameOptions()[0], "timeFrameForm:timeFrameToday"),
    DAYS_AGO_YESTERDAY(CoreMethodLib.getTimeFrameOptions()[1], "timeFrameForm:timeFrameOneDay"),
    DAYS_AGO_2(CoreMethodLib.getFiveDayPeriodShort()[0], "timeFrameForm:timeFrameTwoDay"),
    DAYS_AGO_3(CoreMethodLib.getFiveDayPeriodShort()[1], "timeFrameForm:timeFrameThreeDay"),
    DAYS_AGO_4(CoreMethodLib.getFiveDayPeriodShort()[2], "timeFrameForm:timeFrameFourDay"),
    DAYS_AGO_5(CoreMethodLib.getFiveDayPeriodShort()[3], "timeFrameForm:timeFrameFiveDay"),
    DAYS_AGO_6(CoreMethodLib.getFiveDayPeriodShort()[4], "timeFrameForm:timeFrameSixDay"),

    THIS_WEEK("7 Days", "timeFrameForm:timeFrameWeek"),
    THIS_MONTH(CoreMethodLib.getCurrentMonth(), "timeFrameForm:timeFrameMonth"),
    THIS_YEAR("365 Days", "timeFrameForm:timeFrameYear"), ;

    private String ID, text;

    private TeamTimeFrames(String text, String ID) {
        this.ID = ID;
        this.text = text;
    }

    public String getID() {
        return ID;
    }

    public String getText() {
        return text;
    }

    public String getURL() {
        return null;
    }

    public String getXpath() {
        return null;
    }

    public String getXpath_alt() {
        return null;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public List<String> getLocators() {  
        List<String> ids = new ArrayList<String>(1);
        ids.add(ID);
        return ids;
    }
    
    @Override
    public TeamTimeFrames replaceNumber(String number) {
        return this;
    }

    @Override
    public TeamTimeFrames replaceWord(String word) {
        return this;
    }

}
