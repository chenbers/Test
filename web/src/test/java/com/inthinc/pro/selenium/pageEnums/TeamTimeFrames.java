package com.inthinc.pro.selenium.pageEnums;




import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public enum TeamTimeFrames implements SeleniumEnums {
    TODAY(AutomationCalendar.getTimeFrameOptions()[0], "timeFrameForm:timeFrameToday"),
    YESTERDAY(AutomationCalendar.getTimeFrameOptions()[1], "timeFrameForm:timeFrameOneDay"),
    TWO_DAYS_AGO(AutomationCalendar.getFiveDayPeriodShort()[0], "timeFrameForm:timeFrameTwoDay"),
    THREE_DAYS_AGO(AutomationCalendar.getFiveDayPeriodShort()[1], "timeFrameForm:timeFrameThreeDay"),
    FOUR_DAYS_AGO(AutomationCalendar.getFiveDayPeriodShort()[2], "timeFrameForm:timeFrameFourDay"),
    FIVE_DAYS_AGO(AutomationCalendar.getFiveDayPeriodShort()[3], "timeFrameForm:timeFrameFiveDay"),
    SIX_DAYS_AGO(AutomationCalendar.getFiveDayPeriodShort()[4], "timeFrameForm:timeFrameSixDay"),

    WEEK("7 Days", "timeFrameForm:timeFrameWeek"),
    MONTH(AutomationCalendar.getCurrentMonth(), "timeFrameForm:timeFrameMonth"),
    YEAR("365 Days", "timeFrameForm:timeFrameYear"), ;

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
