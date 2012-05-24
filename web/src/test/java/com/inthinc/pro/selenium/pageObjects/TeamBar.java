package com.inthinc.pro.selenium.pageObjects;

import java.util.EnumSet;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextObject;
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
    
        public Text whatHappened() {
            return new Text(TeamBarEnum.SUB_TITLE);
        }
    
    }
    
    public class TeamBarLinks extends NavigationBarLinks{
        
        public TextLink duration(TeamTimeFrames timeFrame){
            return new TextLink(timeFrame);
        }
        
        public TextLink driverStatistics() {
            return new TextLink(TeamBarEnum.DRIVER_STATISTICS);
        }
        
        public TextLink vehicleStatistics() {
            return new TextLink(TeamBarEnum.VEHICLE_STATISTICS);
        }
        
        public TextLink trips() {
            return new TextLink(TeamBarEnum.TRIPS);
        }
        
        public TextLink stops() {
            return new TextLink(TeamBarEnum.STOPS);
        }
        
        public TextLink liveTeam() {
            return new TextLink(TeamBarEnum.LIVE_TEAM);
        }
        
        public TextLink overallScore() {
            return new TextLink(TeamBarEnum.OVERALL_SCORE);
        }
        
        public TextLink drivingStyle() {
            return new TextLink(TeamBarEnum.DRIVING_STYLE);
        }
        
        public TextLink speed() {
            return new TextLink(TeamBarEnum.SPEED);
        }
        
    }

    public TeamBar duration_validate() {
        for (TeamTimeFrames selector : EnumSet.allOf(TeamTimeFrames.class)) {
            TextObject object = new TextObject(selector);
            object.validate(selector);
        }
        return this;
    }

}
