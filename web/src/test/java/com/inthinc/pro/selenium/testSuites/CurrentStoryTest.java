package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformance;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveOverallExpansion;
import com.inthinc.pro.selenium.pageObjects.PageLiveFleet;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageTeamDriverStatistics;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformance;
import com.inthinc.pro.selenium.steps.LoginSteps;
import com.inthinc.pro.selenium.steps.MapMarkerSteps;

@UsingSteps(instances={LoginSteps.class, MapMarkerSteps.class})
@PageObjects(list={PageLogin.class, PageExecutiveDashboard.class, PageLiveFleet.class,
        PageExecutiveOverallExpansion.class, PageTeamDriverStatistics.class, PageDriverPerformance.class,
        PageVehiclePerformance.class
        })
@StoryPath(path="CurrentStory.story")
public class CurrentStoryTest extends WebStories {
    
    @Test
    public void test(){}

}