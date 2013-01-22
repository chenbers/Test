package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicles;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleDetails;
import com.inthinc.pro.selenium.pageObjects.PageAdminVehicleEdit;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.steps.AdminVehicleSteps;
import com.inthinc.pro.selenium.steps.LoginSteps;

@UsingSteps(instances={LoginSteps.class,AdminVehicleSteps.class})
@PageObjects(list={PageLogin.class, 
        PageAdminVehicles.class,
        PageAdminVehicleDetails.class,
        PageAdminVehicleEdit.class})
@StoryPath(path="AdminVehicle850.story")
public class AdminVehicle850Test extends WebStories  {

    @Test
    public void test(){}
 
}