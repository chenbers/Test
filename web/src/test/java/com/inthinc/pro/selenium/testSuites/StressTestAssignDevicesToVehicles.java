package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageAdminAccountDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminAddEditUser;
import com.inthinc.pro.selenium.pageObjects.PageAdminAddEditVehicle;
import com.inthinc.pro.selenium.pageObjects.PageAdminDevices;
import com.inthinc.pro.selenium.pageObjects.PageAdminDevicesDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminEditDevice;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicles;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveOverallExpansion;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageTeamDriverStatistics;
import com.inthinc.pro.selenium.pageObjects.PageUtil;
import com.inthinc.pro.selenium.steps.LoginSteps;

@UsingSteps(instances={LoginSteps.class})
@PageObjects(list={PageLogin.class, PageExecutiveDashboard.class, PageExecutiveOverallExpansion.class, PageAdminDevices.class,
		PageAdminDevicesDetails.class, PageAdminEditDevice.class, PageAdminAddEditUser.class, PageAdminAddEditVehicle.class, 
		PageAdminVehicles.class, PageAdminVehicleDetails.class, PageAdminUsers.class, PageAdminAccountDetails.class, 
		PageTeamDriverStatistics.class  })
@StoryPath(path="StressTestAssignDevicesToVehicles.story")

public class StressTestAssignDevicesToVehicles extends WebStories {
	@Ignore
    @Test
    public void test(){}
	
}