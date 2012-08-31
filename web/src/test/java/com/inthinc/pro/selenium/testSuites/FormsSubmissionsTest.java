package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformance;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveOverallExpansion;
import com.inthinc.pro.selenium.pageObjects.PageFormsAdd;
import com.inthinc.pro.selenium.pageObjects.PageFormsAdmin;
import com.inthinc.pro.selenium.pageObjects.PageFormsSubmissions;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageTeamDriverStatistics;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformance;
import com.inthinc.pro.selenium.steps.LoginSteps;

@UsingSteps(instances={LoginSteps.class})
@PageObjects(list={PageLogin.class, 
		PageExecutiveDashboard.class, PageExecutiveOverallExpansion.class, 
        PageTeamDriverStatistics.class, PageDriverPerformance.class,
        PageVehiclePerformance.class, PageFormsSubmissions.class,
        PageFormsAdd.class, PageFormsAdmin.class })
@StoryPath(path="CurrentStory.story")
public class FormsSubmissionsTest extends WebStories  {

    @Test
    public void test(){}
 
}