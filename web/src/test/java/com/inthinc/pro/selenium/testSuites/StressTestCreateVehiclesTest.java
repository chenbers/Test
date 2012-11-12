package com.inthinc.pro.selenium.testSuites;
//Right now this test is only set up for dev, so you must change automation.properties silo and users to dev
import org.jbehave.core.annotations.UsingSteps;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageAdminAccountDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminAddEditUser;
import com.inthinc.pro.selenium.pageObjects.PageAdminAddEditVehicle;
import com.inthinc.pro.selenium.pageObjects.PageAdminUsers;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicles;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveOverallExpansion;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageTeamDriverStatistics;
import com.inthinc.pro.selenium.steps.LoginSteps;

@UsingSteps(instances={LoginSteps.class})
@PageObjects(list={PageLogin.class, PageExecutiveDashboard.class, PageExecutiveOverallExpansion.class,
		PageAdminAddEditUser.class, PageAdminAddEditVehicle.class, PageAdminVehicles.class, PageAdminVehicleDetails.class, 
		PageAdminUsers.class, PageAdminAccountDetails.class, PageTeamDriverStatistics.class })
@StoryPath(path="StressTestCreateVehicles.story")
public class StressTestCreateVehiclesTest extends WebStories {
	@Ignore
    @Test
    public void test(){}

}
