package com.inthinc.pro.selenium.pageObjects;

import java.util.EnumSet;
import java.util.List;

import com.inthinc.pro.automation.enums.SeleniumEnumUtil;
import com.inthinc.pro.automation.enums.SeleniumEnums;
import com.inthinc.pro.automation.selenium.CoreMethodLib;
import com.inthinc.pro.selenium.pageEnums.TeamBarEnum;

public abstract class TeamBar extends NavigationBar {

    public static enum TimeFrames implements SeleniumEnums {
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

        private TimeFrames(String text, String ID) {
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
            return SeleniumEnumUtil.getLocators(this);
        }
    }

    public String title_teamName_getText() {
        return selenium.getText(TeamBarEnum.TEAM_TITLE);
    }

    public String text_teamOverallScore_getText() {
        return selenium.getText(TeamBarEnum.TEAM_SCORE);
    }

    public String text_numberOfCrashesPerX_getText() {
        return selenium.getText(TeamBarEnum.CRASHES_PER_MILLION_NUMBER);
    }

    public String text_textOfCrashesPerX_getText() {
        return selenium.getText(TeamBarEnum.CRASHES_PER_MILLION_TEXT);
    }

    public String text_numberOfDaysSince_getText() {
        return selenium.getText(TeamBarEnum.DAYS_SINCE_NUMBER);
    }

    public String text_textOfDaysSince_getText() {
        return selenium.getText(TeamBarEnum.DAYS_SINCE_TEXT);
    }

    public String text_numberOfMilesSince_getText() {
        return selenium.getText(TeamBarEnum.MILES_SINCE_NUMBER);
    }

    public String text_textOfMilesSince_getText() {
        return selenium.getText(TeamBarEnum.MILES_SINCE_TEXT);
    }

    public String text_whatHappened_getText() {
        return selenium.getText(TeamBarEnum.SUB_TITLE);
    }

    public String link_timeSelector_getText(TimeFrames time) {
        return selenium.getText(time);
    }

    public TeamBar link_timeSelector_click(TimeFrames time) {
        selenium.click(time);
        return this;
    }

    public TeamBar section_timeSelectors_validate() {
        for (TimeFrames selector : EnumSet.allOf(TimeFrames.class)) {
            assertEquals(selector);
        }
        return this;
    }

}
