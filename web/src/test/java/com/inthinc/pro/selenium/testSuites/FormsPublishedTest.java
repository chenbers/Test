package com.inthinc.pro.selenium.testSuites;

import org.jbehave.core.annotations.UsingSteps;
import org.junit.Test;

import com.inthinc.pro.automation.annotations.AutomationAnnotations.PageObjects;
import com.inthinc.pro.automation.annotations.AutomationAnnotations.StoryPath;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveDashboard;
import com.inthinc.pro.selenium.pageObjects.PageExecutiveOverallExpansion;
import com.inthinc.pro.selenium.pageObjects.PageFormsAdd;
import com.inthinc.pro.selenium.pageObjects.PageFormsEdit;
import com.inthinc.pro.selenium.pageObjects.PageFormsManage;
import com.inthinc.pro.selenium.pageObjects.PageFormsPublished;
import com.inthinc.pro.selenium.pageObjects.PageLogin;
import com.inthinc.pro.selenium.steps.FormsGenerationSteps;
import com.inthinc.pro.selenium.steps.LoginSteps;

@UsingSteps(instances={LoginSteps.class, FormsGenerationSteps.class})
@PageObjects(list={PageLogin.class, PageFormsPublished.class,
		PageExecutiveDashboard.class, PageExecutiveOverallExpansion.class, 
		PageFormsManage.class, PageFormsAdd.class, PageFormsEdit.class })
@StoryPath(path="FormsPublished.story")
public class FormsPublishedTest extends WebStories  {

    @Test
    public void test(){}
 
}
