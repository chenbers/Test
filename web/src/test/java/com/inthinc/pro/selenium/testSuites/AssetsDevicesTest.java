package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageAssetsDevices;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveOverallExpansion;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.steps.LoginSteps;


@UsingSteps(instances={LoginSteps.class})
@PageObjects(list={PageLogin.class,
        PageAssetsDevices.class,
		PageExecutiveDashboard.class,
        PageExecutiveOverallExpansion.class})
@StoryPath(path="AssetsDevices.story")
public class AssetsDevicesTest extends WebStories {
    
    @Test
    public void test(){}

}
