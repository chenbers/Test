package com.inthinc.pro.selenium.pageEnums;




import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.utils.AutomationCalendar;

public enum TeamTimeFrames implements SeleniumEnums {
    DAYS_AGO_TODAY(AutomationCalendar.getTimeFrameOptions()[0], "timeFrameForm:timeFrameToday"),
    DAYS_AGO_YESTERDAY(AutomationCalendar.getTimeFrameOptions()[1], "timeFrameForm:timeFrameOneDay"),
    DAYS_AGO_2(AutomationCalendar.getFiveDayPeriodShort()[0], "timeFrameForm:timeFrameTwoDay"),
    DAYS_AGO_3(AutomationCalendar.getFiveDayPeriodShort()[1], "timeFrameForm:timeFrameThreeDay"),
    DAYS_AGO_4(AutomationCalendar.getFiveDayPeriodShort()[2], "timeFrameForm:timeFrameFourDay"),
    DAYS_AGO_5(AutomationCalendar.getFiveDayPeriodShort()[3], "timeFrameForm:timeFrameFiveDay"),
    DAYS_AGO_6(AutomationCalendar.getFiveDayPeriodShort()[4], "timeFrameForm:timeFrameSixDay"),

    THIS_WEEK("7 Days", "timeFrameForm:timeFrameWeek"),
    THIS_MONTH(AutomationCalendar.getCurrentMonth(), "timeFrameForm:timeFrameMonth"),
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
