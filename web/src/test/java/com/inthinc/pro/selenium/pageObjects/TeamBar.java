package com.inthinc.pro.selenium.pageObjects;

import java.util.EnumSet;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.selenium.pageEnums.TeamBarEnum;
import com.inthinc.pro.selenium.pageEnums.TeamTimeFrames;

public abstract class TeamBar extends NavigationBar {
    
    protected class TeamBarButtons extends NavigationBarButtons{}
    protected class TeamBarTextFields extends NavigationBarTextFields{}
    protected class TeamBarDropDowns extends NavigationBarDropDowns{}

    protected class TeamBarTexts extends NavigationBarTexts{

        public Text teamName() {
            return new Text(TeamBarEnum.TEAM_TITLE);
        }
    
        public Text teamOverallScore() {
            return new Text(TeamBarEnum.TEAM_SCORE);
        }
    
        public Text numberOfCrashesPerX() {
            return new Text(TeamBarEnum.CRASHES_PER_MILLION_NUMBER);
        }
    
        public Text textOfCrashesPerX() {
            return new Text(TeamBarEnum.CRASHES_PER_MILLION_TEXT);
        }
    
        public Text numberOfDaysSince() {
            return new Text(TeamBarEnum.DAYS_SINCE_NUMBER);
        }
    
        public Text textOfDaysSince() {
            return new Text(TeamBarEnum.DAYS_SINCE_TEXT);
        }
    
        public Text numberOfMilesSince() {
            return new Text(TeamBarEnum.MILES_SINCE_NUMBER);
        }
    
        public Text textOfMilesSince() {
            return new Text(TeamBarEnum.MILES_SINCE_TEXT);
        }
    
        public Text whatHappened_getText() {
            return new Text(TeamBarEnum.SUB_TITLE);
        }
    
    }
    
    public class TeamBarLinks extends NavigationBarLinks{
        
        public TextLink timeSelector(TeamTimeFrames timeFrame){
            return new TextLink(timeFrame);
        }
    }

    public TeamBar section_timeSelectors_validate() {
        for (TeamTimeFrames selector : EnumSet.allOf(TeamTimeFrames.class)) {
            assertEquals(selector);
        }
        return this;
    }

}
