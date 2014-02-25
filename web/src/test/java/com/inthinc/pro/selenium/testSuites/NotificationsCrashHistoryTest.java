package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformance;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsCrashHistory;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsCrashHistoryAddEdit;
import com.inthinc.pro.selenium.pageObjects.PageNotificationsRedFlags;
import com.inthinc.pro.selenium.pageObjects.PageReportsDrivers;
import com.inthinc.pro.selenium.pageObjects.PageTeamDriverStatistics;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformance;
import com.inthinc.pro.selenium.steps.LoginSteps;

@UsingSteps(instances={LoginSteps.class})
@PageObjects(list={PageLogin.class, PageExecutiveDashboard.class, PageNotificationsCrashHistory.class,
		PageNotificationsCrashHistoryAddEdit.class, PageNotificationsRedFlags.class, PageDriverPerformance.class, 
		PageVehiclePerformance.class, PageReportsDrivers.class, PageTeamDriverStatistics.class})
@StoryPath(path="NotificationsCrashHistory.story")
public class NotificationsCrashHistoryTest extends WebStories {
    
    @Test
    public void test(){}

}