package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformance;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceSeatBelt;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceSpeed;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceStyle;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceTrips;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveOverallExpansion;
import com.inthinc.pro.selenium.pageObjects.PageLiveFleet;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageMyAccount;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsSafety;
import com.inthinc.pro.selenium.pageObjects.PageReportsDevices;
import com.inthinc.pro.selenium.pageObjects.PageReportsDrivers;
import com.inthinc.pro.selenium.pageObjects.PageReportsVehicles;
import com.inthinc.pro.selenium.pageObjects.PageTeamDriverStatistics;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformance;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceSeatBelt;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceSpeed;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceStyle;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceTrips;
import com.inthinc.pro.selenium.steps.LoginSteps;
import com.inthinc.pro.selenium.steps.WebSteps;


@UsingSteps(instances={LoginSteps.class,
		WebSteps.class})
@PageObjects(list={PageLogin.class, 
		PageExecutiveDashboard.class,
		PageAdminUsers.class, 
		PageMyAccount.class, 
		PageLiveFleet.class,
        PageExecutiveOverallExpansion.class, 
        PageReportsDrivers.class, 
        PageNotificationsSafety.class, 
        PageTeamDriverStatistics.class,
        PageReportsDevices.class,
        PageDriverPerformance.class,
        PageDriverPerformanceSpeed.class, 
        PageDriverPerformanceSeatBelt.class, 
        PageDriverPerformanceStyle.class, 
        PageDriverPerformanceTrips.class,
        PageVehiclePerformanceSeatBelt.class, 
        PageVehiclePerformanceSpeed.class, 
        PageVehiclePerformanceStyle.class, 
        PageVehiclePerformanceTrips.class, 
        PageVehiclePerformance.class,
        PageReportsVehicles.class,
        })
@StoryPath(path="ReportsDriver.story")
public class ReportsDriverTest extends WebStories {
    
    @Test
    public void test(){}

}