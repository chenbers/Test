package com.inthinc.pro.selenium.pageEnums;




import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.automation.enums.SeleniumEnums;

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

    private String text, url;
    private String[] IDs;
    
    private TeamTimeFrames(String url){
    	this.url = url;
    }
    private TeamTimeFrames(String text, String ...IDs){
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
