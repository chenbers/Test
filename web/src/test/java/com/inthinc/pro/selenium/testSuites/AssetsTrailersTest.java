package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageAssetsTrailers;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformance;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceSeatBelt;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceSpeed;
import com.inthinc.pro.selenium.pageObjects.PageDriverPerformanceStyle;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveOverallExpansion;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.pageObjects.PageReportsDrivers;
import com.inthinc.pro.selenium.pageObjects.PageReportsTrailers;
import com.inthinc.pro.selenium.pageObjects.PageReportsVehicles;
import com.inthinc.pro.selenium.pageObjects.PageTeamDriverStatistics;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformance;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceSeatBelt;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceSpeed;
import com.inthinc.pro.selenium.pageObjects.PageVehiclePerformanceStyle;
import com.inthinc.pro.selenium.steps.LoginSteps;


@UsingSteps(instances={LoginSteps.class})
@PageObjects(list={PageLogin.class,
        PageAssetsTrailers.class,
		PageExecutiveDashboard.class,
        PageExecutiveOverallExpansion.class})
@StoryPath(path="AssetsTrailers.story")
public class AssetsTrailersTest extends WebStories {
    
    @Test
    public void test(){}

}
